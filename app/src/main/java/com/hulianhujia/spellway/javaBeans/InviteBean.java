package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/8/1.
 */

public class InviteBean {

    /**
     * code : 1
     * msg : 添加成功
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
