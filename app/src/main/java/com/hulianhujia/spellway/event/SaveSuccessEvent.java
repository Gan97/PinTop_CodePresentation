package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/13.
 */

public class SaveSuccessEvent {
    private String type;

    public SaveSuccessEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
