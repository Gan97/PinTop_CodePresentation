package com.hulianhujia.spellway.javaBeans;

/**
 * Created by FHP on 2017/8/10.
 */

public class MoneyLeftBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"amount":"0.00"}
     */

    private int code;
    private String msg;
    /**
     * amount : 0.00
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
        private String amount;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
