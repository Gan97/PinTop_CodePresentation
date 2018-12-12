package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.GradeGuideBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
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

public class GradeAty extends BaseActivity implements RatingBar.OnRatingBarChangeListener, SimpleRatingBar.OnRatingBarChangeListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.userIcon)
    CircleImageView userIcon;
    @Bind(R.id.dv)
    TextView dv;
    @Bind(R.id.ratingBar)
    SimpleRatingBar ratingBar;
    @Bind(R.id.lo1)
    LinearLayout lo1;
    @Bind(R.id.lvl)
    TextView lvl;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.lo)
    RelativeLayout lo;
    @Bind(R.id.btCommit)
    TextView btCommit;
    private LoadingDialog dialog;
    private String guideName;
    private UserInfoBean userInfoBean;
    private String TAG = "info";
    private String odId;
    @Override
    public int getContentId() {
        return R.layout.activity_grade_aty;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void initView() {
        Intent intent = getIntent();
        odId = intent.getStringExtra("odId");
        dialog = new LoadingDialog(this);
        userInfoBean = (UserInfoBean) SharedUtils.readUserInfo(this);
        ratingBar.setOnRatingBarChangeListener(this);
        guideName = getIntent().getStringExtra("guidename");
    }
    @OnClick({R.id.btExit, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btCommit:
                if (etContent.getText().toString().length() != 0) {
                    OkGo.post(Urls.PUBLIC_URL + Urls.GRADE_GUIDE)
                            .params("guidename", guideName)
                            .params("username", userInfoBean.getReturnArr().getUsername())
                            .params("content", etContent.getText().toString())
                            .params("star", ratingBar.getRating())
                            .params("order_id", odId)
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    dialog.show();
                                    Log.e(TAG, "call: 评价道友开始" + odId);
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    dialog.dismiss();
                                    Log.e(TAG, "call:评价道友获得" + s);
                                    Gson g = new Gson();
                                    GradeGuideBean guideBean = g.fromJson(s, GradeGuideBean.class);
                                    if (guideBean.getCode() == 1) {
                                        etContent.setText("");
                                        ToastUtils.toast("评论成功");
                                        OverOrderAty.instance.finish();
                                        finish();
                                    } else {
                                        ToastUtils.toast("错误");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    dialog.dismiss();
                                    Log.e(TAG, "call: 评价道友错误" + throwable.toString());
                                }
                            });
                }else {
                    ToastUtils.toast("请输入内容");
                }
                break;
        }
    }
    @Override
    public void onRatingChanged(RatingBar ratingBar, final float rating, boolean fromUser) {

    }
    @Override
    public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
        if (rating >= 4.0) {
            Log.e(TAG, "onRatingChanged: 0000" + rating + (rating > 1));
            lvl.setText("满意");
        } else if (rating >= 2.0 && rating < 4.0) {
            lvl.setText("一般");
        } else if (rating < 2.0) {
            lvl.setText("差评");
        }
    }
}
