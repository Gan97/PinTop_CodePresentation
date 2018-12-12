package com.hulianhujia.spellway.javaBeans;

/**
 * author: ShuaiTao
 * data: on 2017\9\12 0012 16:20
 * E-Mail: bill_dream@sina.com
 */

public class BaseBean {

    /**
     * code : -1
     * msg : 获取失败
     * returnArr : null
     */

    private int code;
    private String msg;
    private Object returnArr;

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

    public Object getReturnArr() {
        return returnArr;
    }

    public void setReturnArr(Object returnArr) {
        this.returnArr = returnArr;
    }
}
