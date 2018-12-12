package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24/024.
 */

public class AccountDetailBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"log_id":"64","user_name":"17781481226","user_yue":"99999999.99","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 00:18:39","change_desc":"充值余额","change_type":"1"},{"log_id":"65","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 01:05:22","change_desc":"充值余额","change_type":"1"},{"log_id":"66","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:51:55","change_desc":"充值余额","change_type":"2"},{"log_id":"67","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:53:18","change_desc":"充值余额","change_type":"2"},{"log_id":"68","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:53:46","change_desc":"充值余额","change_type":"2"},{"log_id":"69","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:55:08","change_desc":"充值余额","change_type":"2"},{"log_id":"70","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:55:36","change_desc":"充值余额","change_type":"2"},{"log_id":"71","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:57:21","change_desc":"充值余额","change_type":"1"},{"log_id":"72","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 14:59:09","change_desc":"充值余额","change_type":"2"},{"log_id":"73","user_name":"17781481226","user_yue":"0.01","user_yajin":"0.00","user_jifen":"0","change_time":"2017-09-21 15:08:07","change_desc":"充值余额","change_type":"2"}]
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
         * log_id : 64
         * user_name : 17781481226
         * user_yue : 99999999.99
         * user_yajin : 0.00
         * user_jifen : 0
         * change_time : 2017-09-21 00:18:39
         * change_desc : 充值余额
         * change_type : 1
         */

        private String log_id;
        private String user_name;
        private String user_yue;
        private String user_yajin;
        private String user_jifen;
        private String change_time;
        private String change_desc;
        private String change_type;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_yue() {
            return user_yue;
        }

        public void setUser_yue(String user_yue) {
            this.user_yue = user_yue;
        }

        public String getUser_yajin() {
            return user_yajin;
        }

        public void setUser_yajin(String user_yajin) {
            this.user_yajin = user_yajin;
        }

        public String getUser_jifen() {
            return user_jifen;
        }

        public void setUser_jifen(String user_jifen) {
            this.user_jifen = user_jifen;
        }

        public String getChange_time() {
            return change_time;
        }

        public void setChange_time(String change_time) {
            this.change_time = change_time;
        }

        public String getChange_desc() {
            return change_desc;
        }

        public void setChange_desc(String change_desc) {
            this.change_desc = change_desc;
        }

        public String getChange_type() {
            return change_type;
        }

        public void setChange_type(String change_type) {
            this.change_type = change_type;
        }
    }
}
