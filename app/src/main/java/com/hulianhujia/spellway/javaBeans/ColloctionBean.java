package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/15.
 */

public class ColloctionBean {


    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"id":"1","user_id":null,"password":"","username":"17781481226","diary_id":"2","product_id":"0","picture":"","focus_time":"1500876978","status":"1","nickname":"至邪药","userpicture":"http://pintu.schlhjnetworktesturl.com/upload/2017-09-222017-09-22/59c4e041bfdac.jpg","autograph":"心情签名","news_id":"2","cateid":null,"title":"饿哦腿模","content":"顶你XP泼猴","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1500628679","update_time":"","count":null,"collection_num":"0","tag":"三星级旅游景区","lng":"0","lat":"0"},{"id":"2","user_id":null,"password":"","username":"17781481226","diary_id":"2","product_id":"0","picture":"","focus_time":"1500884960","status":"1","nickname":"至邪药","userpicture":"http://pintu.schlhjnetworktesturl.com/upload/2017-09-222017-09-22/59c4e041bfdac.jpg","autograph":"心情签名","news_id":"2","cateid":null,"title":"饿哦腿模","content":"顶你XP泼猴","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1500628679","update_time":"","count":null,"collection_num":"0","tag":"三星级旅游景区","lng":"0","lat":"0"},{"id":"3","user_id":null,"password":"","username":"17781481226","diary_id":"2","product_id":"0","picture":"","focus_time":"1500884968","status":"1","nickname":"至邪药","userpicture":"http://pintu.schlhjnetworktesturl.com/upload/2017-09-222017-09-22/59c4e041bfdac.jpg","autograph":"心情签名","news_id":"2","cateid":null,"title":"饿哦腿模","content":"顶你XP泼猴","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1500628679","update_time":"","count":null,"collection_num":"0","tag":"三星级旅游景区","lng":"0","lat":"0"}]
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
         * id : 1
         * user_id : null
         * password :
         * username : 17781481226
         * diary_id : 2
         * product_id : 0
         * picture :
         * focus_time : 1500876978
         * status : 1
         * nickname : 至邪药
         * userpicture : http://pintu.schlhjnetworktesturl.com/upload/2017-09-222017-09-22/59c4e041bfdac.jpg
         * autograph : 心情签名
         * news_id : 2
         * cateid : null
         * title : 饿哦腿模
         * content : 顶你XP泼猴
         * where : 成都市
         * zan : 0
         * comment : 0
         * listorder : 0
         * create_time : 1500628679
         * update_time :
         * count : null
         * collection_num : 0
         * tag : 三星级旅游景区
         * lng : 0
         * lat : 0
         */

        private String id;
        private Object user_id;
        private String password;
        private String username;
        private String diary_id;
        private String product_id;
        private String picture;
        private String focus_time;
        private String status;
        private String nickname;
        private String userpicture;
        private String autograph;
        private String news_id;
        private Object cateid;
        private String title;
        private String content;
        private String where;
        private String zan;
        private String comment;
        private String listorder;
        private String create_time;
        private String update_time;
        private Object count;
        private String collection_num;
        private String tag;
        private String lng;
        private String lat;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getUser_id() {
            return user_id;
        }

        public void setUser_id(Object user_id) {
            this.user_id = user_id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getDiary_id() {
            return diary_id;
        }

        public void setDiary_id(String diary_id) {
            this.diary_id = diary_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getFocus_time() {
            return focus_time;
        }

        public void setFocus_time(String focus_time) {
            this.focus_time = focus_time;
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

        public String getUserpicture() {
            return userpicture;
        }

        public void setUserpicture(String userpicture) {
            this.userpicture = userpicture;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public Object getCateid() {
            return cateid;
        }

        public void setCateid(Object cateid) {
            this.cateid = cateid;
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

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

        public String getCollection_num() {
            return collection_num;
        }

        public void setCollection_num(String collection_num) {
            this.collection_num = collection_num;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }
}
