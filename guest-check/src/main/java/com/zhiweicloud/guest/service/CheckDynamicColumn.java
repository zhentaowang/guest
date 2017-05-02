package com.zhiweicloud.guest.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.executor.ReuseExecutor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangpengfei
 * @version V1.0
 * @ClassName: CheckDynamicColumn
 * @Description: 动态列定义
 * @date 2017年03月16日 下午6:23:57
 * 字段排序：按照 26个字母排序，所以可以另外去别名来达到排序的需求
 */
@Component
public class CheckDynamicColumn {
    class ColumnType {
        String[] column;
        String func;

        ColumnType(String[] column, String func) {
            this.column = column;
            this.func = func;
        }
    }
    /**
     * 定义全部的查询字段
     * 最后一个字段：0：不需要求和，1：需要求和
     */
    public final Map<String, String[]> ALL_COLUMN = new HashMap<String, String[]>() {{
        put("orderNo", new String[]{"订单号", "concat(o.airport_code,lpad(o.order_id,6,'0')) AS orderNo", "0"});
        put("flightDate", new String[]{"航班日期", "date_format(f.flight_date, '%Y-%m-%d')  AS flightDate","0"});
        put("flightNo", new String[]{"航班号", "f.flight_no AS flightNo", "0"});
        put("routeSegment", new String[]{"航段", "concat(f.flight_dep_airport,' - ',flight_arr_airport) AS routeSegment","0"});
        put("isInOrOut", new String[]{"进出港", "if(f.is_in_or_out=0,'出港','进港') as isInOrOut","0"});
        put("planNo", new String[]{"机号", "f.plan_no as planNo","0"});
        put("vipPersonNum", new String[]{"贵宾厅人次", "(select os.service_detail ->'$.serverNum' as personNum from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 1) as vipPersonNum","1"});
        put("vipPrice", new String[]{"贵宾厅费用", "(select os.price_rule ->'$.price' as price from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 1) as vipPrice","1"});
        put("passengerName", new String[]{"旅客姓名", "group_concat(p.name) as passengerName","0"});
        put("accompanyPersonNum", new String[]{"陪同人次", "(select os.service_detail ->'$.serverNum' as personNum from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 3) as accompanyPersonNum","1"});
        put("accompanyPrice", new String[]{"陪同费用", "(select os.price_rule ->'$.price' as price from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 3) as accompanyPrice","1"});
        put("restRoomPersonNum", new String[]{"休息室人次", "(select os.service_detail ->'$.serverNum' as personNum from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 5) as restRoomPersonNum","1"});
        put("restRoomPrice", new String[]{"休息室费用", "(select os.price_rule ->'$.price' as price from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 5) as restRoomPrice","1"});
        put("securityCheckPersonNum", new String[]{"安检人次", "(select os.service_detail ->'$.serverNum' as personNum from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 6) as securityCheckPersonNum","1"});
        put("securityCheckPrice", new String[]{"安检费用", "(select os.price_rule ->'$.price' as price from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 6) as securityCheckPrice","1"});
        put("vipCard", new String[]{"卡号", "group_concat(p.vip_card) as vipCard","0"});
        put("ticketNo", new String[]{"客票号", "group_concat(p.ticket_no) as ticketNo","0"});
        put("cardType", new String[]{"金银卡类别", "group_concat(DISTINCT(if(p.card_type=0,'金卡','银卡'))) as cardType","0"});
        put("sitNo", new String[]{"座位号", "group_concat(p.sit_no) as sitNo", "0"});
        put("expireTime", new String[]{"有效期", "group_concat(date_format(p.expire_time, '%Y-%m-%d')) as expireTime","0"});
        put("alongTotal", new String[]{"随行人次", "o.along_total as alongTotal","1"});
    }};

    public final Map<String, ColumnType> COLUMN = new HashMap<String, ColumnType>() {{
        /**
         *所有-VIP接送机 LONG_DISTANCE
         * @ 订单号，航班日期，航班号，航段，进出港，贵宾厅人次，贵宾厅费用，陪同人次，陪同费用
         */
        put("VIP接送机", new ColumnType(new String[]{"orderNo", "flightDate","flightNo","routeSegment","isInOrOut","planNo","vipCard","passengerName","ticketNo","vipPersonNum", "vipPrice", "accompanyPersonNum", "accompanyPrice"}, "vipPersonNum * vipPrice + accompanyPersonNum * accompanyPrice AS totalAmount"));

        /**
         *所有-异地服务 LONG_DISTANCE
         * @ 订单号，航班日期，航班号，航段，进出港，贵宾厅人次，贵宾厅费用，陪同人次，陪同费用
         */
        put("异地贵宾服务", new ColumnType(new String[]{"orderNo", "flightDate","flightNo","routeSegment","isInOrOut","planNo","vipCard","passengerName","ticketNo","vipPersonNum", "vipPrice", "accompanyPersonNum", "accompanyPrice"}, "vipPersonNum * vipPrice + accompanyPersonNum * accompanyPrice AS totalAmount"));

        /**
         * 除头等舱，金银卡 - 两舱休息室 FIRST_CABINS_AND_GOLD_SILVER_CARD
         * @ 订单号，航班日期，航班号，航段，进出港，休息室人次，休息室费用，安检人次，安检费用
         */
        put("两舱休息室", new ColumnType(new String[]{"orderNo", "flightDate","flightNo","routeSegment","isInOrOut","restRoomPersonNum","restRoomPrice","securityCheckPersonNum","securityCheckPrice"}, "restRoomPersonNum * restRoomPrice + securityCheckPersonNum * securityCheckPrice AS totalAmount"));

        /**
         * 头等舱 - 两舱休息室
         * @ 订单号，航班日期，航班号，航段，机号，旅客姓名，客票号，座位，休息室人次，休息室费用
         */
        put("两舱休息室10", new ColumnType(new String[]{"orderNo", "flightDate","flightNo","routeSegment","planNo","passengerName","ticketNo","sitNo","restRoomPersonNum", "restRoomPrice"}, "restRoomPersonNum * restRoomPrice AS totalAmount"));

        /**
         * 金银卡 - 两舱休息室 GOLD_SILVER_CARD
         * @ 订单号，航班日期，航班号，航段，机号，旅客姓名，客票号，金银卡类型，卡号，有效期，随行人次，休息室人次，休息室费用
         */
        put("两舱休息室9", new ColumnType(new String[]{"orderNo", "flightDate","flightNo","routeSegment","planNo","passengerName","ticketNo","cardType","expireTime","alongTotal","restRoomPersonNum", "restRoomPrice"}, "restRoomPersonNum * restRoomPrice AS totalAmount"));

        /**
         * 所有 - 独立安检通道
         * @ 订单号，航班日期，航班号，航段，进出港，安检人次，安检费用
         */
        put("独立安检通道", new ColumnType(new String[]{"orderNo", "flightDate","flightNo","routeSegment","isInOrOut","securityCheckPersonNum", "securityCheckPrice"}, "securityCheckPersonNum * securityCheckPrice AS totalAmount"));
    }};

    /**
     * 获取header
     *
     * @param tableName
     * @return
     */
    public JSONArray getHeader(String tableName) {
        JSONArray result = new JSONArray();
        for (String column : COLUMN.get(tableName).column) {
            result.add(JSON.parseObject("{'displayName': '" + ALL_COLUMN.get(column)[0] + "', 'columnName': '" + column + "'}"));
        }
        return result;
    }

    public String getColumn(String tableName) {
        try {
            String result = "";
            ColumnType columnType = COLUMN.get(tableName);
            for (String column : columnType.column) {
                result += ALL_COLUMN.get(column)[1] + ",";
            }
            return result.substring(0, result.length() - 1);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getTotalAmount(String tableName) {
        return COLUMN.get(tableName).func;
    }

}
