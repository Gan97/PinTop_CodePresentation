package com.hulianhujia.spellway.javaBeans;

/**
 * Created by FHP on 2017/7/21.
 */

public class ChangeHobbyEvent {
    private String hobby;

    public ChangeHobbyEvent(String hobby) {
        this.hobby = hobby;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
