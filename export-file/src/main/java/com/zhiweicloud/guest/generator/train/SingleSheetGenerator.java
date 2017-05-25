package com.zhiweicloud.guest.generator.train;

import com.alibaba.fastjson.JSONObject;

/**
 * SingleSheetGenerator.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/24 18:04
 * @author tiecheng
 */
public abstract class SingleSheetGenerator {

    JSONObject object;

    public SingleSheetGenerator(JSONObject object) {
        this.object = object;
    }

    public abstract void create();

    public JSONObject getObject() {
        return object;
    }

}
