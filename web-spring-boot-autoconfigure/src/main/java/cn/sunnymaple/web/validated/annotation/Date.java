package cn.sunnymaple.web.validated.annotation;

import cn.sunnymaple.web.validated.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 日期验证，格式固定为yyyy-MM-dd
 * @author wangzb
 * @date 2019/12/14 16:52
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(Date.List.class)
@Documented
@Constraint(validatedBy = {DateValidator.class})
public @interface Date {

    /**
     * 默认错误消息
     * @return
     */
    String message() default "validate.date";

    /**
     * 分组
     * @return
     */
    Class<?>[] groups() default { };

    /**
     * 负载
     * @return
     */
    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Date[] value();
    }
}
