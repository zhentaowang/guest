package com.zhiweicloud.guest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyun.thrift.server.business.IBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by THINK on 2017-05-09.
 */
@Component
public class BusinessService implements IBusinessService {
    @Autowired
    private MenuController menuController;

    @Autowired
    private RoleController roleController;

    private InitNewAirport initNewAirport;

    @Autowired
    public BusinessService(MenuController menuController, RoleController roleController,InitNewAirport initNewAirport) {
        this.menuController = menuController;
        this.roleController = roleController;
        this.initNewAirport = initNewAirport;
    }

    @Override
    public JSONObject handle(String operation,JSONObject request) {
        String success = null;
//        String operation = null; //operation表示从参数中获取的操作类型"operation"
//        if (request.get("operation") != null) {
//            operation = request.getString("operation");
//        }

        switch (operation) {
            case "addMenu":
                success = menuController.addMenu(request);
                break;
            case "deleteMenus":
                success = menuController.deleteMenus(request);
                break;
            case "getMenuByRoleId":
                success = menuController.getMenuByRoleId(request);
                break;
            case "getMenuByUserId":
                success = menuController.getMenuByUserId(request);
                break;
            case "menuTree":
                success = menuController.menuTree(request);
                break;
            case "viewMenu":
                success = menuController.viewMenu(request);
                break;

            case "deleteRoles":
                success = roleController.deleteRoles(request);
                break;
            case "list":
                success = roleController.list(request);
                break;
            case "save":
                success = roleController.save(request);
                break;
            case "view":
                success = roleController.view(request);
                break;
            case "addNewAirport":
                success = initNewAirport.addNewAirport(request);
                break;
            default:
                break;
        }

        return JSON.parseObject(success);
    }

}
