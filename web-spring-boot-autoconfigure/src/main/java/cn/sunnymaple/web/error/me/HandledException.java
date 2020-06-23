package cn.sunnymaple.web.error.me;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.Objects.requireNonNull;

/**
 * Encapsulates details about a handled exception, including:
 * <ul>
 * <li>The mapped business level error codes</li>
 * <li>The corresponding HTTP status code</li>
 * <li>A collection of arguments that can be used for message translation</li>
 * </ul>
 *
 * @author Ali Dehghani
 * @see WebErrorHandler
 */
public class HandledException {

    /**
     * Collection of error codes corresponding to the handled exception. Usually this collection
     * contains only one error code but not always, say for validation errors.
     */
    private final String errorCode;

    /**
     * Corresponding status code for the handled exception.
     */
    private final HttpStatus statusCode;
    /**
     * 提示信息
     * 错误码之外的业务独特信息由 error_message 来承载，而不是让错误码本身涵盖过
     * 多具体业务属性
     */
    private final Set<String> userTips;

    /**
     * Collection of to-be-exposed arguments grouped by the error code. This is a mapping
     * between the error code and all its to-be-exposed arguments. For example, suppose
     * we have a bean like:
     * <pre>
     *     public class User {
     *
     *         &#64;Size(min=1, max=7, message="interests.range_limit")
     *         private List&lt;String&gt; interests;
     *         // omitted for the sake of brevity
     *     }
     * </pre>
     * If the given interest list wasn't valid, then this map would contain an entry with the
     * {@code interests.range_limit} as the key and {@code List(Argument(min, 1), Argument(max, 7))}
     * as the values. Later on we can use those exposed values in our message, for example,
     * the following error template:
     * <pre>
     *     You should define between {0} and {1} interests.
     * </pre>
     * Would be translated to:
     * <pre>
     *     You should define between 1 and 7 interests.
     * </pre>
     */
    private final Map<String, List<Argument>> arguments;

    /**
     * Initialize a handled exception with a set of error codes, a HTTP status code and an
     * optional collection of arguments.
     *
     * @param errorCode The corresponding error codes for the handled exception.
     * @param statusCode The corresponding status code for the handled exception.
     * @param userTips 用户提示信息
     * @param arguments  Arguments to be exposed from the handled exception to the outside world.
     * @throws NullPointerException     When one of the required parameters is null.
     * @throws IllegalArgumentException At least one error code should be provided.
     */
    public HandledException(@NonNull String errorCode,
                            @NonNull HttpStatus statusCode,
                            @Nullable Set<String> userTips,
                            @Nullable Map<String, List<Argument>> arguments) {
        enforcePreconditions(errorCode, statusCode);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
        this.userTips = userTips;
        this.arguments = arguments == null ? Collections.emptyMap() : arguments;
    }

    /**
     * Initialize a handled exception with an error code, a HTTP status code
     *
     * @param errorCode  The corresponding error code for the handled exception.
     * @param statusCode The corresponding status code for the handled exception.
     * @param userTips 用户提示信息
     * @throws NullPointerException     When one of the required parameters is null.
     * @throws IllegalArgumentException At least one error code should be provided.
     */
    public HandledException(@NonNull String errorCode,
                            @NonNull HttpStatus statusCode,
                            @Nullable Set<String> userTips) {
        this(errorCode, statusCode,  userTips, null);
    }
    /**
     * Initialize a handled exception with an error code, a HTTP status code
     *
     * @param errorCode  The corresponding error code for the handled exception.
     * @param statusCode The corresponding status code for the handled exception.
     * @param userTip 用户提示信息
     * @throws NullPointerException     When one of the required parameters is null.
     * @throws IllegalArgumentException At least one error code should be provided.
     */
    public HandledException(@NonNull String errorCode,
                            @NonNull HttpStatus statusCode,
                            @Nullable String userTip) {
        this(errorCode, statusCode,  singleton(userTip), null);
    }


    public HandledException(@NonNull String errorCode,
                            @NonNull HttpStatus statusCode,
                            @Nullable String userTip,
                            @Nullable Map<String, List<Argument>> arguments) {
        this(errorCode, statusCode,  singleton(userTip), arguments);
    }

    public HandledException(@NonNull String errorCode,
                            @NonNull HttpStatus statusCode,
                            @Nullable Map<String, List<Argument>> arguments) {
        this(errorCode, statusCode,  "", arguments);
    }

    /**
     * @return Collection of mapped error codes.
     * @see #errorCode
     */
    @NonNull
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * @return The mapped status code.
     * @see #statusCode
     */
    @NonNull
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Set<String> getUserTips() {
        return userTips;
    }

    /**
     * @return Collection of to-be-exposed arguments.
     * @see #arguments
     */
    @NonNull
    public Map<String, List<Argument>> getArguments() {
        return arguments;
    }

    private void enforcePreconditions(String errorCode, HttpStatus statusCode) {
        requireNonNull(errorCode, "Error codes is required");
        requireNonNull(statusCode, "Status code is required");

        if (StrUtil.isBlank(errorCode)){
            throw new IllegalArgumentException("At least one error code should be provided");
        }
    }
}
