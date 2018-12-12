package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.SelfDiaryListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.HomeBean;
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
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class TravelDiaryAty extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.btExit)
    ImageView btExit;
    private SelfDiaryListViewAdp adp;
    private List<HomeBean.ReturnArrBean> datas= new ArrayList<>();
    private LoginBean.UserInfoBean userInfo;
    private String TAG="info";
    private LoadingDialog dialog;
    private String userName;
    @Override
    public int getContentId() {
        return R.layout.activity_travel_diary_aty;
    }
    @Override
    public void initView() {
        Intent intent = getIntent();
        userName=intent.getStringExtra("username");
        getUser();
        dialog=new LoadingDialog(this);
        adp = new SelfDiaryListViewAdp(datas, this);
        listView.setAdapter(adp);
        View head = LayoutInflater.from(this).inflate(R.layout.usernews_head, null);
        CircleImageView userIcon = (CircleImageView) head.findViewById(R.id.userIcon);
        ImageView sexImg = (ImageView) head.findViewById(R.id.sex);
        TextView name = (TextView) head.findViewById(R.id.userName);

        Glide.with(context).load(Contents.USER.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(userIcon);
        switch (Contents.USER.getSex()){
            case "男":
                sexImg.setImageResource(R.mipmap.male);
                break;
            case "女":
                sexImg.setImageResource(R.mipmap.female);
                break;
        }
        name.setText(Contents.USER.getNickname());
        listView.addHeaderView(head);
        listView.setOnItemClickListener(this);
        OkGo.post(Urls.PUBLIC_URL+Urls.GET_DIARY)
                .params("username",userName)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获取我的日记列表开始" );
                        dialog.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 获取我的日记列表获得" + s);
                        Gson g = new Gson();
                        HomeBean bean = g.fromJson(s, HomeBean.class);
                        if (bean.getCode()==1){
                            datas.clear();
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 获取我的日记列表错误"+throwable.toString() );
                    }
                });
    }

    private void getUser() {
        userInfo= (LoginBean.UserInfoBean) SharedUtils.readObject(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            Intent intent = new Intent(this, DiaryAty.class);
            String news_id = datas.get(position-1).getNews_id();
            Log.e(TAG, "onItemClick: "+news_id );
            intent.putExtra("nid", news_id);
            startActivity(intent);
        }

    }


    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }
}
