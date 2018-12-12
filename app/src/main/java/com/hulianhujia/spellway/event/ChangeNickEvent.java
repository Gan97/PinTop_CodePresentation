package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/21.
 */

public class ChangeNickEvent {
    private String content;

    public ChangeNickEvent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
