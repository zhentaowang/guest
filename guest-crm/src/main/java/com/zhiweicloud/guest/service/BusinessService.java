package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wyun.thrift.server.business.IBusinessService;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.ListUtil;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.model.PassengerQuery;
import com.zhiweicloud.guest.model.ServiceInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/2/23.
 */
@Service
public class BusinessService implements IBusinessService {

    private static Logger logger = LoggerFactory.getLogger(BusinessService.class);

    @Autowired
    private PassengerMapper passengerMapper;

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;

        switch (operation) {
            case "getPassengerList":
                success = getPassengerList(request);
                break;
            case "getPassengerById":
                success = getPassengerById(request);
                break;
            case "getLabelInfo":
                success = getLabelInfo(request);
                break;
            default:
                break;
        }

        return JSON.parseObject(success);
    }

    /**
     * 分页查询
     * @author E.in
     * modified on 2017/5/8 by zhengyiyin
     * @return
     * @throws Exception
     */
    public String getPassengerList(JSONObject request){
        LZResult<Object> result = new LZResult<>();
        String airportCode = request.getString("client_id");
        //分页参数
        int page = 1;
        if(request.containsKey("page")) {
            page = request.getInteger("page");
        }

        int rows = 10;
        if (request.containsKey("rows")) {
            rows = request.getInteger("rows");
        }
        try {
            //查询参数
            PassengerQuery passengerQuery = JSON.toJavaObject(request, PassengerQuery.class);
            passengerQuery.setAirportCode(airportCode);
            passengerQuery.setTypes(ListUtil.StringFormat(passengerQuery.getProtocolTypes(),request.getString("labels")));

            int total = passengerMapper.getListCount(passengerQuery);
            List<Passenger> passengerList = passengerMapper.queryPassengerList(passengerQuery,(page-1)*rows,rows);
            if(!CollectionUtils.isEmpty(passengerList)){
                for(Passenger p : passengerList){
                    //电话不为空，查询使用次数（目前已电话为唯一标识）
                    if(p.getPhone() != null){
                        int count = passengerMapper.queryBuyTimes(p.getPhone(),p.getIdentityCard(),p.getAirportCode());
                        p.setBuyTimes(count);
                    }else{
                        p.setBuyTimes(1);
                    }
                }
            }
            PaginationResult<Passenger> eqr = new PaginationResult<>(total, passengerList);

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result = new LZResult<>(eqr);

        } catch (Exception e) {
            logger.error("PassengerService.getPassengerList:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return  JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm", SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据id 获取用户信息
     * @author E.in
     * @return
     */
    public String getPassengerById(JSONObject request){
        String airportCode = request.getString("client_id");
        Long passengerId = request.getLong("passengerId");
        LZResult<Passenger> result = new LZResult<>();

        try {
            //获取客户基本信息
            List<Passenger> passengerList = passengerMapper.queryById(passengerId,airportCode);
            if(!CollectionUtils.isEmpty(passengerList)){
                Passenger passenger = passengerList.get(0);
                //客户存在的话，获取该手机号客户的 服务信息
                if(passenger != null){
                    Map<String, Object> param = new HashMap();
                    param.put("airportCode", airportCode);
                    param.put("crmPassengerId", passengerId);
                    param.put("phone", passenger.getPhone());
                    param.put("identityCard", passenger.getIdentityCard());
                    List<ServiceInfo>  serviceInfoList = getServiceInfoList(param);
                    passenger.setServiceInfoList(serviceInfoList);
                }
                //返回客户信息
                result.setMsg(LZStatus.SUCCESS.display());
                result.setStatus(LZStatus.SUCCESS.value());
                result.setData(passenger);
            }

        }  catch (Exception e){
            logger.error("PassengerService.getPassengerById:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
    }

    public String getLabelInfo(JSONObject request){
        String airportCode = request.getString("client_id");
        LZResult<List<ServiceInfo>> result = new LZResult<>();
        try{
            Map<String, Object> param = new HashMap();
            param.put("airportCode", airportCode);
            param.put("crmPassengerId", request.getLong("passengerId"));
            param.put("phone", request.getLong("phone"));
            param.put("identityCard", request.getString("identityCard"));
            param.put("protocolTypes", request.getString("protocolTypes"));

            List<ServiceInfo> serviceInfoList = getServiceInfoList(param);

            result.setMsg(LZStatus.SUCCESS.display());
            result.setStatus(LZStatus.SUCCESS.value());
            result.setData(serviceInfoList);
        }catch (Exception e){
            logger.error("PassengerService.getLabelInfo:", e);
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
        }
        return JSON.toJSONStringWithDateFormat(result, "yyyy-MM-dd HH:mm", SerializerFeature.WriteMapNullValue);

    }

    /**
     * 获取手机号相同的 服务信息
     * 手机号的话 获取 crmPassengerId 的服务信息
     * @author E.in
     * @param param
     * @return
     */
    public List<ServiceInfo> getServiceInfoList(Map<String, Object> param){
        return passengerMapper.queryServiceInfoList(param);
    }

}
