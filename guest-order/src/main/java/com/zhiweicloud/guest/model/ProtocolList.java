package com.zhiweicloud.guest.model;

import java.util.List;

/**
 * Created by tc on 2017/3/16.
 */
public class ProtocolList {

    private Long customerId;

    private List<Dropdownlist> dropdownlistList;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Dropdownlist> getDropdownlistList() {
        return dropdownlistList;
    }

    public void setDropdownlistList(List<Dropdownlist> dropdownlistList) {
        this.dropdownlistList = dropdownlistList;
    }

}
