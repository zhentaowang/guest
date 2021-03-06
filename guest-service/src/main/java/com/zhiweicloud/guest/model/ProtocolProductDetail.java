package com.zhiweicloud.guest.model;

import java.util.HashMap;
import java.util.Map;

/**
 * ProtocolProductDetail.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-07 16:25:01 Created By wzt
 */
public enum ProtocolProductDetail {
    guestRoom_price("price", 1),//贵宾厅
    accompany_price("price", 3), accompany_freeRetinueNum("freeRetinueNum", 3), accompany_overStaffUnitPrice("overStaffUnitPrice", 3),//迎送机陪同
    restRoom_price("price", 5),//休息室
    securityCheckChannel_price("price", 6);//安检通道
    // 成员变量
    private String name;
    private int typeId;
    // 构造方法
    ProtocolProductDetail(String name, int typeId) {
        this.name = name;
        this.typeId = typeId;
    }
    // 普通方法
    public static Map<String,Object> getProtocolProductFieldName(Long typeId) {
        Map<String,Object> protocolProductFieldName = new HashMap<>();
        for (ProtocolProductDetail protocolProductDetail : ProtocolProductDetail.values()) {
            if (protocolProductDetail.getTypeId() == typeId) {
                protocolProductFieldName.put(protocolProductDetail.getName(),0);
            }
        }
        return protocolProductFieldName;
    }
    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
