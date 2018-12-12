package com.hulianhujia.spellway.javaBeans;

import java.util.List;

/**
 * author: ShuaiTao
 * data: on 2017\9\6 0006 15:54
 * E-Mail: bill_dream@sina.com
 */

public class TagBean {

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"tag":"四星级旅游景区"},{"tag":"市级风景区"},{"tag":"三星级旅游景区"},{"tag":"国家生态旅游区"},{"tag":"四星级旅游景区"}]
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
         * tag : 四星级旅游景区
         */

        private String tag;

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
