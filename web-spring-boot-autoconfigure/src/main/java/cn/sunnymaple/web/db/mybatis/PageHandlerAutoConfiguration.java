package cn.sunnymaple.web.db.mybatis;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * 分页自动配置类
 * @author wangzb
 * @date 2019/12/11 22:29
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(PageHandlerProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@ConditionalOnClass(MybatisAutoConfiguration.class)
@Import({PageHandlerAspect.class,
        ParamIndexPageHandlerParamResolver.class,
        RequestRegionPageHandlerParamResolver.class,
        SpElPageHandlerParamResolver.class,
        PageHandlerRestResultHandler.class})
public class PageHandlerAutoConfiguration {

    /**
     * mysql的SqlSessionFactory
     */
    @Autowired
    private List<SqlSessionFactory> sqlSessionFactories;
    /**
     * 配置属性类
     */
    @Autowired
    private PageHandlerProperties pageHandlerProperties;

    @PostConstruct
    public void addPageInterceptor() {
        PageInterceptor interceptor = new PageInterceptor();
        Properties properties = pageHandlerProperties.getProperties();
        //判断是否有指定方言，没有则默认指定为mysql
        for (String helperDialect : PageHandlerConstant.HELPER_DIALECT){
            if (StrUtil.isEmpty(properties.getProperty(helperDialect))){
                properties.setProperty(helperDialect, PageHandlerConstant.DEFAULT_DIALECT);
            }
        }

        //在把特殊配置放进去，由于close-conn 利用上面方式时，属性名就是 close-conn 而不是 closeConn，所以需要额外的一步
        interceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
