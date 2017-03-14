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
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.HttpClientUtil;
import com.zhiweicloud.guest.mapper.CheckMapper;
import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.Dropdownlist;
import com.zhiweicloud.guest.model.Employee;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class CheckService {

    @Autowired
    private CheckMapper checkMapper;

    public LZResult<PaginationResult<Map>> getAll(Long userId,String airportCode,CheckQueryParam checkQueryParam, Integer page, Integer rows) throws Exception{
        BasePagination<CheckQueryParam> queryCondition = new BasePagination<>(checkQueryParam, new PageModel(page, rows));

        //productType 1,2,3 这种格式
        if (checkQueryParam.getQueryProtocolType() != null && !checkQueryParam.getQueryProtocolType().equals("")) {
            if(checkQueryParam.getQueryProtocolType().length() > 0 && checkQueryParam.getQueryProtocolType().contains(",")){
                String protocolTypeArr[] = checkQueryParam.getQueryProtocolType().split(",");
                for(int i = 0; i < protocolTypeArr.length;i++){
                    List protocolIdList = this.getProtocolList(protocolTypeArr[i],userId,airportCode);
                    if (protocolIdList.size() > 0) {
                        checkQueryParam.setQueryProtocolId(ListUtil.List2String(protocolIdList));//协议id
                    }
                }
            }else{
                List protocolIdList = this.getProtocolList(checkQueryParam.getQueryProtocolType(),userId,airportCode);
                if (protocolIdList.size() > 0) {
                    checkQueryParam.setQueryProtocolId(ListUtil.List2String(protocolIdList));//协议id
                }
            }
        }

        int total = checkMapper.selectCheckTotal(checkQueryParam);
        List<Map> checkList = checkMapper.selectCheckList(queryCondition);
        PaginationResult<Map> eqr = new PaginationResult<>(total, checkList);
        LZResult<PaginationResult<Map>> result = new LZResult<>(eqr);
        return result;
    }

    private List getProtocolList(String protocolType,Long userId,String airportCode) throws Exception{
        Map<String, Object> headerMap = new HashMap();
        headerMap.put("user-id", userId);
        headerMap.put("client-id", airportCode);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("protocolType", protocolType);
        List protocolIdList = new ArrayList();
        JSONObject protocolParam = JSON.parseObject(HttpClientUtil.httpGetRequest("http://guest-protocol/guest-protocol/getProtocolNameDropdownList",headerMap,paramMap));
        if (protocolParam != null) {
            JSONArray protocolArray = protocolParam.getJSONArray("data");
            for (int i = 0; i < protocolArray.size(); i++) {
                JSONObject jsonObject = JSON.parseObject(protocolArray.get(i).toString());
                protocolIdList.add(jsonObject.get("id"));
            }
        }
        return protocolIdList;
    }










}
