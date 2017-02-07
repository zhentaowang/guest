/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.zhiweicloud.guest.controller;

import com.zhiweicloud.guest.APIUtil.LXResult;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.LZStatus;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.RequsetParams;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.SysMenu;
import com.zhiweicloud.guest.model.SysRole;
import com.zhiweicloud.guest.model.SysRoleParam;
import com.zhiweicloud.guest.service.SysRoleService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * SysMenuController.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-20 19:34:25 Created By zhangpengfei
 */
@RequestMapping(value ="/guest-role")
@RestController
@Api(value="角色管理",description="", tags={"SysRole"})
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value ="/list")
    @ApiOperation(value = "角色列表 - 分页查询", notes = "返回分页结果", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "page", value = "起始页", dataType = "Integer", defaultValue = "1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "rows", value = "每页显示数目", dataType = "Integer", defaultValue = "10", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "String", required = false, paramType = "query")})
    public LZResult<PaginationResult<SysRole>> list(
            @RequestParam(value = "page", required = true, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = true, defaultValue = "10") Integer rows,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value="airportCode",required = false) String airportCode) {
        SysRole SysRoleParam = new SysRole();
        SysRoleParam.setName(name);
        SysRoleParam.setAirportCode(airportCode);

        LZResult<PaginationResult<SysRole>> result  = sysRoleService.getAll(SysRoleParam,page,rows);
        return result;
    }

    /**
     * 角色管理 - 新增or更新
     * 需要判断name是否重复
     * @param params
     * @return
     */
    @RequestMapping(value="/saveOrUpdate", method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="角色管理 - 新增/修改", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult save(@ApiParam(value = "sysRole", required = true) @RequestBody RequsetParams<SysRole> params){
        try{
            SysRole sysRole = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                sysRole = params.getData().get(0);
            }

            if (sysRole == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            sysRoleService.saveOrUpdate(sysRole);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }


    /**
     * 角色管理 - 根据id查询员工
     * @param id
     * @return
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "角色管理- 根据id查询员工 ", notes = "返回合同详情", httpMethod = "GET", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "员工id", dataType = "Long", required = true, paramType = "query"),
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    })
    public LZResult<SysRole> view(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "airportCode", required = true) String airportCode) {
        SysRole sysRole = sysRoleService.getById(id,airportCode);
        return new LZResult<SysRole>(sysRole);
    }

    /**
     * 角色管理 - 删除
     * {
        "data": [
        6,7,8
        ]
    }
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "角色管理 - 删除", notes = "返回响应结果", httpMethod = "POST", produces = "application/json")
    @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query")
    public LXResult delete(
            @RequestBody RequsetParams<Long> params,
            @RequestParam(value = "airportCode", required = true) String airportCode) {
        try {
            List<Long> ids = params.getData();
            sysRoleService.deleteById(ids,airportCode);
            return LXResult.success();
        } catch (Exception e) {
            logger.error("delete SysRole by ids error", e);
            return LXResult.error();
        }
    }

    /**
     * 角色管理 - 给角色分配菜单
     * @param params
     * @return
     */
    @RequestMapping(value="/assignMenuToRole", method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="角色管理 - 给角色分配菜单", notes ="返回成功还是失败",httpMethod ="POST", produces="application/json")
    public LXResult assignMenuToRole(@ApiParam(value = "sysRole", required = true) @RequestBody RequsetParams<SysRoleParam> params){
        try{
            SysRoleParam sysRoleParam = null;
            if(!CollectionUtils.isEmpty(params.getData())){
                sysRoleParam = params.getData().get(0);
            }

            if (sysRoleParam == null) {
                return LXResult.build(LZStatus.DATA_EMPTY.value(), LZStatus.DATA_EMPTY.display());
            }
            sysRoleService.assignMenuToRole(sysRoleParam);
            return LXResult.build(LZStatus.SUCCESS.value(), LZStatus.SUCCESS.display());
        } catch (Exception e) {
            e.printStackTrace();
            return LXResult.build(LZStatus.ERROR.value(), LZStatus.ERROR.display());
        }
    }

    /**
     * 角色管理 - 给角色分配菜单
     * @param
     * @return
     */
    @RequestMapping(value="/getMenuByUserId", method=RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="菜单管理 - 查询所有菜单", notes ="返回成功还是失败",httpMethod ="GET", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "airportCode", value = "机场编号", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "access_token", value = "Access_Token", dataType = "String", required = true, paramType = "query")
    })
    public LZResult<List<SysMenu>> getMenuByUserId( HttpServletRequest request,
            @RequestParam(value = "airportCode", required = true) String airportCode,
            @RequestParam(value = "access_token", required = true) String access_token){
        try{
            String userIdStr = (String)request.getSession().getAttribute("userId");
            List<SysMenu> result = sysRoleService.getMenuByUserId(Long.valueOf(userIdStr),airportCode);
            return new LZResult<>(result);
        } catch (Exception e) {
            e.printStackTrace();
            LZResult<List<SysMenu>> result = new LZResult<>();
            result.setMsg(LZStatus.ERROR.display());
            result.setStatus(LZStatus.ERROR.value());
            result.setData(null);
            return result;
        }
    }



}
