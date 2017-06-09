package com.zhiweicloud.guest.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by tc on 2017/6/9.
 */
public class PropertyUtils {

    private static final Log log = LogFactory.getLog(PropertyUtils.class);

    public static Properties load(String fileName) {
        log.info("开始加载properties文件内容.......");
        Properties props = new Properties();
        try (InputStream in = PropertyUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            // 第一种，通过类加载器进行获取properties文件流
            // 第二种，通过类进行获取properties文件流
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            log.error("文件未找到");
        } catch (IOException e) {
            log.error("出现IOException");
        }
        log.info("加载properties文件内容完成...........");
        log.info("properties文件内容：" + props);
        return props;
    }

    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }

    /**
     * 获取字符型属性（可指定默认值）
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取布尔型属性（默认值为 false）
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 获取布尔型属性（可指定默认值）
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = Boolean.parseBoolean(props.getProperty(key));
        }
        return value;
    }

    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    // 获取数值型属性（可指定默认值）
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.containsKey(key)) {
            value = Integer.parseInt(props.getProperty(key));
        }
        return value;
    }

    public static long getLong(Properties props, String key) {
        return getLong(props, key, 0L);
    }

    // 获取数值型属性（可指定默认值）
    public static long getLong(Properties props, String key, long defaultValue) {
        long value = defaultValue;
        if (props.containsKey(key)) {
            value = Long.parseLong(props.getProperty(key));
        }
        return value;
    }

}
