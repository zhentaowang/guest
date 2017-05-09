package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.service.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by THINK on 2017-05-09.
 */
@Component
public class ServiceSender implements IBusinessService {
    @Autowired
    private OrderInfoController orderInfoController;

    @Autowired
    private OrderServiceRecordController orderServiceRecordController;

    @Autowired
    private PassengerController passengerController;


    @Autowired
    public ServiceSender(OrderInfoController orderInfoController,OrderServiceRecordController orderServiceRecordController,PassengerController passengerController) {
        this.orderInfoController = orderInfoController;
        this.orderServiceRecordController = orderServiceRecordController;
        this.passengerController = passengerController;
    }

    @Override
    public String handle(JSONObject request) {
        String success = null;
        String operation = null; //operation表示从参数中获取的操作类型"operation"
        if (request.get("operation") != null) {
            operation = request.getString("operation");
        }

        switch (operation) {
            case "list":
                success = orderInfoController.list(request);
                break;
            case "saveOrUpdate":
                success = orderInfoController.saveOrUpdate(request);
                break;
            case "getAirportCode":
                success = orderInfoController.getAirportCode(request);
                break;
            case "delete":
                success = orderInfoController.delete(request);
                break;
            case "view":
                success = orderInfoController.view(request);
                break;
            case "updateServerComplete":
                success = orderInfoController.updateServerComplete(request);
                break;
            case "getServerNumByServlId":
                success = orderInfoController.getServerNumByServlId(request);
                break;
            case "getOrderCountByProtocolId":
                success = orderInfoController.getOrderCountByProtocolId(request);
                break;
            case "queryProtocolIdsInOrderInfoByCustomId":
                success = orderInfoController.queryProtocolIdsInOrderInfoByCustomId(request);
                break;
            case "getCardType":
                success = orderInfoController.getCardType(request);
                break;
            case "addOrderServiceRecord":
                success = orderServiceRecordController.addOrderServiceRecord(request);
                break;
            case "getOrderServiceRecord":
                success = orderServiceRecordController.getOrderServiceRecord(request);
                break;
            case "getIdentityCardDropdownList":
                success = passengerController.getIdentityCardDropdownList(request);
                break;
            case "getPassengerByFlightId":
                success = passengerController.getPassengerByFlightId(request);
                break;
            default:
                break;
        }

        return success;
    }

}
