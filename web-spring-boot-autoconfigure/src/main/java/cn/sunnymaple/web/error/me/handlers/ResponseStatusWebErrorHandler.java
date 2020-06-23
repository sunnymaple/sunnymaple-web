package cn.sunnymaple.web.error.me.handlers;

import cn.sunnymaple.web.error.me.Argument;
import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.sunnymaple.web.error.ErrorCode.*;
import static cn.sunnymaple.web.error.me.Argument.arg;
import static cn.sunnymaple.web.error.me.handlers.ServletWebErrorHandler.METHOD_NOT_ALLOWED;
import static cn.sunnymaple.web.error.me.handlers.ServletWebErrorHandler.NOT_ACCEPTABLE;
import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.*;

/**
 * {@link WebErrorHandler} implementation expert at handling exceptions of type
 * {@link ResponseStatusException}.
 *
 * @author Ali Dehghani
 */
public class ResponseStatusWebErrorHandler implements WebErrorHandler {

    /**
     * To delegate new validations exceptions like {@link WebExchangeBindException} to our old
     * binding result handler.
     */
    private final SpringValidationWebErrorHandler validationWebErrorHandler = new SpringValidationWebErrorHandler();
    private final TypeMismatchWebErrorHandler typeMismatchWebErrorHandler = new TypeMismatchWebErrorHandler();

    /**
     * Only can handle exceptions of type {@link ResponseStatusException}.
     *
     * @param exception The exception to examine.
     * @return {@code true} for {@link ResponseStatusException}s, {@code false} for others.
     */
    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof ResponseStatusException;
    }

    /**
     * Handle each subtype of {@link ResponseStatusException} class in its own unique and appropriate way.
     *
     * @param exception The exception to handle.
     * @return A handled exception.
     */
    @NonNull
    @Override
    public HandledException handle(Throwable exception) {
        if (exception instanceof MediaTypeNotSupportedStatusException) {
            Set<String> types = getMediaTypes(((MediaTypeNotSupportedStatusException) exception).getSupportedMediaTypes());
            Map<String, List<Argument>> args = types.isEmpty() ? emptyMap() : argMap(ServletWebErrorHandler.NOT_SUPPORTED, arg("types", types));
            return new HandledException(A0402, UNSUPPORTED_MEDIA_TYPE, ServletWebErrorHandler.NOT_SUPPORTED, args);
        }

        if (exception instanceof UnsupportedMediaTypeStatusException) {
            Set<String> types = getMediaTypes(((UnsupportedMediaTypeStatusException) exception).getSupportedMediaTypes());
            Map<String, List<Argument>> args = types.isEmpty() ? emptyMap() : argMap(ServletWebErrorHandler.NOT_SUPPORTED, arg("types", types));
            return new HandledException(A0402, UNSUPPORTED_MEDIA_TYPE, ServletWebErrorHandler.NOT_SUPPORTED, args);
        }

        if (exception instanceof NotAcceptableStatusException) {
            Set<String> types = getMediaTypes(((NotAcceptableStatusException) exception).getSupportedMediaTypes());
            Map<String, List<Argument>> args = types.isEmpty() ? emptyMap() : argMap(ServletWebErrorHandler.NOT_ACCEPTABLE, arg("types", types));
            return new HandledException(A0402, NOT_ACCEPTABLE, ServletWebErrorHandler.NOT_ACCEPTABLE, args);
        }

        if (exception instanceof MethodNotAllowedException) {
            String httpMethod = ((MethodNotAllowedException) exception).getHttpMethod();
            return new HandledException(A0402, METHOD_NOT_ALLOWED, ServletWebErrorHandler.METHOD_NOT_ALLOWED, argMap(ServletWebErrorHandler.METHOD_NOT_ALLOWED, arg("method", httpMethod)));
        }

        if (exception instanceof WebExchangeBindException) {
            return validationWebErrorHandler.handle(exception);
        }

        if (exception instanceof ServerWebInputException) {
            MethodParameter parameter = ((ServerWebInputException) exception).getMethodParameter();
            if (exception.getCause() instanceof TypeMismatchException) {
                TypeMismatchException cause = ((TypeMismatchException) exception.getCause());
                if (cause.getPropertyName() == null) cause.initPropertyName(parameter.getParameterName());

                return typeMismatchWebErrorHandler.handle(cause);
            }

            HandledException handledException = handleMissingParameters(parameter);
            if (handledException != null) return handledException;

            return new HandledException(A0427, BAD_REQUEST, ServletWebErrorHandler.INVALID_OR_MISSING_BODY, null);
        }

        if (exception instanceof ResponseStatusException) {
            HttpStatus status = ((ResponseStatusException) exception).getStatus();
            if (status == NOT_FOUND) return new HandledException(ServletWebErrorHandler.NO_HANDLER, status,"", null);

            return new HandledException(B0001, status, LastResortWebErrorHandler.UNKNOWN_ERROR_CODE, null);
        }

        return new HandledException(B0001, INTERNAL_SERVER_ERROR, LastResortWebErrorHandler.UNKNOWN_ERROR_CODE, null);
    }

    /**
     * Spring WebFlux throw just one exception, i.e. {@link WebExchangeBindException} for
     * all request body binding failures, i.e. missing required parameter or missing matrix
     * variables. On the contrary, Traditional web stack throw one specific exception for
     * each scenario. In order to provide a consistent API for both stacks, we chose to
     * throw a bunch of if-else es to determines the actual cause and provide explicit feedback
     * to the client.
     *
     * @param parameter The invalid method parameter.
     * @return Possibly a handled exception.
     */
    private HandledException handleMissingParameters(MethodParameter parameter) {
        if (parameter == null) return null;

        String code = null;
        String parameterName = null;

        RequestHeader requestHeader = parameter.getParameterAnnotation(RequestHeader.class);
        if (requestHeader != null) {
            code = MissingRequestParametersWebErrorHandler.MISSING_HEADER;
            parameterName = extractParameterName(requestHeader, parameter);
        }

        RequestParam requestParam = parameter.getParameterAnnotation(RequestParam.class);
        if (requestParam != null) {
            code = ServletWebErrorHandler.MISSING_PARAMETER;
            parameterName = extractParameterName(requestParam, parameter);
        }

        RequestPart requestPart = parameter.getParameterAnnotation(RequestPart.class);
        if (requestPart != null) {
            code = ServletWebErrorHandler.MISSING_PART;
            parameterName = extractParameterName(requestPart, parameter);
        }

        CookieValue cookieValue = parameter.getParameterAnnotation(CookieValue.class);
        if (cookieValue != null) {
            code = MissingRequestParametersWebErrorHandler.MISSING_COOKIE;
            parameterName = extractParameterName(cookieValue, parameter);
        }

        MatrixVariable matrixVariable = parameter.getParameterAnnotation(MatrixVariable.class);
        if (matrixVariable != null) {
            code = MissingRequestParametersWebErrorHandler.MISSING_MATRIX_VARIABLE;
            parameterName = extractParameterName(matrixVariable, parameter);
        }

        if (code != null) {
            return new HandledException(code, BAD_REQUEST,"",
                argMap(code, arg("name", parameterName), arg("expected", parameter.getParameterType().getSimpleName())));
        }

        return null;
    }

    /**
     * Creates a map of arguments to exposed under the given {@code code} as a key.
     *
     * @param code      The map key.
     * @param arguments The to-be-exposed arguments.
     * @return The intended map.
     */
    private static Map<String, List<Argument>> argMap(String code, Argument... arguments) {
        return singletonMap(code, asList(arguments));
    }

    private String extractParameterName(Annotation annotation, MethodParameter parameter) {
        String name = getNameAttribute(annotation);

        return name.isEmpty() ? parameter.getParameterName() : name;
    }

    private Set<String> getMediaTypes(List<MediaType> mediaTypes) {
        if (mediaTypes == null) return emptySet();

        return mediaTypes.stream().map(MediaType::toString).collect(toSet());
    }

    private String getNameAttribute(Annotation annotation) {
        try {
            Method method = annotation.getClass().getMethod("name");
            return (String) method.invoke(method);
        } catch (Exception e) {
            return "";
        }
    }
}
