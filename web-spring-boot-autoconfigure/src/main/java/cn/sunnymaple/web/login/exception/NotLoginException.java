package cn.sunnymaple.web.login.exception;


import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;

import static cn.sunnymaple.web.error.ErrorCode.A0230;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * 未登录或者登录超时异常
 * @author wangzb
 * @date 2020/1/10 10:46
 */
@ExceptionMapping(statusCode = UNAUTHORIZED,errorCode = A0230, userTip = NotLoginException.LOGIN_UNAUTHORIZED)
public class NotLoginException extends RuntimeException {
    /**
     * 未登录或者登陆超时 userTip
     */
    public static final String LOGIN_UNAUTHORIZED = "login.un_authorized";
}
