package cn.sunnymaple.web.response.format.exception;


import cn.sunnymaple.web.error.ErrorCode;
import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 数据格式化异常，原则上该异常不应该抛到客户端
 * 开发人员再开发或者自行测试过程中应该避免{@link DataFormatException}及其子类异常
 * 发现抛出该类及其子类异常时，及时的查找异常的原因并处理异常
 * @author wangzb
 * @date 2020/4/14 9:45
 */
@ExceptionMapping(errorCode = ErrorCode.B0001, userTip= "dataformat.error", statusCode = INTERNAL_SERVER_ERROR)
public class DataFormatException extends RuntimeException{
    /**
     * 类（接口）全路径
     */
    private String classPath;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public DataFormatException(String classPath) {
        this.classPath = classPath;
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public DataFormatException(String message, String classPath) {
        super(message);
        this.classPath = classPath;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public DataFormatException(String message, Throwable cause, String classPath) {
        super(message, cause);
        this.classPath = classPath;
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public DataFormatException(Throwable cause, String classPath) {
        super(cause);
        this.classPath = classPath;
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
     * @since 1.7
     */
    public DataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String classPath) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.classPath = classPath;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return classPath;
    }
}
