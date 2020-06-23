package cn.sunnymaple.web.login.controller;

/**
 * web模式
 * @author wangzb
 * @date 2020/3/24 17:32
 */
public enum WebPattern {
    /**
     * 只含有网页版（视图）的web项目
     * 如后台管理系统，官网，商城等具有html(h5)页面的web项目
     */
    VIEW("VIEW"),
    /**
     * 只提供API接口的web项目
     * 如移动端或者提供第三方调用API接口web项目
     */
    API("API");

    WebPattern(String webPattern) {
        this.webPattern = webPattern;
    }

    private String webPattern;

    public String getWebPattern() {
        return webPattern;
    }
}
