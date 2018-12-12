package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/8/2.
 */

public class MessageBean {

    /**
     * code : 1
     * msg : 消息获取成功
     * returnArr : [{"id":"1","user1_id":"6","user2_id":"19","message":"哄孩子培训","time":"1501644041","status":"1","menick":"Tom","mepicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","henick":"","hepicture":"","who":"me"},{"id":"2","user1_id":"19","user2_id":"6","message":"？您热一下","time":"1501644542","status":"1","menick":"","mepicture":"","henick":"Tom","hepicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","who":"he"},{"id":"3","user1_id":"6","user2_id":"19","message":"哈哈哈哈","time":"1501645415","status":"1","menick":"Tom","mepicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","henick":"","hepicture":"","who":"me"},{"id":"4","user1_id":"19","user2_id":"6","message":"哈哈哈哈","time":"1501645548","status":"1","menick":"","mepicture":"","henick":"Tom","hepicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","who":"he"},{"id":"5","user1_id":"19","user2_id":"6","message":"事故","time":"1501645564","status":"1","menick":"","mepicture":"","henick":"Tom","hepicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","who":"he"}]
     */

    private int code;
    private String msg;
    /**
     * id : 1
     * user1_id : 6
     * user2_id : 19
     * message : 哄孩子培训
     * time : 1501644041
     * status : 1
     * menick : Tom
     * mepicture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
     * henick :
     * hepicture :
     * who : me
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
        private String user1_id;
        private String user2_id;
        private String message;
        private String time;
        private String status;
        private String menick;
        private String mepicture;
        private String henick;
        private String hepicture;
        private String who;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser1_id() {
            return user1_id;
        }

        public void setUser1_id(String user1_id) {
            this.user1_id = user1_id;
        }

        public String getUser2_id() {
            return user2_id;
        }

        public void setUser2_id(String user2_id) {
            this.user2_id = user2_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMenick() {
            return menick;
        }

        public void setMenick(String menick) {
            this.menick = menick;
        }

        public String getMepicture() {
            return mepicture;
        }

        public void setMepicture(String mepicture) {
            this.mepicture = mepicture;
        }

        public String getHenick() {
            return henick;
        }

        public void setHenick(String henick) {
            this.henick = henick;
        }

        public String getHepicture() {
            return hepicture;
        }

        public void setHepicture(String hepicture) {
            this.hepicture = hepicture;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
