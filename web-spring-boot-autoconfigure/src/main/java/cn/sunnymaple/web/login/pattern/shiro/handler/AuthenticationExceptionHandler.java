package cn.sunnymaple.web.login.pattern.shiro.handler;

import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static cn.sunnymaple.web.error.ErrorCode.A0200;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * shiro登录认证异常{@link AuthenticationException}处理器
 * @author wangzb
 * @date 2020/3/26 15:06
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthenticationExceptionHandler implements WebErrorHandler {

    /**
     * shiro验证异常处理类集合
     */

    private List<WebErrorHandler> handlers = Arrays.asList(new UnsupportedTokenExceptionHandler(),

            new ExpiredCredentialsExceptionHandler(),
            new IncorrectCredentialsExceptionHandler(),
            new CredentialsExceptionHandler(),

            new UnknownAccountExceptionHandler(),
            new LockedAccountExceptionHandler(),
            new ExcessiveAttemptsExceptionHandler(),
            new DisabledAccountExceptionHandler(),
            new ConcurrentAccessExceptionHandler(),
            new AccountExceptionHandler());

    /**
     * 添加外部异常处理类
     * @param webErrorHandlers
     */
    public void add(WebErrorHandler... webErrorHandlers){
        handlers.addAll(Arrays.asList(webErrorHandlers));
    }

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
        return exception instanceof AuthenticationException;
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
        Optional<WebErrorHandler> first = handlers.stream()
                .filter(webErrorHandler -> webErrorHandler.canHandle(exception)).findFirst();
        if (first.isPresent()){
            return first.get().handle(exception);
        }
        return new HandledException(A0200, BAD_REQUEST,"login.pwdUserName_error");
    }
}
