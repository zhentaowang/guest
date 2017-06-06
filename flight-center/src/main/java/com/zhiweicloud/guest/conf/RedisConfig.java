package com.zhiweicloud.guest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by tc on 2017/6/5.
 */
@Configuration
@Import({SpringConfiguration.class})
@EnableTransactionManagement
public class RedisConfig {

    @Value("${redis.keyPrefix}")
    private static String keyPrefix;

    @Value("${redis.host.local}")
    private static String hostLocal;

    @Value("${redis.port.local}")
    private static int portLocal;

    @Value("${redis.password.local}")
    private static String passwordLocal;

    @Value("${redis.host.test}")
    private static String hostTest;

    @Value("${redis.port.test}")
    private static int portTest;

    @Value("${redis.password.test}")
    private static String passwordTest;

    @Value("${redis.maxIdle}")
    private static int maxIdle;

    @Value("${redis.maxActive}")
    private static int maxActive;

    @Value("${redis.maxWait}")
    private static long maxWait;

    @Value("${redis.testOnBorrow}")
    private static boolean testOnBorrow;

    public static String getKeyPrefix() {
        return keyPrefix;
    }

    public static void setKeyPrefix(String keyPrefix) {
        RedisConfig.keyPrefix = keyPrefix;
    }

    public static String getHostLocal() {
        return hostLocal;
    }

    public static void setHostLocal(String hostLocal) {
        RedisConfig.hostLocal = hostLocal;
    }

    public static int getPortLocal() {
        return portLocal;
    }

    public static void setPortLocal(int portLocal) {
        RedisConfig.portLocal = portLocal;
    }

    public static String getPasswordLocal() {
        return passwordLocal;
    }

    public static void setPasswordLocal(String passwordLocal) {
        RedisConfig.passwordLocal = passwordLocal;
    }

    public static String getHostTest() {
        return hostTest;
    }

    public static void setHostTest(String hostTest) {
        RedisConfig.hostTest = hostTest;
    }

    public static int getPortTest() {
        return portTest;
    }

    public static void setPortTest(int portTest) {
        RedisConfig.portTest = portTest;
    }

    public static String getPasswordTest() {
        return passwordTest;
    }

    public static void setPasswordTest(String passwordTest) {
        RedisConfig.passwordTest = passwordTest;
    }

    public static int getMaxIdle() {
        return maxIdle;
    }

    public static void setMaxIdle(int maxIdle) {
        RedisConfig.maxIdle = maxIdle;
    }

    public static int getMaxActive() {
        return maxActive;
    }

    public static void setMaxActive(int maxActive) {
        RedisConfig.maxActive = maxActive;
    }

    public static long getMaxWait() {
        return maxWait;
    }

    public static void setMaxWait(long maxWait) {
        RedisConfig.maxWait = maxWait;
    }

    public static boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public static void setTestOnBorrow(boolean testOnBorrow) {
        RedisConfig.testOnBorrow = testOnBorrow;
    }

}
