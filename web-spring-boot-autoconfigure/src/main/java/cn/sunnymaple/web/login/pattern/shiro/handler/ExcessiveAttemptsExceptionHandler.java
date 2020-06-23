package cn.sunnymaple.web.login.pattern.shiro.handler;

import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import static cn.sunnymaple.web.error.ErrorCode.A0241;

/**
 * {@link ExcessiveAttemptsException} 操作过去频繁
 * @author wangzb
 * @date 2020/4/3 11:01
 */
public class ExcessiveAttemptsExceptionHandler implements WebErrorHandler {
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
        return exception instanceof ExcessiveAttemptsException;
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
        return new HandledException(A0241, HttpStatus.BAD_REQUEST,"login.excessive_attempts");
    }
}
