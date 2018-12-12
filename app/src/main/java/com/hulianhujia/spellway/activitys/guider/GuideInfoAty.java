package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.hulianhujia.spellway.activitys.ChangeHobbyAty;
import com.hulianhujia.spellway.activitys.ChangeMottoAty;
import com.hulianhujia.spellway.activitys.ChangeNickAty;
import com.hulianhujia.spellway.customViews.ChangeAddressPopwindow;
import com.hulianhujia.spellway.customwheel.WPopupWindow;
import com.hulianhujia.spellway.customwheel.WheelView;
import com.hulianhujia.spellway.event.ChangeIntroduceEvent;
import com.hulianhujia.spellway.event.ChangeMottoEvent;
import com.hulianhujia.spellway.event.ChangeNickEvent;
import com.hulianhujia.spellway.event.ChangeSkillsEvent;
import com.hulianhujia.spellway.javaBeans.ChangeHBean;
import com.hulianhujia.spellway.javaBeans.ChangeHobbyEvent;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuideInfoAty extends BaseActivity {
    @Bind(R.id.father)
    LinearLayout father;
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btSave)
    TextView btSave;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.iconImg)
    CircleImageView iconImg;
    @Bind(R.id.t0)
    TextView t0;
    @Bind(R.id.userIcon)
    RelativeLayout userIcon;
    @Bind(R.id.t1)
    TextView t1;
    @Bind(R.id.userNameLo)
    RelativeLayout userNameLo;
    @Bind(R.id.t2)
    TextView t2;
    @Bind(R.id.userAgeLo)
    RelativeLayout userAgeLo;
    @Bind(R.id.t3)
    TextView t3;
    @Bind(R.id.userSexLo)
    RelativeLayout userSexLo;
    @Bind(R.id.t4)
    TextView t4;
    @Bind(R.id.userTallLo)
    RelativeLayout userTallLo;
    @Bind(R.id.t5)
    TextView t5;
    @Bind(R.id.userWeightLo)
    RelativeLayout userWeightLo;
    @Bind(R.id.t6)
    TextView t6;
    @Bind(R.id.userLocLo)
    RelativeLayout userLocLo;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.t7)
    TextView t7;
    @Bind(R.id.userHobbyLo)
    RelativeLayout userHobbyLo;
    @Bind(R.id.t8)
    TextView t8;
    @Bind(R.id.userMottoLo)
    RelativeLayout userMottoLo;
    @Bind(R.id.t9)
    TextView t9;
    @Bind(R.id.skills)
    RelativeLayout skills;
    @Bind(R.id.t10)
    TextView t10;
    @Bind(R.id.introduce)
    RelativeLayout introduce;
    private String TAG = "info";
    private UserInfoBean userInfo;
    private String height;
    private String weight;
    private String path;
    private static final int PICK_PHOTO = 1;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback;
    private static  final int REQUEST_CODE_GALLERY =16;

    @Override
    public int getContentId() {
        return R.layout.activity_guide_info_aty;
    }
    @OnClick({R.id.btExit, R.id.btSave, R.id.userIcon, R.id.userNameLo, R.id.userAgeLo, R.id.userSexLo,
            R.id.userTallLo, R.id.userWeightLo, R.id.userLocLo, R.id.userHobbyLo, R.id.userMottoLo,R.id.introduce,
            R.id.skills
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btSave:
                if (path == null) {
                    OkGo.post(Urls.PUBLIC_URL + Urls.EDIT_USERINFO)
                            .params("username", Contents.GUIDE.getUsername())
                            .params("sex", t3.getText().toString().equals("男") ? "1" : t3.getText().toString().equals("女") ? "2" : "")
                            .params("nickname", t1.getText().toString())
                            .params("age", t2.getText().toString())
                            .params("sex", t3.getText().toString())
                            .params("height", height)
                            .params("weight", weight)
                            .params("address", t6.getText().toString())
                            .params("hoby", t7.getText().toString())
                            .params("autograph", t8.getText().toString())
                            .params("skill",t9.getText().toString())
                            .params("introduce",t10.getText().toString())
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call:更改开始 ");
                                    loadingDialog.show();
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    loadingDialog.dismiss();
                                    Log.e(TAG, "call: 更改获得" + s);
                                    Gson g = new Gson();
                                    ChangeHBean bean = g.fromJson(s, ChangeHBean.class);
                                    if (bean.getCode() == 1) {
                                        ToastUtils.toast("修改成功");
                                        Contents.GUIDE.setSex(t3.getText().toString());
                                        Contents.GUIDE.setNickname(t1.getText().toString());
                                        Contents.GUIDE.setAge(t2.getText().toString());
                                        if (height!=null)Contents.GUIDE.setHeight(height);
                                        if (weight!=null)Contents.GUIDE.setWeight(weight);
                                        Contents.GUIDE.setHoby(t7.getText().toString());
                                        Contents.GUIDE.setAddress(t6.getText().toString());
                                        Contents.GUIDE.setAutograph(t8.getText().toString());
                                        Contents.GUIDE.setSkill(t9.getText().toString());
                                        Contents.GUIDE.setIntroduce(t10.getText().toString());
                                        if (path != null) {
                                            Contents.GUIDE.setPicture(path);
                                        }
                                    } else {
                                        ToastUtils.toast("修改失败，请稍后重试");
                                    }
                                    finish();
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    loadingDialog.dismiss();
                                    Log.e(TAG, "call: 更改失败" + throwable.toString());
                                }
                            });
                } else {
                    OkGo.post(Urls.PUBLIC_URL + Urls.EDIT_USERINFO)
                            .params("picture", new File(path))
                            .params("username", Contents.GUIDE.getUsername())
                            .params("sex", t3.getText().toString().equals("男") ? "1" : t3.getText().toString().equals("女") ? "2" : "")
                            .params("nickname", t1.getText().toString())
                            .params("age", t2.getText().toString())
                            .params("sex", t3.getText().toString())
                            .params("height", height)
                            .params("weight", weight)
                            .params("address", t6.getText().toString())
                            .params("hoby", t7.getText().toString())
                            .params("autograph", t8.getText().toString())
                            .params("skill",t9.getText().toString())
                            .params("introduce",t10.getText().toString())
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call:更改开始 ");
                                    loadingDialog.show();
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    loadingDialog.dismiss();
                                    Log.e(TAG, "call: 更改获得" + s);
                                    Gson g = new Gson();
                                    ChangeHBean bean = g.fromJson(s, ChangeHBean.class);
                                    if (bean.getCode() == 1) {
                                        ToastUtils.toast("修改成功");
                                        Contents.GUIDE.setSex(t3.getText().toString());
                                        Contents.GUIDE.setNickname(t1.getText().toString());
                                        Log.e(TAG, "call: 昵称" + Contents.GUIDE.getNickname());
                                        Contents.GUIDE.setAge(t2.getText().toString());
                                        if (height!=null)Contents.GUIDE.setHeight(height);
                                        if (weight!=null)Contents.GUIDE.setWeight(weight);
                                        Contents.GUIDE.setHoby(t7.getText().toString());
                                        Contents.GUIDE.setAddress(t6.getText().toString());
                                        Contents.GUIDE.setAutograph(t8.getText().toString());
                                        Contents.GUIDE.setSkill(t9.getText().toString());
                                        Contents.GUIDE.setIntroduce(t10.getText().toString());
                                        if (path != null) {
                                            Contents.GUIDE.setPicture(path);
                                        }
                                    } else {
                                        ToastUtils.toast("修改失败，请稍后重试");
                                    }
                                    finish();
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    loadingDialog.dismiss();
                                    Log.e(TAG, "call: 更改失败" + throwable.toString());
                                }
                            });
                }
                break;
            case R.id.userIcon:
                mOnHanlderResultCallback=new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        List<String> strings = new ArrayList<>();
                        for (PhotoInfo pi : resultList){
                            strings.add(pi.getPhotoPath());
                        }
                        if (strings!=null&&strings.size()!=0){
                            path=strings.get(0);
                            Glide.with(getApplicationContext()).load(strings.get(0)).asBitmap().into(iconImg);
                        }
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {

                    }
                };
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                break;
            case R.id.userNameLo:
                startActivity(new Intent(this, ChangeNickAty.class));

                break;
            case R.id.userAgeLo:
                View wha = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                final WheelView pickera = (WheelView) wha.findViewById(R.id.wheel);
                for (int i = 16; i < 60; i++) {
                    pickera.addData(i + "");
                }
                pickera.setCenterItem(5);
                final WPopupWindow popupWindowa = new WPopupWindow(wha);
                popupWindowa.showAtLocation(father, Gravity.BOTTOM, 0, 0);
                wha.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowa.dismiss();
                    }
                });
                wha.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: " + pickera.getCenterItem());
                        t2.setText(pickera.getCenterItem().toString());
                        popupWindowa.dismiss();
                    }
                });
                break;
            case R.id.userSexLo:
                View wh = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                final WheelView picker = (WheelView) wh.findViewById(R.id.wheel);
                picker.addData("男");
                picker.addData("女");
                picker.setCenterItem(0);
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
                        Log.e(TAG, "onClick: " + picker.getCenterItem());
                        t3.setText(picker.getCenterItem().toString());
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.userTallLo:
                View wht = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                final WheelView pickert = (WheelView) wht.findViewById(R.id.wheel);
                for (int i = 150; i < 220; i++) {
                    pickert.addData(i + "");
                }
                pickert.setCenterItem(35);
                final WPopupWindow popupWindowt = new WPopupWindow(wht);
                popupWindowt.showAtLocation(father, Gravity.BOTTOM, 0, 0);

                wht.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindowt.dismiss();
                    }
                });
                wht.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: " + pickert.getCenterItem());
                        t4.setText(pickert.getCenterItem().toString() + "cm");
                        height = pickert.getCenterItem().toString();
                        popupWindowt.dismiss();
                    }
                });
                break;
            case R.id.userWeightLo:
                View whw = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                final WheelView pickerw = (WheelView) whw.findViewById(R.id.wheel);
                for (int i = 35; i < 100; i++) {
                    pickerw.addData(i + "");
                }
                pickerw.setCenterItem(20);
                final WPopupWindow popupWindoww = new WPopupWindow(whw);
                popupWindoww.showAtLocation(father, Gravity.BOTTOM, 0, 0);
                whw.findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindoww.dismiss();
                    }
                });
                whw.findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: " + pickerw.getCenterItem());
                        t5.setText(pickerw.getCenterItem().toString() + "kg");
                        weight = pickerw.getCenterItem().toString();
                        popupWindoww.dismiss();
                    }
                });
                break;
            case R.id.userLocLo:
                ChangeAddressPopwindow mChangeAddressPopwindow = new ChangeAddressPopwindow(getApplicationContext());
                mChangeAddressPopwindow.showAtLocation(t6, Gravity.BOTTOM, 0, 0);
                mChangeAddressPopwindow
                        .setAddresskListener(new ChangeAddressPopwindow.OnAddressCListener() {
                            @Override
                            public void onClick(String province, String city, String area) {
                                // TODO Auto-generated method stub
                                t6.setText(province + "-" + city);
                            }
                        });
                break;
            case R.id.userHobbyLo:
                startActivity(new Intent(this, ChangeHobbyAty.class));
                break;
            case R.id.userMottoLo:
                startActivity(new Intent(this, ChangeMottoAty.class));
                break;
            case R.id.introduce:
                startActivity(new Intent(this, ChangeIntroduceAty.class));
                break;
            case R.id.skills:
                startActivity(new Intent(this, ChangeSkillsAty.class));
                break;
        }
    }
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        UserInfoBean.ReturnArrBean user = Contents.GUIDE;

        if (user.getNickname() != null && user.getNickname().length() > 0) {
            t1.setText(user.getNickname());
        }
        if (user.getAge() != null && user.getAge().length() > 0) {
            t2.setText(user.getAge());
        }
        if (user.getSex() != null && user.getSex().length() > 0) {
            t3.setText(user.getSex());
        }
        if (user.getHeight() != null && user.getHeight().length() > 0) {
            t4.setText(user.getHeight() + "cm");
        }

        if (user.getWeight() != null && user.getWeight().length() > 0) {
            t5.setText(user.getWeight() + "kg");
        }
        if (user.getAddress()!=null&&user.getAddress().length()!=0){
            t6.setText(user.getAddress());
        }
        if (user.getHoby() != null && user.getHoby().length() > 0) {
            t7.setText(user.getHoby());
        }
        if (user.getAutograph() != null && user.getAutograph().length() != 0) {
            t8.setText(user.getAutograph());
        }
        if (user.getPicture() != null && user.getPicture().length() != 0) {
            Glide.with(getApplicationContext()).load(user.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(iconImg);
        }
        if (user.getSkill()!=null&&user.getSkill().length()!=0){
            t9.setText(user.getSkill());
        }
        if (user.getIntroduce()!=null&&user.getIntroduce().length()!=0){
            t10.setText(user.getIntroduce());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeHobby(ChangeHobbyEvent event) {
        t7.setText(event.getHobby());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeMotto(ChangeMottoEvent event) {
        t8.setText(event.getMotto());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeNick(ChangeNickEvent event) {
        t1.setText(event.getContent());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeSkill(ChangeSkillsEvent event) {
        t9.setText(event.getSkill());
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeIntroduce(ChangeIntroduceEvent event) {
        t10.setText(event.getIntroduce());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode != RESULT_OK) return;
                List<String> strings = Matisse.obtainPathResult(data);
                if (strings != null && strings.size() != 0) {
                    path = strings.get(0);
                    Glide.with(getApplicationContext()).load(strings.get(0)).asBitmap().into(iconImg);
                }
                break;
        }
    }




}
