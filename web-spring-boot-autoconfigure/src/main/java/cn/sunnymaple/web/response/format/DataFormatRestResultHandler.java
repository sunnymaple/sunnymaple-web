package cn.sunnymaple.web.response.format;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.sunnymaple.web.response.BeforeBodyWriteParameter;
import cn.sunnymaple.web.response.IRestResultHandler;
import cn.sunnymaple.web.response.format.exception.ClassCastDataFormatException;
import cn.sunnymaple.web.response.format.exception.DataFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 参数格式化{@link IRestResultHandler}
 * @author wangzb
 * @date 2020/4/7 10:40
 */
@Slf4j
public class DataFormatRestResultHandler implements IRestResultHandler {

    /**
     * 是否可以处理，返回true，则执行handle方法
     * @param restResult rest 响应结果对象
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @return
     */
    @Override
    public boolean canHandle(Object restResult, BeforeBodyWriteParameter beforeBodyWriteParameter) {
        MethodParameter methodParameter = beforeBodyWriteParameter.getMethodParameter();
        return methodParameter.getDeclaringClass().getAnnotation(DataFormat.class) != null ||
                methodParameter.getMethodAnnotation(DataFormat.class) != null;
    }

    /**
     * 处理响应结果对象
     * @param restResult rest 响应结果对象
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @return
     */
    @Override
    public Object handle(Object restResult, BeforeBodyWriteParameter beforeBodyWriteParameter){
        MethodParameter methodParameter = beforeBodyWriteParameter.getMethodParameter();
        try {
            //首先判断方法（接口）中是否有相关注解
            Method method = methodParameter.getMethod();
            Annotation methodAnnotation = getHasFormatTypeAnnotation(method.getAnnotations());
            //响应数据类型
            Class<?> type = restResult == null ? method.getReturnType() : restResult.getClass();
            if(isBasicTypeOrString(type)){
                //基本类型或者String
                return format(methodAnnotation,restResult,methodParameter);
            }else {
                return formatReferenced(restResult,type,methodAnnotation,null,beforeBodyWriteParameter);
            }
        }catch (IllegalAccessException | InstantiationException e){
            throw new DataFormatException(e,getClassPath(methodParameter));
        }
    }

    /**
     * 获取执行的类（接口）全路径
     * @param methodParameter {@link MethodParameter}
     * @return
     */
    private String getClassPath(MethodParameter methodParameter){
        return methodParameter.getDeclaringClass() + "." + methodParameter.getMethod().getName();
    }

    /**
     * 格式化操作
     * @param annotation 带有{@link FormatType}注解的相关格式化注解
     * @param tar 目标值（对象）
     * @return
     */
    private Object format(Annotation annotation,Object tar,MethodParameter methodParameter)
            throws IllegalAccessException, InstantiationException {
        if (annotation != null){
            FormatType formatType = annotation.annotationType().getAnnotation(FormatType.class);
            Class<? extends IDataFormat> dataFormatClass = formatType.formatBy();
            IDataFormat dataFormat = dataFormatClass.newInstance();
            dataFormat.initialize(annotation,getClassPath(methodParameter));
            return dataFormat.format(tar);
        }
        return tar;
    }

    /**
     * 从目标注解数组中获取所有带{@link FormatType}注解的注解
     * @param tarAnnotations 目标注解
     * @return List<Annotation> 当存在带有{@link FormatType}注解的注解时返回第一个，暂不支持多项目格式化
     *         null 如果不存在有{@link FormatType}注解的注解时返回null
     */
    private Annotation getHasFormatTypeAnnotation(Annotation[] tarAnnotations){
        if (ArrayUtil.isNotEmpty(tarAnnotations)){
            return Arrays.stream(tarAnnotations)
                    .filter(annotation -> ObjectUtil.isNotEmpty(annotation.annotationType()
                            .getAnnotation(FormatType.class)))
                    .findFirst().orElse(null);
        }
        return null;
    }

    /**
     * 判断是否是基本类型或者String类型
     * @param tarClass
     * @return
     */
    private boolean isBasicTypeOrString(Class tarClass){
        return ClassUtil.isBasicType(tarClass) || tarClass == String.class;
    }

    /**
     * 相应参数为引用类型（非String）数据格式化
     * @param target 目标对象
     * @param targetClass 目标对象的{@link Class}
     * @param annotation 带有{@link FormatType}注解的相关格式化注解
     * @param fieldName 目标对象的字段名称
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @param
     * @return
     */
    private Object formatReferenced(Object target ,Class targetClass,Annotation annotation,String fieldName,BeforeBodyWriteParameter beforeBodyWriteParameter)
            throws IllegalAccessException, InstantiationException {
        //判断是否为空
        if (target == null){
            return formatNull(targetClass,annotation,beforeBodyWriteParameter.getMethodParameter());
        }
        if (target instanceof Map){
            //Map
            return format(annotation,target,beforeBodyWriteParameter.getMethodParameter());
        }else if (target instanceof List){
            //List
            List list = (List) target;
            formatList(list,annotation,fieldName,beforeBodyWriteParameter);
            return list;
        } else if (ArrayUtil.isArray(target)){
            //数组
            List list = Arrays.asList((Object[]) target);
            formatList(list,annotation,fieldName,beforeBodyWriteParameter);
            return target;
        } else{
            //对象
            formatObj(target,targetClass,beforeBodyWriteParameter);
        }
        return target;
    }

    /**
     * 目标为引用类型的对象
     * @param target 目标对象
     * @param targetClass 目标对象的{@link Class}
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     * @param
     * @return
     */
    private void formatObj(Object target ,Class targetClass,BeforeBodyWriteParameter beforeBodyWriteParameter) throws IllegalAccessException, InstantiationException {
        List<Field> fields = getFields(targetClass);
        for (int i=0;i<fields.size();i++){
            Field field = fields.get(i);
            Class<?> type = field.getType();
            String objFieldName = field.getName();
            //判断字段是否存在相关注解
            Optional<Annotation> first = Optional
                    .ofNullable(getHasFormatTypeAnnotation(field.getAnnotations()));
            if (first.isPresent()){
                Annotation objAnnotation = first.get();
                //字段的访问权限
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                //获取字段值
                Object fieldValue = field.get(target);
                if (isBasicTypeOrString(type)){
                    Object formatResult = format(first.get(), fieldValue,beforeBodyWriteParameter.getMethodParameter());
                    checkClass(formatResult.getClass(),type,objFieldName,beforeBodyWriteParameter.getMethodParameter());
                    field.set(target,formatResult);
                } else if (fieldValue instanceof Map){
                    field.set(target,format(objAnnotation,fieldValue,beforeBodyWriteParameter.getMethodParameter()));
                } else{
                    field.set(target,formatReferenced(fieldValue,type, objAnnotation,objFieldName,beforeBodyWriteParameter));
                }
                field.setAccessible(accessible);
            }
        }
    }

    /**
     * 为null时的处理
     * @param targetClass 目标对象的类型
     * @param annotation 带有{@link FormatType}注解的相关格式化注解
     * @param methodParameter {@link MethodParameter}
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Object formatNull(Class targetClass,Annotation annotation,MethodParameter methodParameter) throws InstantiationException, IllegalAccessException {
        if (isBasicTypeOrString(targetClass)){
            //为null 且原本类型为基本类型或者String
            return format(annotation,null,methodParameter);
        }
        return null;
    }

    /**
     * 对list参数对象进行格式化操作
     * @param list 目标list对象
     * @param annotation 带有{@link FormatType}注解的相关格式化注解
     * @param fieldName 目标对象的字段名称
     * @param beforeBodyWriteParameter {@link BeforeBodyWriteParameter}
     */
    private void formatList(List list,Annotation annotation,String fieldName,BeforeBodyWriteParameter beforeBodyWriteParameter)
            throws InstantiationException, IllegalAccessException {
        if (annotation!=null){
            for (int i=0;i<list.size();i++){
                Object value = list.get(i);
                Class<?> aClass = value.getClass();
                if (isBasicTypeOrString(aClass)){
                    Object formatResult = format(annotation, value,beforeBodyWriteParameter.getMethodParameter());
                    checkClass(formatResult.getClass(),aClass,fieldName,beforeBodyWriteParameter.getMethodParameter());
                    list.set(i,formatResult);
                }else {
                    list.set(i,formatReferenced(value,aClass,annotation,fieldName,beforeBodyWriteParameter));
                }
            }
        }
    }

    /**
     * 获取所有属性（包括父类的字段）
     * @param tagClass 目标类
     * @return
     */
    private List<Field> getFields(Class tagClass){
        List<Field> fields = new ArrayList<>();
        //当父类为null的时候说明到达了最上层的父类(Object类).
        while (tagClass != null) {
            fields.addAll(Arrays.asList(tagClass .getDeclaredFields()).stream().filter(field ->
                    !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())
            ).collect(Collectors.toList()));
            //得到父类,然后赋给自己
            tagClass = tagClass.getSuperclass();
        }
        return fields;
    }

    /**
     * 核对格式化后的类型是否和原类型是否一致
     * 不一致则抛出{@link ClassCastDataFormatException}异常
     * @param tagClass 目标class,即格式化后的类型
     * @param sourceClass 原类型
     * @param methodParameter {@link MethodParameter}
     */
    private void checkClass(Class tagClass,Class sourceClass,String fieldName,MethodParameter methodParameter){
        if (tagClass != sourceClass){
            throw new ClassCastDataFormatException(getClassPath(methodParameter),tagClass,sourceClass,fieldName);
        }
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
