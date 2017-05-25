package com.zhiweicloud.guest.source.juhe.service;

import com.zhiweicloud.guest.source.juhe.util.JuheUtils;
import org.springframework.stereotype.Service;

/**
 * JuheService.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/24 19:51
 * @author tiecheng
 */
@Service
public class JuheService {

    public String queryTrainInfoByName(String name) throws Exception {
        return JuheUtils.queryTrainInfoByName(name);
    }

    public String queryTrainByStation(String start,String end,String date) throws Exception {
        return JuheUtils.queryTrainInfoByStation(start, end, date);
    }

}
