package com.zhiweicloud.guest.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.List;

/**
 * SerializeUtils.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/6/9 15:30 
 * @author tiecheng
 */
public class SerializeUtils{

    private static final Log log = LogFactory.getLog(SerializeUtils.class);

    /**
     * 序列化对象
     *
     * @param object 对象
     * @return 序列化的字节码数组
     */
    public static byte[] serialize(Object object) throws IOException {
        byte[] result = null;
        if (object != null) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(object);
                result = baos.toByteArray();
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                throw new IOException();
            }
        }
        return result;
    }

    /**
     * 序列化对象
     *
     * @param list 对象
     * @return 序列化的字节码数组
     */
    public static byte[] serializeList(List<?> list) throws IOException {
        byte[] result = null;
        if (list != null && list.size() != 0) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                for (Object o : list) {
                    oos.writeObject(o);
                }
                result = baos.toByteArray();
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                throw new IOException();
            }
        }
        return result;
    }

    /**
     * 反序列化对象
     *
     * @param bytes 字节码数组
     * @return 对象
     */
    public static Object unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        Object result = null;
        if (bytes != null && bytes.length > 0) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bais)) {
                result = ois.readObject();
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                throw new IOException();
            } catch (ClassNotFoundException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                throw new ClassNotFoundException();
            }
        }
        return result;
    }

    /**
     * 反序列化对象
     *
     * @param bytes 字节码数组
     * @return 对象
     */
    public static List<?> unserializeList(byte[] bytes) throws IOException, ClassNotFoundException {
        List<Object> result = null;
        if (bytes != null && bytes.length > 0) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bais)) {
                while (bais.available() > 0) {
                    Object object = ois.readObject();
                    if (object == null) {
                        break;
                    } else {
                        result.add(object);
                    }
                }
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                throw new IOException();
            } catch (ClassNotFoundException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
                throw new ClassNotFoundException();
            }
        }
        return result;
    }

}
