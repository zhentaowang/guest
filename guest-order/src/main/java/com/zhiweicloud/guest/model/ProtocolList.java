package com.zhiweicloud.guest.model;

import java.util.List;

/**
 * 协议列表
 * Copyright(C) 2017 杭州风数信息技术有限公司
 *
 * 2017/4/27 9:52
 * @author tiecheng
 */
public class ProtocolList {

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 协议
     */
    private List<ProtocolVo> protocolVos;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ProtocolVo> getProtocolVos() {
        return protocolVos;
    }

    public void setProtocolVos(List<ProtocolVo> protocolVos) {
        this.protocolVos = protocolVos;
    }

}
