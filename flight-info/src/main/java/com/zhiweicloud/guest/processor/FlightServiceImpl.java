//package com.zhiweicloud.guest.processor;
//
//import com.alibaba.fastjson.JSONObject;
//import com.wyun.thrift.server.*;
//import com.zhiweicloud.guest.thrift.service.IFlightSerivce;
//import org.apache.thrift.TException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.nio.ByteBuffer;
//
///**
// * Created by tc on 2017/5/5.
// */
//public class FlightServiceImpl implements MyService.Iface {
//
//    private final IFlightSerivce iFlightSerivce;
//
//    @Autowired
//    public FlightServiceImpl(IFlightSerivce iFlightSerivce) {
//        this.iFlightSerivce = iFlightSerivce;
//    }
//
//    @Override
//    public Response send(Request request) throws ServiceException, TException {
//        JSONObject paramJSON = null;
//        try {
//            byte[] paramJSON_bytes = request.getParamJSON();
//            if (paramJSON_bytes != null && paramJSON_bytes.length > 0) {
//                String paramJSON_string = new String(paramJSON_bytes);
//                paramJSON = JSONObject.parseObject(paramJSON_string);
//            }
//        } catch (Exception ignored) {
//        }
//
//        String result;
//        result = iFlightSerivce.handle(paramJSON);
//        byte[] resultBytes = result.getBytes();
//        ByteBuffer returnByteBuffer = ByteBuffer.allocate(resultBytes.length);
//        returnByteBuffer.put(resultBytes);
//        returnByteBuffer.flip();
//        return new Response(RESCODE._200, returnByteBuffer);
//    }
//
//}
//
