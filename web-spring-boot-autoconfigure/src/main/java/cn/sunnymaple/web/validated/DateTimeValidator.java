package cn.sunnymaple.web.validated;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.validated.annotation.DateTime;

import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 时间验证
 * @author wangzb
 * @date 2019/12/14 16:59
 */
public class DateTimeValidator extends DefaultValidator<DateTime, String> {
    /**
     * 时间默认格式
     */
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        try {
            java.util.Date date = formatter.parse(value);
            return Objects.equals(value,formatter.format(date));
        } catch (ParseException e) {
            return false;
        }
    }
}
