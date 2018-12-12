package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24/024.
 */

public class MyInviteEvent {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"user_id":"32","freeze_amount":"0.00","username":"18011286751","type":"1","amount":"0.00","logintime":"0","loginip":"","nickname":"拼途286751","picture":"http://pintu.hlhjapp.com/upload/2017-08-142017-08-14/599141cdca7e7.png","age":"0","sex":"","height":"","weight":"","address":"","hoby":"","autograph":"","idcard":"","certificate":"","lat":"","lon":"","lct_time":"","base_fee":"","time_fee":"0.00","onduty":"1","message_permit":"1","invite_code":"17781481226","level":"3","pindan_permit":"1","pindan_peoplenumber":"","skill":"","introduce":""}]
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
         * user_id : 32
         * freeze_amount : 0.00
         * username : 18011286751
         * type : 1
         * amount : 0.00
         * logintime : 0
         * loginip :
         * nickname : 拼途286751
         * picture : http://pintu.hlhjapp.com/upload/2017-08-142017-08-14/599141cdca7e7.png
         * age : 0
         * sex :
         * height :
         * weight :
         * address :
         * hoby :
         * autograph :
         * idcard :
         * certificate :
         * lat :
         * lon :
         * lct_time :
         * base_fee :
         * time_fee : 0.00
         * onduty : 1
         * message_permit : 1
         * invite_code : 17781481226
         * level : 3
         * pindan_permit : 1
         * pindan_peoplenumber :
         * skill :
         * introduce :
         */

        private String user_id;
        private String freeze_amount;
        private String username;
        private String type;
        private String amount;
        private String logintime;
        private String loginip;
        private String nickname;
        private String picture;
        private String age;
        private String sex;
        private String height;
        private String weight;
        private String address;
        private String hoby;
        private String autograph;
        private String idcard;
        private String certificate;
        private String lat;
        private String lon;
        private String lct_time;
        private String base_fee;
        private String time_fee;
        private String onduty;
        private String message_permit;
        private String invite_code;
        private String level;
        private String pindan_permit;
        private String pindan_peoplenumber;
        private String skill;
        private String introduce;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFreeze_amount() {
            return freeze_amount;
        }

        public void setFreeze_amount(String freeze_amount) {
            this.freeze_amount = freeze_amount;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
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

        public String getOnduty() {
            return onduty;
        }

        public void setOnduty(String onduty) {
            this.onduty = onduty;
        }

        public String getMessage_permit() {
            return message_permit;
        }

        public void setMessage_permit(String message_permit) {
            this.message_permit = message_permit;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPindan_permit() {
            return pindan_permit;
        }

        public void setPindan_permit(String pindan_permit) {
            this.pindan_permit = pindan_permit;
        }

        public String getPindan_peoplenumber() {
            return pindan_peoplenumber;
        }

        public void setPindan_peoplenumber(String pindan_peoplenumber) {
            this.pindan_peoplenumber = pindan_peoplenumber;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
    }
}
