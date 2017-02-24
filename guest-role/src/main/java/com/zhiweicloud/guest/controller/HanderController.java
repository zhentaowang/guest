package com.zhiweicloud.guest.controller;

/**
 * Created by zhangpengfei on 2017/2/23.
 */
public class HanderController {

    /**
     * 根据userId，机场编码，当前url 判断是否能操作（显示列表，添加，删除，修改......）
     * @param userId
     * @param airportCode
     * @param url
     * @return
     */
    private static boolean allowNext(Long userId,String airportCode,String url){
        return true;
    }
}
