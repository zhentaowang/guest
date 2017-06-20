package com.zhiweicloud.guest.common.util;

import com.alibaba.fastjson.JSONArray;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by tc on 2017/6/20.
 */
public class XmlUtils {


    /**
     * String 转 org.dom4j.Document
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Document strToDocument(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml);
    }

    /**
     * org.dom4j.Document 转  com.alibaba.fastjson.JSONObject
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static JSONObject documentToJSONObject(String xml) throws DocumentException {
        return elementToJSONObject(strToDocument(xml).getRootElement());
    }

    public static JSONObject elementToJSONObject(Element node) {
        JSONObject result = new JSONObject();
        // 当前节点的名称、文本内容和属性
        List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
        for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
            result.put(attr.getName(), attr.getValue());
        }
        // 递归遍历当前节点所有的子节点
        List<Element> listElement = node.elements();// 所有一级子节点的list
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {// 遍历所有一级子节点
                if (e.attributes().isEmpty() && e.elements().isEmpty()) // 判断一级节点是否有属性和子节点
                    result.put(e.getName(), e.getTextTrim());// 沒有则将当前节点作为上级节点的属性对待
                else {
                    if (!result.containsKey(e.getName())) // 判断父节点是否存在该一级节点名称的属性
                        result.put(e.getName(), new JSONArray());// 没有则创建
                    ((JSONArray) result.get(e.getName())).add(elementToJSONObject(e));// 将该一级节点放入该节点名称的属性对应的值中
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws DocumentException {
        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<IBE_AVResult xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://www.ibeservice.com/\">\n" +
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
        s = s.replace("xmlns=\"http://ws.ibeservice.com/\"", "");
        JSONObject object = documentToJSONObject(s);
    }

}
