package cn.sunnymaple.web.login.config;

/**
 * @author wangzb
 * @date 2020/4/30 15:32
 */
public class Patten {
    /**
     * 默认的不会被拦截的链接
     * <p>/login/** 登录页面的相关操作路径</p>
     * <p>/favicon.ico ico小图标，用于页面title</p>
     * <p>/static/**  静态资源文件，
     *   注意，如果是springboot项目，目录/resources/static下的静态资源请求路径是:"/xx.js"，默认是拦截的
     *   只有在目录static下再创建一个static目录，即/resources/static/static目录下的请求路径才是"/static/xx.js"
     * </p>
     * <p>
     *     包括swagger
     * </p>
     */
    public static final String[] ANON_DEFAULT = {"/login/**","/favicon.ico","/static/**","/logout","/",
            "/swagger-ui.html","/swagger-resources/**","/v2/api-docs" ,"/webjars/springfox-swagger-ui/**","/csrf"};

    /**
     * 默认拦截的链接
     */
    public static final String[] AUTHC_DEFAULT = {"/**"};
}
