package cn.sunnymaple.web.error.me.adapter.attributes;

import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;
import cn.sunnymaple.web.error.me.annotation.ExposeAsArg;
import cn.sunnymaple.web.error.me.handlers.ServletWebErrorHandler;

import java.util.Map;

import static cn.sunnymaple.web.error.ErrorCode.A0404;
//import static com.seagetech.web.error.me.handlers.SpringSecurityWebErrorHandler.ACCESS_DENIED;
//import static com.seagetech.web.error.me.handlers.SpringSecurityWebErrorHandler.AUTH_REQUIRED;
import static org.springframework.http.HttpStatus.*;

/**
 * A simple container of a few peculiar exceptions.
 *
 * @author Ali Dehghani
 */
class Exceptions {

    /**
     * Given a classic set of error attributes, it will determines the to-be-handled
     * exception from the status code.
     *
     * @param attributes Key-value pairs representing the error attributes.
     * @return The mapped exception.
     */
    static Exception refineUnknownException(Map<String, Object> attributes) {
        switch (getStatusCode(attributes)) {
            case 401:
//                return new UnauthorizedException();
            case 403:
//                return new ForbiddenException();
            case 404:
                return new HandlerNotFoundException(getPath(attributes));
            default:
                return new IllegalStateException("The exception is null: " + attributes);
        }
    }

    private static String getPath(Map<String, Object> attributes) {
        Object path = attributes.get("path");
        return path == null ? "unknown" : path.toString();
    }

    /**
     * Extracts the status code from error attributes.
     *
     * @param attributes The error attributes.
     * @return Extracted status code.
     */
    private static int getStatusCode(Map<String, Object> attributes) {
        try {
            return (Integer) attributes.getOrDefault("status", 0);
        } catch (Exception e) {
            return 0;
        }
    }

//    @ExceptionMapping(statusCode = UNAUTHORIZED, errorCode = AUTH_REQUIRED)
//    private static final class UnauthorizedException extends RuntimeException {
//    }
//
//    @ExceptionMapping(statusCode = FORBIDDEN, errorCode = ACCESS_DENIED)
//    private static final class ForbiddenException extends RuntimeException {
//    }

    @ExceptionMapping(statusCode = NOT_FOUND, userTip = ServletWebErrorHandler.NO_HANDLER,errorCode = A0404)
    private static final class HandlerNotFoundException extends RuntimeException {

        /**
         * The to-be-exposed path.
         */
        @ExposeAsArg(0)
        private final String path;

        private HandlerNotFoundException(String path) {
            this.path = path;
        }
    }
}
