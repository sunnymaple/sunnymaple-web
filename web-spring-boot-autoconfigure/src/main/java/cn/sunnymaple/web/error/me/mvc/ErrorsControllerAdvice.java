package cn.sunnymaple.web.error.me.mvc;

import cn.sunnymaple.web.error.me.HttpError;
import cn.sunnymaple.web.error.me.WebErrorHandlers;
import cn.sunnymaple.web.error.me.adapter.HttpErrorAttributesAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

/**
 * Tries its best to catch and handle all exceptions thrown inside the
 * Web layer using {@link WebErrorHandlers} assistance.
 *
 * @author Ali Dehghani
 */
@RestControllerAdvice
public abstract class ErrorsControllerAdvice {

    /**
     * Responsible for handling exceptions and converting them to appropriate {@link HttpError}s.
     */
    private final WebErrorHandlers errorHandlers;

    /**
     * To adapt our error representation to Spring Boot's representation of an error.
     */
    private final HttpErrorAttributesAdapter httpErrorAttributesAdapter;

    /**
     * Initializing the rest controller advice by injecting the {@link WebErrorHandlers} bean.
     *
     * @param errorHandlers              The exception handler collaborator.
     * @param httpErrorAttributesAdapter To adapt our error representation to Spring Boot's representation of an error.
     * @throws NullPointerException When one of the required parameters are null.
     */
    public ErrorsControllerAdvice(WebErrorHandlers errorHandlers,
                                  HttpErrorAttributesAdapter httpErrorAttributesAdapter) {
        this.errorHandlers = requireNonNull(errorHandlers, "Error handlers is required");
        this.httpErrorAttributesAdapter = requireNonNull(httpErrorAttributesAdapter, "Adapter is required");
    }

    /**
     * Catches any exception and converts it to a HTTP response with appropriate status
     * code and error code-message combinations.
     *
     * @param exception  The caught exception.
     * @param webRequest The current HTTP request.
     * @param locale     Determines the locale for message translation.
     * @return A HTTP response with appropriate error body and status code.
     */
    @ExceptionHandler
    public ResponseEntity<?> handleException(Throwable exception, WebRequest webRequest, Locale locale) {
        HttpError httpError = errorHandlers.handle(exception, webRequest, locale);

        return ResponseEntity.status(httpError.getHttpStatus()).body(httpErrorAttributesAdapter.adapt(httpError));
    }
}
