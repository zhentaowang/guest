package com.zhiweicloud.guest.signature;

import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * RSACoder.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/26 20:36 
 * @author tiecheng
 */
public abstract class RSACoder {

    /**
     * 数字签名
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 数字签名
     * 签名/验证算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";

    // 公钥
    public static final String PUBLIC_KEY = "RSAPublicKey";

    // 私钥
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    private static byte[] salt = "slajlghei##lgsl21&213".getBytes();

    /**
     * RSA密钥长度 默认1024位
     * 密钥长度必须是64倍数
     * 范围在512~65536位之间
     */
    private static final int KEY_SIZE = 1024;

    /**
     * 签名
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return byte[] 数字签名
     * @throws Exception
     */
    public static byte[] sign(byte[] data,byte[] privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);
        // 签名
        return signature.sign();
    }

    public static byte[] sign(String data,byte[] privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data.getBytes());
        // 签名
        return signature.sign();
    }

    public static byte[] sign(byte[] data,String privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);
        // 签名
        return signature.sign();
    }

    public static byte[] sign(String data,String privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data.getBytes());
        // 签名
        return signature.sign();
    }

    public static String signToStr(byte[] data,byte[] privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);
        // 签名
        return Base64.encode(signature.sign());
    }

    public static String signToStr(String data,byte[] privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data.getBytes());
        // 签名
        return Base64.encode(signature.sign());
    }

    public static String signToStr(byte[] data,String privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);
        // 签名
        return Base64.encode(signature.sign());
    }

    public static String signToStr(String data,String privateKey) throws Exception{
        // 转换私钥材料
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        // 实力化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data.getBytes());
        // 签名
        return Base64.encode(signature.sign());
    }

    public static String signToStr(Map<String,Object> data, String privateKey) throws Exception {
        String signContent = getSignContent(data);
        return signToStr(signContent, privateKey);
    }

    /**
     * 校验
     * @param data 待校验数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * @return boolean 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception{
        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }

    public static boolean verify(String data, byte[] publicKey, byte[] sign) throws Exception{
        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data.getBytes());
        // 验证
        return signature.verify(sign);
    }

    public static boolean verify(byte[] data, byte[] publicKey, String sign) throws Exception{
        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(Base64.decode(sign));
    }

    public static boolean verify(String data, String publicKey, String sign) throws Exception{
        // 转换公钥材料
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
        // 实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 生成公钥
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data.getBytes());
        // 验证
        return signature.verify(Base64.decode(sign));
    }

    public static boolean verify(Map<String,Object> data, String publicKey, String sign) throws Exception{
        String signContent = getSignContent(data);
        return verify(signContent, publicKey, sign);
    }

    private static String getSignContent(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        List list = new ArrayList<>(params.keySet());
        Collections.sort(list);

        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            String key = (String) list.get(i);
            String value = (String) params.get(key);
            if (StringUtils.isNoneBlank(key) && StringUtils.isNoneBlank(value)) {
                sb.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * 取得私钥
     * @param keyMap 密钥Map
     * @return byte[] 私钥
     * @throws Exception
     */
    public static byte[] getPrivateKey(Map<String,Object> keyMap) throws Exception{
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    public static RSAPrivateKey getPrivateKeyByByte(byte[] privateKey) throws Exception{
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new SignatureException("私钥非法");
        } catch (NullPointerException e) {
            throw new SignatureException("私钥数据为空");
        }
    }

    /**
     * 从字符串中获取私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @return RSAPrivateKey 私钥
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKeyByStr(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new SignatureException("私钥非法");
        } catch (NullPointerException e) {
            throw new SignatureException("私钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param path 私钥文件名
     * @return 私钥
     * @throws Exception
     */
    public static String getPrivateKeyByFile(String path) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(path + "/privateKey.keystore"))) {
            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥Map
     * @return byte[] 公钥
     * @throws Exception
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    public static RSAPublicKey getPublicKeyByByte(byte[] publicKey) throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new SignatureException("公钥非法");
        } catch (NullPointerException e) {
            throw new SignatureException("公钥数据为空");
        }
    }

    /**
     * 从字符串中获取公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @return RSAPublicKey 公钥
     * @throws Exception
     */
    public static RSAPublicKey getPublicKeyByStr(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new SignatureException("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new SignatureException("公钥非法");
        } catch (NullPointerException e) {
            throw new SignatureException("公钥数据为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param path  公钥输入流
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static String getPublicKeyByFile(String path) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(path + "/publicKey.keystore"))){
            String readLine;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                sb.append(readLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 生成密钥对
     *
     * @return
     */
    public static Map<String,Object> initKey(){
        // 实例化密钥生成器
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
//        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 封装密钥
        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 初始化密钥对文件
     *
     * @param filePath
     */
    public static void initKeyFile(String filePath){
        Map<String, Object> map = initKey();
        RSAPublicKey publicKey = (RSAPublicKey) map.get(PUBLIC_KEY);
        RSAPrivateKey privateKey = (RSAPrivateKey) map.get(PRIVATE_KEY);
        // 得到公钥字符串
        String publicKeyString = Base64.encode(publicKey.getEncoded());
        // 得到私钥字符串
        String privateKeyString = Base64.encode(privateKey.getEncoded());
        try (FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
             FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
             BufferedWriter pubbw = new BufferedWriter(pubfw);
             BufferedWriter pribw = new BufferedWriter(prifw)) {
            // 将密钥对写入到文件
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pribw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 公钥加密过程
     *
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return 密文数据
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new SignatureException("加密公钥为空, 请设置");
        }
        Cipher cipher;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥加密过程
     *
     * @param privateKey 私钥
     * @param plainTextData 明文数据
     * @return 密文数据
     * @throws Exception
     *             加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    public static byte[] encrypt(byte[] privateKey, byte[] plainTextData) throws Exception {
        if (privateKey == null) {
            throw new Exception("加密私钥为空, 请设置");
        }
        Cipher cipher;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKeyByByte(privateKey));
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文数据
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 公钥解密过程
     *
     * @param publicKey 公钥
     * @param cipherData 密文数据
     * @return 明文数据
     * @throws Exception
     *             解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    public static byte[] decrypt(byte[] publicKey, byte[] cipherData) throws Exception {
        if (publicKey == null) {
            throw new Exception("解密公钥为空, 请设置");
        }
        Cipher cipher;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, getPublicKeyByByte(publicKey));
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }

    /**
     * 加盐加密
     * @param str
     * @param salt
     * @return
     */
    public static String saltEncrypt(String str, byte[] salt) throws Exception{
        String result = "";
        MessageDigest instance = null;
        BASE64Encoder b64Encoder = new BASE64Encoder();
        try {
            instance = MessageDigest.getInstance("MD5", "SUN");
            instance.reset();
            instance.update(salt);
            instance.update(str.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            result += (char)salt[0];
            result += (char)salt[1];
            result +=b64Encoder.encode(digest);
        } catch (UnsupportedEncodingException e) {
            throw new Exception("不支持的编码格式");
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (NoSuchProviderException e) {
            throw new Exception("无此提供者");
        }
        return result;
    }

    public static String saltEncrypt(String str) throws Exception{
        return saltEncrypt(str, salt);
    }

    private static byte[] getSaltOfASCII(int len){
        byte[] salt = new byte[len];
        Random rand = new Random();
        for(int i=0; i<len; i++){
            salt[i] = (byte) ((rand.nextInt('~'-'!')+'!') & 0x007f);
        }
        return salt;
    }

}
