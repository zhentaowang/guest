package com.zhiweicloud.guest.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.common.model.FlightCenterResult;
import com.zhiweicloud.guest.common.model.FlightCenterStatus;
import com.zhiweicloud.guest.mapper.StationPoMapper;
import com.zhiweicloud.guest.mapper.TrainPoMapper;
import com.zhiweicloud.guest.mapper.TrainPojoMapper;
import com.zhiweicloud.guest.po.StationPo;
import com.zhiweicloud.guest.po.TrainPo;
import com.zhiweicloud.guest.pojo.TrainPojo;
import com.zhiweicloud.guest.source.juhe.service.JuheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TrainService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/25 14:04
 * @author tiecheng
 */
@Service
public class TrainService {

    private static final Log log = LogFactory.getLog(TrainService.class);

    @Autowired
    private TrainPojoMapper trainPojoMapper;

    @Autowired
    private TrainPoMapper trainPoMapper;

    @Autowired
    private StationPoMapper stationPoMapper;

    @Autowired
    private JuheService juheService;

    @Transactional
    public String queryTrainByCondition(JSONObject request){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        FlightCenterResult<TrainPojo> re = new FlightCenterResult<>();
        try{
            String name = request.getString("name");
            String trainDate = request.getString("trainDate");

            if (log.isInfoEnabled()) {
                log.info("【参数   name:" +name + " trainDate:"+ trainDate +"】");
            }

            Map<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("trainDate", trainDate);

            TrainPojo trainPojo = trainPojoMapper.queryTrainByCondition(params);

            if (trainPojo == null) {

                if (log.isInfoEnabled()) {
                    log.info("【需要从聚合数据获取数据解析】");
                }

                String success = juheService.queryTrainInfoByName(name);

                if (log.isInfoEnabled()) {
                    log.info("【聚合数据:】" + success);
                }

                // begin to parse source data
                JSONObject object = JSON.parseObject(success);
                JSONObject result = object.getJSONObject("result");

                if(result == null){
                    re.setMessage(FlightCenterStatus.NON_TRAIN.display());
                    re.setState(FlightCenterStatus.NON_TRAIN.value());
                    re.setData(null);
                }else {
                    TrainPo trainPo = result.getJSONObject("train_info").toJavaObject(TrainPo.class);
                    trainPo.setTrainDate(simpleDateFormat.parse(trainDate));

                    // make sure the train is not exist
                    TrainPo query = trainPoMapper.select(trainPo);
                    if (query == null) {
                        trainPoMapper.insert(trainPo);

                        List<StationPo> stations = JSONArray.parseArray(result.getString("station_list"), StationPo.class);

                        // filter by condition
//                    int startStation = 0;
//                    for (StationPo station : stations) {
//                        if (start.equals(station.getStationName())) {
//                            startStation = station.getStationOrdinal();
//                            break;
//                        }
//                    }
//
//                    final int compare = startStation;
//
//                    List<StationPo> result1 = stations.stream()
//                        .filter(x -> x.getStationOrdinal() > compare)
//                        .collect(Collectors.toList());

                        stationPoMapper.insertBatch(stations,trainPo.getTrainId());
                        trainPojo = new TrainPojo();
                        BeanUtils.copyProperties(trainPo,trainPojo);
                        trainPojo.setStationPos(stations);
                    }
                }
            }
            re.setMessage(FlightCenterStatus.SUCCESS.display());
            re.setState(FlightCenterStatus.SUCCESS.value());
            re.setData(trainPojo);
        }catch (Exception e){
            e.printStackTrace();
            re.setMessage(FlightCenterStatus.ERROR.display());
            re.setState(FlightCenterStatus.ERROR.value());
            re.setData(null);
        }
        return JSON.toJSONString(re);
    }

}
