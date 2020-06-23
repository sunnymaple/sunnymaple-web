package cn.sunnymaple.web.error.me.annotation;

import cn.sunnymaple.web.error.me.handlers.AnnotatedWebErrorHandler;
import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

/**
 * When an exception annotated with this annotation happens, the metadata encapsulated
 * in the annotation would help us to transform the language level exception to REST API
 * error code/status code combination.
 *
 * @author Ali Dehghani
 * @see ExposeAsArg
 * @see AnnotatedWebErrorHandler
 */
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionMapping {

    /**
     * 错误码
     *
     * @return The error code.
     */
    String errorCode();

    /**
     * 提示消息，支持国际化
     * 不指定时，使用errorCode对应的信息作为提示消息
     * @return 提示消息
     */
    String userTip() default "";

    /**
     * HTTP状态码
     *
     * @return The status code.
     */
    HttpStatus statusCode();
}
