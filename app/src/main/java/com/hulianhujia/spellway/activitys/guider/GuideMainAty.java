package com.hulianhujia.spellway.activitys.guider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.MyOrderAty;
import com.hulianhujia.spellway.adpaters.GuiderOrderListViewAdp;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.event.FinishMainEvent;
import com.hulianhujia.spellway.event.RefreshGuideListEvent;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.GuideYJBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.OrderBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuideMainAty extends BaseActivity implements AdapterView.OnItemClickListener, TabLayout.OnTabSelectedListener {
    @Bind(R.id.btSelf)
    ImageView btSelf;
    @Bind(R.id.btMessage)
    ImageView btMessage;
    @Bind(R.id.servePoint)
    TextView servePoint;
    @Bind(R.id.todayNum)
    TextView todayNum;
    @Bind(R.id.todayMoney)
    TextView todayMoney;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.listView)
    SwipeListView listView;
    public static String Lon;
    private int pageType=1;
    public static String Lat;
    private LoginBean.UserInfoBean userInfo;
    private String TAG = "info";
    private List<OrderBean.ReturnArrBean> datas = new ArrayList<>();
    private GuiderOrderListViewAdp adp;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener ;
    //监听配置
    public AMapLocationClientOption mLocationOption = null;
    @Override
    public int getContentId() {
        return R.layout.activity_guide_main_aty;
    }
    @Override
    public void initView() {
        Log.e(TAG, "initView: 一次" );
        EventBus.getDefault().register(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                ||ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }else{

        }
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(this);
        adp = new GuiderOrderListViewAdp(datas, this);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
        tabLayout.addOnTabSelectedListener(this);
        setList();
        initJpsh();
        initLocation();
        initUserInfo();
        getData();
    }
    private void setList() {
        listView.setSwipeListViewListener(new BaseSwipeListViewListener(){
            @Override
            public void onClickBackView(final int position) {
                super.onClickBackView(position);
                ConfirmDialogN c = new ConfirmDialogN(GuideMainAty.this);
                c.setFlag(1);
                c.setYesListener(new YesListener() {
                    @Override
                    public void yes(int flag) {
                        OkGo.post(Urls.PUBLIC_URL+Urls.DELET_ORDER)
                                .params("username", Contents.GUIDE.getUsername())
                                .params("password",getSharedPreferences("login",MODE_PRIVATE).getString("pwd",null))
                                .params("order_id",datas.get(position).getOrder_id())
                                .getCall(StringConvert.create(),RxAdapter.<String>create())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        Log.e(TAG, "call: 删除开始" );
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        Log.e(TAG, "call: 删除获得" + s);
                                        Gson g = new Gson();
                                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                        if (baseBean.getCode() == 1) {
                                            datas.remove(position);
                                            adp.notifyDataSetChanged();
                                            listView.closeOpenedItems();
                                        }
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e(TAG, "call: 删除错误"+throwable.toString() );
                                    }
                                });
                    }
                });
                c.show();
                c.setTitle("确认删除此订单？");
            }
            @Override
            public void onClickFrontView(int position) {
                super.onClickFrontView(position);
                if (datas.get(position).getStatus().equals("2")){
                    ToastUtils.toast("此单已经被您拒绝");
                }else {
                    Intent intent = new Intent(GuideMainAty.this, OrderDetailAty.class);
                    intent.putExtra("orderId", datas.get(position).getOrder_id());
                    intent.putExtra("type", 2);
                    startActivity(intent);
                }
            }
            @Override
            public void onListChanged() {
                super.onListChanged();
                listView.closeOpenedItems();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void initJpsh() {
        JPushInterface.setAlias(this, userInfo.getUsername() + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e(TAG, "别名设置成功"+userInfo.getUsername());
            }
        });
    }
    private void setDuty(){
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
            OkGo.post(Urls.PUBLIC_URL+Urls.IS_ONDUTY)
                    .params("guidename",Contents.GUIDE.getUsername())
                    .params("onduty",2)
                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 设置权限" );
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Log.e(TAG, "call: 权限获得" + s);
                            Gson g = new Gson();
                            BaseBean baseBean = g.fromJson(s, BaseBean.class);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e(TAG, "call: 权限错误"+throwable.toString() );
                        }
                    });
        }
    }
    private boolean jude(String str){
        if (str==null){
            return true;
        }else {
            return str.equals("");
        }
    }
    private void initUserInfo() {
        OkGo.post(Urls.PUBLIC_URL+Urls.INIT)
                .params("myusername",userInfo.getUsername())
                .params("username",userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 初始化开始" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 初始化获得" + s);
                        Gson g=new Gson();
                        UserInfoBean bean = g.fromJson(s, UserInfoBean.class);
                        Contents.GUIDE=bean.getReturnArr();
                        Contents.USER=bean.getReturnArr();
                        setDuty();
                        SharedUtils.saveUser(GuideMainAty.this,bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 初始化失败"+throwable.toString() );
                    }
                });
        OkGo.get(Urls.PUBLIC_URL+Urls.GUIDE_POINT)
                .params("guidename",userInfo.getUsername())
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获得开始" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 获得业绩" + s);
                        Gson g = new Gson();
                        GuideYJBean bean = g.fromJson(s, GuideYJBean.class);
                        if (bean.getCode()==1){
                            DecimalFormat df = new DecimalFormat("0.00");
                            String format = df.format(Double.parseDouble(bean.getReturnArr().getAverage_score()));
                            servePoint.setText(format);
                            todayNum.setText(bean.getReturnArr().getCountorder());
                            todayMoney.setText("￥"+bean.getReturnArr().getTotalincome());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 业绩错误"+throwable.toString());
                    }
                });
    }

    private void initLocation() {
        mLocationListener=new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                double longitude = aMapLocation.getLongitude();
                double latitude = aMapLocation.getLatitude();
                Lon=longitude+"";
                Lat=latitude+"";
                Log.e(TAG, "onLocationChanged: "+longitude+"==="+latitude );
                OkGo.post(Urls.PUBLIC_URL+Urls.POST_LATLONG)
                        .params("lon",longitude)
                        .params("lat",latitude)
                        .params("username",userInfo.getUsername())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 传经纬度开始" );
                            }
                        }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 穿经纬度返回" + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 穿经纬度错误"+throwable );
                    }
                });
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption=new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (datas.get(position).getStatus().equals("2")){
            ToastUtils.toast("此单已经被你拒绝");
        }else {
            Intent intent = new Intent(this, OrderDetailAty.class);
            intent.putExtra("orderId", datas.get(position).getOrder_id());
            intent.putExtra("type", 2);
            startActivity(intent);
        }
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                getData();
                pageType=1;
                break;
            case 1:
                datas.clear();
                pageType=2;
                adp.notifyDataSetChanged();
                getCouldOrder();
                break;
            case 2:
                datas.clear();
                pageType=3;
                getGOTOrder();
                break;
            case 3:
                datas.clear();
                pageType=4;
                adp.notifyDataSetChanged();
                getOverData();
                break;
        }
    }
    private void getData() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_ORDERLIST)
                .params("guidename", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 获取订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call:获取订单列表返回 " + s);
                        Gson g = new Gson();
                        loadingDialog.dismiss();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取订单列表错误" + throwable.toString());
                    }
                });
    }
    private void getOverData() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.OVER_ORDER)
                .params("username", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 获取完成订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call:获取完成订单列表返回 " + s);
                        Gson g = new Gson();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取完成订单列表错误" + throwable.toString());
                    }
                });
    }
    private void getGOTOrder() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.GOTORDER)
                .params("username", userInfo.getUsername())
                .params("p",1)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 获取已接单订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call:获取已接单订单列表返回 " + s);
                        Gson g = new Gson();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取完成订单列表错误" + throwable.toString());
                    }
                });
    }

    private void getCouldOrder() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.GOT_ORDERLIST)
                .params("guidename", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 获取完成订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call:获取完成订单列表返回 " + s);
                        Gson g = new Gson();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 获取完成订单列表错误" + throwable.toString());
                    }
                });
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    @OnClick({R.id.btSelf, R.id.btMessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSelf:
                Intent i = new Intent(GuideMainAty.this,GuideSelfAty.class);
                startActivity(i);
                break;
            case R.id.btMessage:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handRefresh(RefreshGuideListEvent event){
        switch (pageType){
            case 1:
                getData();
                break;
            case 2:
                getCouldOrder();
                break;
            case 3:
                getGOTOrder();
                break;
            case 4:
                getOverData();
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handFinish(FinishMainEvent event){
        finish();
    }
}
