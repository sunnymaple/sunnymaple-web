package cn.sunnymaple.web.response.format;

/**
 * 定义数据格式化对象，所有的格式化类实现该接口
 * 每个格式化操作对应一个注解，通过{@link FormatType#formatBy()}注解指定
 * @author wangzb
 * @date 2020/4/8 17:40
 */
public interface IDataFormat<A> {
    /**
     * 初始化操作，设置对应注解
     * @param annotation 格式化注解
     * @param classPath 当前类路径
     */
    default void initialize(A annotation,String classPath){}

    /**
     * 格式化操作
     * @param data 响应数据对象
     * @return
     */
    Object format(Object data);
}
