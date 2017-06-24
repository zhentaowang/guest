package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.wyun.thrift.client.utils.ClientUtil;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.Response;
import com.wyun.utils.SpringBeanUtil;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by tc on 2017/6/22.
 */
@Service
public class AppService {

    private static MyService.Iface baseInfoClient = SpringBeanUtil.getBean("baseInfoClient");

    /**
     * 获取机场信息(列表 or 条件)
     *
     * @param request
     * @return
     */
    public String queryAirport(JSONObject request) {
        FlightCenterResult result = new FlightCenterResult();
        Integer around = request.getInteger("around");
        String queryName = request.getString("queryName");
        JSONObject object = null;
        try {
            JSONObject params = new JSONObject();
            params.put("region", around);
            if (StringUtils.isNoneBlank(queryName)) {
                params.put("queryName", queryName);
                params.put("type", "机场");
            }
            Response re = ClientUtil.clientSendData(baseInfoClient, "businessService", "queryAirports", params);
            if (re != null && re.getResponeCode().getValue() == 200) {
                object = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
            }
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
            result.setData(object);
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 查询热门机场
     *
     * @param request
     * @return
     */
    public String queryHotAirport(JSONObject request) {
        FlightCenterResult result = new FlightCenterResult();
        Integer around = request.getInteger("around");
        JSONObject object = null;
        try {
            JSONObject params = new JSONObject();
            params.put("region", around);
            Response re = ClientUtil.clientSendData(baseInfoClient, "businessService", "queryHotAirport", params);
            if (re != null && re.getResponeCode().getValue() == 200) {
                object = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
            }
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
            result.setData(object);
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

    /**
     * 增加机场热度
     * @param request
     * @return
     */
    public String addHotNum(JSONObject request) {
        FlightCenterResult result = new FlightCenterResult();
        String airportCode = request.getString("airportCode");
        JSONObject object = null;
        try {
            JSONObject params = new JSONObject();
            params.put("airportCode", airportCode);
            Response re = ClientUtil.clientSendData(baseInfoClient, "businessService", "addHotNum", params);
            if (re != null && re.getResponeCode().getValue() == 200) {
                object = JSON.parseObject(new String(re.getResponseJSON()), Feature.OrderedField);
            }
            result.setState(FlightCenterStatus.SUCCESS.value());
            result.setMessage(FlightCenterStatus.SUCCESS.display());
            result.setData(object);
        } catch (Exception e) {
            result.setState(FlightCenterStatus.ERROR.value());
            result.setMessage(FlightCenterStatus.ERROR.display());
            result.setData(null);
        }
        return JSON.toJSONString(result);
    }

}
