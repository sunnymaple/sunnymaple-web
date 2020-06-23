package cn.sunnymaple.web.login.interceptor;

import java.lang.annotation.*;

/**
 * 登录操作注解
 * 可以定义拦截器
 * @author wangzb
 * @date 2020/3/23 14:39
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Login {
    /**
     * 定义登录操作后置拦截器
     * @return
     */
    Class<? extends ILoginPostInterceptor>[] loginPostInterceptor() default {};
}
