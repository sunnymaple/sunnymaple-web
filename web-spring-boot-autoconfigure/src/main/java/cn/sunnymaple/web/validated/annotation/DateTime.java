package cn.sunnymaple.web.validated.annotation;

import cn.sunnymaple.web.validated.DateTimeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 时间验证，默认为yyyy-MM-dd HH:mm:ss
 * @author wangzb
 * @date 2019/12/14 16:57
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(DateTime.List.class)
@Documented
@Constraint(validatedBy = {DateTimeValidator.class})
public @interface DateTime {
    /**
     * 默认错误消息
     * @return
     */
    String message() default "validate.date_time";

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
        DateTime[] value();
    }
}
