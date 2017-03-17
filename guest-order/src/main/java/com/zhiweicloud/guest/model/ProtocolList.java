package com.zhiweicloud.guest.model;

import java.util.List;

/**
 * Created by tc on 2017/3/16.
 */
public class ProtocolList {

    private Long customerId;

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
