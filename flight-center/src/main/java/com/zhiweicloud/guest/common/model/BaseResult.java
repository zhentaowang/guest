package com.zhiweicloud.guest.common.model;

/**
 * Created by tc on 2017/5/18.
 */
public class BaseResult<T> {

    private int state;

    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
