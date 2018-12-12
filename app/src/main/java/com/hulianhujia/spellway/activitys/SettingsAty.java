package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.FinishMainEvent;
import com.hulianhujia.spellway.event.OverEvent;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.DataCleanManager;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hyphenate.chat.EMClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class SettingsAty extends BaseActivity implements YesListener, SwitchButton.OnCheckedChangeListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.tvCache)
    TextView tvCache;
    @Bind(R.id.loDelet)
    RelativeLayout loDelet;
    @Bind(R.id.isInviteSwitch)
    SwitchButton isInviteSwitch;
    @Bind(R.id.lo1)
    RelativeLayout lo1;
    @Bind(R.id.dv)
    TextView dv;
    @Bind(R.id.isNoticeSwitch)
    SwitchButton isNoticeSwitch;
    @Bind(R.id.lo2)
    RelativeLayout lo2;
    @Bind(R.id.btPoint)
    TextView btPoint;
    @Bind(R.id.btLoginOut)
    TextView btLoginOut;
    @Bind(R.id.btChangePwd)
    TextView btChangePwd;
    private ConfirmDialogN confirmDialog;
    private UserInfoBean userInfo;
    private LoadingDialog loadingDialog;
    private String TAG = "info";
    private SharedPreferences sp;

    @Override
    public int getContentId() {
        return R.layout.activity_settings_aty;
    }

    @Override
    public void initView() {

        sp = getSharedPreferences("login", MODE_PRIVATE);
        userInfo = (UserInfoBean) SharedUtils.readUserInfo(this);
        loadingDialog = new LoadingDialog(this);
        EventBus.getDefault().register(this);
        confirmDialog = new ConfirmDialogN(this);
        confirmDialog.setYesListener(this);
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            tvCache.setText(totalCacheSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isInviteSwitch.setOnCheckedChangeListener(this);
        isNoticeSwitch.setOnCheckedChangeListener(this);

        switch (userInfo.getReturnArr().getMessage_permit()) {
            case "1":
                isNoticeSwitch.setChecked(true);
                break;
            case "2":
                isNoticeSwitch.setChecked(false);
                break;
        }
    }

    @OnClick({R.id.btExit, R.id.loDelet, R.id.btLoginOut, R.id.btPoint,R.id.btChangePwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btChangePwd:
                startActivity(new Intent(this, ForgetAty.class));
                break;
            case R.id.btPoint:
                startActivity(new Intent(this, GradeAppAty.class));
                break;
            case R.id.btExit:
                finish();
                break;
            case R.id.loDelet:
                confirmDialog.show();
                confirmDialog.setTitle("确定清除缓存？");
                confirmDialog.setFlag(1);
                break;
            case R.id.btLoginOut:
                confirmDialog.show();
                confirmDialog.setFlag(2);
                confirmDialog.setTitle("确定退出登陆？");

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void overEvent(OverEvent overEvent) {
        confirmDialog.dismiss();
        try {
            tvCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        switch (view.getId()) {
            case R.id.isInviteSwitch:
                OkGo.post(Urls.PUBLIC_URL + Urls.IS_INVITE)
                        .params("username", userInfo.getReturnArr().getUsername())
                        .params("invite", isInviteSwitch.isChecked() ? 1 : 2)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 邀请开关开始");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 邀请获得" + s);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 邀请错误" + throwable.toString());
                            }
                        });
                break;
            case R.id.isNoticeSwitch:
                OkGo.post(Urls.PUBLIC_URL + Urls.IS_USER_SMS)
                        .params("username", userInfo.getReturnArr().getUsername())
                        .params("message_permit", isNoticeSwitch.isChecked() ? 1 : 2)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 邀请开关开始");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 邀请获得" + s);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 邀请错误" + throwable.toString());
                            }
                        });
                break;
        }
    }
    @Override
    public void yes(int flag) {
        switch (flag) {
            case 1:
                DataCleanManager.clearAllCache(this);
                break;
            case 2:
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isKeep", false);
                edit.commit();
                startActivity(new Intent(this, InitAty.class));
                EMClient.getInstance().logout(true);
                EventBus.getDefault().postSticky(new FinishMainEvent());
                finish();
                break;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
