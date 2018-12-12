package com.hulianhujia.spellway.javaBeans;

/**
 * Created by FHP on 2017/8/18.
 */

public class GetCashBean {

    /**
     * code : 1
     * msg :
     * returnArr : {"service":"batch_trans_notify","partner":"2088721621691827","notify_url":"http://pintu.hlhjapp.com/home/AliPay/notify","email":"2233265022@qq.com","account_name":"成都霖铃科技有限公司","pay_date":"20170818","batch_no":"20170818161555000001","batch_fee":"0.01","batch_num":1,"detail_data":"2017052215564029837543^876585938@qq.com^张洋^0.01^测试","_input_charset":"utf-8"}
     */

    private int code;
    private String msg;
    /**
     * service : batch_trans_notify
     * partner : 2088721621691827
     * notify_url : http://pintu.hlhjapp.com/home/AliPay/notify
     * email : 2233265022@qq.com
     * account_name : 成都霖铃科技有限公司
     * pay_date : 20170818
     * batch_no : 20170818161555000001
     * batch_fee : 0.01
     * batch_num : 1
     * detail_data : 2017052215564029837543^876585938@qq.com^张洋^0.01^测试
     * _input_charset : utf-8
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
        private String service;
        private String partner;
        private String notify_url;
        private String email;
        private String account_name;
        private String pay_date;
        private String batch_no;
        private String batch_fee;
        private int batch_num;
        private String detail_data;
        private String _input_charset;

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getPartner() {
            return partner;
        }

        public void setPartner(String partner) {
            this.partner = partner;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAccount_name() {
            return account_name;
        }

        public void setAccount_name(String account_name) {
            this.account_name = account_name;
        }

        public String getPay_date() {
            return pay_date;
        }

        public void setPay_date(String pay_date) {
            this.pay_date = pay_date;
        }

        public String getBatch_no() {
            return batch_no;
        }

        public void setBatch_no(String batch_no) {
            this.batch_no = batch_no;
        }

        public String getBatch_fee() {
            return batch_fee;
        }

        public void setBatch_fee(String batch_fee) {
            this.batch_fee = batch_fee;
        }

        public int getBatch_num() {
            return batch_num;
        }

        public void setBatch_num(int batch_num) {
            this.batch_num = batch_num;
        }

        public String getDetail_data() {
            return detail_data;
        }

        public void setDetail_data(String detail_data) {
            this.detail_data = detail_data;
        }

        public String get_input_charset() {
            return _input_charset;
        }

        public void set_input_charset(String _input_charset) {
            this._input_charset = _input_charset;
        }
    }
}
