package com.hulianhujia.spellway.event;

/**
 * author: ShuaiTao
 * data: on 2017\9\27 0027 17:52
 * E-Mail: bill_dream@sina.com
 */

public class ImageReadyEvent {
    private String lat;
    private String lon;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public ImageReadyEvent(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
