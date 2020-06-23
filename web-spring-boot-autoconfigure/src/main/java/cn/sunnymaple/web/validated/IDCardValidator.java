package cn.sunnymaple.web.validated;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.validated.annotation.IDCard;

import javax.validation.ConstraintValidatorContext;

/**
 * 身份证验证
 * @author wangzb
 * @date 2019/12/16 10:52
 */
public class IDCardValidator extends DefaultValidator<IDCard, String>{
    /**
     * 身份证正则表达式
     *
     * 1、 地区代码，第一位不为0，共计6位  [1-9]\\d{5}
     *
     * 2、 出生年份，年份第一位不为0，共计4位 [1-9]\\d{3}
     *
     * 3、出生月份，月份第一位为0或者1，如果为0则第二位为1-9，如果为1则为0-2，共计2位 ((0[1-9])|(1[0-2]))
     *
     * 4、出生日期，日期第一位为0或者1或者2或者3，
     *    如果第一位是0，则第二位可能是1-9
     *    如果第一位是1，2，则第二位可能是0-9
     *    如果第一位是3，第二位为0-1
     *    (0[1-9]|([1,2][0-9])|3[0-1])
     *
     * 5、结尾可以为四位任意数字或者为三位数加大写X（此处注意传进身份证号大小写） ((\\d{4})|\\d{3}X)
     */
    private static final String ID_CARD_PATTERN = "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(0[1-9]|([1,2][0-9])|3[0-1])((\\d{4})|\\d{3}X)$";

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
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StrUtil.isEmpty(value)){
            return true;
        }
        return isMatches(ID_CARD_PATTERN,value);
    }
}
