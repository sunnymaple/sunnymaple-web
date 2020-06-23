package cn.sunnymaple.web.login.pattern;

import cn.sunnymaple.web.login.controller.WebPattern;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author wangzb
 * @date 2020/3/26 13:58
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(WebPatternCondition.class)
public @interface ConditionalOnWebPattern {
    /**
     * 模式
     * @return
     */
    WebPattern value();
}
