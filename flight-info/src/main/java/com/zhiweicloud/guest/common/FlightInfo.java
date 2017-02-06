package com.zhiweicloud.guest.common;


import com.dragon.sign.DragonSignature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhangpengfei on 2017/1/22.
 */
@RequestMapping(value = "/flight-info", method = RequestMethod.GET)
@RestController
@Api(value = "航班信息", description = "航班信息desc ", tags = {"flight-info"})
public class FlightInfo {

    /**
     * 订单管理 - 根据id查询
     *
     * @param date
     * @param fnum
     * @return
     */
    @RequestMapping(value = "/flightInfo", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "航班信息 - 航班号+航班日期,航班号+航班日期+航段,航班日期+航段 查询 ", notes = "返回订单详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fnum", value = "航班号", dataType = "String", required = true, paramType = "query",defaultValue = "CA1352"),
            @ApiImplicitParam(name = "date", value = "航班日期", dataType = "String", required = true, paramType = "query",defaultValue = "2017-02-03")
    })
    public String view(@RequestParam(value = "fnum", required = true) String fnum,
                       @RequestParam(value = "date", required = true) String date) {
        try {
            String privateKey = Global.getPrivateKey();
            Map<String, String> params = new HashMap<>();
            params.put("date",date);
            params.put("fnum",fnum);

            params.put("lg", "zh-cn");
            params.put("sysCode", "dpctest");


            String sign = DragonSignature.rsaSign(params, privateKey, "UTF-8");
            //System.out.print("sign:" + sign);


            Map<String, Object> p = new HashMap<>();
            p.put("date",date);
            p.put("fnum",fnum);
            p.put("lg", "zh-cn");
            p.put("sysCode", "dpctest");
            p.put("sign", sign);

            String ret = HttpClientUtil.httpPostRequest("http://183.63.121.12:8012/FlightCenter/wcf/FlightWcfService.svc/GetFlightInfo_Lg", p);
            System.out.println(ret);
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return "error...";
        }


    }

    /*public static void main(String args[]) throws Exception{
        String privateKey = Global.getPrivateKey();
        Map<String, String> params = new HashMap<>();
        params.put("date", "2017-01-27");
        params.put("fnum", "CA1352");
        params.put("lg", "zh-cn");
        params.put("sysCode", "dpctest");


        String sign = DragonSignature.rsaSign(params, privateKey, "UTF-8");
        System.out.print("sign:" + sign);


        Map<String, Object> p = new HashMap<>();
        p.put("date", "2017-01-27");
        p.put("fnum", "CA1352");
        p.put("lg", "zh-cn");
        p.put("sysCode", "dpctest");
        p.put("sign", sign);

        String ret = HttpClientUtil.httpPostRequest("http://183.63.121.12:8012/FlightCenter/wcf/FlightWcfService.svc/GetFlightInfo_Lg", p);
        System.out.println(ret);

    }*/
}
