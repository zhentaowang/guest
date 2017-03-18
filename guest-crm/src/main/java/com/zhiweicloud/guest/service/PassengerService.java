package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.model.PassengerQuery;
import com.zhiweicloud.guest.model.ServiceInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/2/23.
 */
@Service
public class PassengerService {

    @Autowired
    private PassengerMapper passengerMapper;

    public LZResult<PaginationResult<Passenger>> getPassengerList(PassengerQuery passengerQuery, int page, int rows) throws Exception{
        int total = passengerMapper.getListCount(passengerQuery);
        List<Passenger> passengerList = passengerMapper.queryPassengerList(passengerQuery,(page-1)*rows,rows);
        if(!CollectionUtils.isEmpty(passengerList)){
            for(Passenger p : passengerList){
                //电话或身份证任一不为空，查询使用次数
                if(p.getPhone() != null || !StringUtils.isEmpty(p.getIdentityCard())){
                    int count = passengerMapper.queryBuyTimes(p.getPhone(),p.getIdentityCard(),p.getAirportCode());
                    p.setBuyTimes(count);
                }else{
                    p.setBuyTimes(1);
                }
            }
        }
        PaginationResult<Passenger> eqr = new PaginationResult<>(total, passengerList);
        LZResult<PaginationResult<Passenger>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 根据id 获取用户信息
     * @param passengerId
     * @return
     */
    public Passenger getPassengerById(Long passengerId,String airportCode) throws Exception{
        List<Passenger> passengerList = passengerMapper.queryById(passengerId,airportCode);
        if(!CollectionUtils.isEmpty(passengerList)){
            Passenger passenger = passengerList.get(0);

            return passenger;
        }
        return null;
    }

    /**
     * 获取手机或者身份证匹配的 服务信息
     * 手机号 身份证为空的话 获取 crmPassengerId 的服务信息
     * @param param
     * @return
     */
    public List<ServiceInfo> getServiceInfoList(Map<String, Object> param){
        return passengerMapper.queryServiceInfoList(param);
    }

}
