package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.model.CheckQueryParam;
import com.zhiweicloud.guest.model.LoungeCheckPo;
import com.zhiweicloud.guest.pageUtil.BasePagination;

import java.util.List;
import java.util.Map;

/**
 * 休息室Mapper
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/5/22 10:31
 * @author tiecheng
 */
public interface LoungeMapper {

    List<LoungeCheckPo> selectLoungeCheckList(CheckQueryParam checkQueryParam, int protocolType);

    List<Map> selectSpecialCheckList(CheckQueryParam checkQueryParam);

}
