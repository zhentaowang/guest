package com.zhiweicloud.guest.common.utils;

/**
 * Created by tc on 2017/5/17.
 */
public class StringUtils {

    public static boolean isNone(String string) {
        if (string == null || "".equals(string) || " ".equals(string)|| "null".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNone(String string) {
        return isNone(string) == true ? false : true;
    }

}
