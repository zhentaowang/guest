package com.airportcloud.protocolmanage.common;

import java.text.DecimalFormat;

/**
 * Created by zhangpengfei on 2016/12/26.
 */
public class GeneratorSerNo {

    //流水号格式：流水号长度为3
    private static final String STR_FORMAT_THREE = "000";

    //流水号格式：流水号长度为4
    private static final String STR_FORMAT_FOUR = "0000";

    /**
     * 根据传入的参数 左边补齐
     * @param serNo 3
     * @return 003
     */
    public static String generatorCodeFormatThree(Integer serNo){
        DecimalFormat df = new DecimalFormat(STR_FORMAT_THREE);
        return df.format(serNo);
    }

    /**
     * 根据传入的参数 左边补齐
     * @param serNo 3
     * @return 0003
     */
    public static String generatorCodeFormatFour(Integer serNo){
        DecimalFormat df = new DecimalFormat(STR_FORMAT_FOUR);
        return df.format(serNo);
    }

}
