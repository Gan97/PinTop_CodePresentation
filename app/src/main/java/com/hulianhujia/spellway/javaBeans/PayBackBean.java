package com.hulianhujia.spellway.javaBeans;

/**
 * Created by FHP on 2017/8/1.
 */

public class PayBackBean {

    /**
     * memo : xxxxx
     * result : {"alipay_trade_app_pay_response":{"code":"10000","msg":"Success","app_id":"2014072300007148","out_trade_no":"081622560194853","trade_no":"2016081621001004400236957647","total_amount":"0.01","seller_id":"2088702849871851","charset":"utf-8","timestamp":"2016-10-11 17:43:36"},"sign":"NGfStJf3i3ooWBuCDIQSumOpaGBcQz+aoAqyGh3W6EqA/gmyPYwLJ2REFijY9XPTApI9YglZyMw+ZMhd3kb0mh4RAXMrb6mekX4Zu8Nf6geOwIa9kLOnw0IMCjxi4abDIfXhxrXyj********","sign_type":"RSA2"}
     * resultStatus : 9000
     */

    private String memo;
    /**
     * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2014072300007148","out_trade_no":"081622560194853","trade_no":"2016081621001004400236957647","total_amount":"0.01","seller_id":"2088702849871851","charset":"utf-8","timestamp":"2016-10-11 17:43:36"}
     * sign : NGfStJf3i3ooWBuCDIQSumOpaGBcQz+aoAqyGh3W6EqA/gmyPYwLJ2REFijY9XPTApI9YglZyMw+ZMhd3kb0mh4RAXMrb6mekX4Zu8Nf6geOwIa9kLOnw0IMCjxi4abDIfXhxrXyj********
     * sign_type : RSA2
     */

    private ResultBean result;
    private String resultStatus;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public static class ResultBean {
        /**
         * code : 10000
         * msg : Success
         * app_id : 2014072300007148
         * out_trade_no : 081622560194853
         * trade_no : 2016081621001004400236957647
         * total_amount : 0.01
         * seller_id : 2088702849871851
         * charset : utf-8
         * timestamp : 2016-10-11 17:43:36
         */

        private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
        private String sign;
        private String sign_type;

        public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
            return alipay_trade_app_pay_response;
        }

        public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
            this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSign_type() {
            return sign_type;
        }

        public void setSign_type(String sign_type) {
            this.sign_type = sign_type;
        }

        public static class AlipayTradeAppPayResponseBean {
            private String code;
            private String msg;
            private String app_id;
            private String out_trade_no;
            private String trade_no;
            private String total_amount;
            private String seller_id;
            private String charset;
            private String timestamp;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getApp_id() {
                return app_id;
            }

            public void setApp_id(String app_id) {
                this.app_id = app_id;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getTrade_no() {
                return trade_no;
            }

            public void setTrade_no(String trade_no) {
                this.trade_no = trade_no;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getCharset() {
                return charset;
            }

            public void setCharset(String charset) {
                this.charset = charset;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
