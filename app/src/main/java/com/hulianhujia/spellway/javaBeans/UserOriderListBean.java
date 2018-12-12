package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/27.
 */

public class UserOriderListBean {
    /**
     * code : 1
     * msg : 订单获取成功
     * returnArr : [{"order_id":"10","status":"3","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":"","title":"","base_fee":"80.00","time_fee":"100.00","create_time":"1501036794","update_time":"1501128422","end_time":"","order_no":"2017072610395407625075"},{"order_id":"9","status":"1","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":"","title":"","base_fee":"80.00","time_fee":"100.00","create_time":"1500865561","update_time":"","end_time":"","order_no":"2017072411060148250523"},{"order_id":"8","status":"1","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":null,"title":"","base_fee":"80.00","time_fee":"100.00","create_time":"1500862133","update_time":"","end_time":"","order_no":"2017072410085334136738"},{"order_id":"7","status":"1","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":null,"title":"","base_fee":"80.00","time_fee":"12.00","create_time":"1500718176","update_time":"","end_time":"","order_no":"2017072218093689233645"},{"order_id":"6","status":"1","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":null,"title":"","base_fee":"80.00","time_fee":"12.00","create_time":"1500716840","update_time":"","end_time":"","order_no":"2017072217472011580980"},{"order_id":"5","status":"0","paystatus":"0","username":"huni","guidename":"1598","productid":null,"title":"","base_fee":"80.00","time_fee":"30.00","create_time":"1500716281","update_time":"","end_time":"","order_no":"2017072217380107898027"},{"order_id":"2","status":"0","paystatus":"0","username":"huni","guidename":"1598","productid":null,"title":"","base_fee":"80.00","time_fee":"30.00","create_time":"1500714021","update_time":"","end_time":"","order_no":"Empty String"},{"order_id":"1","status":"1","paystatus":"0","username":"huni","guidename":"huni1","productid":null,"title":"","base_fee":"50.00","time_fee":"99999999.99","create_time":"0000-00-00","update_time":"0000-00-00","end_time":"","order_no":"Empty String"}]
     */
    private int code;
    private String msg;
    /**
     * order_id : 10
     * status : 3
     * paystatus : 0
     * username : 17781481226
     * guidename : 13028194826
     * productid :
     * title :
     * base_fee : 80.00
     * time_fee : 100.00
     * create_time : 1501036794
     * update_time : 1501128422
     * end_time :
     * order_no : 2017072610395407625075
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
        private String order_id;
        private String status;
        private String paystatus;
        private String username;
        private String guidename;
        private String productid;
        private String title;
        private String base_fee;
        private String time_fee;
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
        private String total_hour;
        private String pdr_trade_sn;
        private String pay_type;
        private String picture;
        private String nickname;
        private String sex;
        private String type;
        private String age;
        private String level;


        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public String getTotal_hour() {
            return total_hour;
        }

        public void setTotal_hour(String total_hour) {
            this.total_hour = total_hour;
        }

        public String getPdr_trade_sn() {
            return pdr_trade_sn;
        }

        public void setPdr_trade_sn(String pdr_trade_sn) {
            this.pdr_trade_sn = pdr_trade_sn;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
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
    }
}
