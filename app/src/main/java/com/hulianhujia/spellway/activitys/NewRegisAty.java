package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.SmsBean;
import com.hulianhujia.spellway.untils.CodeUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class NewRegisAty extends BaseActivity implements TextWatcher {

    @Bind(R.id.lobg)
    ImageView lobg;
    @Bind(R.id.btUser)
    TextView btUser;
    @Bind(R.id.btGuider)
    TextView btGuider;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etPwdAg)
    EditText etPwdAg;
    @Bind(R.id.btGetSms)
    TextView btGetSms;
    @Bind(R.id.etSMSCode)
    EditText etSMSCode;
    @Bind(R.id.imgYz)
    ImageView imgYz;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.IdImg)
    ImageView IdImg;
    @Bind(R.id.btIdCard)
    RelativeLayout btIdCard;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.GuideImg)
    ImageView GuideImg;
    @Bind(R.id.btGuideCard)
    RelativeLayout btGuideCard;
    @Bind(R.id.btCommit)
    TextView btCommit;
    @Bind(R.id.btLogin)
    TextView btLogin;
    @Bind(R.id.lo)
    ScrollView lo;
    @Bind(R.id.userSJ)
    ImageView userSJ;
    @Bind(R.id.guideSJ)
    ImageView guideSJ;
    @Bind(R.id.idNum)
    TextView idNum;
    @Bind(R.id.guideNum)
    TextView guideNum;
    @Bind(R.id.etIVCode)
    EditText etIVCode;
    private CodeUtils codeUtils;
    private String TAG = "info";
    private String smsCode;
    private String smsName;
    private int time = 60;
    private static final int PICK_PHOTO = 1;
    private static final int PICK_GUIDE = 2;
    private List<String> idCardImgs = new ArrayList<>();
    private List<String> guideCardImgs = new ArrayList<>();
    private List<File> idCardFiles = new ArrayList<>();
    private List<File> guideCardFiles = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private int flag = 1;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            time--;
            if (time <= 0) {
                time = 60;
                btGetSms.setClickable(true);
                btGetSms.setText("获取验证码");
            } else {
                btGetSms.setClickable(false);
                btGetSms.setText(time + "s");
                handler.sendEmptyMessageDelayed(1, 1000);
            }
            return false;
        }
    });

    @Override
    public int getContentId() {
        return R.layout.activity_new_regis_aty;
    }

    /*使能图片验证码控件*/
    private void initCodeUtils() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        imgYz.setImageBitmap(bitmap);
    }

    @Override
    public void initView() {
        initCodeUtils();
        loadingDialog = new LoadingDialog(this);

        etPwd.addTextChangedListener(this);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (s1.length()!=11){
                    etPhone.setError("手机号码格式不正确");
                }else {
                    etPhone.setError(null);
                }
            }
        });
        etPwdAg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.equals(etPwd.getText().toString())){
                    etPwdAg.setError("两次输入的密码不一致");
                }else {
                    etPwdAg.setError(null);
                }
            }
        });
    }

    @OnClick({R.id.btUser, R.id.btGuider, R.id.btGetSms, R.id.btIdCard, R.id.btGuideCard, R.id.btCommit, R.id.btLogin
            , R.id.imgYz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btUser:
                flag = 1;
                etIVCode.setVisibility(View.VISIBLE);
                idCardImgs.clear();
                IdImg.setVisibility(View.GONE);
                guideCardImgs.clear();
                GuideImg.setVisibility(View.GONE);
                userSJ.setVisibility(View.VISIBLE);
                guideSJ.setVisibility(View.GONE);
                btIdCard.setVisibility(View.GONE);
                btGuideCard.setVisibility(View.GONE);
                break;
            case R.id.btGuider:
                flag = 2;
                etIVCode.setVisibility(View.GONE);
                userSJ.setVisibility(View.GONE);
                guideSJ.setVisibility(View.VISIBLE);
                btGuideCard.setVisibility(View.VISIBLE);
                btIdCard.setVisibility(View.VISIBLE);
                break;
            case R.id.btGetSms:
                OkGo.post(Urls.PUBLIC_URL + Urls.SMS_URL)
                        .params("phone", etPhone.getText().toString())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 短信验证码开始");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 短信验证码获得" + s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                if (baseBean.getCode()==1){
                                    SmsBean smsBean = g.fromJson(s, SmsBean.class);
                                    smsCode = smsBean.getReturnArr().getMsgCode();
                                    smsName =smsBean.getReturnArr().getMobile();
                                }else {
                                    ToastUtils.toast("发送失败，请检查手机号是否正确");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 短信验证码错误" + throwable.toString());
                            }
                        });
                handler.sendEmptyMessageDelayed(1, 0);
                break;
            case R.id.btIdCard:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(2)
                        .spanCount(3)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(new GlideEngine())
                        .forResult(PICK_PHOTO);
                break;
            case R.id.btGuideCard:
                Matisse.from(this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .spanCount(3)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(new GlideEngine())
                        .forResult(PICK_GUIDE);
                break;
            case R.id.btCommit:
                if (flag == 1) {
                    if (etPhone.getText().length() == 0) {
                        ToastUtils.toast("手机号不能为空");
                    } else {
                        if (etPwd.getText().length() == 0 || etPwdAg.getText().length() == 0) {
                            ToastUtils.toast("密码不能为空");
                        } else {
                            if (!etPwd.getText().toString().equals(etPwdAg.getText().toString())) {
                                ToastUtils.toast("两次输入的密码不相同，请重新输入");
                            } else {
                                if (etSMSCode.getText().length() == 0) {
                                    ToastUtils.toast("验证码不能为空");
                                } else {
                                    if (etYzm.getText().length() == 0) {
                                        ToastUtils.toast("图片校验码不能为空");
                                    } else {
                                        if (!smsCode.equals(etSMSCode.getText().toString())||!smsName.equals(etPhone.getText().toString())) {
                                            ToastUtils.toast("短信验证码错误");
                                        } else {
                                            if (!etYzm.getText().toString().equalsIgnoreCase(codeUtils.getCode())) {
                                                ToastUtils.toast("图片校验码错误");
                                            } else {
                                                OkGo.get(Urls.PUBLIC_URL + Urls.REGISER)
                                                        .params("username", etPhone.getText().toString())
                                                        .params("password", etPwd.getText().toString())
                                                        .params("type", flag)
                                                        .params("invite_code", etIVCode.getText().toString()==null?"":etIVCode.getText().toString())
                                                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                                                        .doOnSubscribe(new Action0() {
                                                            @Override
                                                            public void call() {
                                                                loadingDialog.show();
                                                                Log.e(TAG, "call: 注册游客开始" + etPhone.getText().toString() + "" + etPwd.getText().toString() + "flag" + flag);
                                                            }
                                                        }).observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new Action1<String>() {
                                                            @Override
                                                            public void call(String s) {
                                                                loadingDialog.dismiss();
                                                                Log.e(TAG, "call: 注册游客获得" + s);
                                                                Gson g = new Gson();
                                                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                                                if (baseBean.getCode() == 1) {
                                                                    ToastUtils.toast("注册成功");

                                                                        new Thread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                try {
                                                                                    EMClient.getInstance().createAccount(etPhone.getText().toString(), etPwd.getText().toString());//同步方法
                                                                                } catch (HyphenateException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }).start();

                                                                    finish();
                                                                } else {
                                                                    ToastUtils.toast(baseBean.getMsg());
                                                                }
                                                            }
                                                        }, new Action1<Throwable>() {
                                                            @Override
                                                            public void call(Throwable throwable) {
                                                                loadingDialog.dismiss();
                                                                ToastUtils.toast("注册失败");
                                                                Log.e(TAG, "call: 注册游客错误" + throwable.toString());
                                                            }
                                                        });
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (flag == 2) {
                    if (etPwd.getText().length() == 0 || etPwdAg.getText().length() == 0) {
                        ToastUtils.toast("密码不能为空");
                    } else {
                        if (!etPwd.getText().toString().equals(etPwdAg.getText().toString())) {
                            ToastUtils.toast("两次输入的密码不相同，请重新输入");
                        } else {
                            if (etSMSCode.getText().length() == 0) {
                                ToastUtils.toast("验证码不能为空");
                            } else {
                                if (etYzm.getText().length() == 0) {
                                    ToastUtils.toast("图片校验码不能为空");
                                } else {
                                    if (idCardImgs.size() != 2 &&guideCardImgs.size()!=1) {
                                        ToastUtils.toast("请上传身份证正反面照片和导游证照片");
                                    } else {
                                        if (!smsCode.equals(etSMSCode.getText().toString())||!smsName.equals(etPhone.getText().toString())) {
                                            ToastUtils.toast("短信验证码错误");
                                        } else {
                                            if (!etYzm.getText().toString().equalsIgnoreCase(codeUtils.getCode())) {
                                                ToastUtils.toast("图片校验码错误");
                                            } else {
                                                if (flag == 2) {
                                                    idCardFiles.clear();
                                                    guideCardFiles.clear();
                                                    for (int i = 0; i < idCardImgs.size(); i++) {
                                                        File f = new File(idCardImgs.get(i));
                                                        idCardFiles.add(f);
                                                    }
                                                    for (int i = 0; i < guideCardImgs.size(); i++) {
                                                        File f = new File(guideCardImgs.get(i));
                                                        guideCardFiles.add(f);
                                                    }
                                                    OkGo.post(Urls.PUBLIC_URL + Urls.REGISER)
                                                            .params("username", etPhone.getText().toString())
                                                            .params("password", etPwd.getText().toString())
                                                            .params("type", flag)
                                                            .addFileParams("idCard[]", idCardFiles)
                                                            .addFileParams("certificate[]", guideCardFiles)
                                                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                                                            .doOnSubscribe(new Action0() {
                                                                @Override
                                                                public void call() {
                                                                    loadingDialog.show();
                                                                    Log.e(TAG, "call: 注册导游开始");
                                                                }
                                                            }).observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Action1<String>() {
                                                                @Override
                                                                public void call(String s) {
                                                                    loadingDialog.dismiss();
                                                                    Gson g = new Gson();
                                                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                                                    if (baseBean.getCode() == 1) {
                                                                        ToastUtils.toast("注册成功");
                                                                        //注册失败会抛出HyphenateException
                                                                        new Thread(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                try {
                                                                                    EMClient.getInstance().createAccount(etPhone.getText().toString(), etPwd.getText().toString());//同步方法
                                                                                } catch (HyphenateException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }).start();
                                                                        finish();
                                                                    } else {
                                                                        ToastUtils.toast(baseBean.getMsg());
                                                                    }
                                                                    Log.e(TAG, "call: 注册导游获得" + s);
                                                                }
                                                            }, new Action1<Throwable>() {
                                                                @Override
                                                                public void call(Throwable throwable) {
                                                                    loadingDialog.dismiss();
                                                                    ToastUtils.toast("注册失败");
                                                                    Log.e(TAG, "call: 注册导游错误" + throwable.toString());
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.btLogin:
                startActivity(new Intent(this, LoginAty.class));
                finish();
                break;
            case R.id.imgYz:
                codeUtils.createCode();
                Bitmap bitmap = codeUtils.createBitmap();
                imgYz.setImageBitmap(bitmap);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode != RESULT_OK) return;
//                if (idCardImgs.size() >= 2) {
//                    ToastUtils.toast("超出限制");
//                } else if (idCardImgs.size() + Matisse.obtainPathResult(data).size() > 2) {
//                    ToastUtils.toast("超出限制");
//                } else {
                    List<String> paths = Matisse.obtainPathResult(data);
                    idCardImgs.clear();
                    idCardImgs.addAll(paths);
                    IdImg.setVisibility(View.VISIBLE);
                    Glide.with(NewRegisAty.this).load(idCardImgs.get(idCardImgs.size() - 1)).asBitmap().into(IdImg);
                    idNum.setText(idCardImgs.size() + "");
                idNum.setVisibility(View.VISIBLE);
//                }
                break;
            case PICK_GUIDE:
                if (resultCode != RESULT_OK) return;
                if (guideCardImgs.size() >= 2) {
                    ToastUtils.toast("请只上传身份证正面反面两张照片");
                } else if (guideCardImgs.size() + Matisse.obtainPathResult(data).size() > 2) {
                    ToastUtils.toast("请只上传身份证正面反面两张照片");
                } else {
                    List<String> paths1 = Matisse.obtainPathResult(data);
                    guideCardImgs.addAll(paths1);
                    GuideImg.setVisibility(View.VISIBLE);
                    Glide.with(NewRegisAty.this).load(guideCardImgs.get(guideCardImgs.size() - 1)).asBitmap().into(GuideImg);
                    guideNum.setText(guideCardImgs.size() + "");
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        char[] chars = str.toCharArray();
        for (char c :chars){
            boolean digit = Character.isDigit(c);
            if (!digit){
                etPwd.setError(null);
                return;
            }else {
                etPwd.setError("密码不能全为数字");
            }
        }
    }
}
