package com.zhiweicloud.guest.common;

import com.dragon.security.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Dictionary.java
 * 航班模块字典类
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * @url https://www.zhiweicloud.com
 * @date 2017/3/17 17:45
 * @author tiecheng
 */
public class Dictionary {

    /**
     * 系统码 -- 龙腾
     */
    public static final String SYSCODE = "vip_cloud";

    /**
     * 私钥 -- 龙腾
     */
    public static String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJMFzKLC8AB0d/k4qkl5OXRTRNHgVVGbNcWyA/Od1V1zozsVX5PET2PTn3Pi08F3RuYWiunb39t5UglOKH4yhCZfFswthHVt8K5KtujhcWn/YU10u63qZFtyO5hY8bs25PluR3mu+sN1Uf6nJuOegtTrewYTDH1qZT1fab/xc/9JAgMBAAECgYBIG60NGwz5PCZnjno+hP0/52M2iI9v8e8Cole70WqHZiLl5Iq2/65saKuo+9Qd7DFSwjWpk1SM4UjyFWpY0s/q/bNf8qwTwt4J8cSNLJ9To5kYRV0cJqt2dpF3EYwZYVMXAW5a703om26480xbpKA3bLeogs2CstX+L+DC3xZvMQJBAN/D8SHxJ7VJORxA1aUxR7Q4HmoEWfwDro+oCYI84J8th6Teyz1UlR+1+DvxMWJJxyyUuV+nEUrHdYXRhhGhYAUCQQCoM7m+4sr8aLEVVmi7XdCy5kB8/IbJIZSJoWUhBWHxxDNpzyoeNRAtrmdkrS3Da3nY3vJXQ4ONUhAWlBo9qjl1AkBEjnTfcFI5IbEErfyG+x074wG2e5TSOJKP4uze4SX0vu9THQtpw/NYXtkqDFCUC4ShbQOKgdYzcf7YvAP2fNm9AkAiasMdHgbu8vUZKp8vh5jGonqbmIhz610bdFzfDtzlvYHPqsYxfCYdUsxxO0Zb8Ef0alooJPZsGliUZiWgbI5pAkA4LcX7cyHhQSzDXe9hRUSL3vhjkG5cXrXNDup2zsfcZO0Pv4XYatK+8VAlZxhtyskqUwuHi93OxrcL1Xu+x8jX";

    /**
     * 语言 -- 龙腾
     */
    public static final String LG = "zh-cn";

    public static final String ENCODING_UTF_8 = "UTF-8";

    public static final String ENCODING_ISO8859_1 = "ISO-8859-1";

    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static void getKeyPari() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024,new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair(); // 生成一个密钥对，保存在keyPair中
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到公钥字符串
            String publicKeyString = Base64.encode(publicKey.getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64.encode(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }


}
