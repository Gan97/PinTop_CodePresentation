package com.hulianhujia.spellway.event;

import java.util.List;

/**
 * Created by FHP on 2017/8/8.
 */

public class MyMessageListBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"id":"2","user1_id":"19","user2_id":"6","message":"？您热一下","time":"1501644542","status":"1","menick":"Tom","mename":"17781481226","mepicture":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","henick":"","hename":"18875016690","hepicture":"http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/598199917587e.jpg"}]
     */

    private int code;
    private String msg;
    /**
     * id : 2
     * user1_id : 19
     * user2_id : 6
     * message : ？您热一下
     * time : 1501644542
     * status : 1
     * menick : Tom
     * mename : 17781481226
     * mepicture : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
     * henick :
     * hename : 18875016690
     * hepicture : http://pintu.hlhjapp.com/upload/2017-08-022017-08-02/598199917587e.jpg
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
        private String mename;
        private String mepicture;
        private String henick;
        private String hename;
        private String hepicture;

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

        public String getMename() {
            return mename;
        }

        public void setMename(String mename) {
            this.mename = mename;
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

        public String getHename() {
            return hename;
        }

        public void setHename(String hename) {
            this.hename = hename;
        }

        public String getHepicture() {
            return hepicture;
        }

        public void setHepicture(String hepicture) {
            this.hepicture = hepicture;
        }
    }
}
