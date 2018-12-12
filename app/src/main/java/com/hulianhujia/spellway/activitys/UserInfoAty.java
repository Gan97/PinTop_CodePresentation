package com.hulianhujia.spellway.activitys;

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
import com.hulianhujia.spellway.customViews.ChangeAddressPopwindow;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.customwheel.WPopupWindow;
import com.hulianhujia.spellway.customwheel.WheelView;
import com.hulianhujia.spellway.event.ChangeAgeEvent;
import com.hulianhujia.spellway.event.ChangeHeightEvetn;
import com.hulianhujia.spellway.event.ChangeLocEvent;
import com.hulianhujia.spellway.event.ChangeMottoEvent;
import com.hulianhujia.spellway.event.ChangeNickEvent;
import com.hulianhujia.spellway.event.ChangeSexEvent;
import com.hulianhujia.spellway.event.ChangeWeightEvent;
import com.hulianhujia.spellway.interfaces.NoListener;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.ChangeHBean;
import com.hulianhujia.spellway.javaBeans.ChangeHobbyEvent;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
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

public class UserInfoAty extends BaseActivity {


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
    @Bind(R.id.father)
    LinearLayout father;
    private UserInfoBean userInfo;
    private String TAG = "info";
    private int sexFlag = 0;
    private String height;
    private String weight;
    private  static  final int PICK_PHOTO=1;
    private String path;
    private static  final int REQUEST_CODE_GALLERY=16;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback;
    public static boolean isChanged =false;
    @Override
    public int getContentId() {
        return R.layout.activity_user_info_aty;
    }
    @Override
    public void initView() {
        isChanged =false;
        EventBus.getDefault().register(this);
        getUserInfo();
        try {
            setData();
        } catch (Exception e) {
            Log.e(TAG, "initView: " + e.toString());
        }
    }

    private void getUserInfo() {
        userInfo = (UserInfoBean) SharedUtils.readUserInfo(this);
    }

    private void setData() {
        UserInfoBean.ReturnArrBean bean = userInfo.getReturnArr();
        Log.e(TAG, "setData: " + bean.getSex());
        UserInfoBean.ReturnArrBean user = Contents.USER;

        if (user.getNickname()!=null&&user.getNickname().length()>0){
            t1.setText(user.getNickname());
        }
        if (user.getAge()!=null&&user.getAge().length()>0){
            t2.setText(user.getAge());
        }
        if (user.getSex()!=null&&user.getSex().length()>0){
            t3.setText(user.getSex());
        }
        if (user.getHeight()!=null&&user.getHeight().length()>0){
            t4.setText(user.getHeight()+"cm");
        }

        if (user.getWeight()!=null&&user.getWeight().length()>0){
            t5.setText(user.getWeight()+"kg");
        }
        if (user.getHoby()!=null&&user.getHoby().length()>0){
            t7.setText(user.getHoby());
        }
        if (user.getAutograph()!=null&&user.getAutograph().length()!=0){
            t8.setText(user.getAutograph());
        }
        if (user.getPicture()!=null&&user.getPicture().length()!=0){
            Glide.with(getApplicationContext()).load(user.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(iconImg);
        }
        if (user.getAddress()!=null&&user.getAddress().length()!=0){
            t6.setText(user.getAddress());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (isChanged){
            final ConfirmDialogN c=new ConfirmDialogN(this);
            c.setFlag(1);
            c.setYesListener(new YesListener() {
                @Override
                public void yes(int flag) {
                    save();
                }
            });
            c.setNoListener(new NoListener() {
                @Override
                public void no() {
                    Log.e(TAG, "no: 执行" );
                    UserInfoAty.this.finish();
                }
            });
            c.show();
            c.setTitle("确认保存?");
        }else {
            finish();
        }

    }

    @OnClick({R.id.userNameLo, R.id.userAgeLo, R.id.userSexLo, R.id.userTallLo, R.id.userWeightLo, R.id.userLocLo,
            R.id.userHobbyLo, R.id.userMottoLo, R.id.btExit, R.id.btSave,R.id.userIcon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                if (isChanged){
                    final ConfirmDialogN c=new ConfirmDialogN(this);
                    c.setFlag(1);
                    c.setYesListener(new YesListener() {
                        @Override
                        public void yes(int flag) {
                            save();
                        }
                    });
                    c.setNoListener(new NoListener() {
                        @Override
                        public void no() {
                            Log.e(TAG, "no: 执行" );
                            UserInfoAty.this.finish();
                        }
                    });
                    c.show();
                    c.setTitle("确认保存?");
                }else {
                    finish();
                }
                break;
            case R.id.btSave:
                if (isChanged){
                    save();
                }else {
                    finish();
                }
                break;
            case R.id.userIcon:
                mOnHanlderResultCallback=new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        isChanged=true;
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
                startActivity(new Intent(UserInfoAty.this, ChangeNickAty.class));
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
                        isChanged=true;
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
                        isChanged=true;
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
                        isChanged=true;
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
                        isChanged=true;
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
                                isChanged=true;
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
        }
    }

    private void save() {
        if (path==null){
            OkGo.post(Urls.PUBLIC_URL+Urls.EDIT_USERINFO)
                    .params("username",Contents.USER.getUsername())
                    .params("sex",t3.getText().toString().equals("男")?"1":t3.getText().toString().equals("女")?"2":"")
                    .params("nickname",t1.getText().toString())
                    .params("age",t2.getText().toString())
                    .params("sex",t3.getText().toString())
                    .params("height",height)
                    .params("weight",weight)
                    .params("address",t6.getText().toString())
                    .params("hoby",t7.getText().toString())
                    .params("autograph",t8.getText().toString())
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
                            Log.e(TAG, "call: 更改获得"+s);
                            Gson g = new Gson();
                            ChangeHBean bean = g.fromJson(s, ChangeHBean.class);
                            if (bean.getCode()==1){
                                ToastUtils.toast("修改成功");
                                Contents.USER.setSex(t3.getText().toString());
                                Contents.USER.setNickname(t1.getText().toString());
                                Contents.USER.setAge(t2.getText().toString());
                                Contents.USER.setHeight(height!=null?height:Contents.USER.getHeight());
                                Contents.USER.setWeight(weight!=null?weight:Contents.USER.getWeight());
                                Contents.USER.setHoby(t7.getText().toString());
                                Contents.USER.setAutograph(t8.getText().toString());
                                Contents.USER.setAddress(t6.getText().toString());
                                finish();
                            }else {
                                ToastUtils.toast("修改失败，请稍后重试");
                            }
                            finish();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 更改失败"+throwable.toString() );
                        }
                    });
        }else {
            OkGo.post(Urls.PUBLIC_URL+Urls.EDIT_USERINFO)
                    .params("picture",new File(path))
                    .params("username",Contents.USER.getUsername())
                    .params("sex",t3.getText().toString().equals("男")?"1":t3.getText().toString().equals("女")?"2":"")
                    .params("nickname",t1.getText().toString())
                    .params("age",t2.getText().toString())
                    .params("sex",t3.getText().toString())
                    .params("height",height)
                    .params("weight ",weight)
                    .params("address",t6.getText().toString())
                    .params("hoby",t7.getText().toString())
                    .params("autograph",t8.getText().toString())
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
                            Log.e(TAG, "call: 更改获得"+s);
                            Gson g = new Gson();
                            ChangeHBean bean = g.fromJson(s, ChangeHBean.class);
                            if (bean.getCode()==1){
                                ToastUtils.toast("修改成功");
                                Contents.USER.setSex(t3.getText().toString());
                                Contents.USER.setNickname(t1.getText().toString());
                                Contents.USER.setAge(t2.getText().toString());
                                Contents.USER.setHeight(height);
                                Contents.USER.setWeight(weight);
                                Contents.USER.setHoby(t7.getText().toString());
                                Contents.USER.setAutograph(t8.getText().toString());
                                Contents.USER.setAddress(t6.getText().toString());
                                Contents.USER.setPicture(path);
                                finish();
                            }else {
                                ToastUtils.toast("修改失败，请稍后重试");
                            }
                            finish();
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 更改失败"+throwable.toString() );
                        }
                    });
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeNick(ChangeNickEvent event) {
        isChanged=true;
        t1.setText(event.getContent());
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeHobby(ChangeHobbyEvent event) {
        isChanged=true;
        t7.setText(event.getHobby());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeMotto(ChangeMottoEvent event) {
        isChanged=true;
        t8.setText(event.getMotto());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_PHOTO:
                if (resultCode!=RESULT_OK)return;
                List<String> strings = Matisse.obtainPathResult(data);
                if (strings!=null&&strings.size()!=0){
                    path=strings.get(0);
                    Glide.with(getApplicationContext()).load(strings.get(0)).asBitmap().into(iconImg);
                }
                break;
        }
    }
}
