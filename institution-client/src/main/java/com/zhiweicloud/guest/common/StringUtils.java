package com.zhiweicloud.guest.common;

/**
 * Created by tc on 2017/3/13.
 */
public class StringUtils {

    public static String deleteEndChar(StringBuilder sb){
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("1,2,3,4,5,6,");
        deleteEndChar(sb);
    }


}
