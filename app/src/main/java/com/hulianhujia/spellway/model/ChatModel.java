package com.hulianhujia.spellway.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\6\9 0009.
 */

public class ChatModel implements Serializable{

    private String icon = "";
    private String content = "";
    private String type = "";

    public String getIcon() {
        return icon;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(String type) {
        this.type = type;
    }
}
