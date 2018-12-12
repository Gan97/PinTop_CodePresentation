package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/22.
 */
public class OrderBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"order_id":"28","status":"4","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":null,"title":"","base_fee":"5.00","time_fee":"100.00","total_fee":"0.00","create_time":"1505979437","update_time":"1505980196","end_time":"1505980271","order_no":"2017092115371785811747","push_status":"0","book_time":"2017-09-21 18:00","pindan":"0","dazhe":"1","pindan_order_no":"0","jiedan_time":"1505980157","pay_time":"","nickname":"至邪药","picture":"http://pintu.schlhjnetworktesturl.com/upload/2017-09-212017-09-21/59c336b53a22d.png"}]
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
         * order_id : 28
         * status : 4
         * paystatus : 0
         * username : 17781481226
         * guidename : 13028194826
         * productid : null
         * title :
         * base_fee : 5.00
         * time_fee : 100.00
         * total_fee : 0.00
         * create_time : 1505979437
         * update_time : 1505980196
         * end_time : 1505980271
         * order_no : 2017092115371785811747
         * push_status : 0
         * book_time : 2017-09-21 18:00
         * pindan : 0
         * dazhe : 1
         * pindan_order_no : 0
         * jiedan_time : 1505980157
         * pay_time :
         * nickname : 至邪药
         * picture : http://pintu.schlhjnetworktesturl.com/upload/2017-09-212017-09-21/59c336b53a22d.png
         */

        private String order_id;
        private String status;
        private String paystatus;
        private String username;
        private String guidename;
        private Object productid;
        private String title;
        private String base_fee;
        private String time_fee;
        private String total_fee;
        private String create_time;
        private String update_time;
        private String end_time;
        private String order_no;
        private String push_status;
        private String book_time;
        private String pindan;
        private String dazhe;
        private String pindan_order_no;
        private String jiedan_time;
        private String pay_time;
        private String nickname;
        private String picture;

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

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGuidename() {
            return guidename;
        }

        public void setGuidename(String guidename) {
            this.guidename = guidename;
        }

        public Object getProductid() {
            return productid;
        }

        public void setProductid(Object productid) {
            this.productid = productid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
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

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getPush_status() {
            return push_status;
        }

        public void setPush_status(String push_status) {
            this.push_status = push_status;
        }

        public String getBook_time() {
            return book_time;
        }

        public void setBook_time(String book_time) {
            this.book_time = book_time;
        }

        public String getPindan() {
            return pindan;
        }

        public void setPindan(String pindan) {
            this.pindan = pindan;
        }

        public String getDazhe() {
            return dazhe;
        }

        public void setDazhe(String dazhe) {
            this.dazhe = dazhe;
        }

        public String getPindan_order_no() {
            return pindan_order_no;
        }

        public void setPindan_order_no(String pindan_order_no) {
            this.pindan_order_no = pindan_order_no;
        }

        public String getJiedan_time() {
            return jiedan_time;
        }

        public void setJiedan_time(String jiedan_time) {
            this.jiedan_time = jiedan_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
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
