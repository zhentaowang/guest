package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.mapper.PassengerMapper;
import com.zhiweicloud.guest.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhengyiyin on 2017/3/2.
 */
@Service
public class PassengerService {

    @Autowired
    private PassengerMapper passengerMapper;

    public List<String> getIdentityCardDropdownList(String identityCard, String airportCode){
        return passengerMapper.getIdentityCardDropdownList(identityCard,airportCode);
    }

    public List<Passenger> getPassengerlistByFlightId(Long flightId,String airportCode){
        return passengerMapper.getPassengerlistByFlightId(flightId,airportCode);
    }
}
