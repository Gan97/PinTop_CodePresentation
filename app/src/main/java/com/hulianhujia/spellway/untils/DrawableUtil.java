package com.hulianhujia.spellway.untils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class DrawableUtil {

    //设置textView里Drawable大小(Left)
    public static void setDrawable(TextView tv, int resId, int d, int m) {
        Drawable drawable = ContextCompat.getDrawable(AppManager.appContext(), resId);
        drawable.setBounds(0, 0, d, m);
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setRightDrawale(TextView tv, int resId, int d, int m) {
        Drawable drawable = ContextCompat.getDrawable(AppManager.appContext(), resId);
        drawable.setBounds(0,0,d,m);
        tv.setCompoundDrawables(null,null,drawable,null);
    }
}
