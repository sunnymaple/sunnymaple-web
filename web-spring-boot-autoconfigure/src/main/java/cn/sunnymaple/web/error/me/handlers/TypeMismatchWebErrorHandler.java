package cn.sunnymaple.web.error.me.handlers;

import cn.sunnymaple.web.error.me.Argument;
import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.springframework.beans.TypeMismatchException;
import org.springframework.lang.NonNull;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static cn.sunnymaple.web.error.ErrorCode.A0421;
import static cn.sunnymaple.web.error.me.Argument.arg;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * A {@link WebErrorHandler} implementation responsible for handling {@link TypeMismatchException}s.
 *
 * @author Mona Mohamadinia
 */
public class TypeMismatchWebErrorHandler implements WebErrorHandler {

    /**
     * Basic error code for all type mismatches.
     */
    public static final String TYPE_MISMATCH = "binding.type_mismatch";

    /**
     * Only can handle exceptions of type {@link TypeMismatchException}.
     *
     * @param exception The exception to examine.
     * @return {@code true} if the {@code exception} is {@link TypeMismatchException}, {@code false} otherwise.
     */
    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof TypeMismatchException;
    }

    /**
     * Cast the given exception to {@link TypeMismatchException} and return a handled exception
     * instance by extracting the error code and exposing appropriate arguments.
     *
     * @param exception The exception to handle.
     * @return The handled exception instance.
     */
    @NonNull
    @Override
    public HandledException handle(Throwable exception) {
        TypeMismatchException mismatchException = (TypeMismatchException) exception;
//        String errorCode = getErrorCode(mismatchException);
        List<Argument> arguments = getArguments(mismatchException);

        return new HandledException(A0421, BAD_REQUEST,TYPE_MISMATCH, singletonMap(TYPE_MISMATCH, arguments));
    }

    static List<Argument> getArguments(TypeMismatchException mismatchException) {
        List<Argument> arguments = new ArrayList<>();
        arguments.add(arg("property", getPropertyName(mismatchException)));
        arguments.add(arg("invalid", mismatchException.getValue()));
        if (mismatchException.getRequiredType() != null) {
            arguments.add(arg("expected", mismatchException.getRequiredType().getSimpleName()));
        }

        return arguments;
    }

    static String getErrorCode(TypeMismatchException mismatchException) {
        return TYPE_MISMATCH + "." + getPropertyName(mismatchException);
    }

    private static String getPropertyName(TypeMismatchException mismatchException) {
        if (mismatchException instanceof MethodArgumentTypeMismatchException)
            return ((MethodArgumentTypeMismatchException) mismatchException).getName();

        return mismatchException.getPropertyName();
    }


}
