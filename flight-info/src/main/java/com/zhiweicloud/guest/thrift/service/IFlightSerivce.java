package com.zhiweicloud.guest.thrift.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 航班服务接口
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/9 20:27
 * @author tiecheng
 */
public interface IFlightSerivce {

    String handle(JSONObject request);

}
