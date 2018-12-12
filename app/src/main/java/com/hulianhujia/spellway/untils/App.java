package com.hulianhujia.spellway.untils;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.initToastUtils(this);
        OkGo.init(this);
        AppManager.getInstance().init(this);
    }
}
