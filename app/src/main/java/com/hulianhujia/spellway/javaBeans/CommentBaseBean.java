package com.hulianhujia.spellway.javaBeans;

/**
 * author: ShuaiTao
 * data: on 2017\9\7 0007 11:34
 * E-Mail: bill_dream@sina.com
 */

public class CommentBaseBean {
    /**
     * Code : -1
     * Msg : 没有符合条件的评价
     * Returndata :
     */

    private int Code;
    private String Msg;
    private Object Returndata;
    private int totalPage ;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

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

    public Object getReturndata() {
        return Returndata;
    }

    public void setReturndata(String Returndata) {
        this.Returndata = Returndata;
    }
}
