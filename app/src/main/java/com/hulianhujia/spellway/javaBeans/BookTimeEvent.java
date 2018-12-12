package com.hulianhujia.spellway.javaBeans;

/**
 * author: ShuaiTao
 * data: on 2017\9\21 0021 15:31
 * E-Mail: bill_dream@sina.com
 */

public class BookTimeEvent {
    private String time ;
    private int flag;
    public BookTimeEvent(String time,int flag) {
        this.time = time;
        this.flag=flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
