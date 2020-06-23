package cn.sunnymaple.web.response.format.impl;

/**
 * 为空时的模式处理
 * @author wangzb
 * @date 2020/4/9 16:13
 */
public enum MoneyEmptyMode {
    /**
     * 为空时不处理，即保持为空
     */
    EMPTY(null),
    /**
     * 为空时设置为0
     */
    ZERO("0.00");

    private Object value;

    MoneyEmptyMode(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
