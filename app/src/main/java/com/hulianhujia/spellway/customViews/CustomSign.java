package com.hulianhujia.spellway.customViews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by FHP on 2017/8/23.
 */

public class CustomSign extends LinearLayout {
    public CustomSign(Context context) {
        this(context,null);
    }

    public CustomSign(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomSign(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }

}
