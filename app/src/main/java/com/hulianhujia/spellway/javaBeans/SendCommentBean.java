package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/13.
 */

public class SendCommentBean {

    /**
     * Code : 1
     * Msg : 评论成功
     * Returndata : []
     */

    private int Code;
    private String Msg;
    private List<?> Returndata;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<?> getReturndata() {
        return Returndata;
    }

    public void setReturndata(List<?> Returndata) {
        this.Returndata = Returndata;
    }
}
