package cn.sunnymaple.web.login.controller;

import cn.sunnymaple.web.login.exception.NotLoginException;
import org.springframework.ui.ModelMap;

import javax.validation.Valid;

/**
 * 定义登录接口的Controller规范，针对有PC版具有页面的系统，如后台管理系统
 * @author wangzb
 * @date 2020/3/23 14:06
 */
public interface ILoginViewController extends ILoginController{
    /**
     * 登录页面，可以使用项目根路径
     * 直接访问登录页
     * @param url 登录成功后跳转的页面
     * @param modelMap 模型
     * @return 登录页面的视图
     */
    String loginView(String url, ModelMap modelMap);

    /**
     * 登录
     * @param LoginParam 登录参数 {@link LoginParam}
     * @param modelMap 模型
     * @return 视图
     */
    String login(@Valid LoginParam LoginParam, ModelMap modelMap);

    /**
     * 被登录拦截时调用的接口
     * @param modelMap 模型
     */
    String notLogin(ModelMap modelMap);

    /**
     * <p>ResponseBody请求时未登录接口</p>
     * <p>如ajax请求等</p>
     * <p>前端可以使用全局的登录超时处理：</p>
     * <p>$.ajaxSetup({</p>
     * <p>  statusCode: {</p>
     * <p>     401: function () {</p>
     * <p>         layPop.error("未登录或者登录超时",function () {</p>
     * <p>             window.open (path,'_top');</p>
     * <p>         });</p>
     * <p>     }</p>
     * <p>  }</p>
     * <p>});</p>
     * @throws NotLoginException
     */
    void notLogin() throws NotLoginException;

    /**
     * 退出登录，一般作删除session或者token删除操作
     * @param modelMap 模型{@link LoginParam}
     * @return 视图，一般为登录页面
     */
    String logout(ModelMap modelMap);

}
