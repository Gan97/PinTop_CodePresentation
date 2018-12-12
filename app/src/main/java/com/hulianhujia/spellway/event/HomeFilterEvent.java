package com.hulianhujia.spellway.event;

/**
 * author: ShuaiTao
 * data: on 2017\9\6 0006 16:07
 * E-Mail: bill_dream@sina.com
 */

public class HomeFilterEvent {
    private String city;
    private String tag;

    public HomeFilterEvent(String city, String tag) {
        this.city = city;
        this.tag = tag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
