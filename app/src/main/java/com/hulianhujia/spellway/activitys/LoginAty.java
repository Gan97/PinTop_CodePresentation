package com.hulianhujia.spellway.activitys;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.MainActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.GuideMainAty;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.SaveSuccessEvent;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.FinishInitEvent;
import com.hulianhujia.spellway.javaBeans.HXPWDBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.SmsBean;
import com.hulianhujia.spellway.untils.MyUtils;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
public class LoginAty extends BaseActivity {
    @Bind(R.id.lobg)
    ImageView lobg;
    @Bind(R.id.viewPager)
    RelativeLayout viewPager;
    @Bind(R.id.userSJ)
    ImageView userSJ;
    @Bind(R.id.guideSJ)
    ImageView guideSJ;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btGetYzm)
    TextView btGetYzm;
    @Bind(R.id.btLogin)
    TextView btLogin;
    @Bind(R.id.btForget)
    TextView btForget;
    @Bind(R.id.btUser)
    TextView btUser;
    @Bind(R.id.btGuider)
    TextView btGuider;
    private int loginFlag = 1;
    private LoadingDialog loadingDialog;
    private int time=60;
    private String smsCode;
    private SharedPreferences sp;
    private Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            time--;
            if (time<=0){
                time=60;
                btGetYzm.setClickable(true);
                btGetYzm.setText("获取验证码");
            }else {
                btGetYzm.setClickable(false);
                btGetYzm.setText(time+"s");
                handler.sendEmptyMessageDelayed(1,1000);
            }
            return false;
        }
    });
    private String TAG="info";
    @Override
    public int getContentId() {
        return R.layout.activity_login_aty;
    }
    @Override
    public void initView() {
        sp=getSharedPreferences("login",MODE_PRIVATE);
        EventBus.getDefault().register(this);
        loadingDialog=new LoadingDialog(this);



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @OnClick({R.id.btUser, R.id.btGuider, R.id.btGetYzm, R.id.btLogin, R.id.btForget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btUser:
                loginFlag=1;
/*                etPhone.setText("17781481226");
                etPwd.setText("5135156");*/
                userSJ.setVisibility(View.VISIBLE);
                guideSJ.setVisibility(View.GONE);
                break;
            case R.id.btGuider:
                loginFlag=2;
//                etPhone.setText("13028194826");
//                etPwd.setText("123456");
                userSJ.setVisibility(View.GONE);
                guideSJ.setVisibility(View.VISIBLE);
                break;
            case R.id.btGetYzm:
                OkGo.post(Urls.PUBLIC_URL+Urls.SMS_URL)
                        .params("phone",etPhone.getText().toString())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 短信验证码开始");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 短信验证码获得" + s);
                                Gson g = new Gson();
                                SmsBean smsBean = g.fromJson(s, SmsBean.class);
                                if (smsBean.getCode()==1){
                                    smsCode=smsBean.getReturnArr().getMsgCode();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 短信验证码错误"+throwable.toString() );
                            }
                        });
                handler.sendEmptyMessageDelayed(1,0);
                break;
            case R.id.btLogin:
                if (etPhone.getText().toString().length()==0||etPwd.getText().length()==0){
                    ToastUtils.toast("请填写用户名和密码");
                }else {
                    if (etYzm.getText().length()==0&&false){
                        ToastUtils.toast("验证码不能为空");
                    }else {
                        if (etYzm.getText().toString().equals(smsCode)||true){
                            OkGo.post(Urls.PUBLIC_URL + Urls.LOGIN)
                                    .params("username", etPhone.getText().toString())
                                    .params("password", etPwd.getText().toString())
                                    .params("type",loginFlag)
                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            Log.e(TAG, "call:登陆开始"+loginFlag );
                                            loadingDialog.show();
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            loadingDialog.dismiss();
                                            Log.e(TAG, "call: 成功" + s);
                                            Gson g = new Gson();
                                            BaseBean bea = g.fromJson(s, BaseBean.class);
                                            if (bea.getCode()==1){
                                                ToastUtils.toast(bea.getMsg());
                                                LoginBean.UserInfoBean userInfoBean = g.fromJson(g.toJson(bea.getReturnArr()), LoginBean.UserInfoBean.class);
                                                OkGo.post(Urls.PUBLIC_URL+Urls.GET_HXPWD)
                                                        .params("username",etPhone.getText().toString())
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
                                                                    EMClient.getInstance().login(etPhone.getText().toString(), etPwd.getText().toString(), new EMCallBack() {
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

                                                SharedPreferences.Editor edit = sp.edit();
                                                edit.putBoolean("isKeep",true);
                                                edit.putString("username",etPhone.getText().toString());
                                                edit.putString("pwd",etPwd.getText().toString());
                                                edit.putInt("flag",loginFlag);
                                                Log.e(TAG, "call: 存用户名密码"+etPhone.getText().toString()+etPwd.getText().toString() );
                                                edit.commit();
                                                SharedUtils.saveObject(getApplicationContext(),userInfoBean,userInfoBean.getType());
                                            }else {
                                                if (bea.getMsg().equals("请在普通用户端登录")){
                                                    ToastUtils.toast("请在游客端登录");
                                                }else {
                                                    ToastUtils.toast(bea.getMsg());
                                                }
                                            }
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            Log.e(TAG, "call: 失败"+throwable.toString() );
                                        }
                                    });
                            break;
                        }else {
                            ToastUtils.toast("验证码错误");
                        }
                    }
                }
                break;
            case R.id.btForget:
                startActivity(new Intent(this,ForgetAty.class));
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void saveSuccess(SaveSuccessEvent event){
        if (event.getType().equals("1")){
            Log.e(TAG, "saveSuccess: 一次" );
            Contents.LOGIN_TYPE=1;
            startActivity(new Intent(this, MainActivity.class));
            EventBus.getDefault().postSticky(new FinishInitEvent());
            finish();
        }else if (event.getType().equals("2")){
            Log.e(TAG, "saveSuccess: 一次" );
            Contents.LOGIN_TYPE=2;
            startActivity(new Intent(this, GuideMainAty.class));
            EventBus.getDefault().postSticky(new FinishInitEvent());
            finish();
        }
    }
}
