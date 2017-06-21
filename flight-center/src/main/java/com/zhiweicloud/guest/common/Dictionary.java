package com.zhiweicloud.guest.common;

import com.zhiweicloud.guest.signature.RSACoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tc on 2017/5/13.
 */
public final class Dictionary {

    /**
     * 私有默认构造.
     */
    private Dictionary() {

    }

    /**
     * 起始打印信息
     */
    public static final String START_MESSAGE = "flight-center start";

    public static Map<Integer, String> errorCodeEnum = new HashMap<>();

    static {
        errorCodeEnum.put(202201, "车次不能为空");
        errorCodeEnum.put(202202, "查询不到车次的相关信息");
        errorCodeEnum.put(202203, "出发站或终点站不能为空");
        errorCodeEnum.put(202204, "查询不到结果");
        errorCodeEnum.put(202205, "错误的出发站名称");
        errorCodeEnum.put(202206, "错误的到达站名称");
        errorCodeEnum.put(202207, "查询不到余票相关数据哦");
        errorCodeEnum.put(202209, "请求12306网络错误,请重试");
        errorCodeEnum.put(202212, "查询出错");
    }

    /**
     * 系统码 -- 龙腾.
     */
    public static final String SYSCODE = "vip_cloud";

    /**
     * 私钥 -- 龙腾.
     */
    public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAAS"
        + "CAl8wggJbAgEAAoGBAJMFzKLC8AB0d/k4qkl5OXRTRNHgVVGbNcWyA/Od1V1zozs"
        + "VX5PET2PTn3Pi08F3RuYWiunb39t5UglOKH4yhCZfFswthHVt8K5KtujhcWn/YU"
        + "10u63qZFtyO5hY8bs25PluR3mu+sN1Uf6nJuOegtTrewYTDH1qZT1fab/xc/"
        + "9JAgMBAAECgYBIG60NGwz5PCZnjno+hP0/52M2iI9v8e8Cole70WqHZiLl5Iq2/"
        + "65saKuo+9Qd7DFSwjWpk1SM4UjyFWpY0s/q/bNf8qwTwt4J8cSNLJ9To5kYRV0"
        + "cJqt2dpF3EYwZYVMXAW5a703om26480xbpKA3bLeogs2CstX+L+DC3xZvMQJBAN/"
        + "D8SHxJ7VJORxA1aUxR7Q4HmoEWfwDro+oCYI84J8th6Teyz1UlR+1+DvxMWJJxy"
        + "yUuV+nEUrHdYXRhhGhYAUCQQCoM7m+4sr8aLEVVmi7XdCy5kB8/IbJIZSJoWUhB"
        + "WHxxDNpzyoeNRAtrmdkrS3Da3nY3vJXQ4ONUhAWlBo9qjl1AkBEjnTfcFI5IbEE"
        + "rfyG+x074wG2e5TSOJKP4uze4SX0vu9THQtpw/NYXtkqDFCUC4ShbQOKgdYzcf7"
        + "YvAP2fNm9AkAiasMdHgbu8vUZKp8vh5jGonqbmIhz610bdFzfDtzlvYHPqsYxf"
        + "CYdUsxxO0Zb8Ef0alooJPZsGliUZiWgbI5pAkA4LcX7cyHhQSzDXe9hRUSL3vh"
        + "jkG5cXrXNDup2zsfcZO0Pv4XYatK+8VAlZxhtyskqUwuHi93OxrcL1Xu+x8jX";

    /**
     * 语言 -- 龙腾.
     */
    public static final String LG = "zh-cn";

    /**
     * 默认UTF-8编码.
     */
    public static final String ENCODING_UTF_8 = "UTF-8";

    /**
     * 默认ISO8859-1编码.
     */
    public static final String ENCODING_ISO8859_1 = "ISO-8859-1";

    public static final int testPort = 8901;

    public static final int proPort = 8080;

}
