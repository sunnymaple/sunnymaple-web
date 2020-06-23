package cn.sunnymaple.web.validated;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.validated.annotation.DateFormat;

import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 日期格式验证方法
 * @author wangzb
 * @date 2019/12/14 16:26
 */
public class DateFormatValidator extends DefaultValidator<DateFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StrUtil.isEmpty(value)){
            return true;
        }
        DateFormat dateFormat = getAnnotation();
        String dateFormatVal = dateFormat.format();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatVal);
        try {
            Date date = formatter.parse(value);
            return Objects.equals(value,formatter.format(date));
        } catch (ParseException e) {
            return false;
        }
    }
}
