package com.zhiweicloud.guest.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
     */
    public final Map<String, String[]> VIP_COLUMN = new HashMap<String, String[]>() {{
        put("orderNo", new String[]{"订单号", "concat(o.airport_code,lpad(o.order_id,6,'0')) AS orderNo"});
        put("flightDate", new String[]{"航班日期", "flight_date AS flightDate"});
        put("flightNo", new String[]{"航班号", "flight_no AS flightNo"});
        put("routeSegment", new String[]{"航段", "flight_date as flightDate,concat(flight_dep_airport,' - ',flight_arr_airport) AS routeSegment"});
        put("isInOrOut", new String[]{"进出港", "if(f.is_in_or_out=0,'出港','进港') as isInOrOut"});
        put("planNo", new String[]{"机号", "f.plan_no as planNo"});
        put("vipPersonNum", new String[]{"贵宾厅人次", "(select os.service_detail ->'$.serverNum' as personNum from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 1) as vipPersonNum"});
        put("vipPrice", new String[]{"贵宾厅费用", "(select os.price_rule ->'$.price' as price from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 1) as vipPrice"});
        put("passengerName", new String[]{"旅客姓名", "group_concat(p.name,',') as passengerName"});
        put("accompanyPersonNum", new String[]{"陪同人次", "(select os.service_detail ->'$.serverNum' as personNum from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 3) as accompanyPersonNum"});
        put("accompanyPrice", new String[]{"陪同费用", "(select os.price_rule ->'$.price' as price from order_service os where os.order_id = o.order_id and os.service_detail -> '$.serviceId' = 3) as accompanyPrice"});

		/*休息室人次
        安检人次
		客票号
		座位
		金银卡类别
		卡号
		有效期
		随行人次*/
    }};

    /**
     * 定义不同的表对应的不同列头
     */
    public final Map<String, ColumnType> COLUMN = new HashMap<String, ColumnType>() {{
        put("VIP_QUERY_COLUMN", new ColumnType(new String[]{"orderNo", "flightDate", "vipPersonNum", "vipPrice", "accompanyPersonNum", "accompanyPrice"}, "vipPersonNum * vipPrice + accompanyPersonNum * accompanyPrice AS totalAmount"));
    }};

    /**
     * 获取header
     *
     * @param columns
     * @return
     */
    public JSONArray getHeader(String tableName) {
        JSONArray result = new JSONArray();
        for (String column : COLUMN.get(tableName).column) {
            result.add(JSON.parseObject("{'displayName': '" + VIP_COLUMN.get(column)[0] + "', 'columnName': '" + column + "'}"));
        }
        return result;
    }

    public String getColumn(String tableName) {
        String result = "";
        ColumnType columnType = COLUMN.get(tableName);
        for (String column : columnType.column) {
            result += VIP_COLUMN.get(column)[1] + ",";
        }
        return result.substring(0, result.length() - 1);
    }

    public String getTotalAmount(String tableName) {
        return COLUMN.get(tableName).func;
    }

}
