package cn.sunnymaple.web.response.format.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.sunnymaple.web.error.me.annotation.ExceptionMapping;
import cn.sunnymaple.web.response.format.AbstractDataFormat;
import cn.sunnymaple.web.response.format.exception.ClassCastDataFormatException;
import cn.sunnymaple.web.response.format.exception.DataFormatException;
import cn.sunnymaple.web.response.format.exception.NoFormatFieldException;
import cn.sunnymaple.web.response.format.exception.NotFindFieldException;
import lombok.Data;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static cn.hutool.core.math.Money.DEFAULT_CURRENCY_CODE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 金额格式化操作实现
 * @author wangzb
 * @date 2020/4/8 17:49
 */
@ExceptionMapping(errorCode= "dataformat.error", statusCode = INTERNAL_SERVER_ERROR)
public class MoneyDataFormatImpl extends AbstractDataFormat<Money> {
    /**
     * 金额格式化模式
     */
    private static final String FORMAT_PATTERN = ",###.00";

    /**
     * 格式化操作
     *
     * @param data 响应数据对象
     * @return
     */
    @Override
    public Object format(Object data) {
        if (ObjectUtil.isEmpty(data)){
            return formatNull();
        }
        if (data instanceof Map){
            return formatMap(data);
        }
        return formatMoney(data);
    }

    /**
     * null的处理
     * @return
     */
    private Object formatNull(){
        return annotation.moneyEmptyMode().getValue();
    }

    /**
     * 对map处理
     * @param data 响应数据对象
     * @return
     */
    private Map formatMap(Object data){
        String[] names = annotation.name();
        if (ArrayUtil.isEmpty(names)){
            throw new NoFormatFieldException(classPath);
        }
        Map map = (Map) data;
        for (String key : names){
            if (!map.containsKey(key)){
                throw new NotFindFieldException(classPath,key);
            }
            Object value = map.get(key);
            if (ObjectUtil.isEmpty(value)){
                map.put(key,formatNull());
            }
            Class valueClass = value.getClass();
            if (valueClass != String.class){
                throw new ClassCastDataFormatException(classPath,valueClass,String.class,key);
            }
            String formatValue = formatMoney(value);
            map.put(key,formatValue);
        }
        return map;
    }

    /**
     * 格式化操作
     * @param data
     * @return
     */
    private String formatMoney(Object data){
        if (!NumberUtil.isNumber(data.toString())){
            int[] formatIndexs = annotation.formatIndex();
            if (ArrayUtil.isEmpty(formatIndexs)){
                throw new DataFormatException("金额格式化：" + data.toString() + "非数字");
            }
            //读取字符串中的数字
            Target target = new Target();
            target.init(data.toString());
            List<String> numbers = target.getNumbers();
            //格式化
            for (int formatIndex : formatIndexs){
                numbers.set(formatIndex,formatMoney(numbers.get(formatIndex)));
            }
            return StrUtil.format(target.getTarget(),numbers.toArray());
        }
        RoundingMode roundingMode = annotation.roundingMode();
        cn.hutool.core.math.Money money = new cn.hutool.core.math.Money(data.toString(),
                Currency.getInstance(DEFAULT_CURRENCY_CODE),roundingMode);
        String result = new DecimalFormat(FORMAT_PATTERN).format(money.getAmount());
        return result.startsWith(StrUtil.DOT) ? ("0" + result) : result;
    }

    @Data
    class Target{
        /**
         * 目标字符串
         */
        private String target;
        /**
         * 在目标字符串中抽取的数字
         */
        private List<String> numbers;

        /**
         * 初始化方法，从目标字符串中读取数字
         * @param target 目标字符串
         * @return Map<String,Double> key 为员数字
         */
        public void init(String target){
            List<String> nums = new ArrayList<>(8);
            String[] subStr = StrUtil.cut(target, 1);
            StringBuilder num = new StringBuilder();
            for (String sub : subStr){
                //是否是数字
                boolean isNum = NumberUtil.isNumber(sub);
                //是否是小数点
                boolean isPoint = Objects.equals(sub,StrUtil.DOT);
                //数字是否终止
                boolean isEnd = !StrUtil.isBlank(num.toString()) && !(isNum || isPoint);
                if (isEnd){
                    target = calcNumber(nums,num,target);
                }
                if (isNum || isPoint){
                    //是小时点，且num不为空（""）
                    if (isPoint && StrUtil.isBlank(num.toString())){
                        continue;
                    }
                    num.append(sub);
                }
            }
            if (StrUtil.isNotBlank(num.toString())){
                target = calcNumber(nums,num,target);
            }
            this.numbers = nums;
            this.target = target;
        }

        /**
         * 获得数字，并返回被{}符号替换后的模板字符串
         * @param nums number集合
         * @param num 当前字符串形式的数字
         * @param target 模板字符串
         * @return
         */
        private String calcNumber(List<String> nums,StringBuilder num,String target){
            String numStr = num.toString();
            nums.add(numStr);
            String repTarget = target.replace(numStr,StrUtil.EMPTY_JSON);
            //清空下num
            num.delete(0,numStr.length());
            return repTarget;
        }
    }
}
