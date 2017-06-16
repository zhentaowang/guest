package com.zhiweicloud.guest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by tc on 2017/6/5.
 */
@Configuration()
@Import({SpringConfiguration.class})
public class RedisConfig {

    public static String keyPrefix;

    public static String hostLocal;

    public static int portLocal;

    public static String passwordLocal;

    public static String hostTest;

    public static int portTest;

    public static String passwordTest;

    public static int maxIdle;

    public static int maxActive;

    public static long maxWait;

    public static boolean testOnBorrow;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    @Value("${redis.keyPrefix}")
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getHostLocal() {
        return hostLocal;
    }

    @Value("${redis.host.local}")
    public void setHostLocal(String hostLocal) {
        this.hostLocal = hostLocal;
    }

    public int getPortLocal() {
        return portLocal;
    }

    @Value("${redis.port.local}")
    public void setPortLocal(int portLocal) {
        this.portLocal = portLocal;
    }

    public String getPasswordLocal() {
        return passwordLocal;
    }

    @Value("${redis.password.local}")
    public void setPasswordLocal(String passwordLocal) {
        this.passwordLocal = passwordLocal;
    }

    public String getHostTest() {
        return hostTest;
    }

    @Value("${redis.host.test}")
    public void setHostTest(String hostTest) {
        this.hostTest = hostTest;
    }

    public int getPortTest() {
        return portTest;
    }

    @Value("${redis.port.test}")
    public void setPortTest(int portTest) {
        this.portTest = portTest;
    }

    public String getPasswordTest() {
        return passwordTest;
    }

    @Value("${redis.password.test}")
    public void setPasswordTest(String passwordTest) {
        this.passwordTest = passwordTest;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    @Value("${redis.maxIdle}")
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    @Value("${redis.maxActive}")
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    @Value("${redis.maxWait}")
    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    @Value("${redis.testOnBorrow}")
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

}
