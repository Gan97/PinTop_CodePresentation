package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/8/2.
 */

public class SendMessageBean {
    /**
     * code : 1
     * msg : 发送成功
     * returnArr : []
     */
    private int code;
    private String msg;
    private List<?> returnArr;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getReturnArr() {
        return returnArr;
    }

    public void setReturnArr(List<?> returnArr) {
        this.returnArr = returnArr;
    }
}
