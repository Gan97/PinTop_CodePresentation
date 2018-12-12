package com.hulianhujia.spellway.activitys;

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
import com.hulianhujia.spellway.adpaters.ChooseRedAdp;
import com.hulianhujia.spellway.event.ChooseRedEvent;
import com.hulianhujia.spellway.event.RedBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class ChooseRedAty extends BaseActivity  {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.btCommit)
    TextView btCommit;

    private ChooseRedAdp adp ;
    private List<RedBean.ReturnArrBean> datas=new ArrayList<>();
    private String TAG="info";

    @Override
    public int getContentId() {
        return R.layout.activity_choose_red_aty;
    }

    @Override
    public void initView() {
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_RED)
                .params("username", Contents.USER.getUsername())
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
                            adp=new ChooseRedAdp(ChooseRedAty.this,datas);
                            listView.setAdapter(adp);
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
    @OnClick({R.id.btExit, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btCommit:
                EventBus.getDefault().post(new ChooseRedEvent(ChooseRedAdp.checkedRedId,ChooseRedAdp.amont));
                finish();
                break;
        }
    }
}
