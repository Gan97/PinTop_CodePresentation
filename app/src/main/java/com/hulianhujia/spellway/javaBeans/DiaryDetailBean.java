package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/14.
 */

public class DiaryDetailBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"news_id":"42","cateid":"","username":"17781481226","title":"吧","content":"交了","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"0000-00-00","update_time":"0000-00-00","count":"","status":"1","judge":[],"judgeCount":0}
     */

    private int code;
    private String msg;
    /**
     * news_id : 42
     * cateid :
     * username : 17781481226
     * title : 吧
     * content : 交了
     * where : 成都市
     * zan : 0
     * comment : 0
     * listorder : 0
     * create_time : 0000-00-00
     * update_time : 0000-00-00
     * count :
     * status : 1
     * judge : []
     * judgeCount : 0
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
        private String news_id;
        private String cateid;
        private String username;
        private String title;
        private String content;
        private String where;
        private String zan;
        private String comment;
        private String listorder;
        private String create_time;
        private String update_time;
        private String count;
        private String status;
        private int judgeCount;
        private List<?> judge;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWhere() {
            return where;
        }

        public void setWhere(String where) {
            this.where = where;
        }

        public String getZan() {
            return zan;
        }

        public void setZan(String zan) {
            this.zan = zan;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getJudgeCount() {
            return judgeCount;
        }

        public void setJudgeCount(int judgeCount) {
            this.judgeCount = judgeCount;
        }

        public List<?> getJudge() {
            return judge;
        }

        public void setJudge(List<?> judge) {
            this.judge = judge;
        }
    }
}
