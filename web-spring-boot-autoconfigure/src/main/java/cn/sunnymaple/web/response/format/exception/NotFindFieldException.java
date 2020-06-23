package cn.sunnymaple.web.response.format.exception;


import cn.sunnymaple.web.error.ErrorCode;
import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author wangzb
 * @date 2020/4/14 18:04
 */
@ExceptionMapping(errorCode = ErrorCode.B0001, userTip= "dataformat.not_find_field", statusCode = INTERNAL_SERVER_ERROR)
public class NotFindFieldException extends DataFormatException{
    /**
     * 字段名称
     */
    private String filedName;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param classPath
     */
    public NotFindFieldException(String classPath, String filedName) {
        super(classPath);
        this.filedName = filedName;
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
    public NotFindFieldException(String message, String classPath, String filedName) {
        super(message, classPath);
        this.filedName = filedName;
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
    public NotFindFieldException(String message, Throwable cause, String classPath, String filedName) {
        super(message, cause, classPath);
        this.filedName = filedName;
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
    public NotFindFieldException(Throwable cause, String classPath, String filedName) {
        super(cause, classPath);
        this.filedName = filedName;
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
    public NotFindFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String classPath, String filedName) {
        super(message, cause, enableSuppression, writableStackTrace, classPath);
        this.filedName = filedName;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return super.getMessage() + ",没有找到字段：" + filedName;
    }
}
