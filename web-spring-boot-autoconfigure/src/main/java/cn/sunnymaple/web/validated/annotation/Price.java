package cn.sunnymaple.web.validated.annotation;

import cn.sunnymaple.web.validated.PriceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 价格验证
 * @author wangzb
 * @date 2019/12/13 12:34
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(Price.List.class)
@Documented
@Constraint(validatedBy = {PriceValidator.class})
public @interface Price {

    /**
     * 默认错误消息
     * @return
     */
    String message() default "validate.price";

    /**
     * @return 整数部位最大位数
     */
    int integerMax() default 10;

    /**
     *
     * @return 整数部位小位数
     */
    int integerMin() default 1;

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
        Price[] value();
    }
}
