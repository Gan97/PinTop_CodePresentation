package com.hulianhujia.spellway.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.RegisterBean;
import com.hulianhujia.spellway.javaBeans.SmsBean;
import com.hulianhujia.spellway.untils.CodeUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017\6\21 0021.
 */

public class UserFragment extends Fragment {
    @Bind(R.id.dv1)
    TextView dv1;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btSend)
    TextView btSend;
    @Bind(R.id.etJym)
    EditText etJym;
    @Bind(R.id.btCommit)
    TextView btCommit;
    @Bind(R.id.avi)
    AVLoadingIndicatorView avi;
    @Bind(R.id.imgYz)
    ImageView imgYz;
    private String TAG = "info";
    private CodeUtils codeUtils;
    private LoadingDialog loadingDialog;
    private int time=60;
    private String smsCode;
    private Handler handler =new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            time--;
            if (time<=0){
                time=60;
                btSend.setClickable(true);
                btSend.setText("获取验证码");
            }else {
                btSend.setClickable(false);
                btSend.setText(time+"s");
                handler.sendEmptyMessageDelayed(1,1000);
            }
            return false;
        }
    });
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.regiter_user, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        loadingDialog=new LoadingDialog(getContext());
        initCodeUtils();
        initYzm();
    }

    private void initYzm() {
        EventHandler eh = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Log.e(TAG, "afterEvent:进入回调 " );
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Log.e(TAG, "afterEvent:进入回调完成" );
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.e(TAG, "提交验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.e(TAG, "发送成功，请查收");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        ArrayList<HashMap<String, Object>> hashMaps = (ArrayList<HashMap<String, Object>>) data;
                        for (HashMap<String, Object> each : hashMaps) {
                            Log.d("TAG", each.toString());
                        }
                    }
                } else {
                    final Throwable throwable = (Throwable) data;
                    Log.e(TAG, event + "sdsdsd" + throwable.toString());
                    ToastUtils.toast(throwable.toString());
                }
            }
        };
        //注册短信验证的监听
        SMSSDK.registerEventHandler(eh);
    }

    private void regis() {
        OkGo.post(Urls.PUBLIC_URL + Urls.REGISER)
                .params("username", etPhone.getText().toString())
                .params("password", etPwd.getText().toString())
                .params("type", "1")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: ");
                        avi.setVisibility(View.VISIBLE);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        avi.setVisibility(View.GONE);
                        Log.e(TAG, "call: 成功" + s);
                        Gson g = new Gson();
                        RegisterBean bean = g.fromJson(s, RegisterBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            getActivity().finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        avi.setVisibility(View.GONE);
                        Log.e(TAG, "call: 失败" + throwable.toString());
                    }
                });
    }

    private void initCodeUtils() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();

        imgYz.setImageBitmap(bitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.etYzm, R.id.btSend,  R.id.btCommit,R.id.imgYz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dv1:
                break;
            case R.id.btSend:
                OkGo.post(Urls.PUBLIC_URL+Urls.SMS_URL)
                        .params("phone",etPhone.getText().toString())
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
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
            case R.id.etJym:
                break;
            case R.id.btCommit:
                if (etPhone.getText().toString().length()!=0&&etPwd.getText().toString().length()!=0
                        &&etYzm.getText().length()!=0&&etJym.getText().toString().length()!=0){
                    if (etYzm.getText().toString().equalsIgnoreCase(codeUtils.getCode())){
                        if (smsCode.equals(etYzm.getText().toString())){
                            regis();
                        }else {
                            ToastUtils.toast("短信验证码错误");
                        }
                    }else {
                        ToastUtils.toast("图片验证码错误");
                    }
                }else {
                    ToastUtils.toast("请输入全部内容");
                }
                break;
            case R.id.imgYz:
                codeUtils.createCode();
                Bitmap bitmap = codeUtils.createBitmap();
                imgYz.setImageBitmap(bitmap);
                break;

        }
    }
}
