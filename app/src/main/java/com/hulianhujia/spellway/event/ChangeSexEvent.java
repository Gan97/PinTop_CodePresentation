package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/27.
 */

public class ChangeSexEvent {
    private int sex;

    public ChangeSexEvent(int sex) {
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
