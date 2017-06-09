package com.zhiweicloud.guest.common.util;

import com.zhiweicloud.guest.conf.RedisConfig;
import jersey.repackaged.com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * JedisUtils.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/5 14:30 
 * @author tiecheng
 */
public class JedisUtils {

    private static final Log log = LogFactory.getLog(JedisUtils.class);

    public static final String KEY_PREFIX = "flight_center";

    private static JedisPool jedisPool = null;

    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXPIRE_HOUR = 60 * 60;          //一小时
    public final static int EXPIRE_DAY = 60 * 60 * 24;            //一天
    public final static int EXPIRE_MONTH = 60 * 60 * 24 * 30;           //一个月

    private static void init(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(RedisConfig.getMaxActive());
        config.setMaxIdle(RedisConfig.getMaxIdle());
        config.setMaxWaitMillis(RedisConfig.getMaxWait());
        config.setTestOnBorrow(RedisConfig.isTestOnBorrow());
        try {
            jedisPool = new JedisPool(config, RedisConfig.getHostLocal(), RedisConfig.getPortLocal());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("First create JedisPool error : "+e);
            }
            try{
                jedisPool = new JedisPool(config, RedisConfig.getHostTest(), RedisConfig.getPortTest());
            }catch(Exception e2){
                if (log.isErrorEnabled()) {
                    log.error("Second create JedisPool error : "+e2);
                }
            }
        }
    }

    static {
        if (jedisPool == null) {
            init();
        }
    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis(){
        Jedis redis = null;
        try {
            if (jedisPool != null) {
                redis = jedisPool.getResource();
                return redis;
            } else {
                return null;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        }finally {
            returnResource(redis);
        }
        return redis;
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool !=null) {
            jedis.close();
        }
    }

    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String set(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(KEY_PREFIX + key, value);
            if (cacheSeconds >= 0) {
                jedis.expire(KEY_PREFIX + key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    public static String set(String key, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(KEY_PREFIX + key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(KEY_PREFIX + key)) {
                value = jedis.get(KEY_PREFIX + key);
                value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.set(getBytesKey(KEY_PREFIX + key), toBytes(value));
            if (cacheSeconds >= 0) {
                jedis.expire(KEY_PREFIX + key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key) {
        Object value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(getBytesKey(KEY_PREFIX + key))) {
                value = toObject(jedis.get(getBytesKey(KEY_PREFIX + key)));
            }
        } catch (Exception e) {

        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public static List<String> getList(String key) {
        List<String> value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(KEY_PREFIX + key)) {
                value = jedis.lrange(KEY_PREFIX + key, 0, -1);
            }
        } catch (Exception e) {

        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setList(String key, List<String> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(KEY_PREFIX + key)) {
                jedis.del(KEY_PREFIX + key);
            }

            String[] valueArray = new String[value.size()] ;

            for(int i = 0 ;i < value.size() ;i ++ ) {
                valueArray[i] = value.get(i) ;
            }

            result = jedis.rpush(KEY_PREFIX + key, valueArray);
            if (cacheSeconds != 0) {
                jedis.expire(KEY_PREFIX + key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(getBytesKey(KEY_PREFIX + key))) {
                jedis.del(KEY_PREFIX + key);
            }
            List<byte[]> list = Lists.newArrayList();
            for (Object o : value) {
                list.add(toBytes(o));
            }
            result = jedis.rpush(getBytesKey(KEY_PREFIX + key), (byte[][]) list.toArray());
            if (cacheSeconds != 0) {
                jedis.expire(KEY_PREFIX + key, cacheSeconds);
            }
        } catch (Exception e) {

        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public static List<Object> getObjectList(String key) {
        List<Object> value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis.exists(getBytesKey(KEY_PREFIX + key))) {
                List<byte[]> list = jedis.lrange(getBytesKey(KEY_PREFIX + key), 0, -1);
                value = Lists.newArrayList();
                for (byte[] bs : list) {
                    value.add(toObject(bs));
                }
            }
        } catch (Exception e) {

        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public static boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.exists(KEY_PREFIX + key);
        } catch (Exception e) {
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public static boolean existsObject(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            result = jedis.exists(getBytesKey(KEY_PREFIX + key));
        } catch (Exception e) {
        } finally {
            returnResource(jedis);
        }
        return result;
    }

    /**
     * 获取byte[]类型Key
     *
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            byte[] result = null;
            try {
                result = ((String) object).getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            return result;
        } else {
            return ObjectUtils.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     *
     * @param object
     * @return
     */
    public static byte[] toBytes(Object object) {
        return ObjectUtils.serialize(object);
    }


    /**
     * byte[]型转换Object
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        return ObjectUtils.unserialize(bytes);
    }

}
