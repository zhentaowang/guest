package com.zhiweicloud.guest.service;


/**
 * @ClassName: CheckEnum
 * @Description: 动态列定义
 * @author zhangpengfei
 * @date 2016年12月14日 下午6:23:57
 * @version V1.0
 */
public class CheckEnum {

	private static final String VIP_COLUMN = "[{columnName:'orderNo',displayName:'订单号'},{columnName:'flightDate',displayName:'航班日期'}]";

	private static final String VIP_QUERY_COLUMN = "concat(o.airport_code,lpad(o.order_id,6,'0')) AS order_no,order_id,customer_id,protocol_id,product_id,flight_date,plan_no,flight_dep_airport,flight_arr_airport,is_in_or_out";



}
