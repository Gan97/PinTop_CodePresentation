package com.hulianhujia.spellway.event;

/**
 * author: ShuaiTao
 * data: on 2017\9\30 0030 17:21
 * E-Mail: bill_dream@sina.com
 */

public class DeletEvent {
    private int poision;

    public DeletEvent(int poision) {
        this.poision = poision;
    }

    public int getPoision() {
        return poision;
    }

    public void setPoision(int poision) {
        this.poision = poision;
    }
}
