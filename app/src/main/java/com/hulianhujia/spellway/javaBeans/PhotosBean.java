package com.hulianhujia.spellway.javaBeans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FHP on 2017/8/4.
 */

public class PhotosBean implements Serializable{

    /**
     * code : 1
     * msg : 获取成功
     * returnArr : [{"id":"19","guidename":"17781481226","picture":"http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/598411598a379.jpg","title":"","status":"1","sort":"0"},{"id":"18","guidename":"17781481226","picture":"http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/5984115989efc.jpg","title":"","status":"1","sort":"0"},{"id":"17","guidename":"17781481226","picture":"http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/59841159899de.jpg","title":"","status":"1","sort":"0"},{"id":"16","guidename":"17781481226","picture":"http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/5984115988e6f.png","title":"","status":"1","sort":"0"},{"id":"15","guidename":"17781481226","picture":"http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/5984115987a90.png","title":"","status":"1","sort":"0"},{"id":"14","guidename":"17781481226","picture":"http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/59840b227bbe1.jpg","title":"","status":"1","sort":"0"}]
     */

    private int code;
    private String msg;
    /**
     * id : 19
     * guidename : 17781481226
     * picture : http://pintu.hlhjapp.com/upload/2017-08-042017-08-04/598411598a379.jpg
     * title :
     * status : 1
     * sort : 0
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

    public static class ReturnArrBean implements Serializable{
        private String id;
        private String guidename;
        private String picture;
        private String title;
        private String status;
        private String sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGuidename() {
            return guidename;
        }

        public void setGuidename(String guidename) {
            this.guidename = guidename;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
