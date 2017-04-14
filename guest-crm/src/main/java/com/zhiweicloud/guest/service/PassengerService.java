package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.Passenger;
import com.zhiweicloud.guest.model.PassengerQuery;
import com.zhiweicloud.guest.model.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengyiyin on 2017/2/23.
 */
@Service
public class PassengerService {

    @Autowired
    private PassengerMapper passengerMapper;

    /**
     * 分页查询
     * @param passengerQuery
     * @param page
     * @param rows
     * @author E.in
     * @return
     * @throws Exception
     */
    public LZResult<PaginationResult<Passenger>> getPassengerList(PassengerQuery passengerQuery, int page, int rows) throws Exception{
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
        LZResult<PaginationResult<Passenger>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 根据id 获取用户信息
     * @param passengerId
     * @author E.in
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
