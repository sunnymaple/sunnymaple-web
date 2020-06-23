package cn.sunnymaple.web.validated.annotation;

import cn.sunnymaple.web.validated.TelephoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 手机号验证注解
 * @author wangzb
 * @date 2019/12/10 12:46
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(Telephone.List.class)
@Documented
@Constraint(validatedBy = {TelephoneValidator.class })
public @interface Telephone {
    /**
     * 默认错误消息
     * @return
     */
    String message() default "validate.telephone";

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

    /**
     * Defines several {@link Telephone} constraints on the same element.
     *
     * @see Telephone
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Telephone[] value();
    }
}
