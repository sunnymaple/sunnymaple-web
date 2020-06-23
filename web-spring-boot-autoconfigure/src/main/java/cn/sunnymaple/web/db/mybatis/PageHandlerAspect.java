package cn.sunnymaple.web.db.mybatis;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 分页切面
 * @author wangzb
 * @date 2019/12/11 17:21
 */
@Aspect
public class PageHandlerAspect {

    @Autowired
    private List<IPageHandlerParamResolver> pageHandlerParamResolvers;

    @Autowired
    private PageHandlerProperties properties;

    /**
     * 定义切点为加@PageHelper注解的方法
     */
    @Pointcut("@annotation(cn.sunnymaple.web.db.mybatis.PageHandler)")
    public void pointcut(){}

    /**
     * 分页之前，
     * 从参数中获取请求分页参数，并设置静态分页
     * @param joinPoint
     */
    @Before(value = "pointcut()")
    public void pageHandlerBefore(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法
        Method method = methodSignature.getMethod();
        //获取注解
        PageHandler pageHandler = method.getAnnotation(PageHandler.class);
        //当前页
        Integer pageNum = getPageNum(joinPoint, pageHandler);
        if (ObjectUtil.isNotEmpty(pageNum)){
            //每页显示的数量
            Integer pageSize = getPageSize(joinPoint,pageHandler);
            //创建静态分页
            PageHelper.startPage(pageNum, pageSize);
        }
    }

    /**
     * 获取每页显示的数量
     * @param joinPoint {@link JoinPoint}
     * @param pageHandler {@link PageHandler}
     * @return
     */
    private Integer getPageSize(JoinPoint joinPoint, PageHandler pageHandler){
        //每页的数量，先从请求参数中获取
        String pageSizeKey = pageHandler.pageSizeKey();
        Optional<Integer> pageSizeOp = resolver(joinPoint, pageSizeKey);
        return pageSizeOp.orElseGet(()->{
            //从注解PageHandler中获取
            Integer pageSize = pageHandler.pageSize();
            if (ObjectUtil.isEmpty(pageSize) || pageSize<=0){
                //从全局配置中获取
                pageSize = properties.getPageSize();
            }
            return pageSize;
        });
    }

    /**
     * 获取当前页
     * @param joinPoint {@link JoinPoint}
     * @param pageHandler {@link PageHandler}
     * @return
     */
    private Integer getPageNum(JoinPoint joinPoint, PageHandler pageHandler){
        //当前页
        String pageNumKey = pageHandler.pageNumKey();
        Optional<Integer> pageNumOp = resolver(joinPoint, pageNumKey);
        return pageNumOp.orElseGet(()->{
            Integer pageHandlerType = getPageHandlerType(pageHandler);
            if (Objects.equals(pageHandlerType,PageHandlerType.NOT_PAGE.getType())){
                return null;
            }else {
                return PageHandlerConstant.DEFAULT_PAGE_NUM;
            }
        });
    }

    /**
     * 获取分页方式
     * @param pageHandler {@link PageHandler}
     * @return
     */
    private Integer getPageHandlerType(PageHandler pageHandler){
        //判断分页方式
        Integer pageHandlerType =  properties.getPageHandlerType();
        if (ObjectUtil.isEmpty(pageHandlerType)){
            pageHandlerType = pageHandler.pageHandlerType().getType();
        }
        return pageHandlerType;
    }

    /**
     * 解析获取分页参数值
     * @param joinPoint {@link JoinPoint}
     * @param expression 表达式
     * @return
     */
    private Optional<Integer> resolver(JoinPoint joinPoint, String expression){
        return pageHandlerParamResolvers
                .stream()
                .filter(pageHandlerParamResolver -> pageHandlerParamResolver.supports(expression))
                .findFirst()
                .map(pageHandlerParamResolver -> pageHandlerParamResolver.resolver(joinPoint, expression));
    }


}
