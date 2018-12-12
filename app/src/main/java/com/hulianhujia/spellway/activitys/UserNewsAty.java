package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.MyNewsLIstViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.CommunityListBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
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

public class UserNewsAty extends BaseActivity implements AdapterView.OnItemClickListener {


    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.listView)
    ListView listView;
    private LoadingDialog dialog;
    private LoginBean.UserInfoBean userInfo;
    private int page = 1;
    private String TAG = "info";
    private MyNewsLIstViewAdp adp;
    private List<CommunityListBean.ReturnArrBean> datas = new ArrayList<>();
    @Override
    public int getContentId() {
        return R.layout.activity_my_news_aty;
    }


    @Override
    public void initView() {
        dialog = new LoadingDialog(this);
        getUser();
        adp = new MyNewsLIstViewAdp(datas, this);
        int type = getIntent().getIntExtra("type", 0);
        View head = LayoutInflater.from(this).inflate(R.layout.usernews_head, null);
        ImageView icon = (ImageView) head.findViewById(R.id.userIcon);
        ImageView sex = (ImageView) head.findViewById(R.id.sex);
        TextView name = (TextView) head.findViewById(R.id.userName);
        switch (type){
            case 1:
                OkGo.post(Urls.PUBLIC_URL + Urls.MY_COMMUNITY)
                        .params("username", userInfo.getUsername())
                        .params("page", page)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                dialog.show();
                                Log.e(TAG, "call:获取我的动态开始 ");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                dialog.dismiss();
                                Log.e(TAG, "call:获取我的动态获得" + s);
                                Gson g = new Gson();
                                CommunityListBean bean = g.fromJson(s, CommunityListBean.class);
                                if (bean.getCode() == 1) {
                                    datas.clear();
                                    datas.addAll(bean.getReturnArr());
                                    adp.notifyDataSetChanged();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                dialog.dismiss();
                                Log.e(TAG, "call: 获取我的动态错误" + throwable.toString());
                            }
                        });

                Glide.with(this).load(Contents.USER.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(icon);
                switch (Contents.USER.getSex()){
                    case "男":
                        Glide.with(this).load(R.mipmap.male).asBitmap().into(sex);
                        break;
                    case "女":
                        Glide.with(this).load(R.mipmap.female).asBitmap().into(sex);
                        break;
                }
                name.setText(Contents.USER.getNickname());
                break;
            case 2:
                OkGo.post(Urls.PUBLIC_URL + Urls.MY_COMMUNITY)
                        .params("username", getIntent().getStringExtra("userName"))
                        .params("page", page)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                dialog.show();
                                Log.e(TAG, "call:获取我的动态开始 ");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                dialog.dismiss();
                                Log.e(TAG, "call:获取我的动态获得" + s);
                                Gson g = new Gson();
                                CommunityListBean bean = g.fromJson(s, CommunityListBean.class);
                                if (bean.getCode() == 1) {
                                    datas.clear();
                                    datas.addAll(bean.getReturnArr());
                                    adp.notifyDataSetChanged();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                dialog.dismiss();
                                Log.e(TAG, "call: 获取我的动态错误" + throwable.toString());
                            }
                        });
                break;
        }
        listView.addHeaderView(head);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
    }
    private void getUser() {
        try {
            userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(this);
        } catch (Exception e) {
            Log.e(TAG, "getUser: " + e.toString());
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!(position ==0)){
            Intent intent = new Intent(UserNewsAty.this, CommunityDetailAty.class);
            intent.putExtra("cid", datas.get(position - 1).getCmnt_id());
            intent.putExtra("uid", ((UserInfoBean) SharedUtils.readUserInfo(getApplicationContext())).getReturnArr().getUser_id());
            startActivity(intent);
        }
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }

}
