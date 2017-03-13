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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.mapper.InstitutionClientMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.InstitutionClient;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InstitutionClientMapper.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2016-12-26 15:45:36 Created By zhangpengfei
 */
@Service
public class InstitutionClientService {

    @Autowired
    private InstitutionClientMapper institutionClientMapper;

    public LZResult<PaginationResult<InstitutionClient>> getAll(InstitutionClient param, Integer page, Integer rows) {
        BasePagination<InstitutionClient> queryCondition = new BasePagination<>(param, new PageModel(page, rows));

        Integer count = institutionClientMapper.getListByConiditionCount(param);

        List<InstitutionClient> institutionClientList = institutionClientMapper.getListByConidition(queryCondition);

        PaginationResult<InstitutionClient> eqr = new PaginationResult<>(count, institutionClientList);
        LZResult<PaginationResult<InstitutionClient>> result = new LZResult<>(eqr);
        return result;
    }

    public InstitutionClient getById(Long institutionClientId,String airportCode) {
        return institutionClientMapper.viewByIdAndAirCode(institutionClientId,airportCode);
    }

    public void saveOrUpdate(InstitutionClient institutionClient) {
        if (institutionClient.getInstitutionClientId() != null) {
            institutionClientMapper.updateByPrimaryKeySelective(institutionClient);
        } else {
            institutionClientMapper.insertSelective(institutionClient);
        }
    }

    public List<Map<Long,String>> deleteByIds(List<Long> ids, Long userId, String airportCode) throws Exception{
        List<Long> deleteIds = new ArrayList<>();
        List<Map<Long,String>> disableDeleteIds = new ArrayList<>();
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("user-id", userId);
        headerMap.put("client-id", airportCode);
        for (Long id : ids) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("institutionClientId", id);
//            String s = HttpClientUtil.httpGetRequest("http://127.0.0.1:8084/protocolList", headerMap, paramMap);
            String s = HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/protocolList", headerMap, paramMap);
            System.out.println(s);
            JSONObject protocolList = JSON.parseObject(s);
            if (protocolList != null) {
                JSONArray rows = protocolList.getJSONObject("data").getJSONArray("rows");
                if (rows.size()!=0) {
                    Map<Long, String> maps = new HashMap<>();
                    maps.put(id, institutionClientMapper.viewByIdAndAirCode(id, airportCode).getName());
                    disableDeleteIds.add(maps);
                }else {
                    deleteIds.add(id);
                }
            }
        }
        if (deleteIds.size()>0){
            Map params = new HashMap();
            params.put("ids",deleteIds);
            params.put("userId", userId);
            params.put("airportCode", airportCode);
            institutionClientMapper.deleteBatchByIdsAndUserId(params);
        }
        return disableDeleteIds;
        //一条一条删除数据
//        for (Long id : ids) {
//            JSONObject protocolList = JSON.parseObject(HttpClientUtil.httpGetRequest("http://ifeicloud.zhiweicloud.com/guest-protocol/protocolList?institutionClientId="+ id +"&access_token=grvRY7bhYS8BzC0dO1k3NfZ4d0o32peJtyCr4emx"));
//            if(protocolList!=null || protocolList.size()!=0){
//                throw new InstitutionException("机构已经被协议占用，无法删除");
//            }
//            institutionClientMapper.markAsDeleted(id,userId,airportCode);
//        }
    }

    public List<Dropdownlist> queryInstitutionClientDropdownList(String airportCode,String name,String no) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("airportCode",airportCode);
        map.put("name",name);
        map.put("no",no);

        return institutionClientMapper.getInstitutionClientDropdownList(map);
    }

}
