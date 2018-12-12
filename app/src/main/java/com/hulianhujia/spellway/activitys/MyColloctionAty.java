package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.ColloctionListViewAdp;
import com.hulianhujia.spellway.javaBeans.ColloctionBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
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

public class MyColloctionAty extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.divider)
    TextView divider;
    @Bind(R.id.listView)
    ListView listView;
    private int page = 1;
    private LoginBean.UserInfoBean userInfo;
    private String TAG = "info";
    private List<ColloctionBean.ReturnArrBean> datas = new ArrayList<>();
    private ColloctionListViewAdp adp;
    @Override
    public int getContentId() {
        return R.layout.activity_my_colloction_aty;
    }
    @Override
    public void initView() {
        getUser();
        adp = new ColloctionListViewAdp(datas, this);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
        OkGo.post(Urls.PUBLIC_URL + Urls.CLOLOCTION)
                .params("username", userInfo.getUsername())
                .params("p", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获取收藏开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 收藏返回" + s);
                        Gson g = new Gson();
                        ColloctionBean bean = g.fromJson(s, ColloctionBean.class);
                        if (bean.getCode() == 1) {
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call:收藏获取失败 " + throwable.toString());
                    }
                });
    }
    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getApplicationContext());
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent  = new Intent(this,DiaryAty.class);
        intent.putExtra("nid",datas.get(i).getNews_id());
        startActivity(intent);
    }
}
