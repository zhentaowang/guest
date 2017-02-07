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
import com.zhiweicloud.guest.model.*;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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
    private SysMenuMapper sysMenuMapper;


    public LZResult<PaginationResult<SysRole>> getAll(SysRole sysRoleParam, Integer page, Integer rows) {
        

        BasePagination<SysRole> queryCondition = new BasePagination<>(sysRoleParam, new PageModel(page, rows));

        int total = sysRoleMapper.selectSysRoleTotal(sysRoleParam);
        List<SysRole> flightList = sysRoleMapper.selectSysRoleList(queryCondition);
        PaginationResult<SysRole> eqr = new PaginationResult<>(total, flightList);
        LZResult<PaginationResult<SysRole>> result = new LZResult<>(eqr);
        return result;
    }

    public SysRole getById(Long id,String airportCode) {
        Map map = new HashMap();
        map.put("id",id);
        map.put("airportCode",airportCode);
        return sysRoleMapper.selectByIdAndAirportCode(map);
    }

    public void saveOrUpdate(SysRole sysRole) {
        if (sysRole.getId() != null) {
            Example example = new Example(SysRole.class);
            String sql = "id = " + sysRole.getId() + " and airport_code = '" + sysRole.getAirportCode() + "'";
            example.createCriteria().andCondition(sql);
            sysRoleMapper.updateByExampleSelective(sysRole, example);
        } else {
            sysRoleMapper.insert(sysRole);
        }
    }

    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            SysRole temp = new SysRole();
            temp.setId(ids.get(i));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            temp.setAirportCode(airportCode);
            sysRoleMapper.updateByPrimaryKeySelective(temp);
        }
    }


    public List<Dropdownlist> querySysRoleDropdownList(String airportCode) {
        return sysRoleMapper.getSysRoleDropdownList(airportCode);
    }

    public void assignMenuToRole(SysRoleParam sysRoleParam) {
        List<SysMenuParam> menuIds = sysRoleParam.getSysMenuList();
        Map<String,Object> map = new HashMap();
        map.put("roleId",sysRoleParam.getId());
        map.put("airportCode",sysRoleParam.getAirportCode());

        for(int i = 0; i < menuIds.size();i++){
            map.put("menuId",menuIds.get(i).getId());
            sysRoleMapper.assignMenuToRole(map);
        }
    }

    /**
     * 查询所有的菜单
     * @return
     */
    public List<SysMenu> queryAllMenus() {
       //return this.queryAllNodeListMenus(0L);
        return null;
    }

    /**
     * 查询所有的菜单
     * @return
     */
    public List<SysMenu> getMenuByUserId(Long userId,String airportCode) {
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        map.put("airportCode",airportCode);
        //List<Long> userIdList =  sysRoleMapper.getMenuIdByUserId(map);
        List<SysMenu> menuList = this.queryAllNodeListMenus(0L,userId,airportCode);
        return menuList;
    }

    /**
     * 查询所有的子节点
     */
    private List<SysMenu> queryAllNodeListMenus(Long pid,Long userId,String airportCode) {
        List<SysMenu> list = this.querySubNodeMenusList(pid,userId,airportCode);//查询parentId=0的，那么就是从根节点开始查询了,即：查询所有节点
        for(SysMenu deal:list){
            recursionMenus(deal,userId,airportCode);
        }
        return list;
    }

    /**
     * 递归树
     * @param tree
     */
    private void recursionMenus(SysMenu tree,Long userId,String airportCode){
        try {
            List<SysMenu> subList = this.querySubNodeMenusList(tree.getId(),userId,airportCode);
            if(subList != null && subList.size() > 0){
                tree.setChildren(subList);
                for(SysMenu sub:subList){
                    recursionMenus(sub,userId,airportCode);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据父id查询子对象
     */
    private List<SysMenu> querySubNodeMenusList(Long parentId,Long userId,String airportCode) {
        Map<String,Object> map = new HashMap();
        map.put("parentId",parentId);
        map.put("airportCode",airportCode);
        map.put("userId",userId);
        return sysMenuMapper.getChildMenusByParentId(map);
    }
}
