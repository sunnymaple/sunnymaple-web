package cn.sunnymaple.web.response.format.exception;


import cn.sunnymaple.web.error.ErrorCode;
import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 没有指定的类型
 * @author wangzb
 * @date 2020/4/14 16:52
 */
@ExceptionMapping(errorCode = ErrorCode.B0001 ,userTip= "dataformat.no_format_field_error", statusCode = INTERNAL_SERVER_ERROR)
public class NoFormatFieldException extends DataFormatException{
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param classPath
     */
    public NoFormatFieldException(String classPath) {
        super(classPath);
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param classPath
     */
    public NoFormatFieldException(String message, String classPath) {
        super(message, classPath);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message   the detail message (which is saved for later retrieval
     *                  by the {@link #getMessage()} method).
     * @param cause     the cause (which is saved for later retrieval by the
     *                  {@link #getCause()} method).  (A <tt>null</tt> value is
     *                  permitted, and indicates that the cause is nonexistent or
     *                  unknown.)
     * @param classPath
     * @since 1.4
     */
    public NoFormatFieldException(String message, Throwable cause, String classPath) {
        super(message, cause, classPath);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause     the cause (which is saved for later retrieval by the
     *                  {@link #getCause()} method).  (A <tt>null</tt> value is
     *                  permitted, and indicates that the cause is nonexistent or
     *                  unknown.)
     * @param classPath
     * @since 1.4
     */
    public NoFormatFieldException(Throwable cause, String classPath) {
        super(cause, classPath);
    }

    /**
     * Constructs a new runtime exception with the specified detail
     * message, cause, suppression enabled or disabled, and writable
     * stack trace enabled or disabled.
     *
     * @param message            the detail message.
     * @param cause              the cause.  (A {@code null} value is permitted,
     *                           and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     * @param classPath
     * @since 1.7
     */
    public NoFormatFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String classPath) {
        super(message, cause, enableSuppression, writableStackTrace, classPath);
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return super.getMessage() + "，没有指定格式化字段";
    }
}
