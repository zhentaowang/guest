package com.zhiweicloud.guest.mapper;

import com.zhiweicloud.guest.po.SourceApiPo;

import java.util.List;
import java.util.Map;

/**
 * SourceApiPoMapper.java
 * Copyright(C) 2017 杭州风数信息技术有限公司
 * 
 * 2017/5/26 19:30
 * @author tiecheng
 */
public interface SourceApiPoMapper {

    int insert(SourceApiPo sourceApiPo);

    List<String> selectSourceDropDownList();

    List<SourceApiPo> selectSourceApiByNameAndDate(Map<String,Object> params);

}
