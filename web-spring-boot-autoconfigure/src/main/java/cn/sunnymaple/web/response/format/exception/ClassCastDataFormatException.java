package cn.sunnymaple.web.response.format.exception;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.error.ErrorCode;
import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;
import cn.sunnymaple.web.response.format.impl.Money;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 数据格式化时类型转换异常
 * 比如{@link Money}注解格式化后的数据类型是String类型的，但是响应参数实体类的该属性确是Double类型的，
 * 在数据格式化后，将格式化后的数据赋值给响应实体的对应属性，则会出现类型转换异常。
 * 这往往是开发人员的Bug问题,原则上不应该抛给客户端，所有要求开发人员在开发时或者自行测试时，如出现该异常，
 * 请及时处理
 * @author wangzb
 * @date 2020/4/14 10:14
 */
@ExceptionMapping(errorCode = ErrorCode.B0001,userTip= "dataformat.class_cast_error", statusCode = INTERNAL_SERVER_ERROR)
public class ClassCastDataFormatException extends DataFormatException {
    /**
     * 目标Class类型
     */
    private Class tagClass;
    /**
     * 源Class类型
     */
    private Class sourceClass;
    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param classPath
     */
    public ClassCastDataFormatException(String classPath, Class tagClass, Class sourceClass, String fieldName) {
        super(classPath);
        this.tagClass = tagClass;
        this.sourceClass = sourceClass;
        this.fieldName = fieldName;
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
    public ClassCastDataFormatException(String message, String classPath, Class tagClass, Class sourceClass, String fieldName) {
        super(message, classPath);
        this.tagClass = tagClass;
        this.sourceClass = sourceClass;
        this.fieldName = fieldName;
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
    public ClassCastDataFormatException(String message, Throwable cause, String classPath, Class tagClass, Class sourceClass, String fieldName) {
        super(message, cause, classPath);
        this.tagClass = tagClass;
        this.sourceClass = sourceClass;
        this.fieldName = fieldName;
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
    public ClassCastDataFormatException(Throwable cause, String classPath, Class tagClass, Class sourceClass, String fieldName) {
        super(cause, classPath);
        this.tagClass = tagClass;
        this.sourceClass = sourceClass;
        this.fieldName = fieldName;
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
    public ClassCastDataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String classPath, Class tagClass, Class sourceClass, String fieldName) {
        super(message, cause, enableSuppression, writableStackTrace, classPath);
        this.tagClass = tagClass;
        this.sourceClass = sourceClass;
        this.fieldName = fieldName;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder(super.getMessage()).append(",");
        if (StrUtil.isNotBlank(fieldName)){
            stringBuilder.append("字段").append(fieldName).append("：");
        }
        return stringBuilder.append(tagClass.getName()).append(" 不能转换为 ").append(sourceClass.getName()).toString();
    }
}
