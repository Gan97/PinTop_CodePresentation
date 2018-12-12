package com.hulianhujia.spellway.activitys.guider.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.UserInfoAty;
import com.hulianhujia.spellway.activitys.guider.ChangeHPriceAty;
import com.hulianhujia.spellway.activitys.guider.GuidePhotosAty;
import com.hulianhujia.spellway.activitys.guider.GuiderClientAty;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.ChangeHPEvent;
import com.hulianhujia.spellway.event.InitGuideBean;
import com.hulianhujia.spellway.event.SaveUserSuccessEvent;
import com.hulianhujia.spellway.javaBeans.ChangeStatusBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.suke.widget.SwitchButton;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;

/**
 * Created by FHP on 2017/5/25.
 */

public class GudierSelfFgm extends Fragment implements View.OnClickListener, SwitchButton.OnCheckedChangeListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.selfIcon)
    CircleImageView selfIcon;
    @Bind(R.id.selfName)
    TextView selfName;
    @Bind(R.id.selfMotto)
    TextView selfMotto;
    @Bind(R.id.btSelf)
    RelativeLayout btSelf;
    @Bind(R.id.btClient)
    RelativeLayout btClient;
    @Bind(R.id.btMyPhotos)
    RelativeLayout btMyPhotos;
    @Bind(R.id.isOkSwitch)
    SwitchButton isOkSwitch;
    @Bind(R.id.btIsOk)
    RelativeLayout btIsOk;
    @Bind(R.id.isTogetherSwitch)
    SwitchButton isTogetherSwitch;
    @Bind(R.id.btIsTogether)
    RelativeLayout btIsTogether;
    @Bind(R.id.a)
    ImageView a;
    @Bind(R.id.btTogetherNum)
    RelativeLayout btTogetherNum;
    @Bind(R.id.guidePrice)
    TextView guidePrice;
    @Bind(R.id.btGuiderPrice)
    RelativeLayout btGuiderPrice;
    @Bind(R.id.tvHourPrice)
    TextView tvHourPrice;
    @Bind(R.id.btHourPrice)
    RelativeLayout btHourPrice;
    @Bind(R.id.isSmsOkSwitch)
    SwitchButton isSmsOkSwitch;
    @Bind(R.id.btSms)
    RelativeLayout btSms;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.btMyMessage)
    RelativeLayout btMyMessage;
    @Bind(R.id.btSuggest)
    RelativeLayout btSuggest;
    @Bind(R.id.btKefu)
    RelativeLayout btKefu;
    private LoginBean.UserInfoBean userInfo;
    private UserInfoBean user;
    private String TAG = "info";
    private LoadingDialog loadingDialog;
    private static final int PICK_PHOTO = 1;
    private File file;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgm_guiderself, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        loadingDialog = new LoadingDialog(getContext());
        initview();
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initview() {
        getUser();
        user = (UserInfoBean) SharedUtils.readUserInfo(getContext());

        initListener();
        initUser();
        isSmsOkSwitch.setOnCheckedChangeListener(this);
        isOkSwitch.setOnCheckedChangeListener(this);

    }

    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getContext());
    }

    private void initUser() {
        OkGo.post(Urls.PUBLIC_URL + Urls.INIT)
                .params("username", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 导游初始化开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 导游初始化获得" + s);
                        Gson g = new Gson();
                        InitGuideBean bean = g.fromJson(s, InitGuideBean.class);
                        if (bean.getCode() == 1) {

                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 导游初始化错误" + throwable.toString());
                    }
                });
    }

    private void initListener() {
        btSelf.setOnClickListener(this);
        btClient.setOnClickListener(this);
        btHourPrice.setOnClickListener(this);
        btMyPhotos.setOnClickListener(this);
        selfIcon.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btClient:
                startActivity(new Intent(getActivity(), GuiderClientAty.class));
                break;
            case R.id.btSelf:
                startActivity(new Intent(getActivity(), UserInfoAty.class));
                break;
            case R.id.btHourPrice:
                startActivity(new Intent(getActivity(), ChangeHPriceAty.class));
                break;
            case R.id.btMyPhotos:
                Intent intent = new Intent(getActivity(), GuidePhotosAty.class);
                intent.putExtra("guideName", userInfo.getUsername());
                startActivity(intent);
                break;
            case R.id.selfIcon:
                Log.e(TAG, "onCheckedChanged: ");
                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(1)
                        .spanCount(3)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(new GlideEngine())
                        .forResult(PICK_PHOTO);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeH(ChangeHPEvent event) {
        tvHourPrice.setText("￥" + event.getPrice());
    }

    @Override
    public void onCheckedChanged(SwitchButton view, final boolean isChecked) {
        switch (view.getId()) {
            case R.id.isOkSwitch:
                OkGo.post(Urls.PUBLIC_URL + Urls.IS_ONDUTY)
                        .params("guidename", userInfo.getUsername())
                        .params("onduty", isChecked ? 1 : 2)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call:接单？开始 " + (isChecked ? 1 : 0));
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 接单？获得" + s);
                                Gson g = new Gson();
                                ChangeStatusBean bean = g.fromJson(s, ChangeStatusBean.class);
                                ToastUtils.toast(bean.getMsg());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call:接单?错误 " + throwable.toString());
                            }
                        });
                break;
            case R.id.isSmsOkSwitch:
                OkGo.post(Urls.PUBLIC_URL + Urls.IS_SMS)
                        .params("guidename", userInfo.getUsername())
                        .params("message_permit", isChecked ? 1 : 2)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call:接单？开始 " + (isChecked ? 1 : 0));
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 接单？获得" + s);
                                Gson g = new Gson();
                                ChangeStatusBean bean = g.fromJson(s, ChangeStatusBean.class);
                                ToastUtils.toast(bean.getMsg());
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call:接单?错误 " + throwable.toString());
                            }
                        });
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    try {
                        /*获取图骗路径*/
                        String path = MyUtils.getPath(getContext(), uris.get(0));
                        file = new File(path);
                        Glide.with(getContext()).load(uris.get(0)).asBitmap().into(selfIcon);

                        OkGo.post(Urls.PUBLIC_URL + Urls.EDIT_USERINFO)
                                .params("username", userInfo.getUsername())
                                .params("picture", file)
                                .getCall(StringConvert.create(), RxAdapter.<String>create())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        Log.e(TAG, "call: 穿头像开始" + (file == null) + file.getAbsolutePath());
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        Log.e(TAG, "call:头像获得 0" + s);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e(TAG, "call: 头像错误" + throwable.toString());
                                    }
                                });
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void saveSuccess(SaveUserSuccessEvent event) {
        user = (UserInfoBean) SharedUtils.readUserInfo(getContext());
        switch (user.getReturnArr().getOnduty()) {
            case "1":
                isOkSwitch.setChecked(true);
                break;
            case "2":
                isOkSwitch.setChecked(false);
                break;
        }
        switch (user.getReturnArr().getMessage_permit()) {
            case "1":
                isSmsOkSwitch.setChecked(true);
                break;
            case "2":
                isSmsOkSwitch.setChecked(false);
                break;
        }
    }
}
