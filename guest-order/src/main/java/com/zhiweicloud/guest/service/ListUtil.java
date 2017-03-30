package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {
    /**
     * List 转String，以“，”分割
     *
     * @param <E>
     * @param <E>
     */
    public static <E> String List2String(List<E> list) {
        if (list == null || list.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append("'" + list.get(i) + "'");
            if (i != list.size() - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("a", 1);
        jsonMap.put("b", "");
        jsonMap.put("c", null);
        jsonMap.put("d", "wuzhuti.cn");


        String str = JSONObject.toJSONString(jsonMap, SerializerFeature.WriteMapNullValue);
        System.out.println(str);
        //输出结果:{"a":1,"b":"","c":null,"d":"wuzhuti.cn"}
    }
}

