package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.AddFocusBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class OtherUserInfoAty extends BaseActivity {


    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvNum)
    TextView tvNum;
    @Bind(R.id.userIcon)
    CircleImageView userIcon;
    @Bind(R.id.lo)
    RelativeLayout lo;
    @Bind(R.id.userAge)
    TextView userAge;
    @Bind(R.id.userName)
    TextView name;
    @Bind(R.id.pay_attention_a)
    TextView payAttentionA;
    @Bind(R.id.init_to_travel)
    TextView initToTravel;
    @Bind(R.id.loEx)
    LinearLayout loEx;
    @Bind(R.id.loTitle)
    RelativeLayout loHead;
    @Bind(R.id.textView_useless_one)
    TextView textViewUselessOne;
    @Bind(R.id.comment_tv)
    TextView commentTv;
    @Bind(R.id.data_tv)
    TextView dataTv;
    @Bind(R.id.comment_run_use)
    LinearLayout commentRunUse;
    @Bind(R.id.relativeLayout_useless)
    RelativeLayout relativeLayoutUseless;
    @Bind(R.id.textView_useless)
    TextView textViewUseless;
    @Bind(R.id.height_use)
    TextView heightUse;
    @Bind(R.id.weight_use)
    TextView weightUse;
    @Bind(R.id.imageView2)
    ImageView imageView2;
    @Bind(R.id.mode_use)
    TextView modeUse;
    @Bind(R.id.location_use)
    TextView locationUse;
    @Bind(R.id.hobby_use)
    TextView hobbyUse;
    @Bind(R.id.imageView5)
    ImageView imageView5;
    @Bind(R.id.sign_use)
    TextView signUse;
    @Bind(R.id.last_ll)
    LinearLayout lastLl;
    @Bind(R.id.linear_useless)
    LinearLayout linearUseless;
    @Bind(R.id.sex)
    ImageView sex;
    private String userName;
    private LoadingDialog dialog;
    private String TAG = "info";
    private UserInfoBean userInfoBean;
    private String uid;

    @Override
    public int getContentId() {
        return R.layout.activity_other_user_info_aty;
    }

    @Override
    public void initView() {
        dialog = new LoadingDialog(this);
        final Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        userInfoBean = (UserInfoBean) SharedUtils.readUserInfo(this);
        /*判断是否查看自己的信息*/
        if (userName.equals(userInfoBean.getReturnArr().getUsername())) {
            loEx.setVisibility(View.GONE);
        } else {
            loEx.setVisibility(View.VISIBLE);
            payAttentionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkGo.post(Urls.PUBLIC_URL + Urls.ADD_FOCUS)
                            .params("user_id", userInfoBean.getReturnArr().getUser_id())
                            .params("focus_uid", uid)
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    dialog.show();
                                    Log.e(TAG, "call:添加关注开始 ");
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    dialog.dismiss();
                                    Log.e(TAG, "call: 添加关注获得" + s);
                                    Gson g = new Gson();
                                    AddFocusBean bean = g.fromJson(s, AddFocusBean.class);
                                    if (bean.getCode() == 1) {
                                        ToastUtils.toast(bean.getMsg());
                                        if (bean.getMsg().equals("关注成功")){
                                            payAttentionA.setText("已关注");
                                        }
                                        if (bean.getMsg().equals("取消关注成功")){
                                            payAttentionA.setText("关注");
                                        }
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    dialog.dismiss();
                                    Log.e(TAG, "call: 添加关注错误" + throwable.toString());
                                }
                            });
                }
            });
            /*会话*/
            initToTravel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OtherUserInfoAty.this, ChatAty.class);
                    intent.putExtra("toNick", getIntent().getStringExtra("toNick"));
                    intent.putExtra("userId", getIntent().getStringExtra("userName"));
                    intent.putExtra("chatType", Contents.CHATTYPE_SINGLE);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.INIT)
                .params("username", userName)
                .params("myusername", Contents.USER.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 别人信息开始" + userName);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 别人信息获得" + s);
                        Gson g = new Gson();
                        UserInfoBean bean = g.fromJson(s, UserInfoBean.class);
                        if (bean.getCode() == 1) {
                            UserInfoBean.ReturnArrBean info = bean.getReturnArr();
                            if (info.getIs_guanzhu() == 0) {
                                payAttentionA.setText("关注");
                            } else {
                                payAttentionA.setText("已关注");
                            }
                            if (info.getNickname() != null) {
                                name.setText(info.getNickname());
                            }
                            if (info.getAge() != null) {
                                userAge.setText(info.getAge() + "岁");
                            }
                            switch (info.getSex()){
                                case "男":
                                    sex.setImageResource(R.mipmap.male);
                                    break;

                                case "女":
                                    sex.setImageResource(R.mipmap.female);
                                    break;

                            }
                            uid = info.getUser_id();
                            if (info.getHeight() != null) {
                                heightUse.setText(info.getHeight() + "cm");
                            }
                            if (info.getWeight() != null) {
                                weightUse.setText(info.getWeight() + "kg");
                            }
                            if (info.getAddress() != null) {
                                locationUse.setText(info.getAddress());
                            }
                            if (info.getFocusNum() != null) {
                                tvNum.setText(info.getFocusNum());
                            }
                            if (info.getHoby() != null) {
                                hobbyUse.setText(info.getHoby());
                            }
                            if (info.getAutograph() != null) {
                                signUse.setText(info.getAutograph());
                            }
                            if (info.getPicture() != null && info.getPicture().length() != 0) {
                                Glide.with(context).load(info.getPicture()).asBitmap().into(userIcon);
                            } else {

                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 别人信息错误" + throwable.toString());
                    }
                });
    }

    @OnClick({R.id.btExit, R.id.pay_attention_a,
            R.id.comment_tv, R.id.data_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.pay_attention_a:
                OkGo.post(Urls.PUBLIC_URL + Urls.ADD_FOCUS)
                        .params("user_id", userInfoBean.getReturnArr().getUser_id())
                        .params("focus_uid", uid)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call:添加关注开始 ");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.e(TAG, "call:添加关注获得" + s);
                                Gson g = new Gson();
                                AddFocusBean bean = g.fromJson(s, AddFocusBean.class);
                                if (bean.getCode() == 1) {
                                    if (payAttentionA.getText().toString().equals("关注")) {
                                        payAttentionA.setText("已关注");
                                    } else {
                                        payAttentionA.setText("关注");
                                    }
                                }
                                ToastUtils.toast(bean.getMsg());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call: 添加关注错误" + throwable.toString());
                            }
                        });
                break;
            case R.id.comment_tv:
                Intent intent1 = new Intent(this, UserNewsAty.class);
                intent1.putExtra("type", 2);
                intent1.putExtra("userName", userName);
                startActivity(intent1);
                break;
            case R.id.data_tv:
                Intent intent = new Intent(OtherUserInfoAty.this, TravelDiaryAty.class);
                intent.putExtra("username", userName);
                startActivity(intent);
                break;
        }
    }

}
