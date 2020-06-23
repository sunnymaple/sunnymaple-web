package cn.sunnymaple.web.login.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wangzb
 * @date 2020/3/23 14:57
 */
@Component
@Aspect
public class LoginAspect implements ApplicationContextAware {
    /**
     * 拦截器
     */
    @Autowired(required = false)
    @Interceptor
    private List<ILoginPostInterceptor> interceptors;

    private ApplicationContext applicationContext;

    /**
     * 定义切点为加@Login注解的方法
     */
    @Pointcut("@annotation(cn.sunnymaple.web.login.interceptor.Login)")
    public void pointcut(){}

    @AfterReturning(value = "pointcut()",returning="returnValue")
    public void afterReturning(JoinPoint point, Object returnValue){
        interceptor(point,returnValue);
    }

    private void interceptor(JoinPoint point,Object obj){
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Login login = methodSignature.getMethod().getAnnotation(Login.class);
        //执行拦截器
        List<ILoginPostInterceptor> interceptorList = getInterceptors();
        Class<? extends ILoginPostInterceptor>[] classes = login.loginPostInterceptor();
        if (classes!=null && classes.length>0){
            interceptorList.addAll(Arrays.stream(classes)
                    .map(aClass -> applicationContext.getBean(aClass))
                    .sorted()
                    .collect(Collectors.toList()));
        }
        interceptorList.stream()
                .sorted()
                .collect(Collectors.toList())
                .forEach(interceptor->interceptor.interceptor(obj));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private List<ILoginPostInterceptor> getInterceptors(){
        return Optional.ofNullable(interceptors).orElse(new ArrayList<>());
    }
}
