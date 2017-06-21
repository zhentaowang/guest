package com.zhiweicloud.guest.signature;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by tc on 2017/6/21.
 */
public class StringUtils {

    public static boolean isNoneBlank(final CharSequence... css){
        return !isAnyBlank(css);
    }

    public static boolean isAnyBlank(final CharSequence... css){
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css){
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

}
