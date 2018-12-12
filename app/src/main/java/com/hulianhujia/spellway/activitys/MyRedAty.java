package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.MyRedListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.RedBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
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

public class MyRedAty extends BaseActivity {


    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.listView)
    ListView listView;
    private LoadingDialog loadingDialog;
    private UserInfoBean user;
    private String TAG = "info";
    private MyRedListViewAdp adp;
    private List<RedBean.ReturnArrBean> datas=new ArrayList<>();
    @Override
    public int getContentId() {
        return R.layout.activity_my_red_aty;
    }
    @Override
    public void initView() {
        loadingDialog = new LoadingDialog(this);
        user = (UserInfoBean) SharedUtils.readUserInfo(this);
        adp= new MyRedListViewAdp(datas, this);
        listView.setAdapter(adp);
    }
    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_RED)
                .params("username", user.getReturnArr().getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call:获取红包列表开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取红包列表获得" + s);
                        Gson g =new Gson();
                        RedBean bean = g.fromJson(s, RedBean.class);
                        if (bean.getCode()==1){
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取红包列表错误" + throwable.toString());
                    }
                });
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }
}
