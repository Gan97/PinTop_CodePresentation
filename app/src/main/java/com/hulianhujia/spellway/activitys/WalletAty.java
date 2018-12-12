package com.hulianhujia.spellway.activitys;

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
import com.hulianhujia.spellway.MainActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class WalletAty extends BaseActivity {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.btCharge)
    RelativeLayout btCharge;
    @Bind(R.id.btCash)
    RelativeLayout btCash;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.btDetail)
    TextView btDetail;
    private String TAG="info";

    @Override
    public void initView() {
        money.setText(Contents.USER.getAmount());
    }
    @Override
    protected void onResume() {
        super.onResume();
        OkGo.post(Urls.PUBLIC_URL+Urls.INIT)
                .params("username",Contents.USER.getUsername())
                .params("myusername",Contents.USER.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始刷新钱包" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 刷新钱包获得" + s);
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
                        Log.e(TAG, "call: 错误"+throwable.toString() );
                    }
                });
        money.setText(Contents.USER.getAmount());
    }
    @Override
    public int getContentId() {
        return R.layout.activity_wallet_aty;
    }


    @OnClick({R.id.btExit, R.id.btCharge, R.id.btCash,R.id.btDetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btCharge:
                startActivity(new Intent(this, ChargeAty.class));
                break;
            case R.id.btCash:
                startActivity(new Intent(this, GetMoneyAty.class));
                break;
            case R.id.btDetail:
                Intent i  = new Intent(this,AccountDetailAty.class);
                i.putExtra("type",1);
                startActivity(i);
                break;
        }
    }

}
