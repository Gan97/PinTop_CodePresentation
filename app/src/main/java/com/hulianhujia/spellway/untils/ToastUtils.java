package com.hulianhujia.spellway.untils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by FHP on 2017/7/7.
 */

public class ToastUtils {
    private static Context context;

    public static void initToastUtils(Context cont){
        context=cont;
    }
    public static void toast(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
