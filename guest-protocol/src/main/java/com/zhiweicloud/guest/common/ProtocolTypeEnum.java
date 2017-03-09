package com.zhiweicloud.guest.common;



/**
 * 协议类型
 * Created by zhengyiyin on 2017/2/27.
 */
public enum  ProtocolTypeEnum {

    PROTOCOL_TYPE_1(1, "冠名客户"),
    PROTOCOL_TYPE_2(2, "服务会员"),
    PROTOCOL_TYPE_3(3, "地方政要"),
    PROTOCOL_TYPE_4(4, "机场接待"),
    PROTOCOL_TYPE_5(5, "副部级政要"),
    PROTOCOL_TYPE_6(6, "包量客户"),
    PROTOCOL_TYPE_7(7, "结算会员"),
    PROTOCOL_TYPE_8(8, "结算金银卡"),
    PROTOCOL_TYPE_9(9, "结算头等舱"),
    PROTOCOL_TYPE_10(10, "零散散客");


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
