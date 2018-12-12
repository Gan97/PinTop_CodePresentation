package com.hulianhujia.spellway.activitys;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.MainActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.GuideMainAty;
import com.hulianhujia.spellway.event.SaveSuccessEvent2;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.HXPWDBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.NetWorkUtils;
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

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
public class InitAty2 extends BaseActivity {
    @Bind(R.id.bgImg)
    ImageView bgImg;
    private String TAG="info";
    private SharedPreferences sp ;
    private String TAG2="infoo";
    @Override
    public int getContentId() {
        return R.layout.activity_init_aty2;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        sp=getSharedPreferences("login",MODE_PRIVATE);
        final boolean isKeep = sp.getBoolean("isKeep", false);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!isKeep){
                    Log.e(TAG, "run: 没保存" );
                    startActivity(new Intent(InitAty2.this,InitAty.class));
                    finish();
                }else {
                    Log.e(TAG, "run: 保存了");
                    Log.e(TAG2, "initView:再次登录 " );
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            boolean networkConnected = NetWorkUtils.isNetworkConnected(InitAty2.this);
                            if (!networkConnected){
                                ToastUtils.toast("当前没有网络");
                                startActivity(new Intent(InitAty2.this,InitAty.class));
                                finish();
                            }else {
                                OkGo.post(Urls.PUBLIC_URL + Urls.LOGIN)
                                        .params("username", sp.getString("username",null))
                                        .params("password", sp.getString("pwd",null))
                                        .params("type",sp.getInt("flag",1))
                                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                                        .doOnSubscribe(new Action0() {
                                            @Override
                                            public void call() {
                                                loadingDialog.show();
                                            }
                                        }).observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<String>() {
                                            @Override
                                            public void call(String s) {
                                                loadingDialog.dismiss();
                                                Log.e(TAG, "call: 成功" + s);
                                                Gson g = new Gson();
                                                LoginBean bea = g.fromJson(s, LoginBean.class);
                                                ToastUtils.toast(bea.getMsg());
                                                if (bea.getCode()==1){
                                                    SharedUtils.saveObject2(getApplicationContext(),bea.getReturnArr(),bea.getReturnArr().getType());
                                                    OkGo.post(Urls.PUBLIC_URL+Urls.GET_HXPWD)
                                                            .params("username",sp.getString("username",null))
                                                            .getCall(StringConvert.create(),RxAdapter.<String>create())
                                                            .doOnSubscribe(new Action0() {
                                                                @Override
                                                                public void call() {
                                                                    Log.e(TAG, "call:开始获取环信密码" );
                                                                }
                                                            }).observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Action1<String>() {
                                                                @Override
                                                                public void call(String s) {
                                                                    Log.e(TAG, "call: 获得" + s);
                                                                    Gson g = new Gson();
                                                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                                                    if (baseBean.getCode()==1){
                                                                        HXPWDBean.ReturnArrBean returnArrBean = g.fromJson(g.toJson(baseBean.getReturnArr()), HXPWDBean.ReturnArrBean.class);
                                                                        Log.e(TAG, "call: 登陆环信"+returnArrBean.getUsername()+"====="+returnArrBean.getHuanxin_password() );
                                                                        EMClient.getInstance().login(returnArrBean.getUsername(), returnArrBean.getHuanxin_password(), new EMCallBack() {
                                                                            @Override
                                                                            public void onSuccess() {
                                                                                Log.e(TAG, "onSuccess: 登陆陈宫拉" );
                                                                                EMClient.getInstance().groupManager().loadAllGroups();
                                                                                EMClient.getInstance().chatManager().loadAllConversations();
                                                                            }
                                                                            @Override
                                                                            public void onError(int code, String error) {
                                                                                Log.e(TAG, "onError: 登陆错误code="+code+"错误信息"+error );
                                                                            }
                                                                            @Override
                                                                            public void onProgress(int progress, String status) {
                                                                            }
                                                                        });
                                                                    }else {
                                                                        EMClient.getInstance().login(sp.getString("username",null), sp.getString("pwd",null), new EMCallBack() {
                                                                            @Override
                                                                            public void onSuccess() {
                                                                                Log.e(TAG, "onSuccess: 登陆陈宫拉" );
                                                                                EMClient.getInstance().groupManager().loadAllGroups();
                                                                                EMClient.getInstance().chatManager().loadAllConversations();
                                                                            }
                                                                            @Override
                                                                            public void onError(int code, String error) {
                                                                                Log.e(TAG, "onError: 登陆错误code="+code+"错误信息"+error );
                                                                            }
                                                                            @Override
                                                                            public void onProgress(int progress, String status) {

                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }, new Action1<Throwable>() {
                                                                @Override
                                                                public void call(Throwable throwable) {
                                                                    Log.e(TAG, "call: 环信密码错误" );
                                                                }
                                                            });
                                                    Log.e(TAG, "call: 再次登录"+sp.getString("username",null)+sp.getString("pwd",null) );
                                                }
                                            }
                                        }, new Action1<Throwable>() {
                                            @Override
                                            public void call(Throwable throwable) {
                                                Log.e(TAG, "call: 失败"+throwable.toString() );
                                            }
                                        });
                            }

                        }
                    });
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,3000);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void saveSuccess(SaveSuccessEvent2 event){
        if (event.getType().equals("1")){
            Log.e(TAG, "saveSuccess: 进入init" );
            Contents.LOGIN_TYPE=1;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else if (event.getType().equals("2")){
            Log.e(TAG, "saveSuccess: 进入init" );
            Contents.LOGIN_TYPE=2;
            startActivity(new Intent(this, GuideMainAty.class));
            finish();
        }
    }
}
