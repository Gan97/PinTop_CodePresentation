package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.AccountDetailAty;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuideWalletAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.btCash)
    RelativeLayout btCash;
    @Bind(R.id.btDetail)
    TextView btDetail;
    @Bind(R.id.money)
    TextView money;
    @Override
    public int getContentId() {
        return R.layout.activity_guide_wallet_aty;
    }

    @Override
    public void initView() {
        money.setText(Contents.GUIDE.getAmount());
    }
    @Override
    protected void onResume() {
        super.onResume();
        OkGo.post(Urls.PUBLIC_URL+Urls.INIT)
                .params("username",Contents.GUIDE.getUsername())
                .params("myusername",Contents.GUIDE.getUsername())
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
                        Gson g = new Gson();
                        UserInfoBean bean = g.fromJson(s, UserInfoBean.class);
                        if (bean.getCode() == 1) {
                            Contents.USER.setAmount(bean.getReturnArr().getAmount());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                    }
                });
        money.setText(Contents.GUIDE.getAmount());
    }


    @OnClick({R.id.btExit, R.id.btCash,R.id.btDetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btCash:
                startActivity(new Intent(this,GetCashAty.class));
                break;
            case R.id.btDetail:
                Intent i  = new Intent(this,AccountDetailAty.class);
                i.putExtra("type",2);
                startActivity(i);
                break;
        }
    }
}
