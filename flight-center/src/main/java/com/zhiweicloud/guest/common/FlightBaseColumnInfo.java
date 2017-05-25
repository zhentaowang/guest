package com.zhiweicloud.guest.common;

/**
 * 航班中心标准字段名
 */
public enum FlightBaseColumnInfo {

    DEP_EN_NAME("dapEnName","出发地英文"),
    DEP_CITY("arrCity","出发城市"),
    DEP_DATE("depDate","出发日期"),
    DEP_AIRPORT("depAirport","出发机场"),
    DEP_AIRPORT_CODE("depAirportCode","出发机场三字码"),
    DEP_AIRPORT_NAME("depAirportName","出发机场名"),
    DEP_TIME_ZONE("depTimeZone","出发地时区"),
    DEP_TERMINAL("depTerminal","出发机场候机楼"),
    DEP_SCHEDULED_DATE("depScheduledDate","计划起飞时间"),
    DEP_ESTIMATED_DATE("depEstimatedDate","预计起飞时间"),
    DEP_ACTUAL_DATE("depActualDate","实际起飞时间"),

    BOARD_GATE("boardGate","登机口"),
    BOARD_TIME("boardTime","登机时间"),
    BOARD_STATE("boardState","乘机状态（开始值机，值机结束，开始登机，催促登机，登机结束）"),
    FLIGHT_NO("flightNo","航班号"),
    FLIGHT_TYPE("flightType","机型"),
    PLANE_NO("planeNo","机号"),
    FLIGHT_STATE_CODE("flightStateCode","航班状态代码"),
    FLIGHT_STATE("flightState","航班状态"),
    BOARD_IN_OUT("boardInOut","国际国内：国外：0，国内：1"),
    IS_IN_OR_OUT("isInOrOut","出港：0，进港1 出发/到达"),
    IS_NEAR_OR_FAR("isNearOrFar","远机位：0，近机位：1"),
    CHECK_IN_COUNTER("checkInCounter","值机柜台"),
    CAROUSEL("carousel","行李盘"),
    STOP_FLAG("stopFlag","是否经停"),

    TRANSFER_AIRPORT("TransferAirport","中转机场"),
    TRANSFER_AIRPORT_EN_NAME("TransferAirportCodeEnName","中转机场英文名"),
    TRANSFER_AIRPORT_CODE("TransferAirportCode","中转机场三字码"),

    ARR_EN_NAME("arrEnName","到达地英文"),
    ARR_CITY("arrCity","到达城市"),
    ARR_DATE("arrDate","到达日期"),
    ARR_AIRPORT("arrAirport","到达机场"),
    ARR_AIRPORT_CODE("arrAirportCode","到达机场三字码"),
    ARR_AIRPORT_NAME("arrAirportName","到达机场名"),
    ARR_TIME_ZONE("arrTimeZone","到达地时区"),
    ARR_TERMINAL("arrTerminal","到达地机场候机楼（接机楼）"),
    ARR_SCHEDULED_DATE("arrScheduledDate","计划到达时间"),
    ARR_ESTIMATED_DATE("arrEstimatedDate","预计到达时间"),
    ARR_ACTUAL_DATE("arrActualDate","实际到达时间"),


    AIRLINE_CODE("airlineCode","航空公司编号"),
    AIRLINE_EN_NAME("airlineEnName","航空公司英语名"),
    AIRLINE_NAME("airlineName","航空公司名"),

    CABIN_NO("cabinNo","舱位"),

    BAGGAGE_WEIGHT("baggageWeight","行李重量"),
    BAGGAGE_PIECE("baggagePiece","行李价格"),
    PAY_METHOD("payMethod","支付方法"),

    BOARD_NO("boardNo","登机牌编号"),
    PASSENGER_NAME("passengerName","旅客名"),
    PASSENGER_TYPE("passengerType","旅客类型"),

    TICKET_NO("ticketNo","客票号"),
    TICKET_STATE("ticketState","客票状态"),

    FARE("fare","票价"),
    FARE_COMPUTE("fareCompute","票价计算信息"),

    CURRENT_TYPE("currencyType","货币类型"),
    EXCHANGE_INFO("exchangeInfo","改签信息"),

    // 不明确的字段
    LEG_TYPE("legType","航段类型"),
    LEG_STATE("legState","航段状态"),
    FOLLOW_TICKET_NO("followTicketNo","后续票号"),
    ISI("ISI","ISI信息"),
    ORIGINAL_ISSUE("originalIssue","原始信息（OI信息）"),
    ORIGINAL_STRING("originalString","原始字符信息"),
    RECEIPT_PRINTED("receiptPrinted","是否已打印T4(发票)联"),
    INFANT_BIRTHDAY("infantBirthDay","婴儿的生日（年月）")
    ;

    private String key;

    private String value;

    FlightBaseColumnInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByKey(String key){
        String result = null;
        for (FlightBaseColumnInfo flightBaseColumnInfo : FlightBaseColumnInfo.values()) {
            if (key.equals(flightBaseColumnInfo.getKey())) {
                result = flightBaseColumnInfo.getValue();
            }
        }
        return result;
    }

}
