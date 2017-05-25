package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.common.util.DateUtils;
import com.zhiweicloud.guest.model.Flight;
import com.zhiweicloud.guest.service.FlightService;
import com.zhiweicloud.guest.source.ibe.service.IbeService;
import com.zhiweicloud.guest.source.juhe.service.JuheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tc on 2017/5/13.
 */
//@Component
public class FlightCenterController {

    private static final Log log = LogFactory.getLog(FlightCenterController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private IbeService ibeService;

    @Autowired
    private JuheService juheService;

    /**
     * 查询航班信息
     * @param request
     * @return
     */
    public String flightInfo(JSONObject request){
        FlightCenterResult<List<Flight>> resultSet = new FlightCenterResult<>();
        String flightNo = request.getString("flightNo");
        String flightDate = request.getString("flightDate");
        String sysCode = request.getString("sysCode"); // 系统码
        String sign = request.getString("sign"); // 签名
        // 生成sign
        try {
            Flight params = new Flight();
            params.setFlightNo(flightNo);
            params.setFlightDate(DateUtils.stringToDate(flightDate, "yyyy-MM-dd"));
            List<Flight> flightList = flightService.selects(params);
            if (flightList.size() == 3) { // 已经请求过本次数据 从数据库获取
                resultSet.setState(FlightCenterStatus.LOCAL_OK.value());
                resultSet.setMessage(FlightCenterStatus.LOCAL_OK.display());
                resultSet.setData(flightList);
            }else { // 请求远端
                if (DateUtils.getString("yyyy-MM-dd").equals(flightDate)) { // 本日航班
                    resultSet = ibeService.queryFlightNo(flightNo);
                    flightService.insertBatch(resultSet.getData());
                }else {
                    resultSet = ibeService.queryFlightNoByDate(flightNo, flightDate);
                    flightService.insertBatch(resultSet.getData());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            resultSet.setState(FlightCenterStatus.FLIGHT_CENTER_ERROR.value());
            resultSet.setMessage(FlightCenterStatus.FLIGHT_CENTER_ERROR.display());
            resultSet.setData(null);
        }
        return JSON.toJSONString(resultSet);
    }

    /**
     * 定制航班
     * @param request
     * @return
     */
    public String customFlight(JSONObject request){
        FlightCenterResult resultSet = new FlightCenterResult();
        String result = null;
        String flightNo = request.getString("flightNo");
        String flightDate = request.getString("flightDate");
        String flightDepcode = request.getString("flightDepcode");
        String flightArrcode = request.getString("flightArrcode");
        String sysCode = request.getString("sysCode"); // 系统码
        String sign = request.getString("sign"); // 签名
        try {
            Flight params = new Flight();
            params.setFlightDate(DateUtils.stringToDate(flightDate,"yyyy-MM-dd"));
            params.setFlightNo(flightNo);
            params.setFlightDepcode(flightDepcode);
            params.setFlightArrcode(flightArrcode);
            Flight flight = flightService.select(params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            resultSet.setState(FlightCenterStatus.FLIGHT_CENTER_ERROR.value());
            resultSet.setMessage(FlightCenterStatus.FLIGHT_CENTER_ERROR.display());
            resultSet.setData(null);
            result = JSON.toJSONString(resultSet);
        }
        return result;
    }

    /**
     * 票面信息
     * @param request
     * @return
     */
    public String tickPassengerInfo(JSONObject request){
        FlightCenterResult resultSet;
        String tickNo = request.getString("tickNo");
        String sysCode = request.getString("sysCode"); // 系统码
        String sign = request.getString("sign"); // 签名
        try {
            resultSet = ibeService.queryPassengerByTickNo(tickNo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
            resultSet = new FlightCenterResult();
            resultSet.setState(FlightCenterStatus.FLIGHT_CENTER_ERROR.value());
            resultSet.setMessage(FlightCenterStatus.FLIGHT_CENTER_ERROR.display());
            resultSet.setData(null);
        }
        return JSON.toJSONString(resultSet);
    }

    /**
     * 查询火车信息
     * @param request
     * @return
     */
    public String trainInfo(JSONObject request){
        request.getString("start");

//        juheService.queryTrainInfo();
        return null;
    }

//    public String test(JSONObject request){
//        CANUtils.start();
//        return "test";
//    }

}
