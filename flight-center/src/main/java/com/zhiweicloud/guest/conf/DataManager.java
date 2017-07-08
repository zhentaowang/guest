package com.zhiweicloud.guest.conf;

import com.wyun.zookeeper.conf.DataChangeHandler;

import java.util.Map;

/**
 * Created by tc on 2017/7/7.
 */
public class DataManager extends DataChangeHandler {

    public DataManager() {
    }

    @Override
    public void onConfigAdd(String configName, Map<String, String> newProperties) {
        super.onConfigAdd(configName, newProperties);
        System.out.println(configName);
        for (Map.Entry<String, String> entry : newProperties.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Override
    public void onConfigDelete(String configName, Map<String, String> oldProperties) {
        super.onConfigDelete(configName, oldProperties);
        System.out.println(configName);
        for (Map.Entry<String, String> entry : oldProperties.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Override
    public void onConfigChange(String configName, Map<String, String> oldProperties, Map<String, String> newProperties) {
        super.onConfigChange(configName, oldProperties, newProperties);
        System.out.println(configName);
        for (Map.Entry<String, String> entry : newProperties.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        System.out.println("hahahahahahahaha");
        for (Map.Entry<String, String> entry : oldProperties.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

}
