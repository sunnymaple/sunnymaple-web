package cn.sunnymaple.web.login.pattern.shiro.handler;

import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.apache.shiro.authc.AccountException;
import org.springframework.lang.Nullable;

import static cn.sunnymaple.web.error.ErrorCode.A0210;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * {@link AccountException} 账号、用户名错误
 * @author wangzb
 * @date 2020/4/3 10:41
 */
public class AccountExceptionHandler implements WebErrorHandler {

    /**
     * Determines whether this particular implementation can handle the given exception
     * or not.
     *
     * @param exception The exception to examine.
     * @return {@code true} if this implementation can handle the exception, {@code false}
     * otherwise.
     */
    @Override
    public boolean canHandle(@Nullable Throwable exception) {
        return exception instanceof AccountException;
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
    public HandledException handle(@Nullable Throwable exception) {
        return new HandledException(A0210, BAD_REQUEST,"login.account_error");
    }
}
