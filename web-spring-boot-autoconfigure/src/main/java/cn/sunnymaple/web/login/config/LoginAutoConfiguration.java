package cn.sunnymaple.web.login.config;

import cn.sunnymaple.web.login.interceptor.LoginAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 登录模块自动配置类
 * @author wangzb
 * @date 2020/3/25 14:53
 */
@Configuration
@EnableConfigurationProperties(LoginProperties.class)
@Import(LoginAspect.class)
public class LoginAutoConfiguration {
}
