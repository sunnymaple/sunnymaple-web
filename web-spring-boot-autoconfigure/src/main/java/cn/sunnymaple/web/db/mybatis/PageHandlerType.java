package cn.sunnymaple.web.db.mybatis;

/**
 * 分页类型
 * pageNum参数缺省时，分页的方式
 * 分页方式有两种途径获取：
 * 1、从全局配置中获取
 * 2、在PageHelper注解中指定
 *   优先级顺序：
 *   1>2
 *  即：如果全局配置了，则按全局配置方式
 *  如果全局未配置，则按注解中指定的方式
 * @author wangzb
 * @date 2019/12/11 17:16
 */
public enum PageHandlerType {
    /**
     * 分页，且默认查询第一页
     */
    PAGE_AND_DEFAULT(1),
    /**
     * 不分页
     */
    NOT_PAGE(2);

    private Integer type;

    PageHandlerType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
