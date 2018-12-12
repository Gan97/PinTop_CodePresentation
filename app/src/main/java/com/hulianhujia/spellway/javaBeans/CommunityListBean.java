package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/12.
 */

public class CommunityListBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"cmnt_id":"1","username":"17781481226","title":"eikd","content":"哄你YY","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","zan":"0","comment":"0","listorder":"0","create_time":"1500451884","update_time":"","count":"","status":"1","nickname":"Tom","user_id":"6"},{"cmnt_id":"2","username":"18875016690","title":"不摸伤心","content":"名正言顺我婆","picture":"http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/598199917587e.jpg","zan":"0","comment":"0","listorder":"0","create_time":"1501642218","update_time":null,"count":null,"status":"1","nickname":"","user_id":"19"}]
     */

    private int code;
    private String msg;
    /**
     * cmnt_id : 1
     * username : 17781481226
     * title : eikd
     * content : 哄你YY
     * picture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
     * zan : 0
     * comment : 0
     * listorder : 0
     * create_time : 1500451884
     * update_time :
     * count :
     * status : 1
     * nickname : Tom
     * user_id : 6
     */

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
        private String cmnt_id;
        private String username;
        private String title;
        private String content;
        private String picture;
        private String zan;
        private String comment;
        private String listorder;
        private String create_time;
        private String userPicture;
        private String update_time;
        private String count;
        private String status;
        private String nickname;
        private String user_id;
        private String is_zan;
        private String where;

        public String getWhere() {
            return where;
        }

        public void setWhere(String where) {
            this.where = where;
        }

        public String getUserPicture() {
            return userPicture;
        }

        public String getIs_zan() {
            return is_zan;
        }

        public void setIs_zan(String is_zan) {
            this.is_zan = is_zan;
        }

        public void setUserPicture(String userPicture) {
            this.userPicture = userPicture;
        }

        public String getCmnt_id() {
            return cmnt_id;
        }

        public void setCmnt_id(String cmnt_id) {
            this.cmnt_id = cmnt_id;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
