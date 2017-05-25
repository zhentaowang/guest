package com.zhiweicloud.guest.source.ibe.util;

import com.zhiweicloud.guest.common.util.HttpClientUtils;
import com.zhiweicloud.guest.source.ibe.model.IbeDetrTktResult;
import com.zhiweicloud.guest.source.ibe.model.RootResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jvnet.hk2.annotations.Service;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.*;

/**
 * IBE航班信息获取工具类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/13 13:38
 * @author tiecheng
 */
public class IbeUtils {

    private static final Log log = LogFactory.getLog(IbeUtils.class);

    private static final String IBE_HASHCODE = "a9f885e73e97489ba764b832b7a734e5";

    private static final String IBE_HOST = "ws.ibeservice.com";

    private static final String FLIGHT_INFO_UPDATE_FLIGHT = "http://localhost:8081/flight-info/updateFlight";

    /**
     * 定制航班
     * @param flightNo
     * @param flightDate
     * @return
     * @throws Exception
     */
    public static String customFlight(String flightNo, String flightDate) throws Exception {
        log.info("【方法名：定制航班（customFlight） 参数名：航班号_" + flightNo + "；航班日期_" + flightDate + "】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("Hashcode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("flightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("postURL", FLIGHT_INFO_UPDATE_FLIGHT));
        return HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx?op=FlightPush", nameValuePairs);
    }

    /**
     * 根据航班号查询 -- 当日
     * @param flightNo
     * @return
     * @throws Exception
     */
//    public static FlightCenterResult queryFlightNo(String flightNo) throws Exception {
//        log.info("【方法名：根据航班号查询航班信息（queryFlightNo） 参数名：航班号_" + flightNo + "】");
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
//        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
//        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
//
//        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryByFlightNO", nameValuePairs);
//        log.info("【请求的结果：\n" + result + "】");
//        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
//        RootResult rootResult = JAXB.unmarshal(new StringReader(result), RootResult.class);
//
//        FlightCenterResult<Flight> resultSet = new FlightCenterResult();
//        resultSet.setState(rootResult.getErrorRes().getErrCode());
//        resultSet.setMessage(rootResult.getErrorRes().getErrContent());
//        resultSet.setT(parse(rootResult.getFlightStatuses(), DateUtils.getDate("yyyy-MM-dd")));
//        return resultSet;
//    }

    public static RootResult queryFlightNo(String flightNo) throws Exception {
        log.info("【方法名：根据航班号查询航班信息（queryFlightNo） 参数名：航班号_" + flightNo + "】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryByFlightNO", nameValuePairs);
        log.info("【请求的结果：\n" + result + "】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), RootResult.class);
    }

    /**
     * 根据航班号和日期查询
     * @param flightNo
     * @param flightDate
     * @return FlightCenterResult Object
     * @throws Exception
     */
//    public static FlightCenterResult queryFlightNoByDate(String flightNo, String flightDate) throws Exception {
//        log.info("【方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：航班号_" + flightNo + "；航班日期_" + flightDate + "】");
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
//        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
//        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
//        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
//
//        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryFlightNobydate", nameValuePairs);
//        log.info("【请求的结果：\n" + result + "】");
//        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
//        RootResult rootResult = JAXB.unmarshal(new StringReader(result), RootResult.class);
//
//        FlightCenterResult<Flight> resultSet = new FlightCenterResult();
//        resultSet.setState(rootResult.getErrorRes().getErrCode());
//        resultSet.setMessage(rootResult.getErrorRes().getErrContent());
//        resultSet.setT(parse(rootResult.getFlightStatuses(), DateUtils.stringToDate(flightDate,"yyyy-MM-dd")));
//        return resultSet;
//    }

    public static RootResult queryFlightNoByDate(String flightNo, String flightDate) throws Exception {
        log.info("【方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：航班号_" + flightNo + "；航班日期_" + flightDate + "】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryFlightNobydate", nameValuePairs);
        log.info("【请求的结果：\n" + result + "】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), RootResult.class);
    }

    /**
     * 根据客票号查询旅客信息
     * @param tickNo
     * @return
     * @throws Exception
     */
//    public static FlightCenterResult queryPassengerByTickNo(String tickNo) throws Exception {
//        log.info("【方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：客票号" + tickNo  + "】");
//        List<NameValuePair> nameValuePairs = new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("sHashcode", IBE_HASHCODE));
//        nameValuePairs.add(new BasicNameValuePair("TicketNo", tickNo));
//        nameValuePairs.add(new BasicNameValuePair("Name", ""));
//        nameValuePairs.add(new BasicNameValuePair("BPNR", ""));
//        nameValuePairs.add(new BasicNameValuePair("CPNR", ""));
//        nameValuePairs.add(new BasicNameValuePair("IDCAR", ""));
//        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
//        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/detrservice.asmx/DETR", nameValuePairs);
//        log.info("【请求的结果：\n" + result + "】");
//        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
//        IbeDetrTktResult ibeDetrTktResult = JAXB.unmarshal(new StringReader(result), IbeDetrTktResult.class);
//
//        FlightCenterResult resultSet = new FlightCenterResult();
//        resultSet.setState(ibeDetrTktResult.getErrorRes().getErrCode());
//        resultSet.setMessage(ibeDetrTktResult.getErrorRes().getErrContent());
//        List list = new ArrayList();
//        list.add(ibeDetrTktResult.getTktGroup().getIbeDetrTkt());
//        resultSet.setT(list);
//        return resultSet;
//    }

    public static IbeDetrTktResult queryPassengerByTickNo(String tickNo) throws Exception {
        log.info("【方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：客票号" + tickNo  + "】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("sHashcode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("TicketNo", tickNo));
        nameValuePairs.add(new BasicNameValuePair("Name", ""));
        nameValuePairs.add(new BasicNameValuePair("BPNR", ""));
        nameValuePairs.add(new BasicNameValuePair("CPNR", ""));
        nameValuePairs.add(new BasicNameValuePair("IDCAR", ""));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/detrservice.asmx/DETR", nameValuePairs);
        log.info("【请求的结果：\n" + result + "】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), IbeDetrTktResult.class);
    }

}
