package com.zhiweicloud.protocolmanage.service;


import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.protocolmanage.mapper.ProtocolServMapper;
import com.zhiweicloud.protocolmanage.model.ProtocolServ;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wzt on 2017/1/5.
 */
@Service
public class ProtocolServService {
    @Autowired
    private ProtocolServMapper protocolServMapper;

    /**
     * 分页获取协议服务列表
     * @param param
     * @param page
     * @param rows
     */
    public LZResult<PaginationResult<ProtocolServ>> getAll(Map<String,Object> param, Integer page, Integer rows) {

        int count = protocolServMapper.getListCount(param);

        BasePagination<Map<String,Object>> queryCondition = new BasePagination<>(param, new PageModel(page, rows));
        List<ProtocolServ> protocolservList = protocolServMapper.getListByConidition(queryCondition);
        PaginationResult<ProtocolServ> eqr = new PaginationResult<>(count, protocolservList);
        LZResult<PaginationResult<ProtocolServ>> result = new LZResult<>(eqr);
        return result;
    }

    /**
     * 获取协议服务详情
     * @param param
     */
    public ProtocolServ getById(Map<String,Object> param) {
        return protocolServMapper.selectById(param);
    }

    /**
     * 协议服务添加与修改
     * @param protocolServ
     */
    public void saveOrUpdate(ProtocolServ protocolServ) {
        if (protocolServ.getId() != null) {
            protocolServMapper.updateByIdAndAirportCode(protocolServ);
        } else {
            if(protocolServ.getProtocolServList() != null && protocolServ.getProtocolServList().get(0).getAirportCode() != null){
                for(int i = 0; i < protocolServ.getProtocolServList().size(); i++){
                    protocolServ.getProtocolServList().get(i).setCreateTime(new Date());
                    protocolServ.getProtocolServList().get(i).setUpdateTime(new Date());
                    protocolServ.getProtocolServList().get(i).setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                    protocolServMapper.insert(protocolServ.getProtocolServList().get(i));
                }
            }
            else{
                protocolServ.setCreateTime(new Date());
                protocolServ.setUpdateTime(new Date());
                protocolServ.setIsDeleted(Constant.MARK_AS_BUSS_DATA);
                protocolServMapper.insert(protocolServ);
            }
        }
    }

    /**
     * 删除协议服务
     * @param airportCode
     * @param ids
     */
    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            ProtocolServ protocolServ = new ProtocolServ();
            protocolServ.setAirportCode(airportCode);
            protocolServ.setId(ids.get(i));
            protocolServ.setIsDeleted(Constant.MARK_AS_DELETED);
            protocolServMapper.updateByIdAndAirportCode(protocolServ);
        }
    }
}
