package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * Created by FHP on 2017/7/26.
 */

public class OrderDetailBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"order_id":"3","status":"4","paystatus":"0","username":"17781481226","guidename":"13028194826","productid":"","title":"","base_fee":"80.00","time_fee":"100.00","total_fee":"0.00","create_time":"1501745210","update_time":"1501747969","end_time":"1501747992","order_no":"2017080315265033256664","visitornick":"Tom","visitorpic":"http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png","visitorlat":"30.53421783","visitorlon":"104.06913757","guidenick":"","guidepic":"","guidelat":"30.53420830","guidelon":"104.06912994"}]
     */

    private int code;
    private String msg;
    /**
     * order_id : 3
     * status : 4
     * paystatus : 0
     * username : 17781481226
     * guidename : 13028194826
     * productid :
     * title :
     * base_fee : 80.00
     * time_fee : 100.00
     * total_fee : 0.00
     * create_time : 1501745210
     * update_time : 1501747969
     * end_time : 1501747992
     * order_no : 2017080315265033256664
     * visitornick : Tom
     * visitorpic : http://pintu.hlhjapp.com/upload/2017-07-262017-07-26/5978590ab5ad5.png
     * visitorlat : 30.53421783
     * visitorlon : 104.06913757
     * guidenick :
     * guidepic :
     * guidelat : 30.53420830
     * guidelon : 104.06912994
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
        private String total_fee;
        private String create_time;
        private String update_time;
        private String end_time;
        private String order_no;
        private String visitornick;
        private String visitorpic;
        private String visitorlat;
        private String visitorlon;
        private String guidenick;
        private String guidepic;
        private String guidelat;
        private String guidelon;

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

        public String getVisitornick() {
            return visitornick;
        }

        public void setVisitornick(String visitornick) {
            this.visitornick = visitornick;
        }

        public String getVisitorpic() {
            return visitorpic;
        }

        public void setVisitorpic(String visitorpic) {
            this.visitorpic = visitorpic;
        }

        public String getVisitorlat() {
            return visitorlat;
        }

        public void setVisitorlat(String visitorlat) {
            this.visitorlat = visitorlat;
        }

        public String getVisitorlon() {
            return visitorlon;
        }

        public void setVisitorlon(String visitorlon) {
            this.visitorlon = visitorlon;
        }

        public String getGuidenick() {
            return guidenick;
        }

        public void setGuidenick(String guidenick) {
            this.guidenick = guidenick;
        }

        public String getGuidepic() {
            return guidepic;
        }

        public void setGuidepic(String guidepic) {
            this.guidepic = guidepic;
        }

        public String getGuidelat() {
            return guidelat;
        }

        public void setGuidelat(String guidelat) {
            this.guidelat = guidelat;
        }

        public String getGuidelon() {
            return guidelon;
        }

        public void setGuidelon(String guidelon) {
            this.guidelon = guidelon;
        }
    }
}
