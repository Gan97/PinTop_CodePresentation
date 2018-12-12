package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/22.
 */

public class ChangeAgeEvent {
    private String age;

    public ChangeAgeEvent(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
