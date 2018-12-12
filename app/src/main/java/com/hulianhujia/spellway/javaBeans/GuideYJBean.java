package com.hulianhujia.spellway.javaBeans;

/**
 * author: ShuaiTao
 * data: on 2017\9\19 0019 11:30
 * E-Mail: bill_dream@sina.com
 */

public class GuideYJBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : {"average_score":"2.3333","countorder":"0","totalincome":0}
     */

    private int code;
    private String msg;
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
        /**
         * average_score : 2.3333
         * countorder : 0
         * totalincome : 0
         */
        private String average_score;
        private String countorder;
        private double totalincome;

        public String getAverage_score() {
            return average_score;
        }

        public void setAverage_score(String average_score) {
            this.average_score = average_score;
        }

        public String getCountorder() {
            return countorder;
        }

        public void setCountorder(String countorder) {
            this.countorder = countorder;
        }

        public double getTotalincome() {
            return totalincome;
        }

        public void setTotalincome(double totalincome) {
            this.totalincome = totalincome;
        }
    }
}
