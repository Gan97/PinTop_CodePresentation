package com.hulianhujia.spellway.event;

import java.util.List;

/**
 * author: ShuaiTao
 * data: on 2017\9\21 0021 13:57
 * E-Mail: bill_dream@sina.com
 */

public class RedBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"id":"3","username":"17781481226","invite_user":"18011286751","add_time":"2017-09-20 00:00:00","max_use_time":"1508492579","amount":"5.00","min_use_time":"1505900579","status":"0"}]
     */

    private int code;
    private String msg;
    private List<ReturnArrBean> returnArr;

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

    public List<ReturnArrBean> getReturnArr() {
        return returnArr;
    }

    public void setReturnArr(List<ReturnArrBean> returnArr) {
        this.returnArr = returnArr;
    }

    public static class ReturnArrBean {
        /**
         * id : 3
         * username : 17781481226
         * invite_user : 18011286751
         * add_time : 2017-09-20 00:00:00
         * max_use_time : 1508492579
         * amount : 5.00
         * min_use_time : 1505900579
         * status : 0
         */

        private String id;
        private String username;
        private String invite_user;
        private String add_time;
        private String max_use_time;
        private String amount;
        private String min_use_time;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getInvite_user() {
            return invite_user;
        }

        public void setInvite_user(String invite_user) {
            this.invite_user = invite_user;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getMax_use_time() {
            return max_use_time;
        }

        public void setMax_use_time(String max_use_time) {
            this.max_use_time = max_use_time;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getMin_use_time() {
            return min_use_time;
        }

        public void setMin_use_time(String min_use_time) {
            this.min_use_time = min_use_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
