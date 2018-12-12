package com.hulianhujia.spellway.untils;

import android.content.Context;

import com.hulianhujia.spellway.customViews.LoadingDialog;

/**
 * Created by FHP on 2017/7/24.
 */

public class LoadingUtil {
    public static LoadingDialog loadingDialog;
    public static void initLoading(Context context){
        loadingDialog=new LoadingDialog(context);
    }
    public static void showLoading(){
        loadingDialog.show();
    }
    public static void hindLoading(){
        loadingDialog.hide();
    }
}
