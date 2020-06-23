package cn.sunnymaple.web.db.mybatis;

/**
 * 常量
 * @author wangzb
 * @date 2019/12/11 18:11
 */
public class PageHandlerConstant {
    /**
     * 默认的当前页（pageNum）参数名称
     */
    public static final String DEFAULT_PAGE_NUM_KEY_NAME = "pageNum";
    /**
     * 默认的没有显示的数量（pageSize）参数名称
     */
    public static final String DEFAULT_PAGE_SIZE_KEY_NAME = "pageSize";
    /**
     * spEl表达式开头 - #
     */
    public static final String PARAM_NAME_START_WITH = "#";
    /**
     * spEl表达式开头 #a
     */
    public static final String PARAM_NAME_START_WITH_A = "#a";
    /**
     * spEl表达式开头 #p
     */
    public static final String PARAM_NAME_START_WITH_P = "#p";
    /**
     * 默认的当前页为第1页
     */
    public static final Integer DEFAULT_PAGE_NUM = 1;

    /**
     * 数据库方言
     */
    public static final String[] HELPER_DIALECT = {"helper-dialect","helperDialect"};
    /**
     * 默认的数据库方言为mysql
     */
    public static final String DEFAULT_DIALECT = "mysql";
    /**
     * 默认每页数量
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;



}
