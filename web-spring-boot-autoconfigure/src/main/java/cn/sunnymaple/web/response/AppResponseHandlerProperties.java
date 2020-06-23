package cn.sunnymaple.web.response;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 统一格式的响应参数全局配置
 * @author wangzb
 * @date 2018-10-31 9:36
 */
@ConfigurationProperties(prefix = "response.handler")
public class AppResponseHandlerProperties {
    /**
     * 是否启用AppResponseHandler处理器
     * true 启用
     * false 停用 默认为停用
     */
    private boolean enabled = true;

    /**
     * 过滤的接口
     */
    private List<String> exclusions = new ArrayList<>(16);

    /**
     * 定义不需要使用AppResponseHandler的接口，同@NoResponseHandler
     * 支持通配符：？ 匹配单个字符，如/demo?  则/demo1、/demo2会被匹配  而/demo12则不被匹配
     *             *  除/外的任意字符  如：/demo* 则 /demo1、/demo2、/demo12会被匹配 而/demo/1不会被匹配
     *                                 如：/demo/* 可以匹配/demo/1 /demo/2  /demo/12
     *             ** 匹配任意的多个目录  如：/demo/** 可以匹配/demo/1 /demo/2  /demo/12 /demo/1/2等
     */
    public void setNoResponseHandler(String[] noResponseHandler) {
        this.exclusions.addAll(Arrays.asList(noResponseHandler));
    }

    /**
     * 添加过滤的接口
     * @param uris
     */
    public void addNoResponseHandler(String... uris){
        this.exclusions.addAll(Arrays.asList(uris));
    }

    /**
     * 添加过滤的接口
     * @param uris
     */
    public void addNoResponseHandler(List<String> uris){
        this.exclusions.addAll(uris);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getExclusions() {
        return exclusions;
    }
}
