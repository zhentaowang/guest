package com.zhiweicloud.guest.processor;

import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.MyService;
import com.wyun.thrift.server.RESCODE;
import com.wyun.thrift.server.Request;
import com.wyun.thrift.server.Response;
import com.zhiweicloud.guest.service.IProductService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class ProductServiceImpl implements MyService.Iface {

    private final IProductService iProductServiceImpl;

    @Autowired
    public ProductServiceImpl(IProductService iProductServiceImpl) {
        this.iProductServiceImpl = iProductServiceImpl;
    }

    @Override
    public Response send(Request request) throws TException {
        JSONObject paramJSON = null;
        try {
            byte[] paramJSON_bytes = request.getParamJSON();
            if (paramJSON_bytes != null && paramJSON_bytes.length > 0) {
                String paramJSON_string = new String(paramJSON_bytes);
                paramJSON = JSONObject.parseObject(paramJSON_string);
            }
        } catch (Exception ignored) {
        }

        String result;
        result = iProductServiceImpl.handle(paramJSON);
        byte[] resultBytes = result.getBytes();
        ByteBuffer returnByteBuffer = ByteBuffer.allocate(resultBytes.length);
        returnByteBuffer.put(resultBytes);
        returnByteBuffer.flip();
        return new Response(RESCODE._200, returnByteBuffer);
    }
}
