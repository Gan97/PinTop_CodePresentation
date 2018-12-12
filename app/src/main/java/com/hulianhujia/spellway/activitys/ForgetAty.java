package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.SmsBean;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okrx.RxAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cookie;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class ForgetAty extends BaseActivity {
    @Bind(R.id.lobg)
    ImageView lobg;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etPwdAg)
    EditText etPwdAg;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btGetYzm)
    TextView btGetYzm;
    @Bind(R.id.btLogin)
    TextView btLogin;
    @Bind(R.id.viewPager)
    RelativeLayout viewPager;
    private int time=60;
    private String TAG="info";
    private String smsCode;
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
    @Override
    public int getContentId() {
        return R.layout.activity_forget_aty;
    }


    @Override
    public void initView() {
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                char[] chars = str.toCharArray();
                for (char c :chars){
                    boolean digit = Character.isDigit(c);
                    if (!digit){
                        etPwd.setError(null);
                        return;
                    }else {
                        etPwd.setError("密码不能全为数字");
                    }
                }
            }
        });
        etPwdAg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.equals(etPwd.getText().toString())){
                    etPwdAg.setError("两次输入的密码不一致");
                }else {
                    etPwdAg.setError(null);
                }
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (s1.length()!=11){
                    etPhone.setError("手机号码格式不正确");
                }else {
                    etPhone.setError(null);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }
    @OnClick({R.id.btGetYzm, R.id.btLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                                ToastUtils.toast(smsBean.getMsg());
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
                Log.e(TAG, "onViewClicked: 点击1" );
                if (etPhone.getText().toString().length()==0||etPwd.getText().toString().length()==0
                        ||etPwdAg.getText().toString().length()==0||etYzm.getText().toString().length()==0){
                    ToastUtils.toast("请完整填写内容");
                }else {
                    if (etPhone.getError()!=null&&etPwdAg.getError()!=null&&etPwd.getError()!=null){

                    }else {
                        if (!etPwdAg.getText().toString().equals(etPwd.getText().toString())){
                            ToastUtils.toast("两次输入的密码不一致，请重新输入"+etPhone.getError());
                        }else {
                            Log.e(TAG, "onViewClicked: 点击2" );
                            OkGo.post(Urls.PUBLIC_URL+Urls.RESET_PWD)
                                    .params("username",etPhone.getText().toString())
                                    .params("newpassword",etPwd.getText().toString())
                                    .params("code",etYzm.getText().toString())
                                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            loadingDialog.show();
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            Log.e(TAG, "call: 重置获得");
                                            loadingDialog.dismiss();
                                            Gson g = new Gson();
                                            BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                            ToastUtils.toast(baseBean.getMsg());
                                            if (baseBean.getCode() == 1) {
                                                finish();
                                            }
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            loadingDialog.dismiss();
                                            Log.e(TAG, "call: 重置错误"+throwable.toString());
                                        }
                                    });
                        }
                    }

                }
                break;
        }
    }
}
