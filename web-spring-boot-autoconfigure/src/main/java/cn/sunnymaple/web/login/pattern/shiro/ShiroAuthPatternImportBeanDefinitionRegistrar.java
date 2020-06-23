package cn.sunnymaple.web.login.pattern.shiro;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Set;

/**
 * Shiro登录以及权限验证Bean注册类
 * @author wangzb
 * @date 2020/3/25 15:52
 */
public class ShiroAuthPatternImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        //过滤掉controller
        provider.addExcludeFilter(new AnnotationTypeFilter(Controller.class));
        Optional<Set<BeanDefinition>> beanDefinitionSetOp = Optional.ofNullable(provider.findCandidateComponents(getClass().getPackage().getName()));
        beanDefinitionSetOp.ifPresent(beanDefinitions -> {
            beanDefinitions.forEach(beanDefinition -> {
                registry.registerBeanDefinition(beanDefinition.getBeanClassName(),beanDefinition);
            });
        });
    }
}
