package cn.sunnymaple.web.validated.annotation;

import cn.sunnymaple.web.validated.IDCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 身份证验证
 * @author wangzb
 * @date 2019/12/16 10:51
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(IDCard.List.class)
@Documented
@Constraint(validatedBy = {IDCardValidator.class})
public @interface IDCard {
    /**
     * 默认错误消息
     * @return
     */
    String message() default "validate.id_card";

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
        IDCard[] value();
    }
}
