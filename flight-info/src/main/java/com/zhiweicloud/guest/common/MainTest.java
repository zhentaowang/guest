package com.zhiweicloud.guest.common;

import com.dragon.sign.DragonSignature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tc on 2017/3/18.
 */
public class MainTest {

    public static void tt() throws Exception{
        String filepath = "C:/excel/";

        System.out.println("--------------公钥加密私钥解密过程-------------------");
        String plainText = "ihep_公钥加密私钥解密";
        //公钥加密过程
        byte[] cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), plainText.getBytes());
        String cipher = Base64.encode(cipherData);
        //私钥解密过程
        byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        String restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("--------------私钥加密公钥解密过程-------------------");
        plainText = "ihep_私钥加密公钥解密";
        //私钥加密过程
        cipherData = RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), plainText.getBytes());
        cipher = Base64.encode(cipherData);
        //公钥解密过程
        res = RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));
        restr = new String(res);
        System.out.println("原文：" + plainText);
        System.out.println("加密：" + cipher);
        System.out.println("解密：" + restr);
        System.out.println();

        System.out.println("---------------私钥签名过程------------------");
        String content = "ihep_这是用于签名的原始数据";
        String signstr = FengShuSignature.sign(content, RSAEncrypt.loadPrivateKeyByFile(filepath));
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signstr);
        System.out.println();

        System.out.println("---------------公钥校验签名------------------");
        System.out.println("签名原串：" + content);
        System.out.println("签名串：" + signstr);

        System.out.println("验签结果：" + FengShuSignature.doCheck(content, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));
        System.out.println();

        System.out.println("---------------测试签名参数------------------");

        System.out.println("---------------私钥进行签名------------------");

        String url = "http://121.14.200.54:7072";
        String sysCode = "dragon";
        Map<String, String> params = new HashMap<>();
//        params.put("url", url);
        params.put("sysCode", sysCode);
        System.out.println("签名原串：" + params.toString());
        signstr = FengShuSignature.sign(params, RSAEncrypt.loadPrivateKeyByFile(filepath));
        System.out.println("签名串：" + signstr);

        System.out.println("---------------公钥检验签名------------------");
        System.out.println("签名原串：" + params.toString());
        System.out.println("签名串：" + signstr);
        System.out.println(RSAEncrypt.loadPublicKeyByFile(filepath));
        System.out.println("验签结果：" + FengShuSignature.doCheck(params, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        String data = "data = {" +
                "\"fd_id\": \"2139\"," +
                "\"FlightNo\": \"MU2474\"," +
                "\"FlightDate\": \"2017-03-20\"," +
                "\"FlightCompany\": \"上海东方航空公司\"," +
                "\"FlightDepcode\": \"NGB\"," +
                "\"FlightArrcode\": \"LHW\"," +
                "\"FlightDeptimePlanDate\": \"2017-03-20 15:10:00\"," +
                "\"FlightArrtimePlanDate\": \"2017-03-20 20:10:00\"," +
                "\"FlightDeptimeReadyDate\": \"\"," +
                "\"FlightArrtimeReadyDate\": \"\"," +
                "\"FlightDeptimeDate\": \"2017-03-18 12:30:45\"," +
                "\"FlightArrtimeDate\": \"\"," +
                "\"stopFlag\": \"\"," +
                "\"shareFlag\": \"\"," +
                "\"ShareFlightNo\": \"\"," +
                "\"FillFlightNo\": \"\"," +
                "\"BoardGate\": \"\"," +
                "\"BoardState\": \"\"," +
                "\"FlightState\": \"Plan\"," +
                "\"FlightHTerminal\": \"\"," +
                "\"FlightTerminal\": \"T2\"," +
                "\"FlightDep\": \"宁波市\"," +
                "\"FlightArr\": \"兰州市\"," +
                "\"FlightDepAirport\": \"宁波栎社国际机场\"," +
                "\"FlightArrAirport\": \"兰州中川机场\"," +
                "\"alternate_info\": \"\"," +
                "\"org_timezone\": \"\"," +
                "\"dst_timezone\": \"\"," +
                "\"fcategory\": \"\"," +
                "\"fid\": \"\"," +
                "\"BoardGateTime\": \"\"" +
                "}";
        System.out.println(DragonSignature.rsaSign(data.replace(" ",""), Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8));
        System.out.println(DragonSignature.rsaSign(data, Dictionary.PRIVATE_KEY, Dictionary.ENCODING_UTF_8));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        long time = 1490074398000l;


        String s = "{\"FD_ID\": \"0\",\"FlightNo\": \"MU2474\",\"FlightDate\": \"2017-03-20\",\"FlightCompany\": \"中国东方航空股份有限公司\",\"FlightDepcode\": \"NGB\",\"FlightArrcode\": \"LHW\",\"FlightDeptimePlanDate\": \"2017-03-20 15:10:00\",\"FlightArrtimePlanDate\": \"2017-03-20 20:10:00\",\"FlightDeptimeReadyDate\": \"\",\"FlightArrtimeReadyDate\": \"\",\"FlightDeptimeDate\": \"\",\"FlightArrtimeDate\": \"\",\"StopFlag\": \"\",\"ShareFlag\": \"\",\"ShareFlightNo\": \"\",\"FillFlightNo\": \"\",\"BoardGate\": \"\",\"BoardState\": \"\",\"FlightState\": \"Plan\",\"FlightHTerminal\": \"\",\"FlightTerminal\": \"T2\",\"FlightDep\": \"宁波市\",\"FlightArr\": \"兰州市\",\"FlightDepAirport\": \"宁波栎社国际机场\",\"FlightArrAirport\": \"兰州中川机场\",\"Fcategory\": \"\",\"BoardGateTime\": \"\"}";
        System.out.println(s.replace(" ","").toString());
        System.out.println(simpleDateFormat.format(new Date(time)));

    }

}
