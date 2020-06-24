package cn.sunnymaple.web.login.pattern.shiro;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.error.me.adapter.attributes.IRefineUnknownExceptionContainer;
import cn.sunnymaple.web.login.config.LoginAutoConfiguration;
import cn.sunnymaple.web.login.config.LoginProperties;
import cn.sunnymaple.web.login.config.Patten;
import cn.sunnymaple.web.login.controller.ILoginApiController;
import cn.sunnymaple.web.login.controller.ILoginViewController;
import cn.sunnymaple.web.login.controller.LoginConstant;
import cn.sunnymaple.web.login.controller.WebPattern;
import cn.sunnymaple.web.error.me.WebErrorHandlers;
import cn.sunnymaple.web.login.pattern.ConditionalOnWebPattern;
import cn.sunnymaple.web.login.pattern.IPrincipalHandler;
import cn.sunnymaple.web.login.pattern.shiro.handler.ShiroRefineUnknownExceptionContainer;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ui.ModelMap;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.*;

/**
 * 使用shiro作为登录和权限验证时的自动配置类
 * @author wangzb
 * @date 2020/3/24 14:26
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(SecurityManager.class)
@ConditionalOnBean(IPermission.class)
@EnableConfigurationProperties(ShiroAuthProperties.class)
@AutoConfigureAfter(LoginAutoConfiguration.class)
@Import(ShiroAuthPatternImportBeanDefinitionRegistrar.class)
public class ShiroAuthLoginAutoConfiguration {

    /**
     * 不被拦截的anon
     */
    private static final String ANON = "anon";
    /**
     * 登录拦截authc
     */
    private static final String AUTHC = "authc";

    @Autowired
    private ShiroAuthProperties shiroAuthProperties;

    @Autowired
    private LoginProperties loginProperties;

    @PostConstruct
    public void postConstruct(){
        //开启并初始化缓存
        if (shiroAuthProperties == null){
            AuthorizationInfoCache.getInstance();
        }else {
            AuthorizationInfoCache.getInstance(shiroAuthProperties.getCacheCapacity(),shiroAuthProperties.getCacheTimeout());
        }
    }


    @Bean
    @ConditionalOnMissingBean(IPrincipalHandler.class)
    public IPrincipalHandler principalHandler(){
        return new DefaultShiroAuthPrincipalHandler();
    }

    /**
     * 注册API模式下的登录Controller
     * @return
     */
    @Bean
    @ConditionalOnWebPattern(WebPattern.API)
    public ShiroAuthApiLoginController shiroAuthApiLoginController(){
        return new ShiroAuthApiLoginController();
    }

    /**
     * 初始化VIEW模式下的登录Controller类
     * @param webErrorHandlers
     * @return
     */
    @Bean
    @ConditionalOnWebPattern(WebPattern.VIEW)
    public ShiroAuthViewLoginController shiroAuthViewLoginController(WebErrorHandlers webErrorHandlers){
        return new ShiroAuthViewLoginController(loginProperties,webErrorHandlers);
    }

    /**
     * 定义拦截器,设置shiro的过滤规则
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器。匹配原则是最上面的最优先匹配
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        //过滤器
        Map<String, Filter> filters = new HashMap<>(2);

        //1、配置不会被拦截的链接
        anon(filterChainDefinitionMap);

        //2、需要身份认证的接口
        authc(filterChainDefinitionMap);
        filters.put(AUTHC,new WebFormAuthenticationFilter());

        //3 退出登录
        LogoutFilter logoutFilter = exit(filterChainDefinitionMap);
        filters.put("logout",logoutFilter);

        //4 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        setNotLoginUrl(shiroFilterFactoryBean);
        //5 没有权限跳转的页面
        setUnauthorizedUrl(shiroFilterFactoryBean);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    /**
     * 将Realm转交给SecurityManager
     * @param matcher
     * @return
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher,LoginAuthorizingRealm realm){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        realm.setCredentialsMatcher(matcher);
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 密码匹配凭证管理器
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 采用的加密方式
        String hashAlgorithmName =  ShiroUtils.HASH_ALGORITHM_NAME;
        if (shiroAuthProperties!=null && StrUtil.isNotBlank(shiroAuthProperties.getHashAlgorithmName())){
            hashAlgorithmName = shiroAuthProperties.getHashAlgorithmName();
        }
        hashedCredentialsMatcher.setHashAlgorithmName(hashAlgorithmName);
        // 设置加密次数
        int hashIterations = ShiroUtils.HASH_INTERAYION;
        if (shiroAuthProperties!=null && shiroAuthProperties.getHashIterations()!=null){
            hashIterations = shiroAuthProperties.getHashIterations();
        }
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }


    /**
     * 权限
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }

    /**
     * 特殊异常处理容器
     * @return
     */
    @Bean
    public IRefineUnknownExceptionContainer container(){
        return new ShiroRefineUnknownExceptionContainer();
    }


    /**
     * 添加默认不拦截的路径
     * 包括可能存在的静态资源
     * @param filterChainDefinitionMap
     */
    private void anon(Map<String,String> filterChainDefinitionMap){
        //系统定义的相关接口
        Arrays.stream(Patten.ANON_DEFAULT).forEach(anon->filterChainDefinitionMap.put(anon,ANON));
        //用户定义
        if (loginProperties.getAnon()!=null && loginProperties.getAnon().length>0){
            Arrays.stream(loginProperties.getAnon()).forEach(anon->filterChainDefinitionMap.put(anon,ANON));
        }

    }

    /**
     * 配置登录拦截的接口
     * @param filterChainDefinitionMap
     */
    private void authc(Map<String,String> filterChainDefinitionMap){
        if (loginProperties.getAuthc() !=null && loginProperties.getAuthc().length>0){
            Arrays.stream(loginProperties.getAuthc()).forEach(authc->filterChainDefinitionMap.put(authc,AUTHC));
        }else {
            Arrays.stream(Patten.AUTHC_DEFAULT).forEach(authc->filterChainDefinitionMap.put(authc,AUTHC));
        }
    }


    /**
     * 用户操作了一个他没有权限的功能或者菜单后跳转的页面
     * @param shiroFilterFactoryBean
     */
    private void setUnauthorizedUrl(ShiroFilterFactoryBean shiroFilterFactoryBean){
        if (StrUtil.isNotBlank(loginProperties.getUnauthorizedUrl())){
            shiroFilterFactoryBean.setUnauthorizedUrl(loginProperties.getUnauthorizedUrl());
        }
    }

    /**
     * 设置登录页面的URL地址或者被登录拦截后的操作地址
     * 如果未设置，默认会自动寻找Web工程根目录下的"/login.jsp"页面
     * @param shiroFilterFactoryBean
     */
    private void setNotLoginUrl(ShiroFilterFactoryBean shiroFilterFactoryBean){
        String loginUrl = (loginProperties!=null &&
                StrUtil.isNotBlank(loginProperties.getNotLoginUrl())) ?
                loginProperties.getNotLoginUrl() : LoginConstant.DEFAULT_NOT_LOGIN_URL;
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
    }

    /**
     * 配置退出登录
     * 1、退出登录接口，（URL）,该接口不需要实现，shiro已处理，主要是做session删除操作
     * 2、配置退出登录过滤器，即退出后请求的路径，该路径一般由用户自定义
     *    比如具有PC网页版的项目，一般是退出后跳转到登录页面，参考{@link ILoginViewController#logout(ModelMap)}
     *    如果是api接口，提供第三方应用或者移动端APP的接口，则直接返回类似“退出成功”的操作，参考{@link ILoginApiController#logoutApi()}
     * @return {@link LogoutFilter} 退出登录过滤器
     */
    private LogoutFilter exit(Map<String,String> filterChainDefinitionMap){
        String logout = ShiroUtils.DEFAULT_EXIT_URL;
        filterChainDefinitionMap.put(logout, "logout");

        LogoutFilter logoutFilter = new LogoutFilter();
        String redirectUrl = (loginProperties!=null &&
                StrUtil.isNotBlank(loginProperties.getLogoutUrl())) ?
                loginProperties.getLogoutUrl() : LoginConstant.DEFAULT_LOGOUT_URL;
        logoutFilter.setRedirectUrl(redirectUrl);
        return logoutFilter;
    }
}
