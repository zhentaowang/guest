package com.zhiweicloud.guest.common;

import com.zhiweicloud.guest.model.Flight;

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
                if (o2 != null && !o2.equals(o1)) {
                    value = getValue(map, value, o2);
                    sb.append("\"").append(name).append("\"").append(":").append("\"").append(value).append("\"").append(",");
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1).append("}");
        return sb.toString();
    }

    private static Object getValue(DisplayValue[] map,Object value,Object o) {
        if (map.length > 0) {
            for (DisplayValue displayValue : map) {
                if (displayValue.key().equals(o)) {
                    value = displayValue.value();
                }
            }
        }
        return value;
    }

    public static void main(String[] args) throws Exception {
        Flight flight1 = new Flight();
        Flight flight2 = new Flight();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        flight1.setFlightDate(format.parse("2017-03-29"));
        flight2.setFlightDate(format.parse("2017-03-29"));
        flight1.setAirportCode("LJG");
        flight2.setAirportCode("LJG");
        flight1.setFlightNo("MU7424");
        flight2.setFlightNo("MU7424");
        flight1.setBoardState("开始登机");
        flight2.setBoardState("登机结束");
        flight1.setFcategory("-1");
        flight2.setFcategory("0");
        flight2.setDstTimezone("28800");
        flight2.setOrgTimezone("28800");
        flight1.setBoardGate("31");
        flight2.setBoardGate("7");
        flight1.setFlightDeptimeReadyDate(format2.parse("2017-03-29 11:22:00"));
        flight2.setFlightDeptimeReadyDate(format2.parse("2017-03-29 15:06:00"));
        flight1.setFlightArrtimeReadyDate(format2.parse("2017-03-29 13:12:00"));
        flight2.setFlightArrtimeReadyDate(format2.parse("2017-03-29 17:19:00"));
        System.out.println(compareTwoBean(flight1, flight2));
    }

}
