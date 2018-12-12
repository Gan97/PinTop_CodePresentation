package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/8/19.
 */

public class FilterEvent {
    private String age;
    private int type;

    public FilterEvent(String age, int type) {
        this.age = age;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
