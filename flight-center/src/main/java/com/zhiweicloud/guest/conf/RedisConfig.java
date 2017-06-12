package com.zhiweicloud.guest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by tc on 2017/6/5.
 */
//@Configuration
//@Import({SpringConfiguration.class})
//@EnableTransactionManagement
public class RedisConfig {

    @Value("${redis.keyPrefix}")
    private String keyPrefix;

    @Value("${redis.host.local}")
    private String hostLocal;

    @Value("${redis.port.local}")
    private int portLocal;

    @Value("${redis.password.local}")
    private String passwordLocal;

    @Value("${redis.host.test}")
    private String hostTest;

    @Value("${redis.port.test}")
    private int portTest;

    @Value("${redis.password.test}")
    private String passwordTest;

    @Value("${redis.maxIdle}")
    private int maxIdle;

    @Value("${redis.maxActive}")
    private int maxActive;

    @Value("${redis.maxWait}")
    private long maxWait;

    @Value("${redis.testOnBorrow}")
    private boolean testOnBorrow;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getHostLocal() {
        return hostLocal;
    }

    public void setHostLocal(String hostLocal) {
        this.hostLocal = hostLocal;
    }

    public int getPortLocal() {
        return portLocal;
    }

    public void setPortLocal(int portLocal) {
        this.portLocal = portLocal;
    }

    public String getPasswordLocal() {
        return passwordLocal;
    }

    public void setPasswordLocal(String passwordLocal) {
        this.passwordLocal = passwordLocal;
    }

    public String getHostTest() {
        return hostTest;
    }

    public void setHostTest(String hostTest) {
        this.hostTest = hostTest;
    }

    public int getPortTest() {
        return portTest;
    }

    public void setPortTest(int portTest) {
        this.portTest = portTest;
    }

    public String getPasswordTest() {
        return passwordTest;
    }

    public void setPasswordTest(String passwordTest) {
        this.passwordTest = passwordTest;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

}
