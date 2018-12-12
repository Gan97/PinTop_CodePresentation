package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.MoneyLeftBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuiderClientAty extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.divider)
    TextView divider;
    @Bind(R.id.listView)
    ListView listView;
    private UserInfoBean userInfoBean;
    private LoadingDialog loadingDialog;
    private String TAG = "info";
    private TextView btGetCash;
    @Override
    public int getContentId() {
        return R.layout.activity_guider_client_aty;
    }

    @Override
    public void initView() {
        loadingDialog = new LoadingDialog(this);
        userInfoBean = (UserInfoBean) SharedUtils.readUserInfo(this);
        View view = LayoutInflater.from(this).inflate(R.layout.money_left_head, null);
        btGetCash = ((TextView) view.findViewById(R.id.btGetCash));
        btGetCash.setOnClickListener(this);
        listView.addHeaderView(view);
        Intent i = new Intent(this, GetCashAty.class);
        startActivity(i);
    }

    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.MONEY_LEFT)
                .params("guidename", userInfoBean.getReturnArr().getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call:获取余额开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取余额获得" + s);
                        Gson g = new Gson();
                        MoneyLeftBean moneyLeftBean = g.fromJson(s, MoneyLeftBean.class);
                        if (moneyLeftBean.getCode() == 1) {

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取余额失败" + throwable.toString());
                    }
                });

    }
    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GetCashAty.class);
        startActivity(i);
    }
}
