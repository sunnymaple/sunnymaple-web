package cn.sunnymaple.web.swagger;

import cn.sunnymaple.web.response.AppResponseHandlerAutoConfiguration;
import cn.sunnymaple.web.login.config.LoginAutoConfiguration;
import cn.sunnymaple.web.response.AppResponseHandlerProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;

import javax.annotation.PostConstruct;

/**
 * @author wangzb
 * @date 2020/4/24 11:15
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(Swagger2DocumentationConfiguration.class)
@EnableConfigurationProperties(Swagger2Properties.class)
@AutoConfigureAfter(AppResponseHandlerAutoConfiguration.class)
@AutoConfigureBefore(LoginAutoConfiguration.class)
@EnableSwagger2
public class Swagger2AutoConfiguration {

    private AppResponseHandlerProperties appResponseHandlerProperties;

    /**
     * swagger-ui.html页面相关请求接口API URI
     * 如果使用统一响应格式的响应参数{@link AppResponseHandlerAutoConfiguration}需要指定这些接口不使用统一响应格式的参数
     */
    private static final String[] EXC_RESPONSE_URI = new String[]{"/swagger-resources/**","/v2/api-docs"};

    public Swagger2AutoConfiguration(AppResponseHandlerProperties appResponseHandlerProperties) {
        this.appResponseHandlerProperties = appResponseHandlerProperties;
    }

    /**
     * 相关初始化操作
     */
    @PostConstruct
    public void init(){
        //添加不响应统一格式的响应参数
        appResponseHandlerProperties.addNoResponseHandler(EXC_RESPONSE_URI);
    }

    /**
     * 创建Docket
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public Docket docket(Swagger2Properties properties){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo(properties))
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API信息
     * @param properties
     * @return
     */
    public ApiInfo apiInfo(Swagger2Properties properties) {
        ApiInfo apiInfo = new ApiInfoBuilder()
                //页面标题
                .title(properties.getTitle())
                //创建人
                .contact(properties.getContact())
                //版本号
                .version(properties.getVersion())
                //描述
                .description(properties.getDescription())
                .build();
        return apiInfo;
    }
}
