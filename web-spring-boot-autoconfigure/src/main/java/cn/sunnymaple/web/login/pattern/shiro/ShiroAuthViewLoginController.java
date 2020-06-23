package cn.sunnymaple.web.login.pattern.shiro;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.login.controller.*;
import cn.sunnymaple.web.login.exception.NotLoginException;
import cn.sunnymaple.web.login.interceptor.Login;
import cn.sunnymaple.web.error.me.HttpError;
import cn.sunnymaple.web.error.me.WebErrorHandlers;
import cn.sunnymaple.web.login.config.LoginProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

/**
 * 使用shiro验证登录和权限控制时的默认登录接口
 * web模式为{@link WebPattern#VIEW}
 * @author wangzb
 * @date 2020/3/24 17:47
 */
@Controller
@RequestMapping("/")
@Web(WebPattern.VIEW)
@Validated
@Slf4j
@Api(value = "登录模块，只关注登录接口，其他接口请忽略",tags = {"登录模块，只关注登录接口，其他接口请忽略"})
public class ShiroAuthViewLoginController implements ILoginViewController {

    private LoginProperties loginProperties;

    private WebErrorHandlers webErrorHandlers;

    @Autowired
    private HttpServletRequest request;

    public ShiroAuthViewLoginController(LoginProperties loginProperties, WebErrorHandlers webErrorHandlers) {
        this.loginProperties = loginProperties;
        this.webErrorHandlers = webErrorHandlers;
    }

    /**
     * 登录页面，可以使用项目根路径
     * 直接访问登录页
     *
     * @param url      登录成功后跳转的页面
     * @param modelMap 模型{@link LoginParam}
     * @return  登录页面的视图
     */
    @Override
    @RequestMapping(value="${login.login-view-url:" + LoginConstant.DEFAULT_LOGIN_VIEW_URL + "}",produces = "text/html")
    @ApiOperation(value = "登录页，只有VIEW模式下成立", notes = "用户登录，只有VIEW模式下成立")
    public String loginView(String url, ModelMap modelMap) {
        if (StrUtil.isNotBlank(url)){
            modelMap.put("url",url);
        }
        String loginViewName = loginProperties.getLoginViewName();
        return StrUtil.blankToDefault(loginViewName,LoginConstant.DEFAULT_LOGIN_VIEW_NAME);
    }

    /**
     * 登录
     *
     * @param LoginParam  登录参数 {@link LoginParam}
     * @param modelMap 模型
     * @return 视图
     */
    @Override
    @RequestMapping(value = "${login.login-url:" + LoginConstant.DEFAULT_LOGIN_URL + "}",produces = "text/html")
    @Login
    @ApiOperation(value = "登录接口", notes = "用户登录")
    public String login(@Valid LoginParam LoginParam, ModelMap modelMap) {
        //是否记住我
        Integer rememberMeInt = LoginParam.getRememberMe();
        boolean rememberMe = (rememberMeInt!=null && rememberMeInt == 1) ? true : false;
        try {
            ShiroUtils.login(LoginParam.getUserName(),LoginParam.getPassword(),rememberMe);
            String successUrl = StrUtil.blankToDefault(LoginParam.getLoginSuccessPath(),loginProperties.getSuccessUrl());
            if (!StrUtil.isBlank(successUrl)){
                return "redirect:" + successUrl;
            }
            return loginProperties.getSuccessViewName();
        }catch (AuthenticationException e){
            log.warn(e.getClass().getName() + "：" + e.getMessage());
            HttpError httpError = webErrorHandlers.handle(e, request, Locale.getDefault());
            //登录失败，回到登录页
            modelMap.put("message",httpError.getErrors().stream().findFirst().get().getMessage());
            modelMap.put("userName",LoginParam.getUserName());
            modelMap.put("password",LoginParam.getPassword());
            return loginView(null,modelMap);
        }
    }


    /**
     * 被登录拦截时调用的接口
     *
     * @param modelMap 模型
     */
    @Override
    @RequestMapping(value = "${login.not-login-url:" + LoginConstant.DEFAULT_NOT_LOGIN_URL + "}",produces = "text/html")
    public String notLogin(ModelMap modelMap) {
        return StrUtil.blankToDefault(loginProperties.getNotLoginViewName(),loginView(null,modelMap));
    }

    /**
     * <p>ResponseBody请求时未登录接口</p>
     * <p>如ajax请求等</p>
     * <p>前端可以使用全局的登录超时处理：</p>
     * <p>$.ajaxSetup({</p>
     * <p>  statusCode: {</p>
     * <p>     401: function () {</p>
     * <p>         layPop.error("未登录或者登录超时",function () {</p>
     * <p>             window.open (path,'_top');</p>
     * <p>         });</p>
     * <p>     }</p>
     * <p>  }</p>
     * <p>});</p>
     * @throws NotLoginException
     */
    @Override
    @RequestMapping(value = "${login.not-login-url:" + LoginConstant.DEFAULT_NOT_LOGIN_URL + "}",produces = "application/json")
    @ResponseBody
    public void notLogin() throws NotLoginException{
        throw new NotLoginException();
    }


    /**
     * 退出登录，一般作删除session或者token删除操作
     *
     * @param modelMap 模型{@link LoginParam}
     * @return视图，一般为登录页面
     */
    @Override
    @GetMapping(value = "${login.logout-url:" + LoginConstant.DEFAULT_LOGOUT_URL + "}",produces = "text/html")
    public String logout(ModelMap modelMap) {
        return notLogin(modelMap);
    }
}
