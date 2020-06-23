package cn.sunnymaple.web.login.controller;

/**
 * 常量
 * @author wangzb
 * @date 2020/3/24 16:42
 */
public class LoginConstant {
    /**
     * 登录页面的接口URL
     * 页面版：登录页面请求接口
     * api版：登录拦截后调用该接口作提示
     */
    public static final String DEFAULT_LOGIN_VIEW_URL = "/";

    /**
     * 默认的登录的html资源路径，即视图路径
     * 注意是resources下的/templates/login/login.html
     */
    public static final String DEFAULT_LOGIN_VIEW_NAME = "login/login";

    /**
     * 用户登录接口
     */
    public static final String DEFAULT_LOGIN_URL = "/login";

    /**
     * 被登录拦截时的请求接口
     */
    public static final String DEFAULT_NOT_LOGIN_URL = "/notLogin";

    /**
     * 退出登录的接口URL
     */
    public static final String DEFAULT_LOGOUT_URL = "/login/logout";


}
