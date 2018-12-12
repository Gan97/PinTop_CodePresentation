package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/12.
 */

public class HomeBean  {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"news_id":"6","cateid":"","username":"18875016690","title":"你妈批","content":"你.哦7亿新坡送心热破7只","picture":"http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a15cb.jpg,http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a1cbe.jpg,","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1501642709","update_time":"","count":"","status":"1","collected":0},{"news_id":"5","cateid":null,"username":"15908199407","title":"diff","content":"Dghjj","picture":"","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1501211216","update_time":"","count":null,"status":"1","collected":0},{"news_id":"4","cateid":null,"username":"15908199407","title":"dgh","content":"Fhhh","picture":"","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1501211129","update_time":"","count":null,"status":"1","collected":0},{"news_id":"3","cateid":null,"username":"17781481226","title":"www","content":"Weryrrdfyyfrggg","picture":"","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1501208591","update_time":"","count":null,"status":"1","collected":0},{"news_id":"2","cateid":null,"username":"17781481226","title":"饿哦腿模","content":"顶你XP泼猴","picture":"","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1500628679","update_time":"","count":null,"status":"1","collected":0},{"news_id":"1","cateid":null,"username":"17781481226","title":"龙体咯","content":"同学","picture":"http://pintu.hlhjapp.com/upload/2017-07-212017-07-21/5971638f71961.jpg,http://pintu.hlhjapp.com/upload/2017-07-212017-07-21/5971638f72aee.jpg,http://pintu.hlhjapp.com/upload/2017-07-212017-07-21/5971638f73d46.jpg,http://pintu.hlhjapp.com/upload/2017-07-212017-07-21/5971638f75140.jpg,http://pintu.hlhjapp.com/upload/2017-07-212017-07-21/5971638f7646b.jpg,","where":"成都市","zan":"0","comment":"0","listorder":"0","create_time":"1500603279","update_time":"","count":null,"status":"1","collected":0}]
     */

    private int code;
    private String msg;
    /**
     * news_id : 6
     * cateid :
     * username : 18875016690
     * title : 你妈批
     * content : 你.哦7亿新坡送心热破7只
     * picture : http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a15cb.jpg,http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/59813fd5a1cbe.jpg,
     * where : 成都市
     * zan : 0
     * comment : 0
     * listorder : 0
     * create_time : 1501642709
     * update_time :
     * count :
     * status : 1
     * collected : 0
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
        private String news_id;
        private String cateid;
        private String username;
        private String title;
        private String content;
        private String picture;
        private String where;
        private String zan;
        private String comment;
        private String listorder;
        private String create_time;
        private String update_time;
        private String count;
        private String status;
        private int collected;
        private String userPicture;
        private String nickname;
        private int is_zan;
        private String lng;
        private String lat;

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

        public int getIs_zan() {
            return is_zan;
        }

        public void setIs_zan(int is_zan) {
            this.is_zan = is_zan;
        }

        public String getNews_id() {
            return news_id;
        }

        public String getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(String userPicture) {
            this.userPicture = userPicture;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public int getCollected() {
            return collected;
        }

        public void setCollected(int collected) {
            this.collected = collected;
        }
    }
}
