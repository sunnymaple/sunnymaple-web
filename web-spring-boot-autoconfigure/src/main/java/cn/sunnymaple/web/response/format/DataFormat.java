package cn.sunnymaple.web.response.format;

import java.lang.annotation.*;

/**
 * 在Controller层方法或者类中使用
 * 在类中使用：表示该类中的所有接口（方法）的响应参数都参与数据的格式化
 * 在方法中使用：仅表示该接口（方法）的响应参数参与数据的格式化
 * @author wangzb
 * @date 2020/4/8 17:31
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFormat {
}
