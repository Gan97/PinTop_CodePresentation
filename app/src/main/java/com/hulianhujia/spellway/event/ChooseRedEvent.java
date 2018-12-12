package com.hulianhujia.spellway.event;

/**
 * author: ShuaiTao
 * data: on 2017\10\12 0012 10:53
 * E-Mail: bill_dream@sina.com
 */

public class ChooseRedEvent {
    private String id;
    private String price;

    public ChooseRedEvent(String id, String price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
