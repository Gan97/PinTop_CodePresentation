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
import com.hulianhujia.spellway.adpaters.MyFollowListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.GetFocusBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class MyFollowAty extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.divider)
    TextView divider;
    @Bind(R.id.listView)
    ListView listView;
    private MyFollowListViewAdp adp;
    private UserInfoBean userInfoBean;
    private LoadingDialog dialog;
    private String TAG = "info";
    private List<GetFocusBean.ReturnArrBean> datas = new ArrayList<>();
    @Override
    public int getContentId() {
        return R.layout.activity_my_follow_aty;
    }
    @Override
    public void initView() {
        userInfoBean = (UserInfoBean) SharedUtils.readUserInfo(this);
        dialog = new LoadingDialog(this);
        adp = new MyFollowListViewAdp(this, datas);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
        btExit.setOnClickListener(this);
    }
    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_FOCUS)
                .params("user_id", userInfoBean.getReturnArr().getUser_id())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 获取关注开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call:获取关注返回 " + s);
                        Gson g = new Gson();
                        GetFocusBean bean = g.fromJson(s, GetFocusBean.class);
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
                        Log.e(TAG, "call: 获取关注错误" + throwable.toString());
                    }
                });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (datas.get(position).getType()){
            case "1":
                Intent intent = new Intent(this, OtherUserInfoAty.class);
                intent.putExtra("userName", datas.get(position).getUsername());
                startActivity(intent);
                break;
            case "2":
                Intent intent1 = new Intent(this,GuiderDetailActivity.class);
                intent1.putExtra("guiderName",datas.get(position).getUsername());
                intent1.putExtra("dis","<15");
                startActivity(intent1);
                break;
        }

    }
    @Override
    public void onClick(View v) {
        finish();
    }

}
