package com.hulianhujia.spellway.javaBeans;

import java.io.Serializable;

/**
 * Created by FHP on 2017/7/7.
 */

public class UserInfoBean implements Serializable{

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"user_id":"6","username":"17781481226","password":"5135156","type":"1","amount":"0.00","logintime":"0","loginip":"","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","age":"7","sex":"0","height":"173","weight":"0","address":"四川省成都市武侯区","hoby":"龙图","autograph":"Xhhg","idcard":null,"certificate":"","lat":"30.53419495","lon":"104.06907654","lct_time":"1501058300","base_fee":"0.00","time_fee":"0.00"}
     */

    private int code;
    private String msg;
    /**
     * user_id : 6
     * username : 17781481226
     * password : 5135156
     * type : 1
     * amount : 0.00
     * logintime : 0
     * loginip :
     * nickname : Tom
     * picture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
     * age : 7
     * sex : 0
     * height : 173
     * weight : 0
     * address : 四川省成都市武侯区
     * hoby : 龙图
     * autograph : Xhhg
     * idcard : null
     * certificate :
     * lat : 30.53419495
     * lon : 104.06907654
     * lct_time : 1501058300
     * base_fee : 0.00
     * time_fee : 0.00
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

    public static class ReturnArrBean implements Serializable{
        private String user_id;
        private String username;
        private String password;
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
        private String focusNum;
        private String autograph;
        private Object idcard;
        private String certificate;
        private String onduty;
        private String lat;
        private String lon;
        private String lct_time;
        private String base_fee;
        private String time_fee;
        private String message_permit;
        private String pindan_peoplenumber;
        private String pindan_permit;
        private String skill;
        private String introduce;
        private int is_guanzhu;

        public int getIs_guanzhu() {
            return is_guanzhu;
        }

        public void setIs_guanzhu(int is_guanzhu) {
            this.is_guanzhu = is_guanzhu;
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

        public String getFocusNum() {
            return focusNum;
        }

        public void setFocusNum(String focusNum) {
            this.focusNum = focusNum;
        }

        public String getOnduty() {
            return onduty;
        }

        public String getMessage_permit() {
            return message_permit;
        }

        public void setMessage_permit(String message_permit) {
            this.message_permit = message_permit;
        }

        public void setOnduty(String onduty) {
            this.onduty = onduty;
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

        public Object getIdcard() {
            return idcard;
        }

        public void setIdcard(Object idcard) {
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
    }
}
