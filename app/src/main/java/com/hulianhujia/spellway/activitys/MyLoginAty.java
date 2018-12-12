package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.MainActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.GuiderAty;
import com.hulianhujia.spellway.event.SaveSuccessEvent;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class MyLoginAty extends BaseActivity {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btCommit)
    TextView btCommit;
    @Bind(R.id.avi)
    AVLoadingIndicatorView avi;
    private String TAG="info";

    @Override
    public int getContentId() {
        return R.layout.activity_my_login_aty;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        etPhone.setText("17781481226");
        etPwd.setText("5135156");
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }
    @OnClick({R.id.btExit, R.id.etPhone, R.id.etPwd, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btCommit:
                OkGo.post(Urls.PUBLIC_URL + Urls.LOGIN)
                        .params("username", etPhone.getText().toString())
                        .params("password", etPwd.getText().toString())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                avi.setVisibility(View.VISIBLE);
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                avi.setVisibility(View.GONE);
                                Log.e(TAG, "call: 成功" + s);
                                Gson g = new Gson();
                                LoginBean bea = g.fromJson(s, LoginBean.class);
                                ToastUtils.toast(bea.getMsg());
                                if (bea.getCode()==1){
                                    SharedUtils.saveObject(getApplicationContext(),bea.getReturnArr(),bea.getReturnArr().getType());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call: 失败"+throwable.toString() );
                            }
                        });
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void saveSuccess(SaveSuccessEvent event){
        if (event.getType().equals("1")){
            startActivity(new Intent(this, MainActivity.class));
        }else if (event.getType().equals("2")){
            startActivity(new Intent(this, GuiderAty.class));
        }
    }
}
