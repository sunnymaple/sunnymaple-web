package cn.sunnymaple.web.error.me.handlers;

import cn.sunnymaple.web.error.me.Argument;
import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.sunnymaple.web.error.ErrorCode.*;
import static cn.sunnymaple.web.error.me.Argument.arg;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toSet;

/**
 * A {@link WebErrorHandler} implementation responsible for handling common Spring MVC
 * specific exceptions.
 *
 * @author Ali Dehghani
 */
public class ServletWebErrorHandler implements WebErrorHandler {

    /**
     * When we couldn't process the request body.
     */
    public static final String INVALID_OR_MISSING_BODY = "web.invalid_or_missing_body";

    /**
     * When the {@code Accept} header is invalid.
     */
    public static final String NOT_ACCEPTABLE = "web.not_acceptable";

    /**
     * When the {@code Content-Type} header is not supported.
     */
    public static final String NOT_SUPPORTED = "web.unsupported_media_type";

    /**
     * The HTTP method not supported on the resource.
     */
    public static final String METHOD_NOT_ALLOWED = "web.method_not_allowed";

    /**
     * When a required request parameter is missing.
     */
    public static final String MISSING_PARAMETER = "web.missing_parameter";

    /**
     * When a required request part is missing.
     */
    public static final String MISSING_PART = "web.missing_part";

    /**
     * When we couldn't find any controller to handle the request.
     */
    public static final String NO_HANDLER = "web.no_handler";

    /**
     * Only handles {@link ServletException}s and {@link HttpMessageNotReadableException}s.
     *
     * @param exception The exception to examine.
     * @return {@code true} when can handle the {@code exception}, {@code false} otherwise.
     */
    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof HttpMediaTypeNotAcceptableException ||
            exception instanceof HttpMediaTypeNotSupportedException ||
            exception instanceof HttpRequestMethodNotSupportedException ||
            exception instanceof MissingServletRequestParameterException ||
            exception instanceof MissingServletRequestPartException ||
            exception instanceof NoHandlerFoundException ||
            exception instanceof HttpMessageNotReadableException;
    }

    /**
     * Bunch of if-else-es to return an appropriate {@link HandledException} based on the
     * nature of the given {@code exception}.
     *
     * <p>I've never wanted Switch Expressions and Pattern Matching more!</p>
     *
     * @param exception The exception to handle.
     * @return The corresponding {@link HandledException} to the given {@code exception}.
     */
    @NonNull
    @Override
    public HandledException handle(Throwable exception) {
        if (exception instanceof HttpMessageNotReadableException)
            return new HandledException(A0427, HttpStatus.BAD_REQUEST,INVALID_OR_MISSING_BODY, null);

        if (exception instanceof HttpMediaTypeNotAcceptableException) {
            Set<String> types = getMediaTypes(((HttpMediaTypeNotAcceptableException) exception).getSupportedMediaTypes());
            Map<String, List<Argument>> args = types.isEmpty() ?
                emptyMap() : singletonMap(NOT_ACCEPTABLE, singletonList(arg("types", types)));

            return new HandledException(A0402, HttpStatus.NOT_ACCEPTABLE,NOT_ACCEPTABLE, args);
        }

        if (exception instanceof HttpMediaTypeNotSupportedException) {
            MediaType contentType = ((HttpMediaTypeNotSupportedException) exception).getContentType();

            List<Argument> arguments = null;
            if (contentType != null) arguments = singletonList(arg("type", contentType.toString()));
            return new HandledException(A0402, HttpStatus.UNSUPPORTED_MEDIA_TYPE,NOT_SUPPORTED,
                arguments == null ? emptyMap() : singletonMap(NOT_SUPPORTED, arguments));
        }

        if (exception instanceof HttpRequestMethodNotSupportedException) {
            String method = ((HttpRequestMethodNotSupportedException) exception).getMethod();

            return new HandledException(A0402, HttpStatus.METHOD_NOT_ALLOWED,METHOD_NOT_ALLOWED,
                singletonMap(METHOD_NOT_ALLOWED, singletonList(arg("method", method)))
            );
        }

        if (exception instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException actualException = (MissingServletRequestParameterException) exception;
            String name = actualException.getParameterName();
            String type = actualException.getParameterType();

            return new HandledException(A0402, HttpStatus.BAD_REQUEST,MISSING_PARAMETER,
                singletonMap(MISSING_PARAMETER, asList(arg("name", name), arg("expected", type)))
            );
        }

        if (exception instanceof MissingServletRequestPartException) {
            String name = ((MissingServletRequestPartException) exception).getRequestPartName();

            return new HandledException(A0402, HttpStatus.BAD_REQUEST,MISSING_PART,
                singletonMap(MISSING_PART, singletonList(arg("name", name)))
            );
        }

        if (exception instanceof NoHandlerFoundException) {
            String url = ((NoHandlerFoundException) exception).getRequestURL();

            return new HandledException(A0402, HttpStatus.NOT_FOUND,NO_HANDLER,
                singletonMap(NO_HANDLER, singletonList(arg("path", url)))
            );
        }

        return new HandledException(B0001, HttpStatus.INTERNAL_SERVER_ERROR,LastResortWebErrorHandler.UNKNOWN_ERROR_CODE, null);
    }

    private Set<String> getMediaTypes(List<MediaType> mediaTypes) {
        if (mediaTypes == null) return emptySet();

        return mediaTypes.stream().map(MediaType::toString).collect(toSet());
    }
}
