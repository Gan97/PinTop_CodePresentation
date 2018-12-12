package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.ChatAty;
import com.hulianhujia.spellway.activitys.ReportAty;
import com.hulianhujia.spellway.activitys.SettingsAty;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.customwheel.WPopupWindow;
import com.hulianhujia.spellway.customwheel.WheelView;
import com.hulianhujia.spellway.event.ChangeHPEvent;
import com.hulianhujia.spellway.event.FinishMainEvent;
import com.hulianhujia.spellway.event.InitGuideBean;
import com.hulianhujia.spellway.event.SaveUserSuccessEvent;
import com.hulianhujia.spellway.javaBeans.BaseBean;
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

public class GuideSelfAty extends BaseActivity implements View.OnClickListener, SwitchButton.OnCheckedChangeListener {
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
    @Bind(R.id.father)
    LinearLayout father;
    @Bind(R.id.tvPNum)
    TextView tvPNum;
    @Bind(R.id.btSetting)
    ImageView btSetting;
    private LoginBean.UserInfoBean userInfo;
    private UserInfoBean user;
    private String TAG = "info";
    private LoadingDialog loadingDialog;
    private static final int PICK_PHOTO = 1;
    private File file;
    public Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            isOkSwitch.setChecked(false);
            super.handleMessage(msg);
        }
    };
    @Override
    public int getContentId() {
        return R.layout.activity_guide_self_aty;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        loadingDialog = new LoadingDialog(this);
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(this);
        user = (UserInfoBean) SharedUtils.readUserInfo(this);
        Glide.with(this).load(Contents.GUIDE.getPicture()).asBitmap().into(selfIcon);
        selfName.setText(Contents.GUIDE.getNickname());
        selfMotto.setText(Contents.GUIDE.getAutograph());
        if (Contents.GUIDE.getPindan_peoplenumber().split("-").length> 1) {
            tvPNum.setText(Contents.GUIDE.getPindan_peoplenumber().split("-")[0] + "~" + Contents.GUIDE.getPindan_peoplenumber().split("-")[1]);
        }else if (Contents.GUIDE.getPindan_peoplenumber().split("~").length>1){
            tvPNum.setText(Contents.GUIDE.getPindan_peoplenumber().split("~")[0] + "~" + Contents.GUIDE.getPindan_peoplenumber().split("-")[1]);
        }
        tvHourPrice.setText("￥" + Contents.GUIDE.getTime_fee());
        guidePrice.setText("￥" + Contents.GUIDE.getBase_fee()==null?"0.0":Contents.GUIDE.getBase_fee());
        initListener();
        initUser();
        isSmsOkSwitch.setOnCheckedChangeListener(this);
        isOkSwitch.setOnCheckedChangeListener(this);
        isTogetherSwitch.setOnCheckedChangeListener(this);
        isSmsOkSwitch.setChecked(Contents.GUIDE.getMessage_permit().endsWith("1"));
        isOkSwitch.setChecked(Contents.GUIDE.getOnduty().endsWith("1"));
        isTogetherSwitch.setChecked(Contents.GUIDE.getPindan_permit().endsWith("1"));
    }
    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).load(Contents.GUIDE.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(selfIcon);
        selfName.setText(Contents.GUIDE.getNickname());
        selfMotto.setText(Contents.GUIDE.getAutograph());
    }
    private void initUser() {

    }
    private void initListener() {
        btSelf.setOnClickListener(this);
        btClient.setOnClickListener(this);
        btHourPrice.setOnClickListener(this);
        btMyPhotos.setOnClickListener(this);
        selfIcon.setOnClickListener(this);
        btExit.setOnClickListener(this);
        btTogetherNum.setOnClickListener(this);
        btSetting.setOnClickListener(this);
        btSuggest.setOnClickListener(this);
        btKefu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btKefu:
                ToastUtils.toast("等待接入");
                Intent i = new Intent(this, ChatAty.class);
                i.putExtra("userId","admin");
                i.putExtra("toNick","客服");
                startActivity(i);
                break;
            case R.id.btSetting:
                startActivity(new Intent(this, SettingsAty.class));
                break;
            case R.id.btSuggest:
                startActivity(new Intent(this, ReportAty.class));
                break;
            case R.id.btTogetherNum:
                View wh = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                final WheelView picker = (WheelView) wh.findViewById(R.id.wheel);
                picker.addData("2~3");
                picker.addData("4~5");
                picker.addData("5~6");
                picker.addData("7~8");
                picker.setCenterItem(1);
                final WPopupWindow popupWindow = new WPopupWindow(wh);
                popupWindow.showAtLocation(father, Gravity.BOTTOM, 0, 0);
                wh.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                wh.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OkGo.get(Urls.PUBLIC_URL + Urls.SET_PINDAN_NUM)
                                .params("guidename", Contents.GUIDE.getUsername())
                                .params("pindan_peoplenumber", picker.getCenterItem().toString().split("~")[0]
                                        + "-" +
                                        picker.getCenterItem().toString().split("~")[1])
                                .getCall(StringConvert.create(), RxAdapter.<String>create())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        loadingDialog.show();
                                        Log.e(TAG, "call: 开始设置人数");
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        loadingDialog.dismiss();
                                        Log.e(TAG, "call: 设置人数获得" + s);
                                        Gson g = new Gson();
                                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                        if (baseBean.getCode() == 1) {
                                            ToastUtils.toast(baseBean.getMsg());
                                            tvPNum.setText(picker.getCenterItem().toString());
                                            Contents.GUIDE.setPindan_permit(picker.getCenterItem().toString());
                                            popupWindow.dismiss();
                                        } else {
                                            ToastUtils.toast(baseBean.getMsg());
                                        }
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        loadingDialog.dismiss();
                                        Log.e(TAG, "call: 设置人数错误" + throwable.toString());
                                    }
                                });
                    }
                });
                break;
            case R.id.btExit:
                finish();
                break;
            case R.id.btClient:
                startActivity(new Intent(this, GuideWalletAty.class));
                break;
            case R.id.btSelf:
                break;
            case R.id.btHourPrice:
                ChangeHPop pop = new ChangeHPop(this, Contents.GUIDE.getTime_fee());
                pop.setFocusable(true);
                pop.showAtLocation(father, Gravity.CENTER, 0, 0);
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.btMyPhotos:
                Intent intent = new Intent(this, GuideStyleAty.class);
                startActivity(intent);
                break;
            case R.id.selfIcon:
                startActivity(new Intent(this, GuideInfoAty.class));
                Log.e(TAG, "onCheckedChanged: ");
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeH(ChangeHPEvent event) {
        OkGo.post(Urls.PUBLIC_URL + Urls.EDIT_USERINFO)
                .params("username", Contents.GUIDE.getUsername())
                .params("time_fee", event.getPrice())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 开始设置");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: " + s);
                    }
                });
        tvHourPrice.setText("￥" + event.getPrice());
        Contents.GUIDE.setTime_fee(event.getPrice());
    }

    @Override
    public void onCheckedChanged(SwitchButton view, final boolean isChecked) {
        switch (view.getId()) {
            case R.id.isTogetherSwitch:
                OkGo.get(Urls.PUBLIC_URL + Urls.IS_PINDAN)
                        .params("guidename", userInfo.getUsername())
                        .params("pindan_permit", isChecked ? 1 : 2)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 是否拼单");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 是否拼单" + s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                ToastUtils.toast(baseBean.getMsg());
                                if (baseBean.getCode() == 1) {
                                    Contents.GUIDE.setPindan_permit(isTogetherSwitch.isChecked() ? "1" : "2");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 是否拼单" + throwable.toString());
                            }
                        });
                break;
            case R.id.isOkSwitch:
                String nickname = Contents.GUIDE.getNickname();
                String pindan_peoplenumber = Contents.GUIDE.getPindan_peoplenumber();
                String autograph = Contents.GUIDE.getAutograph();
                String sex = Contents.GUIDE.getSex();
                String hoby = Contents.GUIDE.getHoby();
                String picture = Contents.GUIDE.getPicture();
                String age = Contents.GUIDE.getAge();
                String weight = Contents.GUIDE.getWeight();
                String skill = Contents.GUIDE.getSkill();
                String introduce = Contents.GUIDE.getIntroduce();
                String address = Contents.GUIDE.getAddress();
                String height = Contents.GUIDE.getHeight();
                if (jude(nickname)||jude(pindan_peoplenumber)||jude(autograph)||jude(sex)
                        ||jude(hoby)||jude(picture)||jude(age)||jude(weight)||jude(skill)||jude(introduce)
                        ||jude(address)||jude(height)){
                    ToastUtils.toast("请您填写完所有个人信息后再开启接单");
                    handler.sendEmptyMessage(1);
                }else {
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
                                    if (bean.getCode() == 1) {
                                        Contents.GUIDE.setOnduty(isOkSwitch.isChecked() ? "1" : "2");
                                    }
                                    ToastUtils.toast(bean.getMsg());
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    loadingDialog.dismiss();
                                    Log.e(TAG, "call:接单?错误 " + throwable.toString());
                                }
                            });
                }
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
                                if (bean.getCode() == 1) {
                                    Contents.GUIDE.setMessage_permit(isSmsOkSwitch.isChecked() ? "1" : "2");
                                }
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
    private boolean jude(String str){
        if (str==null){
            return true;
        }else {
            return str.equals("");
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    try {
                        /*获取图骗路径*/
                        String path = MyUtils.getPath(this, uris.get(0));
                        file = new File(path);
                        Glide.with(this).load(uris.get(0)).asBitmap().into(selfIcon);
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
        user = (UserInfoBean) SharedUtils.readUserInfo(this);
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
        switch (user.getReturnArr().getPindan_permit()) {
            case "1":
                isTogetherSwitch.setChecked(true);
                break;
            case "2":
                isTogetherSwitch.setChecked(false);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handFinish(FinishMainEvent event){
        finish();
    }
}
