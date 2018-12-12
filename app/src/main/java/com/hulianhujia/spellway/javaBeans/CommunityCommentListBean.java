package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * author: ShuaiTao
 * data: on 2017\9\6 0006 17:47
 * E-Mail: bill_dream@sina.com
 */

public class CommunityCommentListBean {
    /**
     * Code : 1
     * Msg : 获取成功
     * Returndata : [{"jg_id":"60","cateid":"1","username":"17781481226","jgd_id":"1","content":"我日啊","jg_time":"1503304552","status":"1","nickname":"啦啦队"},{"jg_id":"59","cateid":"1","username":"17781481226","jgd_id":"1","content":"PK去去","jg_time":"1503304540","status":"1","nickname":"啦啦队"},{"jg_id":"58","cateid":"1","username":"17781481226","jgd_id":"1","content":"啊啊啊","jg_time":"1503304525","status":"1","nickname":"啦啦队"},{"jg_id":"48","cateid":"1","username":"17781481226","jgd_id":"1","content":"Dvh","jg_time":"1502349869","status":"1","nickname":"啦啦队"},{"jg_id":"35","cateid":"1","username":"17781481226","jgd_id":"1","content":"sadasf","jg_time":"1501059929","status":"1","nickname":"啦啦队"}]
     */
    private int Code;
    private String Msg;
    private List<ReturndataBean> Returndata;

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

    public List<ReturndataBean> getReturndata() {
        return Returndata;
    }

    public void setReturndata(List<ReturndataBean> Returndata) {
        this.Returndata = Returndata;
    }

    public static class ReturndataBean {
        /**
         * jg_id : 60
         * cateid : 1
         * username : 17781481226
         * jgd_id : 1
         * content : 我日啊
         * jg_time : 1503304552
         * status : 1
         * nickname : 啦啦队
         */

        private String jg_id;
        private String cateid;
        private String username;
        private String jgd_id;
        private String content;
        private String jg_time;
        private String status;
        private String nickname;

        public String getJg_id() {
            return jg_id;
        }

        public void setJg_id(String jg_id) {
            this.jg_id = jg_id;
        }

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getJgd_id() {
            return jgd_id;
        }

        public void setJgd_id(String jgd_id) {
            this.jgd_id = jgd_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getJg_time() {
            return jg_time;
        }

        public void setJg_time(String jg_time) {
            this.jg_time = jg_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
