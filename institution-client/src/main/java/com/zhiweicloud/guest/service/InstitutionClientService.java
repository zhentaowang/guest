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
import com.zhiweicloud.guest.mapper.InstitutionClientMapper;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.InstitutionClient;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
        LZResult<PaginationResult<InstitutionClient>> result = new LZResult<PaginationResult<InstitutionClient>>(eqr);
        return result;
    }

    public InstitutionClient getById(Long institutionClientId,String airportCode) {
        return institutionClientMapper.viewByIdAndAirCode(institutionClientId,airportCode);
    }

    public void saveOrUpdate(InstitutionClient institutionClient) {
        if (institutionClient.getInstitutionClientId() != null) {
            Example example = new Example(InstitutionClient.class);
            String sql = "institution_client_id = " + institutionClient.getInstitutionClientId() + " and airport_code = '" + institutionClient.getAirportCode() + "'";
            example.createCriteria().andCondition(sql);
            institutionClientMapper.updateByExampleSelective(institutionClient,example);
        } else {
            institutionClientMapper.insertSelective(institutionClient);
        }
    }



    public void deleteById(List<Long> ids,String airportCode) {
        for(int i = 0; i< ids.size();i++){
            institutionClientMapper.markAsDeleted(ids.get(i),airportCode);
        }
    }


    public List<Dropdownlist> queryInstitutionClientDropdownList(String airportCode,String name,String no) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("airportCode",airportCode);
        map.put("name",name);
        map.put("no",no);

        return institutionClientMapper.getInstitutionClientDropdownList(map);
    }

}
