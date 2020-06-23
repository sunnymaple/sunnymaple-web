package cn.sunnymaple.web.response.format;

import java.lang.annotation.Annotation;

/**
 * 抽象的{@link IDataFormat}
 * @author wangzb
 * @date 2020/4/8 17:50
 */
public abstract class AbstractDataFormat<A extends Annotation> implements IDataFormat<A>{
    /**
     * 注解
     */
    protected A annotation;
    /**
     * 当前类路径
     */
    protected String classPath;

    /**
     * 初始化操作，设置对应注解
     *
     * @param annotation 格式化注解
     * @param classPath 当前类路径
     */
    @Override
    public void initialize(A annotation,String classPath) {
        this.annotation = annotation;
        this.classPath = classPath;
    }
}
