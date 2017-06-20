package com.zhiweicloud.guest.source.ibe.util;

import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.util.HttpClientUtils;
import com.zhiweicloud.guest.common.util.XmlUtils;
import com.zhiweicloud.guest.conf.BaseAttributeConfig;
import com.zhiweicloud.guest.source.ibe.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * IBE航班信息获取工具类
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/13 13:38
 * @author tiecheng
 */
public class IbeUtils {

    private static final Log log = LogFactory.getLog(IbeUtils.class);

    private static final String IBE_HASHCODE = BaseAttributeConfig.ibeHashCode;

    private static final String IBE_HOST = BaseAttributeConfig.ibeHost;

    private static final String FLIGHT_INFO_UPDATE_FLIGHT = BaseAttributeConfig.customFlightUrl;

    /**
     * 定制航班
     * @param flightNo
     * @param flightDate
     * @return
     * @throws Exception
     */
    public static IbeMessage customFlight(String flightNo, String flightDate) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("【 ************ IBE 方法名：定制航班（customFlight） 参数名：航班号_" + flightNo + "；航班日期_" + flightDate + " ************ 】");
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("Hashcode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("flightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("postURL", FLIGHT_INFO_UPDATE_FLIGHT));
        String result = HttpClientUtils.HttpPostForWebService("http", IBE_HOST, "/FYFTQuery.asmx/FlightPush", nameValuePairs);result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), IbeMessage.class);
    }

    /**
     * 根据航班号查询 -- 当日
     * @param flightNo
     * @return
     * @throws Exception
     */
    public static RootResult queryFlightNo(String flightNo) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("【 ************ IBE 方法名：根据航班号查询航班信息（queryFlightNo） 参数名：航班号_" + flightNo + " ************ 】");
        }
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryByFlightNO", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), RootResult.class);
    }

    /**
     * 根据航班号和日期查询航班信息
     * @param flightNo
     * @param flightDate
     * @return FlightCenterResult Object
     * @throws Exception
     */
    public static RootResult queryFlightNoByDate(String flightNo, String flightDate) throws Exception {
        log.info("【 ************ IBE 方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：航班号_" + flightNo + "；航班日期_" + flightDate + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryFlightNobydate", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), RootResult.class);
    }

    /**
     * 根据航段和日期查询航班信息
     * @param depAirportCode
     * @param arrAirportCode
     * @param flightDate
     * @return FlightCenterResult Object
     * @throws Exception
     */
    public static JSONObject queryFlightByDepCodeAndArrCodeAndDate2(String depAirportCode, String arrAirportCode, String flightDate) throws Exception {
        log.info("【 ************ IBE 方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：航班出发地三字码_" + depAirportCode + "；航班出发地三字码_" + arrAirportCode + "；航班日期_" + flightDate + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("sHashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("orgCity", depAirportCode));
        nameValuePairs.add(new BasicNameValuePair("dstCity", arrAirportCode));
        nameValuePairs.add(new BasicNameValuePair("airline", "ALL"));
        nameValuePairs.add(new BasicNameValuePair("FlightDate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("direct", "false"));
        nameValuePairs.add(new BasicNameValuePair("eticket", "false"));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/vibe.asmx/AV", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        return XmlUtils.documentToJSONObject(result);
    }

    public static IbeAvResult queryFlightByDepCodeAndArrCodeAndDate(String depAirportCode, String arrAirportCode, String flightDate) throws Exception {
        log.info("【 ************ IBE 方法名：根据航班号/日期查询航班信息（queryFlightNoByDate） 参数名：航班出发地三字码_" + depAirportCode + "；航班出发地三字码_" + arrAirportCode + "；航班日期_" + flightDate + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("sHashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("orgCity", depAirportCode));
        nameValuePairs.add(new BasicNameValuePair("dstCity", arrAirportCode));
        nameValuePairs.add(new BasicNameValuePair("airline", "ALL"));
        nameValuePairs.add(new BasicNameValuePair("FlightDate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("direct", "false"));
        nameValuePairs.add(new BasicNameValuePair("eticket", "false"));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/vibe.asmx/AV", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        return JAXB.unmarshal(new StringReader(result.replace(" xmlns=\"http://www.ibeservice.com/\"","")), IbeAvResult.class);
    }

    /**
     * 查询航班历史信息（不包含当日）
     * @param flightNo
     * @param flightDate
     * @return
     * @throws Exception
     */
    public static RootResult queryHistoryByFlightNoAndDepDate(String flightNo, String flightDate) throws Exception {
        log.info("【 ************ IBE 方法名：查询航班历史信息（queryHistoryByFlightNoAndDepDate） 参数名：航班号_" + flightNo + "；航班日期_" + flightDate + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("FlightNo", flightNo));
        nameValuePairs.add(new BasicNameValuePair("flightdate", flightDate));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QuerybyhisFlightNO", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), RootResult.class);
    }

    /**
     * 根据客票号查询旅客信息
     * @param tickNo
     * @return
     * @throws Exception
     */
    public static IbeDetrTktResult queryPassengerByTickNo(String tickNo) throws Exception {
        log.info("【 ************ IBE 方法名：根据客票号查询旅客信息（queryPassengerByTickNo） 参数名：客票号_" + tickNo  + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("sHashcode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("TicketNo", tickNo));
        nameValuePairs.add(new BasicNameValuePair("Name", ""));
        nameValuePairs.add(new BasicNameValuePair("BPNR", ""));
        nameValuePairs.add(new BasicNameValuePair("CPNR", ""));
        nameValuePairs.add(new BasicNameValuePair("IDCAR", ""));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));
        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/detrservice.asmx/DETR", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), IbeDetrTktResult.class);
    }

    /**
     * 根据出发机场/目的机场三字码查询航班信息
     * @param depAirportCode
     * @param arrAirportCode
     * @return
     * @throws Exception
     */
    public static IbeQueryByDepAndArr queryByDepAndArr(String depAirportCode, String arrAirportCode) throws Exception {
        log.info("【 ************ IBE 方法名：根据出发机场/目的机场三字码查询航班信息（queryByDepAndArr） 参数名：出发地三字码_" + depAirportCode + "；目的地三字码_" + arrAirportCode + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("orgCity", depAirportCode));
        nameValuePairs.add(new BasicNameValuePair("dstCity", arrAirportCode));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/QueryByDepAndArr", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), IbeQueryByDepAndArr.class);
    }

    /**
     * 根据机场三字码查询机场状态
     * @param airportCode
     * @return
     * @throws Exception
     */
    public static IbeQueryByDepAndArr queryAirportStatus(String airportCode) throws Exception {
        log.info("【 ************ IBE 方法名：根据机场三字码查询机场状态（queryAirportStatus） 参数名：机场三字码_" + airportCode + " ************ 】");
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("hashCode", IBE_HASHCODE));
        nameValuePairs.add(new BasicNameValuePair("airportCode", airportCode));
        nameValuePairs.add(new BasicNameValuePair("outstyle", "0"));

        String result = HttpClientUtils.HttpGetForWebService("http", IBE_HOST, "/FYFTQuery.asmx/queryAirportStatus", nameValuePairs);
        log.info("【 ************ 请求的结果：\n" + result + "\n ************ 】");
        result = result.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        return JAXB.unmarshal(new StringReader(result), IbeQueryByDepAndArr.class);
    }

    public static void main(String[] args) throws JAXBException {
        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<IBE_AVResult xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"  xmlns=\"http://www.ibeservice.com/\">\n" +
            "  <ErrorRes>\n" +
            "    <Err_code>200</Err_code>\n" +
            "    <Err_content />\n" +
            "  </ErrorRes>\n" +
            "  <AVResult>\n" +
            "    <IBE_FlightGroup>\n" +
            "      <IBE_Flights>\n" +
            "        <IBE_Flight>\n" +
            "          <FlightNO>HU7262</FlightNO>\n" +
            "          <orgCity>HGH</orgCity>\n" +
            "          <dstcity>CAN</dstcity>\n" +
            "          <depDate>2017-06-21T00:00:00</depDate>\n" +
            "          <depTime>0720</depTime>\n" +
            "          <depTimeModify />\n" +
            "          <arrDate>2017-06-21T00:00:00</arrDate>\n" +
            "          <arrtime>0930</arrtime>\n" +
            "          <arrTimeModify />\n" +
            "          <asr>true</asr>\n" +
            "          <isEtkt>true</isEtkt>\n" +
            "          <link>DS</link>\n" +
            "          <meal>true</meal>\n" +
            "          <planeStyle>738</planeStyle>\n" +
            "          <stopNumber>0</stopNumber>\n" +
            "          <isCodeShare>false</isCodeShare>\n" +
            "          <carrier />\n" +
            "          <isTriptype>B</isTriptype>\n" +
            "          <OrgAirportTerminal>T3</OrgAirportTerminal>\n" +
            "          <DstAirportTerminal> </DstAirportTerminal>\n" +
            "          <FlightTime>2:10</FlightTime>\n" +
            "          <PunctualityRate>62</PunctualityRate>\n" +
            "          <PriceFare>0</PriceFare>\n" +
            "          <costFare>0</costFare>\n" +
            "        </IBE_Flight>\n" +
            "      </IBE_Flights>\n" +
            "    </IBE_FlightGroup>\n" +
            "    <IBE_FlightGroup>\n" +
            "      <IBE_Flights>\n" +
            "        <IBE_Flight>\n" +
            "          <FlightNO>JD5270</FlightNO>\n" +
            "          <orgCity>HGH</orgCity>\n" +
            "          <dstcity>CAN</dstcity>\n" +
            "          <depDate>2017-06-21T00:00:00</depDate>\n" +
            "          <depTime>1835</depTime>\n" +
            "          <depTimeModify />\n" +
            "          <arrDate>2017-06-21T00:00:00</arrDate>\n" +
            "          <arrtime>2040</arrtime>\n" +
            "          <arrTimeModify />\n" +
            "          <asr>false</asr>\n" +
            "          <isEtkt>true</isEtkt>\n" +
            "          <link>DS</link>\n" +
            "          <meal>true</meal>\n" +
            "          <planeStyle>33B</planeStyle>\n" +
            "          <stopNumber>0</stopNumber>\n" +
            "          <isCodeShare>false</isCodeShare>\n" +
            "          <carrier />\n" +
            "          <isTriptype>S</isTriptype>\n" +
            "          <OrgAirportTerminal>T3</OrgAirportTerminal>\n" +
            "          <DstAirportTerminal>T1</DstAirportTerminal>\n" +
            "          <FlightTime>2:05</FlightTime>\n" +
            "          <PunctualityRate>95</PunctualityRate>\n" +
            "          <PriceFare>0</PriceFare>\n" +
            "          <costFare>0</costFare>\n" +
            "        </IBE_Flight>\n" +
            "      </IBE_Flights>\n" +
            "    </IBE_FlightGroup>\n" +
            "  </AVResult>\n" +
            "  <Source />\n" +
            "</IBE_AVResult>";
//        JAXBContext jaxbContext      = JAXBContext.newInstance(IbeAvResult.class);
//        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//        IbeAvResult ibeAvResult = (IbeAvResult) jaxbUnmarshaller.unmarshal(new StringReader(s));
        IbeAvResult ibeAvResult = JAXB.unmarshal(new StringReader(s.replace(" xmlns=\"http://www.ibeservice.com/\"","")), IbeAvResult.class);
    }

}
