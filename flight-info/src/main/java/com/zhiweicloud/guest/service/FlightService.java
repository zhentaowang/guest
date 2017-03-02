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

import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.mapper.SysMenuMapper;
import com.zhiweicloud.guest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpengfei
 * @since 2017-02-04 11:09
 */
@Service
public class SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 查询全部菜单，不区分角色和用户
     * @return
     */
    public List<SysMenu> queryAllMenus(String airportCode) {
        Map<String,Object> map = new HashMap();
        map.put("airportCode",airportCode);
        List<SysMenu> menuList = this.queryNodeListAllMenus(0L,airportCode);
        return menuList;
    }

    /**
     * 查询所有的子节点，不区分角色和用户
     */
    private List<SysMenu> queryNodeListAllMenus(Long pid,String airportCode) {
        List<SysMenu> list = this.querySubNodeAllMenus(pid,airportCode);//查询parentId=0的，那么就是从根节点开始查询了,即：查询所有节点
        for(SysMenu deal:list){
            recursionAllMenus(deal,airportCode);
        }
        return list;
    }

    /**
     * 递归树，不区分角色和用户
     * @param tree
     */
    private void recursionAllMenus(SysMenu tree,String airportCode){
        try {
            List<SysMenu> subList = this.querySubNodeAllMenus(tree.getMenuId(),airportCode);
            if(subList != null && subList.size() > 0){
                tree.setChildren(subList);
                for(SysMenu sub:subList){
                    recursionAllMenus(sub,airportCode);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据父id查询子对象，不区分角色和用户
     */
    private List<SysMenu> querySubNodeAllMenus(Long parentId,String airportCode) {
        return sysMenuMapper.getChildNodesByParentId(parentId,airportCode);
    }

    ////据登录人查询他所有的菜单/////////////////////////////////////////////////////////////////////////////

    /**
     * 根据登录人查询他所有的菜单
     * @return
     */
    public List<SysMenu> getMenuByUserId(Long userId,String airportCode) {
        List<SysMenu> menuList = this.queryNodeListMenusByUserId(0L,userId,airportCode);
        return menuList;
    }

    /**
     * 根据登录人查询他所有的菜单 的子节点
     */
    private List<SysMenu> queryNodeListMenusByUserId(Long pid,Long userId,String airportCode) {
        List<SysMenu> list = this.querySubNodeMenusListByUserId(pid,userId,airportCode);//查询parentId=0的，那么就是从根节点开始查询了,即：查询所有节点
        for(SysMenu deal:list){
            recursionMenus(deal,userId,airportCode);
        }
        return list;
    }

    /**
     * 根据登录人查询他所有的菜单 递归树
     * @param tree
     */
    private void recursionMenus(SysMenu tree,Long userId,String airportCode){
        try {
            List<SysMenu> subList = this.querySubNodeMenusListByUserId(tree.getMenuId(),userId,airportCode);
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
     * 根据登录人查询他所有的菜单 根据父id查询子对象
     */
    private List<SysMenu> querySubNodeMenusListByUserId(Long parentId,Long userId,String airportCode) {
        return sysMenuMapper.getChildMenusByParentIdByUserId(parentId,airportCode,userId);
    }



    ////据角色id查询当前角色具有的所有菜单/////////////////////////////////////////////////////////////////////////////
    public List<SysMenu> queryMenuListByRoleId(String airportCode, Long roleId) {
        List<SysMenu> menuList = this.queryNodeListMenusByRoleId(0L,roleId,airportCode);
        return menuList;
    }

    /**
     * 查询所有的子节点
     */
    private List<SysMenu> queryNodeListMenusByRoleId(Long pid,Long roleId,String airportCode) {
        List<SysMenu> list = this.querySubNodeMenusListByRoleId(pid,roleId,airportCode);//查询parentId=0的，那么就是从根节点开始查询了,即：查询所有节点
        for(SysMenu deal:list){
            recursionMenusRole(deal,roleId,airportCode);
        }
        return list;
    }

    /**
     * 递归树
     * @param tree
     */
    private void recursionMenusRole(SysMenu tree,Long roleId,String airportCode){
        try {
            List<SysMenu> subList = this.querySubNodeMenusListByRoleId(tree.getMenuId(),roleId,airportCode);
            if(subList != null && subList.size() > 0){
                tree.setChildren(subList);
                for(SysMenu sub:subList){
                    recursionMenusRole(sub,roleId,airportCode);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据父id查询子对象
     */
    private List<SysMenu> querySubNodeMenusListByRoleId(Long parentId,Long roleId,String airportCode) {
        return sysMenuMapper.getChildMenusByParentIdByRole(parentId,airportCode,roleId);
    }


    ///////////////////////////////////////
    public void saveOrUpdate(SysMenu sysMenu) {
        if (sysMenu.getMenuId() != null) {
            sysMenu.setUpdateTime(new Date());
            sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        } else {
            sysMenu.setCreateTime(new Date());
            sysMenuMapper.insertSelective(sysMenu);
        }
    }


    public String deleteById(List<Long> ids, String airportCode) {
        StringBuffer inUseMenuName = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            int count = sysMenuMapper.menuInUse(ids.get(i),airportCode);
            if(count > 0 ){
                inUseMenuName.append(sysMenuMapper.selectMenuNameByIdAndAirportCode(ids.get(i),airportCode));
                inUseMenuName.append(",");
            }else{
                SysMenu temp = new SysMenu();
                temp.setMenuId(ids.get(i));
                temp.setIsDeleted(Constant.MARK_AS_DELETED);
                temp.setAirportCode(airportCode);
                sysMenuMapper.updateByPrimaryKeySelective(temp);
            }

        }
        return inUseMenuName.toString();
    }

    public SysMenu getMenuInstanceByUserId(Long menuId, String airportCode) {
        return sysMenuMapper.selectMenuInstanceByIdAndAirportCode(menuId,airportCode);
    }
}
