package com.zhiweicloud.guest.pojo;

import com.zhiweicloud.guest.po.StationPo;
import com.zhiweicloud.guest.po.TrainPo;

import java.util.List;

/**
 * TrainPojo.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/24 21:19
 * @author tiecheng
 */
public class TrainPojo extends TrainPo{

    List<StationPo> stationPos;

    public List<StationPo> getStationPos() {
        return stationPos;
    }

    public void setStationPos(List<StationPo> stationPos) {
        this.stationPos = stationPos;
    }
    
}
