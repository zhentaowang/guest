package com.zhiweicloud.guest.flight.center.common;

import com.alibaba.fastjson.JSON;
import com.zhiweicloud.guest.common.DateUtils;
import com.zhiweicloud.guest.flight.center.model.FlightStatus;
import com.zhiweicloud.guest.flight.center.model.RootResult;
import com.zhiweicloud.guest.model.Flight;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by tc on 2017/5/12.
 */
public final class IbeUtils {

    private static final String IBE_HASHCODE = "a9f885e73e97489ba764b832b7a734e5";

    private static final String IBE_HOST = "ws.ibeservice.com";

    public static String customFlight(String flightNo, String flightDate) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("Hashcode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("flightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("postURL", "http://localhost:8081/flight-info/updateFlight"));
        return HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx?op=FlightPush", nameValuePairs);
    }

    public static String queryFlightNo(String flightNo) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryFlightNobydate", nameValuePairs);
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        RootResult rootResult = JAXB.unmarshal(new StringReader(result), RootResult.class);
        Map<String, Object> map = new HashMap<>();
        map.put("data", parse(rootResult.getFlightStatuses(), DateUtils.stringToDate(flightNo, "yyyy-MM-dd")));
        map.put("state", rootResult.getErrorRes().getErrCode());
        map.put("info", rootResult.getErrorRes().getErrContent());
        return JSON.toJSONString(map);
    }

    public static String queryFlightNoByDate(String flightNo, String flightDate) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryFlightNobydate", nameValuePairs);
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        RootResult rootResult = JAXB.unmarshal(new StringReader(result), RootResult.class);
        Map<String, Object> map = new HashMap<>();
        map.put("data", parse(rootResult.getFlightStatuses(), DateUtils.stringToDate(flightDate, "yyyy-MM-dd")));
        map.put("state", rootResult.getErrorRes().getErrCode());
        map.put("info", rootResult.getErrorRes().getErrContent());
        return JSON.toJSONString(map);
    }

    private static List<Flight> parse(List<FlightStatus> flightStatuses, Date flightDate) throws InvocationTargetException, IllegalAccessException {
        List<Flight> flights = new ArrayList<>();
        if (flightStatuses != null) {
            for (FlightStatus flightStatus : flightStatuses) {
                Flight flight = new Flight();
                flight.setFlightDate(flightDate);
                BeanUtils.copyProperties(flight, flightStatus);
                flights.add(flight);
            }
        }
        return flights;
    }

}
