package cn.sunnymaple.web.response.format.impl;

import cn.sunnymaple.web.response.format.FormatType;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.RoundingMode;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 金额格式化
 * @author wangzb
 * @date 2020/4/8 17:47
 */
@Target({FIELD,METHOD})
@Retention(RUNTIME)
@Documented
@FormatType(formatBy = MoneyDataFormatImpl.class)
public @interface Money {
    /**
     * 缺省的取整模式，为{@link RoundingMode#HALF_EVEN}
     * （四舍五入，当小数为0.5时，则取最近的偶数）
     * @return
     */
    RoundingMode roundingMode() default RoundingMode.HALF_EVEN;

    /**
     * 为空时的处理模式{@link MoneyEmptyMode}
     * 默认为{@link MoneyEmptyMode#ZERO}
     * @return
     */
    MoneyEmptyMode moneyEmptyMode() default MoneyEmptyMode.ZERO;

    /**
     * 如果是Map类型的响应对象，可以使用name指定对应的key
     * @return
     */
    String[] name() default {};

    /**
     * 如果提供的格式化字符串不是数字，请提供需要格式化数字的位置</br>
     * 如：运行此渠道，将冻结5.1元，是否确认，确认按1，否则按0</br>
     * 在上述例子中，有三个地方是数字，分别是5.1,1和0。他们对应的下标(index)分别是0,1,2</br>
     * 所有，要格式化5.1这个数字，formatIndex={0}，格式化5.1和1，则：formatIndex={0,1}</br>
     * @return
     */
    int[] formatIndex() default {};
}
