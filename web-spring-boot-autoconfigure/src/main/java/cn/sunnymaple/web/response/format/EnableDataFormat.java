package cn.sunnymaple.web.response.format;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启响应参数格式化
 * 默认不开启，在SpringBoot的主启动类上加上该注解开启该模块
 * @author wangzb
 * @date 2020/4/8 17:28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DataFormatRestResultHandler.class)
public @interface EnableDataFormat {
}
