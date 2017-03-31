package com.zhiweicloud.guest.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BeanCompareUtils.java
 *
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017/3/30 10:33
 * @author tiecheng
 */
public class BeanCompareUtils {

    private static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 比较两个实体类有哪些字段不一致
     * 用到了自定义的注解
     *
     * @param t1 基础对象
     * @param t2 比较对象
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T> String compareTwoBean(T t1, T t2) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
        Object value; // 输出值
        String name;    // 输出名字
        StringBuilder sb = new StringBuilder("{");
        Class<?> clazz = t1.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 如果有DisplayName注解才继续
            DisplayName displayName = field.getAnnotation(DisplayName.class);
            if (displayName != null) {
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                Method getMethod = pd.getReadMethod();//获得get方法
                Object o1 = getMethod.invoke(t1);
                Object o2 = getMethod.invoke(t2);
                // 给输出的key和value设置初始值
                value = o2;
                name = displayName.name();
                // 处理时间日期格式
                if (field.getType().getName().contains("Date") && o2 != null) {
                    value = simpleDateFormat.format((Date) o2);
                }
                DisplayValue[] map = displayName.map();
                if (o2 != null && !"".equals(o2.toString()) && !o2.equals(o1)) {
                    value = getValue(map, value, o2);
                    sb.append("\"").append(name).append("\"").append(":").append("\"").append(value).append("\"").append(",");
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1).append("}");
        return sb.toString();
    }

    /**
     * 根据key得到value 用于存储标识和中文对应
     * @param map
     * @param value
     * @param o
     * @return
     */
    private static Object getValue(DisplayValue[] map,Object value,Object o) {
        if (map.length > 0) {
            for (DisplayValue displayValue : map) {
                boolean equals = displayValue.key().equals(String.valueOf(o));
                if (equals) {
                    value = displayValue.value();
                }
            }
        }
        return value;
    }

}
