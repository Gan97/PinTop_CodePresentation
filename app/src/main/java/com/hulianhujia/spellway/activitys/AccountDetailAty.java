package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.AccountAdp;
import com.hulianhujia.spellway.javaBeans.AccountDetailBean;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class AccountDetailAty extends BaseActivity implements SpringView.OnFreshListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.spView)
    SpringView spView;
    private int page=1;
    private String TAG="info";
    private int type=1;
    private AccountAdp adp ;
    private List<AccountDetailBean.ReturnArrBean> datas= new ArrayList<>();
    @Override
    public int getContentId() {
        return R.layout.activity_account_detail_aty;
    }

    @Override
    public void initView() {
        type=getIntent().getIntExtra("type",1);
        adp=new AccountAdp(this,datas);
        listView.setAdapter(adp);
        spView.setHeader(new DefaultHeader(this));
        spView.setFooter(new DefaultFooter(this));
        spView.setListener(this);
    }
    @Override
    public void initData() {
        if (type==1){
            OkGo.post(Urls.PUBLIC_URL+Urls.DETAIL_ACCOUNT)
                    .params("username", Contents.USER.getUsername())
                    .params("p",page)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            loadingDialog.show();
                            Log.e(TAG, "call: 开始获得" );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 获得明细" + s);
                            Gson g = new Gson();
                            AccountDetailBean accountDetailBean = g.fromJson(s, AccountDetailBean.class);
                            if (accountDetailBean.getCode()==1){
                                datas.clear();
                                datas.addAll(accountDetailBean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                            ToastUtils.toast(accountDetailBean.getMsg());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 明细错误"+throwable.toString() );
                        }
                    });
        }else {
            OkGo.post(Urls.PUBLIC_URL+Urls.DETAIL_ACCOUNT)
                    .params("username", Contents.GUIDE.getUsername())
                    .params("p",page)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            loadingDialog.show();
                            Log.e(TAG, "call: 开始获得"+Contents.GUIDE.getUsername() );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 获得明细" + s);
                            Gson g = new Gson();
                            AccountDetailBean accountDetailBean = g.fromJson(s, AccountDetailBean.class);
                            if (accountDetailBean.getCode()==1){
                                datas.clear();
                                datas.addAll(accountDetailBean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 明细错误"+throwable.toString() );
                        }
                    });
        }


    }

    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onRefresh() {
        page=1;
        if (type==1){
            OkGo.post(Urls.PUBLIC_URL+Urls.DETAIL_ACCOUNT)
                    .params("username", Contents.USER.getUsername())
                    .params("p",page)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            loadingDialog.show();
                            Log.e(TAG, "call: 开始获得" );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            spView.onFinishFreshAndLoad();
                            Log.e(TAG, "call: 获得明细" + s);
                            Gson g = new Gson();
                            AccountDetailBean accountDetailBean = g.fromJson(s, AccountDetailBean.class);
                            if (accountDetailBean.getCode()==1){
                                datas.clear();
                                datas.addAll(accountDetailBean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                            ToastUtils.toast(accountDetailBean.getMsg());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            spView.onFinishFreshAndLoad();
                            Log.e(TAG, "call: 明细错误"+throwable.toString() );
                        }
                    });
        }else {
            OkGo.post(Urls.PUBLIC_URL+Urls.DETAIL_ACCOUNT)
                    .params("username", Contents.GUIDE.getUsername())
                    .params("p",page)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            loadingDialog.show();
                            Log.e(TAG, "call: 开始获得"+Contents.GUIDE.getUsername() );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 获得明细" + s);
                            Gson g = new Gson();
                            spView.onFinishFreshAndLoad();
                            AccountDetailBean accountDetailBean = g.fromJson(s, AccountDetailBean.class);
                            if (accountDetailBean.getCode()==1){
                                datas.clear();
                                datas.addAll(accountDetailBean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            spView.onFinishFreshAndLoad();
                            Log.e(TAG, "call: 明细错误"+throwable.toString() );
                        }
                    });
        }
    }

    @Override
    public void onLoadmore() {
        page++;
        if (type==1){
            OkGo.post(Urls.PUBLIC_URL+Urls.DETAIL_ACCOUNT)
                    .params("username", Contents.USER.getUsername())
                    .params("p",page)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            loadingDialog.show();
                            Log.e(TAG, "call: 开始获得" );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            spView.onFinishFreshAndLoad();
                            Log.e(TAG, "call: 获得明细" + s);
                            Gson g = new Gson();
                            AccountDetailBean accountDetailBean = g.fromJson(s, AccountDetailBean.class);
                            if (accountDetailBean.getCode()==1){
                                datas.addAll(accountDetailBean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                            ToastUtils.toast(accountDetailBean.getMsg());
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            spView.onFinishFreshAndLoad();
                            Log.e(TAG, "call: 明细错误"+throwable.toString() );
                        }
                    });
        }else {
            OkGo.post(Urls.PUBLIC_URL+Urls.DETAIL_ACCOUNT)
                    .params("username", Contents.GUIDE.getUsername())
                    .params("p",page)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            loadingDialog.show();
                            Log.e(TAG, "call: 开始获得"+Contents.GUIDE.getUsername() );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 获得明细" + s);
                            Gson g = new Gson();
                            spView.onFinishFreshAndLoad();
                            AccountDetailBean accountDetailBean = g.fromJson(s, AccountDetailBean.class);
                            if (accountDetailBean.getCode()==1){
                                datas.addAll(accountDetailBean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            spView.onFinishFreshAndLoad();
                            Log.e(TAG, "call: 明细错误"+throwable.toString() );
                        }
                    });
        }
    }
}
