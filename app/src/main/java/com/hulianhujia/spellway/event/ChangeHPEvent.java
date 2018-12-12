package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/24.
 */

public class ChangeHPEvent {
    private String price;

    public ChangeHPEvent(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
