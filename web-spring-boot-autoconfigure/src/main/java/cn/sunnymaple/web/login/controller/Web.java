package cn.sunnymaple.web.login.controller;

import java.lang.annotation.*;

/**
 * 登录接口web模式定义
 * @author wangzb
 * @date 2020/3/24 17:37
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Web {
    /**
     * web模式定义
     * {@link WebPattern}
     * @return
     */
    WebPattern value();
}
