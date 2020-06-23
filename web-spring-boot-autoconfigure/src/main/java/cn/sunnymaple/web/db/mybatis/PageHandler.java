package cn.sunnymaple.web.db.mybatis;

import java.lang.annotation.*;

/**
 * 分页
 * 在分页的（controller层）接口加上该注解，表示该接口是一个分页接口
 * 该功能基于mybatis插件扩展，使用者不用在sql语句中显示的使用分页（
 *      如：mysql分页使用limit，且得自行查询出总数。而使用mybatis分页插件则不用显示的使用limit语句，也不用写查询总数的sql）
 * 但是如果使用mybatis插件，使用者可能需要在分页前调用：
 * PageHelper.startPage(int pageNum, int pageSize)
 * 而使用该注解的目的就是为了省略这一步，做到分页与业务代码正真的解耦
 *
 *
 *
 * 需要注意的是：
 * MVC三层的任意一层方法上加上@PageHandler即可
   一般业务逻辑都在service层实现，该注解只能满足第一条sql语句是分页查询，但不保证第一条sql语句一定是分页查询，
        因为如果第一条查询不是返回list或者第一条并非select语句（insert、update或者delete）。
   接口返回对象必须保证是Page对象，否则响应统一格式的参数模块无法将Page对象封装成PageInfo对象，
      即前端需要的分页信息，包括当前也的list数据、下一页、上一页以及总页数等
 * @author wangzb
 * @date 2019/12/11 17:10
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageHandler {
    /**
     * pageNum参数缺省时，分页的方式
     * 分页方式有两种途径获取：
     * 1、从全局配置中获取
     * 2、在PageHelper注解中指定
     *
     *
     *
     * 优先级顺序：
     *  1>2
     *  即：如果全局配置了，则按全局配置方式
     *  如果全局未配置，则按注解中指定的方式
     * {@link PageHandlerType}
     * @return
     */
    PageHandlerType pageHandlerType() default PageHandlerType.PAGE_AND_DEFAULT;

    /**
     * 当前页参数名称
     * 支持以下spEL表达式：
     * 1、pageNumKey=#参数名，如pageNumKey=#currentPage 表示当前分页参数名称为currentPage，则将从接口请求参数中获取currentPage参数值
     * 2、pageNumKey=#参数名.属性名，如pageNumKey=#paramName.pageNum，表示从参数对象paramName中获取属性pageNum的值
     * 3、pageNumKey=p(Index)，如pageNumKey=p0，表示获取接口的第一个参数获得
     * 4、pageNumKey=a(Index)，如pageNumKey=a0，表示获取接口的第一个参数获得
     * 5、pageNumKey=参数名
     *
     * 其中
     * ①、第1/2/3/4条，将会方法的参数中获取值，所以接口参数必须要有该参数
     * ②、第5条，直接从request域中获取，所有接口并非必须显示该参数
     * ③、如果不指定pageNumKey，则默认为pageNum，将从request域中获取pageNum的值
     * ④、所有方式若获取不到值，都将遵循pageHelperType给定的值。
     *
     * @return
     */
    String pageNumKey() default PageHandlerConstant.DEFAULT_PAGE_NUM_KEY_NAME;

    /**
     * 每页显示的数量参数名称
     * 支持spEL表达式，同pageNumKey
     * @return
     */
    String pageSizeKey() default PageHandlerConstant.DEFAULT_PAGE_SIZE_KEY_NAME;

    /**
     * 每页显示的数量，该值有三种方式可以获得
     * 1、全局配置，默认为{@link PageHandlerConstant#DEFAULT_PAGE_SIZE}
     * 2、@PageHandler的pageSize指定大小
     * 3、客户端（前端），传递pageSizeKey指定的参数
     *
     * 优先级顺序：3>2>1
     *
     * 即：
     * ①、先会判断客户端（前端）是否有传递pageSizeKey指定的参数，如果有，则按该大小显示每页数量
     * ②、如果第①步客户端（前端）没有传递值pageSizeKey指定的参数，则按pageSize指定值，注意该值必须大于等于1（注解中的默认值-1是无效的），否则将进行第③步判断
     * ③、从全局配置中获取pageSize的值
     *
     * @return
     */
    int pageSize() default -1;
}
