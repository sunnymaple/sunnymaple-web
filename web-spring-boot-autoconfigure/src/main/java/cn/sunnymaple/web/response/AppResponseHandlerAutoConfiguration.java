package cn.sunnymaple.web.response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 响应统一参数自动配置类
 * @author wangzb
 * @date 2019/12/4 15:14
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(AppResponseHandlerProperties.class)
public class AppResponseHandlerAutoConfiguration {

    /**
     * 将IRestResultFactory添加到Spring容器中
     * @return
     */
    @ConditionalOnMissingBean
    @Bean
    public IRestResultFactory restResultFactory(){
        return new DefaultRestResultFactory();
    }

    /**
     * 注入{@link AppResponseHandler}统一异常处理器
     * @return
     */
    @Bean
    public AppResponseHandler appResponseHandler(){
        return new AppResponseHandler();
    }
}
