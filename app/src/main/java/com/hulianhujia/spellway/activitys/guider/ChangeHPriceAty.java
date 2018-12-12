package com.hulianhujia.spellway.activitys.guider;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.event.ChangeHPEvent;
import com.hulianhujia.spellway.javaBeans.ChangeHBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class ChangeHPriceAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btSave)
    TextView btSave;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.etNick)
    EditText etNick;

    private LoginBean.UserInfoBean userInfo;
    private String TAG="info";

    @Override
    public int getContentId() {
        return R.layout.changehprice_aty;
    }

    @Override
    public void initView() {
        getUser();
    }

    private void getUser() {
        userInfo= (LoginBean.UserInfoBean) SharedUtils.readObject(this);
    }

    @OnClick({R.id.btExit, R.id.btSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btSave:
                if (etNick.getText().toString()!=null&&etNick.getText().length()!=0){
                    OkGo.post(Urls.PUBLIC_URL+Urls.EDIT_USERINFO)
                            .params("username",userInfo.getUsername())
                            .params("time_fee",etNick.getText().toString())
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call: 更改时长费开始" );
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    Log.e(TAG, "call: 更改市场费返回" + s);
                                    Gson g = new Gson();
                                    ChangeHBean bean = g.fromJson(s, ChangeHBean.class);
                                    ToastUtils.toast(bean.getMsg());
                                    if (bean.getCode()==1){
                                        EventBus.getDefault().post(new ChangeHPEvent(etNick.getText().toString()));
                                        finish();
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.e(TAG, "call: 更改时长费错误"+throwable.toString());
                                }
                            });
                }else {
                    ToastUtils.toast("不能为空");
                }

                break;
        }
    }
}
