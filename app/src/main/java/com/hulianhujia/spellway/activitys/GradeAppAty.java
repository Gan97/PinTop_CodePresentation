package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GradeAppAty extends BaseActivity implements SimpleRatingBar.OnRatingBarChangeListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.rating)
    SimpleRatingBar rating;
    @Bind(R.id.grade)
    TextView grade;
    @Bind(R.id.btCommit)
    TextView btCommit;
    private String TAG="info";

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        rating.setOnRatingBarChangeListener(this);

    }
    @Override
    public int getContentId() {
        return R.layout.activity_grade_app_aty;
    }

    @OnClick({R.id.btExit, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btCommit:
                Log.e(TAG, "onViewClicked: "+Contents.LOGIN_TYPE );
                OkGo.post(Urls.PUBLIC_URL+Urls.GRADE_APP)
                        .params("username", Contents.LOGIN_TYPE==1?Contents.USER.getUsername():Contents.GUIDE.getUsername())
                        .params("score",cutOff(rating.getRating()))
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 评分开始" );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 评分获得"+s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                if (baseBean.getCode() == 1) {
                                    ToastUtils.toast("谢谢您的好意，祝您愉快");
                                    finish();
                                }
                                else {
                                    ToastUtils.toast(baseBean.getMsg());
                                    finish();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call:评分错误"+throwable.toString() );
                            }
                        });
                break;
        }
    }
    @Override
    public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
        Log.e(TAG, "onRatingChanged: "+cutOff(rating));
        if (rating<2){
            grade.setText("差评");
        }else if (rating>=2&&rating<4){
            grade.setText("普普通通");
        }else if (rating>=4){
            grade.setText("很好用");
        }
    }

    private String cutOff(float a){
        String substring = String.valueOf(a).substring(0, 3);
        return substring;
    }
}
