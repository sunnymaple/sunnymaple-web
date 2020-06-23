package cn.sunnymaple.web.db.mybatis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * 分页配置
 * @author wangzb
 * @date 2019/12/11 22:28
 */
@ConfigurationProperties(prefix = PageHandlerProperties.PAGE_HANDLER_PREFIX)
@Data
public class PageHandlerProperties {

    public static final String PAGE_HANDLER_PREFIX = "page.handler";

    /**
     * 分页方式
     * {@link PageHandlerType}
     */
    private Integer pageHandlerType;
    /**
     * 每页显示的数量
     * 默认值 {@link PageHandlerConstant#DEFAULT_PAGE_SIZE}
     */
    private Integer pageSize = PageHandlerConstant.DEFAULT_PAGE_SIZE;
    /**
     * 属性
     */
    private Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    public String getOffsetAsPageNum() {
        return properties.getProperty("offsetAsPageNum");
    }

    public void setOffsetAsPageNum(String offsetAsPageNum) {
        properties.setProperty("offsetAsPageNum", offsetAsPageNum);
    }

    public String getRowBoundsWithCount() {
        return properties.getProperty("rowBoundsWithCount");
    }

    public void setRowBoundsWithCount(String rowBoundsWithCount) {
        properties.setProperty("rowBoundsWithCount", rowBoundsWithCount);
    }

    public String getPageSizeZero() {
        return properties.getProperty("pageSizeZero");
    }

    public void setPageSizeZero(String pageSizeZero) {
        properties.setProperty("pageSizeZero", pageSizeZero);
    }

    public String getReasonable() {
        return properties.getProperty("reasonable");
    }

    public void setReasonable(String reasonable) {
        properties.setProperty("reasonable", reasonable);
    }

    public String getSupportMethodsArguments() {
        return properties.getProperty("supportMethodsArguments");
    }

    public void setSupportMethodsArguments(String supportMethodsArguments) {
        properties.setProperty("supportMethodsArguments", supportMethodsArguments);
    }

    public String getDialect() {
        return properties.getProperty("dialect");
    }

    public void setDialect(String dialect) {
        properties.setProperty("dialect", dialect);
    }

    public String getHelperDialect() {
        return properties.getProperty("helperDialect");
    }

    public void setHelperDialect(String helperDialect) {
        properties.setProperty("helperDialect", helperDialect);
    }

    public String getAutoRuntimeDialect() {
        return properties.getProperty("autoRuntimeDialect");
    }

    public void setAutoRuntimeDialect(String autoRuntimeDialect) {
        properties.setProperty("autoRuntimeDialect", autoRuntimeDialect);
    }

    public String getAutoDialect() {
        return properties.getProperty("autoDialect");
    }

    public void setAutoDialect(String autoDialect) {
        properties.setProperty("autoDialect", autoDialect);
    }

    public String getCloseConn() {
        return properties.getProperty("closeConn");
    }

    public void setCloseConn(String closeConn) {
        properties.setProperty("closeConn", closeConn);
    }

    public String getParams() {
        return properties.getProperty("params");
    }

    public void setParams(String params) {
        properties.setProperty("params", params);
    }

    public String getDefaultCount() {
        return properties.getProperty("defaultCount");
    }

    public void setDefaultCount(String defaultCount) {
        properties.setProperty("defaultCount", defaultCount);
    }

    public String getDialectAlias() {
        return properties.getProperty("dialectAlias");
    }

    public void setDialectAlias(String dialectAlias) {
        properties.setProperty("dialectAlias", dialectAlias);
    }

}
