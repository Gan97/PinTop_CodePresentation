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
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.MyInvitationAdp;
import com.hulianhujia.spellway.javaBeans.MyInviteEvent;
import com.hulianhujia.spellway.untils.MyUtils;
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

public class MyInvitationAty extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.divider)
    TextView divider;
    @Bind(R.id.listView)
    ListView listView;
    private String TAG = "info";
    private MyInvitationAdp adp;
    private int page=1;
    private List<MyInviteEvent.ReturnArrBean> datas = new ArrayList<>();
    @Override
    public void initView() {
        adp = new MyInvitationAdp(this, datas);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.MYINVITE)
                .params("username", Contents.USER.getUsername())
                .params("p",page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 获得邀请");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获得邀请" + s);
                        Gson g = new Gson();
                        MyInviteEvent event = g.fromJson(s, MyInviteEvent.class);
                        if (event.getCode()==1){
                            datas.clear();
                            datas.addAll(event.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 邀请错误" + throwable.toString());
                    }
                });
    }

    @Override
    public int getContentId() {
        return R.layout.activity_my_invitation_aty;
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent  = new Intent(this,OtherUserInfoAty.class);
        intent.putExtra("userName",datas.get(i).getUsername());
        intent.putExtra("toNick",datas.get(i).getNickname());
        startActivity(intent);
        finish();
    }
}
