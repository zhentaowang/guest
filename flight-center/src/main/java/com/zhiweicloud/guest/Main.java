package com.zhiweicloud.guest;

import com.zhiweicloud.guest.common.util.ApplicationContextUtils;

public class Main {

    public static void main( String[] args ) throws InterruptedException {
        ApplicationContextUtils.createContext("production");
    }

}
