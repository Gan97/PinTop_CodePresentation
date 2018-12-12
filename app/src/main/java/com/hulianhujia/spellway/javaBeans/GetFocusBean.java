package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/8/3.
 */

public class GetFocusBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"id":"7","user_id":"6","username":"18875016690","password":"","focus_uid":"19","focus_time":"","status":"1","type":"1","nickname":"","age":"0","sex":"","address":"","autograph":""},{"id":"8","user_id":"6","username":"18875016690","password":"","focus_uid":"19","focus_time":"","status":"1","type":"1","nickname":"","age":"0","sex":"","address":"","autograph":""}]
     */

    private int code;
    private String msg;
    /**
     * id : 7
     * user_id : 6
     * username : 18875016690
     * password :
     * focus_uid : 19
     * focus_time :
     * status : 1
     * type : 1
     * nickname :
     * age : 0
     * sex :
     * address :
     * autograph :
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
        private String id;
        private String user_id;
        private String username;
        private String password;
        private String focus_uid;
        private String focus_time;
        private String status;
        private String type;
        private String nickname;
        private String age;
        private String sex;
        private String address;
        private String autograph;
        private String picture;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFocus_uid() {
            return focus_uid;
        }

        public void setFocus_uid(String focus_uid) {
            this.focus_uid = focus_uid;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }
    }
}
