package com.hulianhujia.spellway.javaBeans;

/**
 * author: ShuaiTao
 * data: on 2017\10\9 0009 10:08
 * E-Mail: bill_dream@sina.com
 */

public class HXPWDBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"username":"17781481226","is_edit_password":"1","huanxin_password":"5135156"}
     */

    private int code;
    private String msg;
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
        /**
         * username : 17781481226
         * is_edit_password : 1
         * huanxin_password : 5135156
         */

        private String username;
        private String is_edit_password;
        private String huanxin_password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getIs_edit_password() {
            return is_edit_password;
        }

        public void setIs_edit_password(String is_edit_password) {
            this.is_edit_password = is_edit_password;
        }

        public String getHuanxin_password() {
            return huanxin_password;
        }

        public void setHuanxin_password(String huanxin_password) {
            this.huanxin_password = huanxin_password;
        }
    }
}
