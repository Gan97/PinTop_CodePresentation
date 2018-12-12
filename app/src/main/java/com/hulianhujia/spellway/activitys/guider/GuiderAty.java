package com.hulianhujia.spellway.activitys.guider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.fragment.GudierSelfFgm;
import com.hulianhujia.spellway.activitys.guider.fragment.GuiderOrderFgm;
import com.hulianhujia.spellway.adpaters.GuiderViewPagerAdp;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuiderAty extends BaseActivity {
    @Bind(R.id.bottomBar)
    LinearLayout bottomBar;
    @Bind(R.id.dv)
    TextView dv;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.btOrder)
    TextView btOrder;
    @Bind(R.id.btMyself)
    TextView btMyself;
    private List<Fragment> fragmentList=new ArrayList<>();
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener ;
    //监听配置
    public AMapLocationClientOption mLocationOption = null;

    private LoginBean.UserInfoBean userInfo;
    private String TAG="info";
    public static String Lon;
    public static String Lat;

    @Override
    public void initView() {
        getUser();
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
        initJpsh();
        initLocation();
        initUserInfo();
        initFgm();
    }
    private void initJpsh() {
        JPushInterface.setAlias(this, userInfo.getUsername() + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e(TAG, "别名设置成功"+userInfo.getUsername());
            }
        });
    }
    private void initUserInfo() {
        OkGo.post(Urls.PUBLIC_URL+Urls.INIT)
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
                        SharedUtils.saveUser(GuiderAty.this,bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 初始化失败"+throwable.toString() );
                    }
                });
    }

    private void getUser() {
        userInfo= (LoginBean.UserInfoBean) SharedUtils.readObject(this);
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
    private void initFgm() {
        GuiderOrderFgm guiderOrderFgm = new GuiderOrderFgm();
        GudierSelfFgm gudierSelfFgm = new GudierSelfFgm();
        fragmentList.add(guiderOrderFgm);
        fragmentList.add(gudierSelfFgm);
        GuiderViewPagerAdp adp = new GuiderViewPagerAdp(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adp);
    }

    @Override
    public int getContentId() {
        return R.layout.activity_guider_aty;
    }


    @OnClick({R.id.btOrder, R.id.btMyself})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btOrder:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btMyself:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
