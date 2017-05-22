package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhiweicloud.guest.common.utils.ExcelUtils;
import com.zhiweicloud.guest.generator.*;
import com.zhiweicloud.guest.mapper.LoungeMapper;
import com.zhiweicloud.guest.model.CheckPassengerPo;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.LoungeCheckPo;
import com.zhiweicloud.guest.model.OrderCheckDetail;
import com.zhiweicloud.guest.pojo.RowContentPo;
import com.zhiweicloud.guest.pojo.SheetContentPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出文件Service
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/22 11:16
 * @author tiecheng
 */
@Service
public class ExportFileService {

    @Autowired
    private LoungeMapper loungeMapper;

    public void exportBill(CheckQueryParam checkQueryParam, String type, HttpServletResponse response, Long userId, String airportCode)throws Exception {

         /*
         * 默认文件名字
         */
        String fileName = "账单";
        String sheetName = "账单";

        // 内容生成器
        ContentGenerator contentGenerator = null;

        if (type == null) {
            return;
        }

        switch (checkQueryParam.getType()) {
            case "firstClass":
                contentGenerator = new FirstClassContentGenerator(getLoungeDateList(checkQueryParam, airportCode,10));
                fileName = "头等舱账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "头等舱账单";
                break;
            case "frequentFlyer":
                contentGenerator = new FrequentFlyerContentGenerator(getLoungeDateList(checkQueryParam, airportCode,9));
                fileName = "常旅客账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "常旅客账单";
                break;
            case "airChina":
                contentGenerator = new AirChinaContentGenerator(getSpecialDateList(checkQueryParam, airportCode));
                fileName = "国际航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
            case "chinaSouthernAirlines":
                contentGenerator = new ChinaSouthernAirlinesContentGenerator(getSpecialDateList(checkQueryParam, airportCode));
                fileName = "南方航空账单" + "_" + System.currentTimeMillis() + ".xls";
                sheetName = "国际航空账单";
                break;
        }

        ExcelUtils.exportExcel(contentGenerator, fileName, sheetName,response);

    }

    public void exportExcel(OrderCheckDetail orderCheckDetail, Map result, HttpServletResponse response){
        JSONArray column = (JSONArray) result.get("column");
        List rows = (List) result.get("rows");
        if (rows.size() > 1) {
            Map<String, String> titleMap = new LinkedHashMap<>();
            column.forEach(x -> {
                String row1 = JSONObject.toJSONString(x, SerializerFeature.WriteMapNullValue);
                Map<String, String> map = JSON.parseObject(row1, LinkedHashMap.class, Feature.OrderedField);
                String[] strArray = new String[2];
                int i = 0;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    strArray[i] = entry.getValue();
                    i++;
                }
                titleMap.put(strArray[1], strArray[0]);
            });
            String fileName = orderCheckDetail.getQueryProductName() + "_" + System.currentTimeMillis() + ".xls";
            String sheetName = orderCheckDetail.getQueryProductName();
            ExcelUtils.download(fileName, sheetName, rows, titleMap,response);
        }
    }

    private List<SheetContentPo> getSpecialDateList(CheckQueryParam checkQueryParam, String airportCode) throws ParseException {
        checkQueryParam.setAirportCode(airportCode);
        List<Map> maps = loungeMapper.selectSpecialCheckList(checkQueryParam);
        SheetContentPo sheetContentPo = new SheetContentPo();
        List<SheetContentPo> sheetContentPos = new LinkedList<>();
        List<RowContentPo> rowContentPos = new LinkedList<>();
        RowContentPo rowContentPo;
        for (Map map : maps) {
            rowContentPo = new RowContentPo();
            rowContentPo.setAmout((Double) map.get("amount"));
            rowContentPo.setFlightDepcode((String) map.get("flightDepcode"));
            rowContentPo.setFlightArrcode((String) map.get("flightArrcode"));
            rowContentPo.setFlightDate(String.valueOf(map.get("flightDate")));
            rowContentPo.setServerPersonNum((Double)(map.get("serverPersonNum")));
            rowContentPo.setCustomerName((String) map.get("customerName"));
            rowContentPo.setPrice(((Long)map.get("price")));
            rowContentPo.setAirpotCode((String)map.get("airportCode"));
            rowContentPo.setPlanNo((String) map.get("planNo"));
            rowContentPo.setFlightNo((String) map.get("flightNo"));
            rowContentPo.setLeg((String) map.get("leg"));
            rowContentPos.add(rowContentPo);
        }
        sheetContentPo.setRowContentPos(rowContentPos);
        sheetContentPos.add(sheetContentPo);
        return sheetContentPos;
    }

    private List<SheetContentPo> getLoungeDateList(CheckQueryParam checkQueryParam, String airportCode,int protocolType) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        checkQueryParam.setAirportCode(airportCode);
        List<LoungeCheckPo> loungeCheckPos = loungeMapper.selectLoungeCheckList(checkQueryParam, protocolType);
        List<SheetContentPo> sheetContentPos = new LinkedList<>();
        List<RowContentPo> rowContentPos;
        RowContentPo rowContentPo;
        SheetContentPo sheetContentPo;
        for (LoungeCheckPo loungeCheckPo : loungeCheckPos) {
            rowContentPos = new LinkedList<>();
            sheetContentPo = new SheetContentPo();
            for (CheckPassengerPo checkPassengerPo : loungeCheckPo.getCheckPassengerPos()) {
                rowContentPo = new RowContentPo();
                rowContentPo.setFlightNo(checkPassengerPo.getFlightNo());
                rowContentPo.setPlanNo(checkPassengerPo.getPlanNo());
                rowContentPo.setAlongTotal(checkPassengerPo.getAlongTotal());
                rowContentPo.setAirpotCode(checkPassengerPo.getAirpotCode());
                rowContentPo.setLeg(checkPassengerPo.getLeg());
                rowContentPo.setCustomerName(loungeCheckPo.getCustomerName());
                rowContentPo.setFlightDate(loungeCheckPo.getFlightDate() == null ? "" : simpleDateFormat.format(loungeCheckPo.getFlightDate()));
                rowContentPo.setCabinNo(checkPassengerPo.getCabinNo());
                rowContentPo.setExpireTime(checkPassengerPo.getExpireTime());
                rowContentPo.setServerPersonNum(checkPassengerPo.getServerPersonNum());
                rowContentPo.setPrice(checkPassengerPo.getPrice());
                rowContentPo.setAmout(checkPassengerPo.getAmout());
                rowContentPo.setFlightDepcode(checkPassengerPo.getFlightDepcode());
                rowContentPo.setFlightArrcode(checkPassengerPo.getFlightArrcode());
                rowContentPo.setCardNo(checkPassengerPo.getCardNo());
                rowContentPo.setCardType(checkPassengerPo.getCardType());
                rowContentPo.setName(checkPassengerPo.getName());
                rowContentPo.setTicketNo(checkPassengerPo.getTicketNo());
                rowContentPos.add(rowContentPo);
            }
            sheetContentPo.setRowContentPos(rowContentPos);
            sheetContentPos.add(sheetContentPo);
        }
        return sheetContentPos;
    }

}
