package cn.sunnymaple.web.validated;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.validated.annotation.Telephone;

import javax.validation.ConstraintValidatorContext;

/**
 * 手机号验证
 * @author wangzb
 * @date 2019/12/10 12:53
 */
public class TelephoneValidator extends DefaultValidator<Telephone, String> {
    /**
     * 广泛的手机号验证
     */
    private static final String TELEPHONE = "^[1][3,4,5,7,8][0-9]{9}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isEmpty(value)){
            return true;
        }
        return isMatches(TELEPHONE,value);
    }
}
