package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/21.
 */

public class ChangeHeightEvetn {
    private String height;

    public ChangeHeightEvetn(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
