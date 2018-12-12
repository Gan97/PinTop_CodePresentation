package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/22.
 */

public class ChangeMottoEvent {
    private String motto;

    public ChangeMottoEvent(String motto) {
        this.motto = motto;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }
}
