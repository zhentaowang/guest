package com.zhiweicloud.guest.common;



/**
 * 协议类型
 * Created by zhengyiyin on 2017/2/27.
 */
public enum  ProtocolTypeEnum {

    PROTOCOL_TYPE_1(1, "协议类型1"),
    PROTOCOL_TYPE_2(2, "协议类型2"),
    PROTOCOL_TYPE_3(3, "协议类型3"),
    PROTOCOL_TYPE_4(4, "协议类型4"),
    PROTOCOL_TYPE_5(5, "协议类型5");


    private Integer typeValue;

    private String typeName;



    //构造函数必须为private的,防止意外调用
    private ProtocolTypeEnum(Integer typeValue, String typeName){
        this.typeValue = typeValue;
        this.typeName = typeName;
    }

    public static String getTypeName(int protocolType) {
        for (ProtocolTypeEnum p : ProtocolTypeEnum.values()) {
            if (p.getTypeValue() == protocolType) {
                return p.typeName;
            }
        }
        return "";
    }

    public Integer getTypeValue() {
        return typeValue;
    }

    public String getTypeName() {
        return typeName;
    }

}
