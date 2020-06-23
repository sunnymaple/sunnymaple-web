package cn.sunnymaple.web.response.format;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *定义格式化类型，用户数据格式化对应的注解
 * @author wangzb
 * @date 2020/4/8 17:37
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface FormatType {
    /**
     * 指定格式化操作的实体类{@link IDataFormat}
     * @return
     */
    Class<? extends IDataFormat> formatBy();
}
