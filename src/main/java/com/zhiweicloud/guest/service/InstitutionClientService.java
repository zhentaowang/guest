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

import com.github.pagehelper.PageHelper;
import com.zhiweicloud.guest.APIUtil.LZResult;
import com.zhiweicloud.guest.APIUtil.PaginationResult;
import com.zhiweicloud.guest.common.Constant;
import com.zhiweicloud.guest.common.GeneratorSerNo;
import com.zhiweicloud.guest.mapper.InstitutionClientMapper;
import com.zhiweicloud.guest.model.InstitutionClient;
import com.zhiweicloud.guest.pageUtil.BasePagination;
import com.zhiweicloud.guest.pageUtil.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author liuzh
 * @since 2015-12-19 11:09
 */
@Service
public class InstitutionClientService {

    @Autowired
    private InstitutionClientMapper institutionClientMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //流水号加1后返回，流水号长度为4
    private static final String STR_FORMAT = "0000";

    public LZResult<PaginationResult<InstitutionClient>> getAll(InstitutionClient param, Integer page, Integer rows) {
        /*if (page != null && rows != null) {
            PageHelper.startPage(page, rows, "id");
        }*/

        // 条件查询，自己拼条件
        Example example = new Example(InstitutionClient.class);
        if(param.getNo() != null && !param.getNo().equals("")){
            example.createCriteria()
                    .andCondition("no like '%" + param.getNo() + "%'");
        }

        if(param.getName() != null && !param.getName().equals("")){
            example.createCriteria()
                    .andCondition("name like '%" + param.getName() + "%'");
        }

        if(param.getType() != null && !param.getType().equals("")){
            example.createCriteria()
                    .andCondition("type = '" + param.getType() + "'");
        }
        example.createCriteria()
                .andCondition("is_deleted = 0");

        //List<InstitutionClientModel> institutionClientList = institutionClientMapper.selectByExample(example);

        BasePagination<InstitutionClient> queryCondition = new BasePagination<InstitutionClient>(param, new PageModel(page, rows));

        Integer count = institutionClientMapper.selectCountByExample(example);

        List<InstitutionClient> institutionClientList = institutionClientMapper.getListByConidition(queryCondition);

        PaginationResult<InstitutionClient> eqr = new PaginationResult<InstitutionClient>(count, institutionClientList);
        LZResult<PaginationResult<InstitutionClient>> result = new LZResult<PaginationResult<InstitutionClient>>(eqr);
        return result;
    }

    public InstitutionClient getById(Long id) {
        return institutionClientMapper.selectByPrimaryKey(id);
    }

    public void saveOrUpdate(InstitutionClient institutionClient) {
        if (institutionClient.getId() != null) {
            institutionClientMapper.updateByPrimaryKeySelective(institutionClient);
        } else {
            String sql = "select  NEXTVAL('orgCustomer')";
            Integer currentValue = this.jdbcTemplate.queryForObject(sql,Integer.class);
            StringBuffer airPortCode = new StringBuffer();
            airPortCode.append("LJG");
            airPortCode.append(GeneratorSerNo.generatorCodeFormatThree(currentValue));
            institutionClient.setNo(airPortCode.toString());

            institutionClientMapper.insert(institutionClient);
        }
    }



    public void deleteById(List<Long> ids) {
        for(int i = 0; i< ids.size();i++){
            InstitutionClient temp = new InstitutionClient();
            temp.setId(ids.get(i));
            temp.setIsDeleted(Constant.MARK_AS_DELETED);
            institutionClientMapper.updateByPrimaryKeySelective(temp);
        }
    }


}
