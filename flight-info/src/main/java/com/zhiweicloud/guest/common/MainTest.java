package com.zhiweicloud.guest.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tc on 2017/3/18.
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
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
        System.out.println(RSAEncrypt.loadPrivateKeyByFile(filepath));

        signstr = FengShuSignature.sign(params, RSAEncrypt.loadPrivateKeyByFile(filepath));
        System.out.println("签名串：" + signstr);

        System.out.println("---------------公钥检验签名------------------");
        System.out.println("签名原串：" + params.toString());
        System.out.println("签名串：" + signstr);
        System.out.println(RSAEncrypt.loadPublicKeyByFile(filepath));
        System.out.println("验签结果：" + FengShuSignature.doCheck(params, signstr, RSAEncrypt.loadPublicKeyByFile(filepath)));
        System.out.println();


        System.out.println(FengShuSignature.doCheck(params, "ns8n2nvq/ob1uqRij1mTfJ6vd9/OYYNVcEJHLhocLLxqlblTRnBH+eNQZEhaGw8eQ57+7b6BypLR7K1dKGt+g5SieRKSK/UbS0vk0Y1RRybKrrCxOlpuNHBjWxUUdEuY4RLBp8wjCITbZz+6fg0003rOo9MiGmiXIG9cEoeeY94=", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2H3v0l99gO9WoIfn//6erEiVg1v1Sy/07tZMD9oJtzXblBFHga+bsLl4zFHycpDc3MhqZuGUtwfyqIpXroYsrhJF1nCHaRoZJkMEAI89+hfbefVgt4t9Y2LiqrqMJtYZ9q0OjtVVgRh7kfpDnzW803cto/FbQmo3CgI7bHhXikwIDAQAB"));
    }

}
