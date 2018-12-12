package com.hulianhujia.spellway.event;

/**
 * author: ShuaiTao
 * data: on 2017\9\19 0019 17:19
 * E-Mail: bill_dream@sina.com
 */

public class ChangeSkillsEvent {
    private String skill;

    public ChangeSkillsEvent(String skill) {
        this.skill = skill;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
