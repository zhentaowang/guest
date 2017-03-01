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

package com.zhiweicloud.guest.service;

import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.SysMenuMapper;
import com.zhiweicloud.guest.mapper.SysRoleMapper;
import com.zhiweicloud.guest.mapper.SysRoleMenuMapper;
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2017-02-04 11:09
 */
@Service
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;



    public LZResult<PaginationResult<SysRole>> getAll(SysRole sysRoleParam, Integer page, Integer rows) {
        

        BasePagination<SysRole> queryCondition = new BasePagination<>(sysRoleParam, new PageModel(page, rows));

        int total = sysRoleMapper.selectSysRoleTotal(sysRoleParam);
        List<SysRole> flightList = sysRoleMapper.selectSysRoleList(queryCondition);
        PaginationResult<SysRole> eqr = new PaginationResult<>(total, flightList);
        LZResult<PaginationResult<SysRole>> result = new LZResult<>(eqr);
        return result;
    }

    public SysRole getById(Long roleId,String airportCode) {
        return sysRoleMapper.selectByIdAndAirportCode(roleId,airportCode);
    }

    public void saveOrUpdate(SysRole sysRole) {
        if (sysRole.getRoleId() != null) {
            sysRoleMapper.updateCustomColumn(sysRole);//更新角色本身的字段，name和description
            /**
             * 更新角色和菜单的关联关系：
             * 1：insertByExists。有就更新，没有就插入
             * 2：删除，用not in 来删除
             *   update ${tableName} set is_deleted = 1 where id not in (${ids})  and airport_code = #{airportCode} and order_id = #{orderId}
             */
            if(sysRole.getMenuIdList() != null && sysRole.getMenuIdList().size() > 0){
                for(int i = 0; i < sysRole.getMenuIdList().size();i++){
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(sysRole.getRoleId());
                    sysRoleMenu.setMenuId(sysRole.getMenuIdList().get(i));
                    sysRoleMenu.setAirportCode(sysRole.getAirportCode());
                    sysRoleMenuMapper.insertByExists(sysRoleMenu);
                }
                //删除
                Map<String,Object> map = new HashMap<>();
                map.put("roleId",sysRole.getRoleId());
                map.put("menuIds",ListUtil.List2String(sysRole.getMenuIdList()));
                map.put("airportCode",sysRole.getAirportCode());
                sysRoleMenuMapper.deleteMenus(map);
            }


        } else {
            sysRoleMapper.insertSelective(sysRole);
            //同时去做role和菜单的关联关系 :即：去sys_role_menu表中新增一条记录
            if(sysRole.getMenuIdList() != null && sysRole.getMenuIdList().size() > 0){
                for(int i = 0; i < sysRole.getMenuIdList().size();i++){
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(sysRole.getRoleId());
                    sysRoleMenu.setMenuId(sysRole.getMenuIdList().get(i));
                    sysRoleMenu.setAirportCode(sysRole.getAirportCode());
                    sysRoleMenu.setCreateUser(sysRole.getCreateUser());
                    sysRoleMenuMapper.insertSelective(sysRoleMenu);
                }
            }
        }
    }

    public String deleteById(List<Long> ids,Long deleteUser,String airportCode) {
        StringBuffer inUseRoleName = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            int count = sysRoleMapper.roleInUse(ids.get(i),airportCode);
            if(count > 0 ){
                inUseRoleName.append(sysRoleMapper.selectRoleNameByIdAndAirportCode(ids.get(i),airportCode));
                inUseRoleName.append(",");
            }else{
                SysRole temp = new SysRole();
                temp.setRoleId(ids.get(i));
                temp.setIsDeleted(Constant.MARK_AS_DELETED);
                temp.setAirportCode(airportCode);
                temp.setUpdateUser(deleteUser);
                temp.setUpdateTime(new Date());
                sysRoleMapper.updateByPrimaryKeySelective(temp);
            }
        }
        return inUseRoleName.toString();
    }


    public List<Dropdownlist> querySysRoleDropdownList(String airportCode) {
        return sysRoleMapper.getSysRoleDropdownList(airportCode);
    }

}
