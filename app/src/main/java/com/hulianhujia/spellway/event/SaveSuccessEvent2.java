package com.hulianhujia.spellway.event;

/**
 * Created by Administrator on 2017/9/21/021.
 */

public class SaveSuccessEvent2 {
    private String type;

    public SaveSuccessEvent2(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
