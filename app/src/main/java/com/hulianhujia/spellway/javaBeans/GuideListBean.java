package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/21.
 */

public class GuideListBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"user_id":"17","username":"13028194826","password":"123456","type":"2","amount":"0.00","logintime":"0","loginip":"","nickname":"","age":"0","sex":"","height":"","weight":"","address":"","hoby":"","autograph":"","idcard":"http://pintu.hlhjapp.com/upload","certificate":"http://pintu.hlhjapp.com/upload","lat":"30.53419876","lon":"104.06905365","lct_time":"1500712694","base_fee":"80.00","time_fee":"12.00","distance":0.0644787159488}]
     */

    private int code;
    private String msg;
    /**
     * user_id : 17
     * username : 13028194826
     * password : 123456
     * type : 2
     * amount : 0.00
     * logintime : 0
     * loginip :
     * nickname :
     * age : 0
     * sex :
     * height :
     * weight :
     * address :
     * hoby :
     * autograph :
     * idcard : http://pintu.hlhjapp.com/upload
     * certificate : http://pintu.hlhjapp.com/upload
     * lat : 30.53419876
     * lon : 104.06905365
     * lct_time : 1500712694
     * base_fee : 80.00
     * time_fee : 12.00
     * distance : 0.0644787159488
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
        private String user_id;
        private String username;
        private String password;
        private String type;
        private String amount;
        private String logintime;
        private String loginip;
        private String nickname;
        private String age;
        private String sex;
        private String height;
        private String weight;
        private String address;
        private String picture;
        private String hoby;
        private String autograph;
        private String idcard;
        private String certificate;
        private String lat;
        private String lon;
        private String lct_time;
        private String total_finish_order;
        private String average_score;
        private String base_fee;
        private String time_fee;
        private String level;
        private double distance;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTotal_finish_order() {
            return total_finish_order;
        }

        public void setTotal_finish_order(String total_finish_order) {
            this.total_finish_order = total_finish_order;
        }

        public String getAverage_score() {
            return average_score;
        }

        public void setAverage_score(String average_score) {
            this.average_score = average_score;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getLoginip() {
            return loginip;
        }

        public void setLoginip(String loginip) {
            this.loginip = loginip;
        }

        public String getNickname() {
            return nickname;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHoby() {
            return hoby;
        }

        public void setHoby(String hoby) {
            this.hoby = hoby;
        }

        public String getAutograph() {
            return autograph;
        }

        public void setAutograph(String autograph) {
            this.autograph = autograph;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLct_time() {
            return lct_time;
        }

        public void setLct_time(String lct_time) {
            this.lct_time = lct_time;
        }

        public String getBase_fee() {
            return base_fee;
        }

        public void setBase_fee(String base_fee) {
            this.base_fee = base_fee;
        }

        public String getTime_fee() {
            return time_fee;
        }

        public void setTime_fee(String time_fee) {
            this.time_fee = time_fee;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
}
