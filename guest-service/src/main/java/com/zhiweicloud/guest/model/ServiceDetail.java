package com.zhiweicloud.guest.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzt on 2017/2/23.
 */
public enum ServiceDetail {
    guestRoom_departType("departType", 1), guestRoom_region("region", 1), guestRoom_positionNum("positionNum", 1),//贵宾厅
    ferryPush_seatNum("seatNum", 2), ferryPush_plateNumber("plateNumber", 2), //VIP摆渡车
    restRoom_departType("departType", 5), restRoom_region("region", 5), restRoom_positionNum("positionNum", 5),//休息室
    securityCheckChannel_seatNum("seatNum", 6), securityCheckChannel_plateNumber("plateNumber", 6),//安检通道
    apronShuttleBus_seatNum("seatNum", 7), apronShuttleBus_plateNumber("plateNumber", 7);//远机位摆渡车
    // 成员变量
    private String name;
    private int typeId;
    // 构造方法
    private ServiceDetail(String name, int typeId) {
        this.name = name;
        this.typeId = typeId;
    }
    // 普通方法
    public static Map<String,Object> getServiceFieldName(Long typeId) {
        Map<String,Object> serviceFieldName = new HashMap<>();
        for (ServiceDetail serviceDetail : ServiceDetail.values()) {
            if (serviceDetail.getTypeId() == typeId) {
                serviceFieldName.put(serviceDetail.getName(),typeId);
            }
        }
        return serviceFieldName;
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
