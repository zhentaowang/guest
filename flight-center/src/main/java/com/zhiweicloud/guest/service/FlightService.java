package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.mapper.FlightMapper;
import com.zhiweicloud.guest.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tc on 2017/5/18.
 */
@Service
public class FlightService {

    @Autowired
    private FlightMapper flightMapper;

    public List<Flight> selects(Flight flight){
        return flightMapper.selects(flight);
    }

    public Flight select(Flight flight){
        return flightMapper.select(flight);
    }

    public void insertBatch(List<Flight> flights){
        flightMapper.insertBatch(flights);
    }

}
