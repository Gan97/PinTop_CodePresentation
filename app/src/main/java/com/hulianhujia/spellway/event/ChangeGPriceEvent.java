package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/22.
 */

public class ChangeGPriceEvent {
    private String gPrice;

    public String getgPrice() {
        return gPrice;
    }

    public void setgPrice(String gPrice) {
        this.gPrice = gPrice;
    }

    public ChangeGPriceEvent(String gPrice) {
        this.gPrice = gPrice;
    }
}
