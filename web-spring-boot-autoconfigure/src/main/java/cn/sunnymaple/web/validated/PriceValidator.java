package cn.sunnymaple.web.validated;


import cn.sunnymaple.web.validated.annotation.Price;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 价格参数验证规则
 * @author wangzb
 * @date 2019/12/13 12:47
 */
public class PriceValidator implements ConstraintValidator<Price, Double> {

    private Price price;
    /**
     * 价格的小数部分最大值为2
     */
    private static final Integer PRICE_FRACTION_MXA_LENGTH = 2;

    @Override
    public void initialize(Price constraintAnnotation) {
        this.price = constraintAnnotation;
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value<0){
            return false;
        }
        //整数部位
        double d = value;
        String integer = ((int) d) + "";
        int integerLength = integer.length();
        if (integerLength > price.integerMax() || integerLength<price.integerMin()){
            return false;
        }
        //小数部位
        String valueStr = d + "";
        String fraction = valueStr.substring(valueStr.indexOf(".") + 1);
        int fractionLength = fraction.length();
        if (fractionLength>PRICE_FRACTION_MXA_LENGTH){
            return false;
        }
        return true;
    }
}
