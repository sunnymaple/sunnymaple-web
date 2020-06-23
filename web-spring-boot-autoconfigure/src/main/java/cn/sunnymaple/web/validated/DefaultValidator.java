package cn.sunnymaple.web.validated;

import javax.validation.ConstraintValidator;
import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

/**
 * 参数验证
 * @author wangzb
 * @date 2019/12/14 16:37
 */
public abstract class DefaultValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

    private A annotation;

    @Override
    public void initialize(A constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    public A getAnnotation() {
        return annotation;
    }

    /**
     * 验证
     * @param pattern 正则表达式
     * @param value 目标值
     * @return
     */
    protected boolean isMatches(String pattern, String value){
        return Pattern.matches(pattern, value);
    }
}
