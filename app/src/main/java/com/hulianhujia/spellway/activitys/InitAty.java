package com.hulianhujia.spellway.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.MainActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.GuideMainAty;
import com.hulianhujia.spellway.event.SaveSuccessEvent;
import com.hulianhujia.spellway.event.SaveSuccessEvent2;
import com.hulianhujia.spellway.javaBeans.FinishInitEvent;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class InitAty extends BaseActivity {
    @Bind(R.id.btLogin)
    TextView btLogin;
    @Bind(R.id.btRegis)
    TextView btRegis;
    @Override
    public int getContentId() {
        return R.layout.activity_init_aty;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                !=PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                !=PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
            }, 0);
        }else{

        }
    }
    @OnClick({R.id.btLogin, R.id.btRegis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                startActivity(new Intent(this, LoginAty.class));
                break;
            case R.id.btRegis:
                startActivity(new Intent(this, NewRegisAty.class));
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handFinish(FinishInitEvent event){
        finish();
    }
}
