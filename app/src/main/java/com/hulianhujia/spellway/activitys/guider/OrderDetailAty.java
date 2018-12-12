package com.hulianhujia.spellway.activitys.guider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.ChooseRedAty;
import com.hulianhujia.spellway.activitys.NaviAty;
import com.hulianhujia.spellway.activitys.OverOrderAty;
import com.hulianhujia.spellway.activitys.guider.overlay.DrivingRouteOverlay;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.customViews.MapContainer;
import com.hulianhujia.spellway.event.ChooseRedEvent;
import com.hulianhujia.spellway.event.ImageReadyEvent;
import com.hulianhujia.spellway.event.RefreshEvent;
import com.hulianhujia.spellway.event.WxPaySuccessEvent;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.EndTravelBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.OrderDetailBean;
import com.hulianhujia.spellway.javaBeans.StartTravelBean;
import com.hulianhujia.spellway.javaBeans.WxBean;
import com.hulianhujia.spellway.untils.AMapUtil;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.OrderInfoUtil2_0;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class OrderDetailAty extends BaseActivity implements AMap.OnMyLocationChangeListener, AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener {


    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.loContainer)
    MapContainer loContainer;
    @Bind(R.id.tvinfo)
    TextView tvinfo;
    @Bind(R.id.tvOrderNum)
    TextView tvOrderNum;
    @Bind(R.id.dv11)
    TextView dv11;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.userPhone)
    TextView userPhone;
    @Bind(R.id.tv12)
    TextView tv12;
    @Bind(R.id.guiderPrice)
    TextView guiderPrice;
    @Bind(R.id.tv13)
    TextView tv13;
    @Bind(R.id.guideHourPrice)
    TextView guideHourPrice;
    @Bind(R.id.tv14)
    TextView tv14;
    @Bind(R.id.tvState)
    TextView tvState;
    @Bind(R.id.btGo)
    TextView btGo;
    @Bind(R.id.btStart)
    TextView btStart;
    @Bind(R.id.loReady)
    RelativeLayout loReady;
    @Bind(R.id.time)
    Chronometer time;
    @Bind(R.id.btFinishTravel)
    TextView btFinishTravel;
    @Bind(R.id.loRunning)
    RelativeLayout loRunning;
    @Bind(R.id.tvTime)
    TextView tvTime;
    @Bind(R.id.travel_Time)
    TextView travelTime;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    @Bind(R.id.travelPrice)
    TextView travelPrice;
    @Bind(R.id.dv)
    TextView dv;
    @Bind(R.id.guideState)
    TextView guideState;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.tvtv)
    TextView tvtv;
    @Bind(R.id.zfbCheck)
    CheckBox zfbCheck;
    @Bind(R.id.loZFB)
    RelativeLayout loZFB;
    @Bind(R.id.dv1)
    TextView dv1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.wxCheck)
    CheckBox wxCheck;
    @Bind(R.id.loWX)
    RelativeLayout loWX;
    @Bind(R.id.btConfrim)
    TextView btConfrim;
    @Bind(R.id.loPay)
    RelativeLayout loPay;
    @Bind(R.id.loCost)
    RelativeLayout loCost;
    @Bind(R.id.userIcon)
    CircleImageView userIcon;
    @Bind(R.id.userSex)
    ImageView userSex;
    @Bind(R.id.lo)
    RelativeLayout lo;
    @Bind(R.id.scroll)
    ScrollView scroll;
    @Bind(R.id.loBank)
    RelativeLayout loBank;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.tvtvtv)
    TextView tvtvtv;
    @Bind(R.id.bankCheck)
    CheckBox bankCheck;
    @Bind(R.id.tvBank)
    TextView tvBank;
    @Bind(R.id.dvi)
    TextView dvi;
    @Bind(R.id.tv7)
    RelativeLayout tv7;
    @Bind(R.id.red)
    TextView red;
    @Bind(R.id.tv8)
    TextView tv8;
    @Bind(R.id.userIcon2)
    CircleImageView userIcon2;
    @Bind(R.id.draw)
    RelativeLayout draw;
    @Bind(R.id.realPay)
    TextView realPay;
    private AMap aMap;
    private boolean isFrist = true;
    private RouteSearch routeSearch;
    private LatLng slatlng;
    private LatLng eLatlng;
    private DriveRouteResult mDriveRouteResult;
    private MapView mapView;
    private String odId;
    private String TAG = "info";
    private LoadingDialog dialog;
    private LoginBean.UserInfoBean userInfo;
    private int payType = 1;
    private long sec;
    private int type = 1;
    private int SDK_PAY_FLAG = 2;
    private String lat ;
    private String lon ;
    private String ava;
    private Bitmap b;
    private IWXAPI api;
    private boolean isRed=false;
    private String redId;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Map<String, String> result = (Map<String, String>) msg.obj;
            String status = result.get("resultStatus");
            Log.e(TAG, "handleMessage: " + status);
            if (status.equals("9000")) {

                getData();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            draw.buildDrawingCache();
            Bitmap bitmap = draw.getDrawingCache();
            initMarker(Double.parseDouble(lat), Double.parseDouble(lon), bitmap, 2);
        }
    };
    @Override
    public int getContentId() {
        return R.layout.activity_took_order_aty;
    }

    @Override
    public void initView() {
        btGo.setClickable(false);
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wx425716eb8696ec69");
        getUser();
        dialog = new LoadingDialog(this);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);
        odId = intent.getStringExtra("orderId");
        mapView = (MapView) findViewById(R.id.mapView);
        aMap = mapView.getMap();
        loContainer.setScrollView(scroll);
        getData();
//        initClickListener();
        initLoction();
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderDetailAty.this, ChooseRedAty.class));
            }
        });
    }

    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(this);
    }

    private void getData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.ORDER_DETAIL)
                .params("order_id", odId)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call:订单详情开始 " + odId);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 订单详情获得" + s);
                        Gson g = new Gson();
                        final OrderDetailBean bean = g.fromJson(s, OrderDetailBean.class);
                        if (bean.getCode() == 1) {
                            final OrderDetailBean.ReturnArrBean data = bean.getReturnArr().get(0);
                            tvOrderNum.setText(data.getOrder_no());
                            guideHourPrice.setText(data.getTime_fee());
                            guiderPrice.setText(data.getBase_fee());
                            if (type==1){
                                slatlng=new LatLng(Double.parseDouble(data.getGuidelat()),Double.parseDouble(data.getGuidelon()));
                            }else {
                                slatlng=new LatLng(Double.parseDouble(data.getVisitorlat()),Double.parseDouble(data.getVisitorlon()));
                            }
                            if (type == 1) {
                                ava=data.getGuidepic();
                                lat=data.getGuidelat();
                                lon=data.getGuidelon();
                                Glide.with(context).load(data.getGuidepic()).asBitmap().placeholder(R.mipmap.head_portrait).into(userIcon);
                                Glide.with(context).load(data.getGuidepic()).into(new GlideDrawableImageViewTarget(userIcon2){
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                        super.onResourceReady(resource, animation);
                                        handler2.sendEmptyMessageDelayed(2,3000);
                                    }
                                });
                                tvinfo.setText("导游信息");
                                name.setText(data.getGuidenick());
                                userPhone.setText(data.getGuidename());
                            } else {
                                ava=data.getVisitorpic();
                                lat=data.getVisitorlat();
                                lon=data.getVisitorlon();
                                Glide.with(context).load(data.getVisitorpic()).asBitmap().placeholder(R.mipmap.head_portrait).into(userIcon);
                                Glide.with(context).load(data.getVisitorpic()).into(new GlideDrawableImageViewTarget(userIcon2){
                                    @Override
                                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                        super.onResourceReady(resource, animation);
                                        handler2.sendEmptyMessageDelayed(2,2000);
//                                        EventBus.getDefault().post(new ImageReadyEvent(data.getVisitorlat(),data.getVisitorlon()));
                                    }
                                });
                                tvinfo.setText("游客信息");
                                name.setText(data.getVisitornick());
                                userPhone.setText(data.getUsername());
                            }
                            if (type == 1) {
                                if (data.getGuidepic().length() != 0 && data.getGuidepic() != null) {
                                    Glide.with(context).load(data.getGuidepic()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            Log.e(TAG, "onResourceReady: 11" + data.getGuidepic());
//                                            initMarker(Double.parseDouble(data.getGuidelat()), Double.parseDouble(data.getGuidelon()), BitmapFactory.decodeResource(getResources(), R.mipmap.smile), 1);
                                        }
                                    });
                                } else {
                                    Log.e(TAG, "call: 12");
                                    initMarker(Double.parseDouble(data.getGuidelat()), Double.parseDouble(data.getGuidelon()), BitmapFactory.decodeResource(getResources(), R.mipmap.smile), 1);
                                }
                            } else {
                                if (data.getVisitorpic().length() != 0 && data.getGuidepic() != null) {
                                    Log.e(TAG, "call:21 ");
                                    Glide.with(context).load(data.getVisitorpic()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                        }
                                    });
                                } else {
                                    Log.e(TAG, "call: 22");
                                    initMarker(Double.parseDouble(data.getVisitorlat()), Double.parseDouble(data.getVisitorlon()), BitmapFactory.decodeResource(getResources(), R.mipmap.smile), 2);
                                }
                            }
                            if (data.getStatus().equals("0")) {
                                if (type == 1) {
                                    tvState.setText("等待导游接单");
                                    loReady.setVisibility(View.GONE);
                                    loRunning.setVisibility(View.GONE);
                                    loCost.setVisibility(View.GONE);
                                } else {
                                    loReady.setVisibility(View.GONE);
                                    loRunning.setVisibility(View.GONE);
                                    loCost.setVisibility(View.GONE);
                                    tvState.setText("等待导游接单");
                                }
                            } else if (data.getStatus().equals("1")) {
                                if (type == 1) {
                                    Log.e(TAG, "call: 进这里");
                                    loReady.setVisibility(View.VISIBLE);
                                    loRunning.setVisibility(View.GONE);
                                    loCost.setVisibility(View.GONE);
                                    tvState.setText("导游已接单，等待游客开始旅行");
                                    btStart.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            OkGo.post(Urls.PUBLIC_URL + Urls.START_TRAVEL)
                                                    .params("order_id", odId)
                                                    .params("username", userInfo.getUsername())
                                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                                    .doOnSubscribe(new Action0() {
                                                        @Override
                                                        public void call() {
                                                            dialog.show();
                                                            Log.e(TAG, "call: +开始旅行开始");
                                                        }
                                                    }).observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<String>() {
                                                        @Override
                                                        public void call(String s) {
                                                            Log.e(TAG, "call: 开始旅行成功" + s);
                                                            Gson g = new Gson();
                                                            StartTravelBean travelBean = g.fromJson(s, StartTravelBean.class);
                                                            Log.e(TAG, "call: 特征码" + travelBean.getCode());
                                                            if (travelBean.getCode() == 1) {
                                                                dialog.dismiss();
                                                                ToastUtils.toast("开始旅行");
                                                                Log.e(TAG, "call: 开始旅行code=1");
                                                                getData();
                                                            } else {
                                                                ToastUtils.toast(travelBean.getMsg());
                                                            }
                                                        }
                                                    }, new Action1<Throwable>() {
                                                        @Override
                                                        public void call(Throwable throwable) {
                                                            Log.e(TAG, "call: 开始旅行错误" + throwable.toString());
                                                        }
                                                    });
                                        }
                                    });
                                } else if (type == 2) {
                                    loReady.setVisibility(View.GONE);
                                    loRunning.setVisibility(View.GONE);
                                    loCost.setVisibility(View.GONE);
                                    tvState.setText("导游已接单，等待游客开始旅行");
                                }
                            } else if (data.getStatus().equals("2")) {
                                loReady.setVisibility(View.GONE);
                                loRunning.setVisibility(View.GONE);
                                loCost.setVisibility(View.GONE);
                                tvState.setText("导游已拒绝");
                            } else if (data.getStatus().equals("3")) {
                                loReady.setVisibility(View.GONE);
                                loRunning.setVisibility(View.VISIBLE);
                                loCost.setVisibility(View.GONE);
                                tvState.setText("旅行已开始");
                                if (type == 1) {
                                    btFinishTravel.setVisibility(View.GONE);
                                } else {
                                    btFinishTravel.setVisibility(View.VISIBLE);
                                    btFinishTravel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            OkGo.post(Urls.PUBLIC_URL + Urls.END_TRAVEL)
                                                    .params("username", userInfo.getUsername())
                                                    .params("order_id", odId)
                                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                                    .doOnSubscribe(new Action0() {
                                                        @Override
                                                        public void call() {
                                                            dialog.show();
                                                            Log.e(TAG, "call:结束旅游开始 " + odId);
                                                        }
                                                    }).observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<String>() {
                                                        @Override
                                                        public void call(String s) {
                                                            dialog.dismiss();
                                                            Log.e(TAG, "call: 结束旅游获得" + s);
                                                            Gson g = new Gson();
                                                            EndTravelBean endBean = g.fromJson(s, EndTravelBean.class);
                                                            if (endBean.getCode() == 1) {
                                                                ToastUtils.toast("旅游结束了！！！");
                                                                getData();
                                                            }
                                                        }
                                                    }, new Action1<Throwable>() {
                                                        @Override
                                                        public void call(Throwable throwable) {
                                                            dialog.dismiss();
                                                            Log.e(TAG, "call:结束旅游错误 " + throwable.toString());
                                                        }
                                                    });
                                        }
                                    });
                                }
                                sec = MyUtils.secLeft(Long.parseLong(data.getUpdate_time()));
                                time.start();
                                time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

                                    @Override
                                    public void onChronometerTick(Chronometer ch) {
                                        if (sec < 0) {
                                            sec = 0;
                                        }
                                        sec++;
                                        ch.setText(FormatMiss(sec));
                                    }

                                });
                            } else if (data.getStatus().equals("4")) {
                                String startTime = data.getUpdate_time();
                                String endTime = data.getEnd_time();
                                long sl = Long.parseLong(startTime);
                                final long el = Long.parseLong(endTime);
                                long totalSec = el - sl;
                                long h = totalSec / 3600;
                                long min = totalSec / 60 % 60;
                                travelTime.setText(h + "小时" + min + "分");
                                travelPrice.setText(data.getTotal_fee());
                                realPay.setText(data.getTotal_fee());
                                if (type == 1) {
                                    tv7.setClickable(true);
                                    tv7.setVisibility(View.VISIBLE);
                                    tv8.setVisibility(View.VISIBLE);
                                    realPay.setVisibility(View.VISIBLE);
                                    /*未支付*/
                                    if (Double.parseDouble(Contents.USER.getAmount()) < Double.parseDouble(data.getTotal_fee())) {
                                        tvBank.setText("您的余额不足啦，请选择其他两种支付方式");
                                        loBank.setClickable(false);
                                    } else {
                                        tvBank.setText("使用您的余额支付");
                                        loBank.setClickable(true);
                                        loBank.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                payType = 3;
                                                zfbCheck.setChecked(false);
                                                wxCheck.setChecked(false);
                                                bankCheck.setChecked(true);
                                            }
                                        });
                                    }
                                    if (data.getPaystatus().equals("0")) {
                                        loReady.setVisibility(View.GONE);
                                        loRunning.setVisibility(View.GONE);
                                        loCost.setVisibility(View.VISIBLE);
                                        loPay.setVisibility(View.VISIBLE);
                                        guideState.setVisibility(View.GONE);
                                        tvState.setText("等待付款");
                                        loZFB.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                payType = 1;
                                                zfbCheck.setChecked(true);
                                                wxCheck.setChecked(false);
                                                bankCheck.setChecked(false);

                                            }
                                        });
                                        loWX.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                payType = 2;
                                                zfbCheck.setChecked(false);
                                                wxCheck.setChecked(true);
                                                bankCheck.setChecked(false);

                                            }
                                        });

                                        btConfrim.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                switch (payType) {
                                                    /*支付宝支付*/
                                                    case 1:
                                                        if (isRed){
                                                            payAli(data,redId);
                                                        }else {
                                                            payAli(data);
                                                        }
                                                        break;
                                                    /*微信支付*/
                                                    case 2:
                                                        if (isRed){
                                                            PayWx(data,redId);
                                                        }else {
                                                            PayWx(data);
                                                        }
                                                        break;
                                                    case 3:
                                                        if (isRed){
                                                            payLeft(data,redId);
                                                        }else {
                                                            payLeft(data);
                                                        }

                                                        break;
                                                    case 0:
                                                        ToastUtils.toast("请选择支付方式");
                                                        break;
                                                }
                                            }
                                        });
                                    } else if (data.getPaystatus().equals("2")) {
                                        Intent intent = new Intent(OrderDetailAty.this, OverOrderAty.class);
                                        intent.putExtra("oid", data.getOrder_id());
                                        startActivity(intent);
                                        finish();
                                    }
                                } else if (type==2){
                                    tv7.setClickable(false);
                                    tv7.setVisibility(View.GONE);
                                    tv8.setVisibility(View.GONE);
                                    realPay.setVisibility(View.GONE);
                                    if (data.getPaystatus().equals("0")) {
                                        Log.e(TAG, "call: 付款1");
                                        loReady.setVisibility(View.GONE);
                                        loRunning.setVisibility(View.GONE);
                                        loCost.setVisibility(View.VISIBLE);
                                        loPay.setVisibility(View.GONE);
                                        guideState.setVisibility(View.VISIBLE);
                                        tvState.setText("等待付款");
                                    } else if (data.getPaystatus().equals("2")) {
                                        Log.e(TAG, "call: 付款2");
                                        loReady.setVisibility(View.GONE);
                                        loRunning.setVisibility(View.GONE);
                                        loCost.setVisibility(View.VISIBLE);
                                        loPay.setVisibility(View.GONE);
                                        guideState.setVisibility(View.VISIBLE);
                                        guideState.setText("游客已付款");
                                        tvState.setText("已付款");

                                        loContainer.setVisibility(View.GONE);
                                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) dvi.getLayoutParams();
                                        lp.height=200;
                                    }
                                }
                            } else if (data.getStatus().equals("6")) {
                                if (type==2){
                                    Log.e(TAG, "call: 付款2");
                                    loReady.setVisibility(View.GONE);
                                    loRunning.setVisibility(View.GONE);
                                    loCost.setVisibility(View.VISIBLE);
                                    loPay.setVisibility(View.GONE);
                                    guideState.setVisibility(View.VISIBLE);
                                    guideState.setText("订单已完成");
                                    tvState.setText("订单已完成");

                                    loContainer.setVisibility(View.GONE);
                                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) dvi.getLayoutParams();
                                    lp.height=200;
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 订单详情错误" + throwable.toString());
                    }
                });
    }
    /*余额支付，不含红包*/
    private void payLeft(final OrderDetailBean.ReturnArrBean data) {
        OkGo.post(Urls.PUBLIC_URL + Urls.PAY_BANK)
                .params("order_no", data.getOrder_no())
                .params("amount", data.getTotal_fee())
                .params("body", "支付导游")
                .params("subject", "旅游")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始余额付款");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 余额支付获得" + s);
                        Gson g = new Gson();
                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                        ToastUtils.toast(baseBean.getMsg());
                        if (baseBean.getCode() == 1) {
                            getData();
                            if (type==1){
                                String amount = Contents.USER.getAmount();
                                double fee = Double.parseDouble(data.getTotal_fee());
                                double v = Double.parseDouble(amount);
                                DecimalFormat df = new DecimalFormat("0.00");
                                String format = df.format(v-fee);
                                Contents.USER.setAmount(format);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 余额支付错误" + throwable.toString());
                    }
                });
    }
    /*余额支付，含红包*/
    private void payLeft(final OrderDetailBean.ReturnArrBean data, String rid) {
        OkGo.post(Urls.PUBLIC_URL + Urls.PAY_BANK)
                .params("order_no", data.getOrder_no())
                .params("amount", data.getTotal_fee())
                .params("body", "支付导游")
                .params("subject", "旅游")
                .params("coupon_id",rid)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始余额付款");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 余额支付获得" + s);
                        Gson g = new Gson();
                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                        ToastUtils.toast(baseBean.getMsg());
                        if (baseBean.getCode() == 1) {
                            double fee = Double.parseDouble(realPay.getText().toString());
                            if (type==1){
                                String amount = Contents.USER.getAmount();
                                double v = Double.parseDouble(amount);
                                DecimalFormat df = new DecimalFormat("0.00");
                                String format = df.format(v-fee);
                                Contents.USER.setAmount(format);
                            }
                            getData();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 余额支付错误" + throwable.toString());
                    }
                });
    }

    /*微信支付，不含红包*/
    private void PayWx(final OrderDetailBean.ReturnArrBean data) {
        OkGo.post(Urls.PUBLIC_URL + Urls.PAY_WX)
                .params("order_no", data.getOrder_no())
                .params("amount", data.getTotal_fee())
                .params("body", "支付导游")
                .params("subject", "旅游")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 微信支付开始访问后台" + data.getOrder_no());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 微信支付后台返回" + s);
                        Gson g = new Gson();
                        WxBean wxBean = g.fromJson(s, WxBean.class);
                        PayReq req = new PayReq();
                        req.appId = wxBean.getAppid();
                        req.appId = wxBean.getAppid();
                        req.partnerId = wxBean.getPartnerid();
                        req.prepayId = wxBean.getPrepayid();
                        req.nonceStr = wxBean.getNoncestr();
                        req.timeStamp = wxBean.getTimestamp() + "";
                        req.packageValue = wxBean.getPackageX();
                        req.sign = wxBean.getSign();
//                                                                        req.extData			= "app data"; // optional
//                                                                        Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 错误" + throwable.toString());
                    }
                });
    }
    /*微信支付，含红包*/
    private void PayWx(final OrderDetailBean.ReturnArrBean data,String rid) {
        OkGo.post(Urls.PUBLIC_URL + Urls.PAY_WX)
                .params("order_no", data.getOrder_no())
                .params("amount", data.getTotal_fee())
                .params("body", "支付导游")
                .params("subject", "旅游")
                .params("coupon_id",rid)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 微信支付开始访问后台" + data.getOrder_no());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 微信支付后台返回" + s);
                        Gson g = new Gson();
                        WxBean wxBean = g.fromJson(s, WxBean.class);
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId = wxBean.getAppid();
                        req.appId = wxBean.getAppid();
                        req.partnerId = wxBean.getPartnerid();
                        req.prepayId = wxBean.getPrepayid();
                        req.nonceStr = wxBean.getNoncestr();
                        req.timeStamp = wxBean.getTimestamp() + "";
                        req.packageValue = wxBean.getPackageX();
                        req.sign = wxBean.getSign();
//                                                                        req.extData			= "app data"; // optional
//                                                                        Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 错误" + throwable.toString());
                    }
                });
    }

    /*支付宝支付，不含红包*/
    private void payAli(final OrderDetailBean.ReturnArrBean data) {
        String orderMap = OrderInfoUtil2_0.builBizcontent("拼途", "旅游", data.getOrder_no(), data.getTotal_fee());
        String timestr = String.valueOf(System.currentTimeMillis());
        Log.e(TAG, "onClick: " + timestr);
        long timeStamp = 0;
        if (timestr.length() >= 11) {
            timeStamp = Long.parseLong(timestr) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = sdf.format(timeStamp * 1000);
        Log.e(TAG, "onClick: 时间" + date);
        Map<String, String> zfp = OrderInfoUtil2_0.buildOrderParamMap(date, orderMap);
        final String orderParam = OrderInfoUtil2_0.buildOrderParam(zfp);
        OkGo.post(Urls.PUBLIC_URL + Urls.APIPAY)
                .params("order_no", data.getOrder_no())
                .params("amount", data.getTotal_fee())
                .params("body", "支付导游")
                .params("subject", "旅游")
//                                                                .params("coupon_id",3)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 支付加签开始" + data.getTotal_fee());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Log.e(TAG, "call: 支付加签signData                           " + s);
                        Log.e(TAG, "call: 支付加签orderParam                        " + orderParam);
                        final String orderInfo = orderParam + "&sign=" + s;
                        Log.e(TAG, "call: " + orderInfo);
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(OrderDetailAty.this);
                                Map<String, String> result = alipay.payV2(s, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                handler.sendMessage(msg);
                            }
                        };
                        Thread t = new Thread(r);
                        t.start();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 支付加签错误" + throwable.toString());
                    }
                });
    }
    /*支付宝支付，含红包*/
    private void payAli(final OrderDetailBean.ReturnArrBean data,String rid) {
        String orderMap = OrderInfoUtil2_0.builBizcontent("拼途", "旅游", data.getOrder_no(), data.getTotal_fee());
        String timestr = String.valueOf(System.currentTimeMillis());
        Log.e(TAG, "onClick: " + timestr);
        long timeStamp = 0;
        if (timestr.length() >= 11) {
            timeStamp = Long.parseLong(timestr) / (long) 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = sdf.format(timeStamp * 1000);
        Log.e(TAG, "onClick: 时间" + date);
        Map<String, String> zfp = OrderInfoUtil2_0.buildOrderParamMap(date, orderMap);
        final String orderParam = OrderInfoUtil2_0.buildOrderParam(zfp);
        OkGo.post(Urls.PUBLIC_URL + Urls.APIPAY)
                .params("order_no", data.getOrder_no())
                .params("amount", data.getTotal_fee())
                .params("body", "支付导游")
                .params("subject", "旅游")
                .params("coupon_id",rid)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 支付加签开始" + data.getTotal_fee());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(final String s) {
                        Log.e(TAG, "call: 支付加签signData                           " + s);
                        Log.e(TAG, "call: 支付加签orderParam                        " + orderParam);
                        final String orderInfo = orderParam + "&sign=" + s;
                        Log.e(TAG, "call: " + orderInfo);
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(OrderDetailAty.this);
                                Map<String, String> result = alipay.payV2(s, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                handler.sendMessage(msg);
                            }
                        };
                        Thread t = new Thread(r);
                        t.start();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 支付加签错误" + throwable.toString());
                    }
                });
    }



    public String FormatMiss(long miss) {
        String hh = miss / 3600 > 9 ? miss / 3600 + "" : "0" + miss / 3600;
        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600) / 60;
        String ss = (miss % 3600) % 60 > 9 ? (miss % 3600) % 60 + "" : "0" + (miss % 3600) % 60;
        return hh + ":" + mm + ":" + ss;
    }

    private void initRoute() {
        NaviLatLng snaviLatLng = new NaviLatLng(slatlng.latitude, slatlng.longitude);
        NaviLatLng enaviLatLng = new NaviLatLng(eLatlng.latitude, eLatlng.longitude);
        Intent i = new Intent(this, NaviAty.class);
        i.putExtra("s", snaviLatLng);
        i.putExtra("e", enaviLatLng);
        startActivity(i);
    }

    private void initMarker(double eLat, double eLon, Bitmap bitmap, int type) {
        aMap.clear();
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(slatlng).title("蛙泽发").snippet("细节说明").period(1);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
        eLatlng = new LatLng(eLat, eLon);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(eLatlng);
        markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(bitmap));

        ArrayList<MarkerOptions> lists = new ArrayList<>();
//        lists.add(markerOptions);
        lists.add(markerOptions2);
        aMap.addMarkers(lists, true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
//         Marker marker = aMap.addMarker(new MarkerOptions().position(slatlng).title("北京").snippet("DefaultMarker"));
        aMap.setOnMarkerClickListener(this);
        btGo.setClickable(true);
    }

    private void initLoction() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//只定位一次。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//只定位一次。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        myLocationStyle.showMyLocation(true);
//        aMap.setMinZoomLevel(12);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        aMap.setOnMyLocationChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler2.removeMessages(2);
        mapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onMyLocationChange(Location location) {
        if (isFrist) {
            isFrist = !isFrist;
            LatLng lat = new LatLng(location.getLatitude(), location.getLongitude());
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(lat));
        }
/*        Log.i("infoo", location.getLatitude() + "sss" + location.getLongitude());
        double latitude = location.getLatitude();
        double lontitue = location.getLongitude();
        slatlng = new LatLng(latitude, lontitue);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(slatlng));*/
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        MarkerOptions options = marker.getOptions();
        Toast.makeText(getApplicationContext(), options.getPeriod() + "", Toast.LENGTH_SHORT).show();
        return false;
    }

    /*路线规划回调*/
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            getApplicationContext(), aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
//                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
//                    mRotueTimeDes.setText(des);
//                    mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
//                    mRouteDetailDes.setText("打车约"+taxiCost+"元");
/*                    mBottomLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });*/
                } else if (result != null && result.getPaths() == null) {
                }
            } else {
            }
        } else {
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void refreshView(RefreshEvent event) {
        getData();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handWxpay(WxPaySuccessEvent e) {
        getData();
    }

    @OnClick({R.id.btExit, R.id.btGo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
/*                draw.buildDrawingCache();
                Bitmap bitmap = draw.getDrawingCache();
                initMarker(Double.parseDouble(lat), Double.parseDouble(lon), bitmap, 2);*/
                finish();
                break;
            case R.id.btGo:
                initRoute();
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public  void  handImage(ImageReadyEvent event){
            draw.buildDrawingCache();
            Bitmap bitmap = draw.getDrawingCache();
            initMarker(Double.parseDouble(event.getLat()), Double.parseDouble(event.getLon()), bitmap, 2);
            draw.buildDrawingCache();
            Bitmap bitmap2 = draw.getDrawingCache();
            initMarker(Double.parseDouble(event.getLat()), Double.parseDouble(event.getLon()), bitmap2, 2);

    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeRed(ChooseRedEvent event){
        red.setText(event.getPrice());
        Log.e(TAG, "changeRed: "+Double.parseDouble(travelPrice.getText().toString())+""+event.getPrice());
//        realPay.setText((Double.parseDouble(travelPrice.getText().toString())-Double.parseDouble(event.getPrice()))+"");
        realPay.setText(sub(travelPrice.getText().toString(),event.getPrice()));
        isRed=true;
        redId=event.getId();
    }
    public static String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        DecimalFormat df = new DecimalFormat("0.00");
        String format = df.format(new Double(b1.subtract(b2).doubleValue()));
        return format;
    }
}
