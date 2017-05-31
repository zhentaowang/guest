package com.zhiweicloud.guest.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tc on 2017/5/13.
 */
public final class Dictionary {

    /**
     * 私有默认构造.
     */
    private Dictionary() {

    }

    /**
     * 起始打印信息
     */
    public static final String START_MESSAGE = "flight-center start";

    public static Map<Integer, String> errorCodeEnum = new HashMap<>();

    static {
        errorCodeEnum.put(202201, "车次不能为空");
        errorCodeEnum.put(202202, "查询不到车次的相关信息");
        errorCodeEnum.put(202203, "出发站或终点站不能为空");
        errorCodeEnum.put(202204, "查询不到结果");
        errorCodeEnum.put(202205, "错误的出发站名称");
        errorCodeEnum.put(202206, "错误的到达站名称");
        errorCodeEnum.put(202207, "查询不到余票相关数据哦");
        errorCodeEnum.put(202209, "请求12306网络错误,请重试");
        errorCodeEnum.put(202212, "查询出错");
    }


}
