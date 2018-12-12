package com.hulianhujia.spellway.javaBeans;

/**
 * Created by FHP on 2017/8/15.
 */

public class SmsBean {

    /**
     * code : 1
     * msg : 发送成功
     * returnArr : {"code":0,"msg":"发送成功","count":1,"fee":0.05,"unit":"RMB","mobile":"17781481226","sid":17002463604,"msgCode":"068890"}
     */

    private int code;
    private String msg;
    /**
     * code : 0
     * msg : 发送成功
     * count : 1
     * fee : 0.05
     * unit : RMB
     * mobile : 17781481226
     * sid : 17002463604
     * msgCode : 068890
     */

    private ReturnArrBean returnArr;

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

    public ReturnArrBean getReturnArr() {
        return returnArr;
    }

    public void setReturnArr(ReturnArrBean returnArr) {
        this.returnArr = returnArr;
    }

    public static class ReturnArrBean {
        private int code;
        private String msg;
        private int count;
        private double fee;
        private String unit;
        private String mobile;
        private long sid;
        private String msgCode;

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

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public long getSid() {
            return sid;
        }

        public void setSid(long sid) {
            this.sid = sid;
        }

        public String getMsgCode() {
            return msgCode;
        }

        public void setMsgCode(String msgCode) {
            this.msgCode = msgCode;
        }
    }
}
