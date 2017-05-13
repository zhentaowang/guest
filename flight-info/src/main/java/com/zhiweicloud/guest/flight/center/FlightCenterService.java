package com.zhiweicloud.guest.flight.center;

import java.util.Map;

/**
 * 航班核心逻辑类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/11 15:04
 * @author tiecheng
 */
public interface FlightCenterService {

    /**
     * 航班信息
     *
     * @param flightNo   航班号
     * @param flightDate 航班日期
     * @return 航信信息集合字符串
     */
    String flightInfo(String flightNo, String flightDate) throws Exception;

    /**
     * 定制航班
     *
     * @param flightId 机场数据库航班ID
     * @return
     */
    String customFlight(Long flightId) throws Exception;

    /**
     * 航班动态信息
     * @param params
     * @return
     * @throws Exception
     */
    String flightInfoDynamic(Map<String,String> params) throws Exception;

}
