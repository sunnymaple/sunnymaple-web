package cn.sunnymaple.web.error.me.handlers;

import cn.sunnymaple.web.error.me.Argument;
import cn.sunnymaple.web.error.me.HandledException;
import cn.sunnymaple.web.error.me.WebErrorHandler;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.sunnymaple.web.error.ErrorCode.A0400;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * A {@link WebErrorHandler} implementation responsible for handling {@link ConstraintViolationException}s
 * from bean validation.
 *
 * @author Ali Dehghani
 */
public class ConstraintViolationWebErrorHandler implements WebErrorHandler {

    /**
     * Only can handle {@link ConstraintViolationException}s that contains at least one
     * {@link ConstraintViolation}.
     *
     * @param exception The exception to examine.
     * @return {@code true} if the {@code exception} is a {@link ConstraintViolationException} with at least one
     * violation, {@code false} otherwise.
     */
    @Override
    public boolean canHandle(Throwable exception) {
        return exception instanceof ConstraintViolationException && hasAtLeastOneViolation(exception);
    }

    /**
     * Handles the given {@link ConstraintViolationException}s by extracting the error codes from the message templates
     * and also, extracting the arguments from {@link javax.validation.metadata.ConstraintDescriptor}s.
     *
     * <h3>Constraint Descriptor</h3>
     * All annotation attributes defined in a constrain annotation, e.g. {@link javax.validation.constraints.Size},
     * can be extracted from the {@link javax.validation.metadata.ConstraintDescriptor#getAttributes()} method. The
     * specification of the Bean Validation API demands, that any constraint annotation must define three mandatory
     * attributes, {@code message}, {@code groups} and {@code payload}. Since these three attributes are not that valuable
     * as arguments, we're not going to expose them.
     *
     * @param exception The exception to handle.
     * @return The handled exception
     */
    @NonNull
    @Override
    public HandledException handle(Throwable exception) {
        ConstraintViolationException violationException = (ConstraintViolationException) exception;
        Set<String> userTips = extractUserTips(violationException);
        Map<String, List<Argument>> arguments = extractTipArguments(violationException);

        return new HandledException(A0400, HttpStatus.BAD_REQUEST,userTips, arguments);
    }

    /**
     * checks if the given {@link ConstraintViolationException} contains at least one constraint violation.
     *
     * @param exception The exception to examine.
     * @return {@code true} if the exception contains at least one violation, {@code false} otherwise.
     */
    private boolean hasAtLeastOneViolation(Throwable exception) {
        Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) exception).getConstraintViolations();

        return violations != null && !violations.isEmpty();
    }

    /**
     * Extract annotation attributes (except for those three mandatory attributes) and expose them as arguments.
     *
     * @param violationException The exception to extract the arguments from.
     * @return To-be-exposed arguments.
     */
    @Deprecated
    private Map<String, List<Argument>> extractArguments(ConstraintViolationException violationException) {
        Map<String, List<Argument>> args = violationException
            .getConstraintViolations()
            .stream()
            .collect(toMap(this::errorCode, ConstraintViolationArgumentsExtractor::extract, (v1, v2) -> v1));
        args.entrySet().removeIf(e -> e.getValue().isEmpty());
        return args;
    }

    /**
     * Extract message templates and use them as error codes.
     *
     * @param violationException The exception to extract the error codes from.
     * @return A set of error codes.
     */
    @Deprecated
    private Set<String> extractErrorCodes(ConstraintViolationException violationException) {
        return violationException
            .getConstraintViolations()
            .stream()
            .map(this::errorCode)
            .collect(toSet());
    }

    @Deprecated
    private String errorCode(ConstraintViolation<?> violation) {
        return violation.getMessageTemplate().replace("{", "").replace("}", "");
    }


    private Map<String, List<Argument>> extractTipArguments(ConstraintViolationException violationException) {
        Map<String, List<Argument>> args = violationException
                .getConstraintViolations()
                .stream()
                .collect(toMap(this::userTip, ConstraintViolationArgumentsExtractor::extract, (v1, v2) -> v1));
        args.entrySet().removeIf(e -> e.getValue().isEmpty());
        return args;
    }

    private Set<String> extractUserTips(ConstraintViolationException violationException) {
        return violationException
                .getConstraintViolations()
                .stream()
                .map(this::userTip)
                .collect(toSet());
    }


    private String userTip(ConstraintViolation<?> violation) {
        return violation.getMessageTemplate();
    }
}
