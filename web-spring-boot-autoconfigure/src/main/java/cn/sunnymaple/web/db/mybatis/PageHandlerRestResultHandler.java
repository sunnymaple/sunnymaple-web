package cn.sunnymaple.web.db.mybatis;

import cn.sunnymaple.web.response.BeforeBodyWriteParameter;
import cn.sunnymaple.web.response.IRestResultHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 响应统一格式的参数前
 * 如果是分页对象，将响应对象转为PageInfo对象
 * @author wangzb
 * @date 2019/12/12 12:59
 */
public class PageHandlerRestResultHandler implements IRestResultHandler {

    /**
     * 是否可以处理，返回true，则执行handle方法
     * @param restResult rest 响应结果对象
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @return
     */
    @Override
    public boolean canHandle(Object restResult, BeforeBodyWriteParameter beforeBodyWriteParameter) {
        //是Page对象则表示是一个分页查询
        return restResult instanceof Page;
    }

    /**
     * 处理响应结果对象
     * @param restResult rest 响应结果对象
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @return
     */
    @Override
    public Object handle(Object restResult, BeforeBodyWriteParameter beforeBodyWriteParameter) {
        //分页查询，返回PageInfo对象
        return new PageInfo((Page) restResult);
    }
}
