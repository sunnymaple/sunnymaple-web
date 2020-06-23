package cn.sunnymaple.web.login.pattern;

import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.login.controller.WebPattern;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

import java.util.Objects;
import java.util.Optional;

/**
 * web模式条件{@link WebPatternCondition}
 * @author wangzb
 * @date 2020/3/26 13:55
 */
public class WebPatternCondition implements Condition {
    /**
     * Determine if the condition matches.
     *
     * @param context  the condition context
     * @param metadata metadata of the {@link AnnotationMetadata class}
     *                 or {@link MethodMetadata method} being checked
     * @return {@code true} if the condition matches and the component can be registered,
     * or {@code false} to veto the annotated component's registration
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Optional<Object> value = metadata.getAnnotations().get(ConditionalOnWebPattern.class).getValue("value");
        WebPattern webPattern = (WebPattern) value.get();

        String property = context.getEnvironment().getProperty("login.web-pattern");
        if (StrUtil.isBlank(property)){
            return webPattern == WebPattern.VIEW ? true : false;
        }
        return Objects.equals(property,webPattern.getWebPattern());
    }
}
