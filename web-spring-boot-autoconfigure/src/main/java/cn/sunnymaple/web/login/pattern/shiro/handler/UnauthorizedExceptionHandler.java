package cn.sunnymaple.web.login.pattern.shiro.handler;

import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static cn.sunnymaple.web.error.ErrorCode.A0312;

/**
 * 权限验证异常
 * {@link SecurityUtils#getSubject()#isPermitted()} 或者接口方法上使用{@link RequiresPermissions}注解时抛出的异常
 * @author wangzb
 * @date 2020/4/23 11:34
 */
@Component
public class UnauthorizedExceptionHandler implements WebErrorHandler {
    /**
     * Determines whether this particular implementation can handle the given exception
     * or not.
     *
     * @param exception The exception to examine.
     * @return {@code true} if this implementation can handle the exception, {@code false}
     * otherwise.
     */
    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof UnauthorizedException;
    }

    /**
     * Handles the given exception and returns an instance of {@link HandledException}.
     * This method should be called iff the call to {@link #canHandle(Throwable)} for
     * the same exception returns {@code true}.
     *
     * @param exception The exception to handle.
     * @return A set of error codes.
     */
    @Override
    public HandledException handle(Throwable exception) {
        return new HandledException(A0312, HttpStatus.FORBIDDEN,"forbidden.error");
    }
}
