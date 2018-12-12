package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/22.
 */

public class ChangeHPriceEvent  {
    private  String hprice;

    public ChangeHPriceEvent(String hprice) {
        this.hprice = hprice;
    }

    public String getHprice() {
        return hprice;
    }

    public void setHprice(String hprice) {
        this.hprice = hprice;
    }
}
