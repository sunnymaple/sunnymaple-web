package cn.sunnymaple.web.error.me.adapter.attributes;

import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;

import java.util.Map;

import static cn.sunnymaple.web.error.ErrorCode.A0230;
import static cn.sunnymaple.web.error.ErrorCode.A0312;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

/**
 * 默认的特殊异常处理容器
 * @author wangzb
 * @date 2020/6/23 15:36
 */
public class DefaultRefineUnknownExceptionContainer implements IRefineUnknownExceptionContainer{


    @ExceptionMapping(statusCode = UNAUTHORIZED, errorCode = A0230,userTip = "login.un_authorized")
    private static final class UnauthorizedException extends RuntimeException {
    }

    @ExceptionMapping(statusCode = FORBIDDEN, errorCode = A0312,userTip = "forbidden.error")
    private static final class ForbiddenException extends RuntimeException {
    }

    /**
     * 给定一组经典的错误属性，它将从状态代码中确定要处理的异常
     *
     * @param attributes 表示错误属性的键-值对
     * @return 映射的异常
     */
    @Override
    public Exception refineUnknownException(Map<String, Object> attributes) {
        switch (getStatusCode(attributes)) {
            case 401:
                return new UnauthorizedException();
            case 403:
                return new ForbiddenException();
            case 404:
                return new HandlerNotFoundException(getPath(attributes));
            default:
                return new IllegalStateException("The exception is null: " + attributes);
        }
    }
}
