package cn.sunnymaple.web.response;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.sunnymaple.web.error.Errors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 统一格式的响应参数
 * @author wangzb
 * @date 2019/12/4 15:08
 */
@Slf4j
@ControllerAdvice
public class AppResponseHandler implements ResponseBodyAdvice {

    @Autowired
    protected AppResponseHandlerProperties properties;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected IRestResultFactory restResultFactory;

    /**
     * 响应统一格式参数之前，对响应体做修改
     */
    @Autowired(required = false)
    private List<IRestResultHandler> restResultHandlers;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * 接口是否支持响应统一格式的参数
     * @param beanClass 类class
     * @param method {@link Method}
     * @param o 响应结果对象
     * @return true ：
     *      ①、response.handler.enabled=true
     *      false：
     *      ①、o instanceof IRestResult（即响应对象本身就是一个IRestResult对象，无需重复封装）
     *      ②、response.handler.enabled=false
     *      ③、response.handler.noResponseHandler指定的接口等
     *      ④、接口方法或者类上使用@NoResponseHandler注解
     */
    public boolean supports(Class<?> beanClass, Method method, Object o) {
        if (!properties.isEnabled()){
            //response.handler.enabled=false或者o本身是一个IRestResult对象
            return false;
        }
        //判断类是否加上了NoResponseHandler注解，如果是则返回false
        NoResponseHandler classNoResponseHandler = beanClass.getAnnotation(NoResponseHandler.class);
        if (classNoResponseHandler!=null){
            return false;
        }
        //判断方法（接口）是否加上了NoResponseHandler注解，如果是则返回false
        NoResponseHandler noResponseHandler = method.getAnnotation(NoResponseHandler.class);
        if (noResponseHandler!=null){
            return false;
        }
        //从配置文件中获取过滤的接口
        String path = request.getServletPath();
        List<String> exclusions = properties.getExclusions();
        return !exclusions
                .stream()
                .filter(exclusion -> new AntPathMatcher().match(exclusion, path))
                .findFirst()
                .isPresent();
    }
    /**
     * 接口是否支持响应统一格式的参数
     * @param methodParameter
     * @param o 结果对象
     * @return
     */
    public boolean supports(MethodParameter methodParameter, Object o) {
        return supports( methodParameter.getDeclaringClass(),methodParameter.getMethod(),o);
    }

    /**
     * 响应统一格式的参数
     * @param o 响应参数对象
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.debug("current uri:" + request.getServletPath());
        if (o instanceof Resource){
            //如果o为资源文件，则直接返回
            return o;
        }
        //返回类型
        Type returnType = methodParameter.getMethod().getReturnType();
        boolean isString = o instanceof String;
        BeforeBodyWriteParameter beforeBodyWriteParameter =
                new BeforeBodyWriteParameter(methodParameter,mediaType,aClass,serverHttpRequest,serverHttpResponse);
        //响应统一格式前对响应体的处理
        if (CollUtil.isNotEmpty(restResultHandlers)){
            for (IRestResultHandler restResultHandler : restResultHandlers){
                if (restResultHandler.canHandle(o,beforeBodyWriteParameter)){
                    o = restResultHandler.handle(o,beforeBodyWriteParameter);
                }
            }
        }
        //是否响应同一格式的参数
        boolean enable = supports(methodParameter,o);
        if (enable){
            //响应统一格式的参数
            int status = response.getStatus();
            if (status!= HttpStatus.OK.value()){
                JSONObject jsonObject = JSONUtil.parseFromMap((Map) o);
                Errors errors = JSONUtil.toBean(jsonObject, Errors.class);
                o = restResultFactory.failure(errors);
            }else {
                o = restResultFactory.success(o,beforeBodyWriteParameter);
            }
            return writeValueAsString(o,returnType,isString);
        }
        return o;
    }

    /**
     * 将对象转json字符串，并序列化
     * 支持实体类对象、Map集合、Collection集合
     * @param result 目标对象
     * @param isString 返回对象是否为String类型
     * @return
     * @throws JsonProcessingException
     */
    protected static Object writeValueAsString(Object result, Type returnType,boolean isString) {
        if (isString || returnType == String.class){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) { }
        }
        return result;
    }
}
