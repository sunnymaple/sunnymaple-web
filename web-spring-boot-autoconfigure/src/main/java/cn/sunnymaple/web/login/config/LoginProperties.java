package cn.sunnymaple.web.login.config;

import cn.sunnymaple.web.login.controller.LoginConstant;
import cn.sunnymaple.web.login.controller.WebPattern;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 登录模块相关配置
 * @author wangzb
 * @date 2020/3/25 14:49
 * @company 矽甲（上海）信息科技有限公司
 */
@Data
@ConfigurationProperties(prefix = "login")
public class LoginProperties {

    /**
     * web模式定义
     * {@link WebPattern}
     */
    private String webPattern;

    /***
     * 定义不被拦截的接口
     */
    private String[] anon;
    /**
     * 登录拦截的接口
     */
    private String[] authc;

    /**
     * 登录页面接口URL
     * 页面版：登录页面请求接口
     * api版：登录拦截后调用该接口作提示
     */
    private String loginViewUrl = LoginConstant.DEFAULT_LOGIN_VIEW_URL;
    /**
     * 登录页面的视图，resources文件下的资源文件，即通常为去掉.html后缀的HTML文件路径
     */
    private String loginViewName = LoginConstant.DEFAULT_LOGIN_VIEW_NAME;

    /**
     * 用户登录接口，如果是web模式为VIEW,登录成功后，将重定向到successPath或者返回指定successViewName的视图
     */
    private String loginUrl = LoginConstant.DEFAULT_LOGIN_URL;
    /**
     * 登录成功重定向的路径，通常为项目内的URL地址，如:/success
     */
    private String successUrl;
    /**
     * 登录成功后的视图
     */
    private String successViewName;

    /**
     * 被登录后拦截的接口
     */
    private String notLoginUrl = LoginConstant.DEFAULT_NOT_LOGIN_URL;

    /**
     * 被登录拦截后请求的视图
     */
    private String notLoginViewName;


    /**
     * 没有权限的是跳转的URL
     */
    private String unauthorizedUrl;


    /**
     * 退出登录的接口URL
     * 使用shiro时，实际退出登录接口固定为/logout，退出后shiro将会重定向到该接口
     */
    private String logoutUrl = LoginConstant.DEFAULT_LOGOUT_URL;
}
