package com.zhiweicloud.guest.generator.train;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;

/**
 * DetailBillGenerator.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/26 10:47
 * @author tiecheng
 */
public class DetailBillGenerator extends TrainGenerator {

    public DetailBillGenerator(JSONObject object, HttpServletResponse response) {
        super(object, response);
    }

}
