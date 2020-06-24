package cn.sunnymaple.web.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

/**
 * Swagger相关配置
 * @author wangzb
 * @date 2020/4/26 15:21
 */
@ConfigurationProperties(prefix = "swagger")
@Data
public class Swagger2Properties {

    /**
     * 扫描的包
     * 默认为com.seagetec
     */
    private String basePackage = "cn.sunnymaple";
    /**
     * 标题
     */
    private String title = "API接口文档";
    /**
     * 版本
     */
    private String version = "1.0.0";
    /**
     * 描述
     */
    private String description;
    /**
     * 联系人或者作者信息
     */
    private Contact contact = new Contact("矽甲（上海）信息科技有限公司", "http://www.seagetech.com/", "");
}
