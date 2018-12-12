package com.hulianhujia.spellway.event;

/**
 * author: ShuaiTao
 * data: on 2017\9\19 0019 17:18
 * E-Mail: bill_dream@sina.com
 */

public class ChangeIntroduceEvent {
    private String introduce;

    public ChangeIntroduceEvent(String introduce) {
        this.introduce = introduce;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
