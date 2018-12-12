package com.hulianhujia.spellway.event;

/**
 * Created by FHP on 2017/7/22.
 */

public class ChangeWeightEvent {
    private String weight;

    public ChangeWeightEvent(String weight) {
        this.weight = weight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
