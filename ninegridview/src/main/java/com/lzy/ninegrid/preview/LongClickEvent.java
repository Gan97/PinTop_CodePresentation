package com.lzy.ninegrid.preview;

/**
 * author: ShuaiTao
 * data: on 2017\9\18 0018 15:30
 * E-Mail: bill_dream@sina.com
 */

public class LongClickEvent {
    private String imgUrl;

    public LongClickEvent(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
