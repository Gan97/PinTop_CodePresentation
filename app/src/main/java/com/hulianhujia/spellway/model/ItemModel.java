package com.hulianhujia.spellway.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017\6\9 0009.
 */

public class ItemModel implements Serializable{

    public static final int CHAT_A = 1001;
    public static final int CHAT_B = 1002;

    public Object object;
    public int type;

    public ItemModel(int type, Object object) {
        this.object = object;
        this.type = type;
    }
}
