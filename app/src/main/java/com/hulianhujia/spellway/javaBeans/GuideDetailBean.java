package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/8/3.
 */

public class GuideDetailBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"user_id":"17","username":"13028194826","password":"123456","type":"2","amount":"0.00","logintime":"0","loginip":"","nickname":"","picture":"","age":"0","sex":"女","height":"0","weight":"","address":"","hoby":"","autograph":"爱上范特西","idcard":"http://pintu.hlhjapp.com/upload","certificate":"http://pintu.hlhjapp.com/upload","lat":"30.53422356","lon":"104.06913757","lct_time":"1502087050","base_fee":"80.00","time_fee":"100.00","onduty":"1","message_permit":"1","judge":[{"jg_id":"2","guidename":"13028194826","username":"17781481226","star":"0","content":"","jg_time":"","order_id":"0","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"3","guidename":"13028194826","username":"17781481226","star":"0","content":null,"jg_time":"","order_id":"0","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"4","guidename":"13028194826","username":"17781481226","star":"3","content":"不满意","jg_time":"1501837127","order_id":"0","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"6","guidename":"13028194826","username":"17781481226","star":"5","content":"你他妈","jg_time":"1502091168","order_id":"2147483647","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"7","guidename":"13028194826","username":"17781481226","star":"5","content":"再次贫困","jg_time":"1502091306","order_id":"2147483647","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"8","guidename":"13028194826","username":"17781481226","star":"5","content":"恩","jg_time":"1502091544","order_id":"2","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"9","guidename":"13028194826","username":"17781481226","star":"5","content":"恩","jg_time":"1502091552","order_id":"2","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"}]}
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
     * picture :
     * age : 0
     * sex : 女
     * height : 0
     * weight :
     * address :
     * hoby :
     * autograph : 爱上范特西
     * idcard : http://pintu.hlhjapp.com/upload
     * certificate : http://pintu.hlhjapp.com/upload
     * lat : 30.53422356
     * lon : 104.06913757
     * lct_time : 1502087050
     * base_fee : 80.00
     * time_fee : 100.00
     * onduty : 1
     * message_permit : 1
     * judge : [{"jg_id":"2","guidename":"13028194826","username":"17781481226","star":"0","content":"","jg_time":"","order_id":"0","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"3","guidename":"13028194826","username":"17781481226","star":"0","content":null,"jg_time":"","order_id":"0","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"4","guidename":"13028194826","username":"17781481226","star":"3","content":"不满意","jg_time":"1501837127","order_id":"0","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"6","guidename":"13028194826","username":"17781481226","star":"5","content":"你他妈","jg_time":"1502091168","order_id":"2147483647","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"7","guidename":"13028194826","username":"17781481226","star":"5","content":"再次贫困","jg_time":"1502091306","order_id":"2147483647","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"8","guidename":"13028194826","username":"17781481226","star":"5","content":"恩","jg_time":"1502091544","order_id":"2","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"},{"jg_id":"9","guidename":"13028194826","username":"17781481226","star":"5","content":"恩","jg_time":"1502091552","order_id":"2","status":"1","nickname":"Tom","picture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png"}]
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
        private String autograph;
        private String idcard;
        private String certificate;
        private String lat;
        private String lon;
        private String level;
        private String lct_time;
        private String base_fee;
        private String time_fee;
        private String onduty;
        private String  average_score;
        private String message_permit;
        private String  countorder;
        private String fengcai_url;
        private String skill;
        private String introduce;
        private int is_guanzhu;
        private String pindan_peoplenumber;
        private String pindan_permit;

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

        public String getFengcai_url() {
            return fengcai_url;
        }

        public void setFengcai_url(String fengcai_url) {
            this.fengcai_url = fengcai_url;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCountorder() {
            return countorder;
        }

        public void setCountorder(String countorder) {
            this.countorder = countorder;
        }

        public String getAverage_score() {
            return average_score;
        }

        public void setAverage_score(String average_score) {
            this.average_score = average_score;
        }

        /**
         * jg_id : 2
         * guidename : 13028194826
         * username : 17781481226
         * star : 0
         * content :
         * jg_time :
         * order_id : 0
         * status : 1
         * nickname : Tom
         * picture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
         */

        private List<JudgeBean> judge;

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

        public List<JudgeBean> getJudge() {
            return judge;
        }

        public void setJudge(List<JudgeBean> judge) {
            this.judge = judge;
        }

        public static class JudgeBean {
            private String jg_id;
            private String guidename;
            private String username;
            private String star;
            private String content;
            private String jg_time;
            private String order_id;
            private String status;
            private String nickname;
            private String picture;

            public String getJg_id() {
                return jg_id;
            }

            public void setJg_id(String jg_id) {
                this.jg_id = jg_id;
            }

            public String getGuidename() {
                return guidename;
            }

            public void setGuidename(String guidename) {
                this.guidename = guidename;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getStar() {
                return star;
            }

            public void setStar(String star) {
                this.star = star;
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

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
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

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
