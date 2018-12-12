package com.hulianhujia.spellway;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.hulianhujia.spellway.event.FinishMainEvent;
import com.hulianhujia.spellway.event.RefreshGuideListEvent;
import com.hulianhujia.spellway.fragments.CommunityFgm;
import com.hulianhujia.spellway.fragments.GuideFgm;
import com.hulianhujia.spellway.fragments.HomeFgm;
import com.hulianhujia.spellway.fragments.SelfFgm;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.UpdataPositionBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends BaseActivity {
    @Bind(R.id.bt_home)
    RadioButton btHome;
    @Bind(R.id.bt_community)
    RadioButton btCommunity;
    @Bind(R.id.bt_guide)
    RadioButton btGuide;
    @Bind(R.id.bt_mine)
    RadioButton btMine;
    @Bind(R.id.rgHomeTab)
    RadioGroup rgHomeTab;
    @Bind(R.id.rp)
    FrameLayout rp;
    private List<Fragment> mFrags;
    private HomeFgm homeFgm;
    private CommunityFgm communityFgm;
    private GuideFgm guideFgm;
    private SelfFgm selfFgm;
    private Fragment mForm;
    private String TAG="info";
    private int page=1;
    private int exitFlag=1;
    private int isExit=0;
    private LoginBean.UserInfoBean userInfo;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener ;
    //监听配置
    public AMapLocationClientOption mLocationOption = null;
    public static String LAT;
    public static String Lon;
    private String id;
    public static String city=null;
    private String TAG2="infoo";
    /*一楼祭天*/
    @Override
    public int getContentId() {
        return R.layout.activity_main;
    }
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        getUser();
        initJpsh();
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
        initLocation();
        mFrags = new ArrayList<>();
        homeFgm = new HomeFgm();
        communityFgm = new CommunityFgm();
        guideFgm = new GuideFgm();
        selfFgm = new SelfFgm();
        mFrags.add(homeFgm);
        mFrags.add(communityFgm);
        mFrags.add(guideFgm);
        mFrags.add(selfFgm);
        rgHomeTab.setOnCheckedChangeListener(tabListener);
        switchPage(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        ToastUtils.toast("再按一次退出应用");
        if (isExit==1){
            MyUtils.AtyContainer.getInstance().finishAllActivity();
        }
        isExit=1;
        Timer timer =new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                isExit=0;
            }
        };
        timer.schedule(timerTask,2000);
    }

    private void initJpsh() {
        JPushInterface.setAlias(this, userInfo.getUsername() + "", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.e(TAG, "别名设置成功"+userInfo.getUsername());
            }
        });
    }
    private void getUser() {
        userInfo= (LoginBean.UserInfoBean) SharedUtils.readObject(getApplicationContext());
    }
    @Override
    public void initData() {
        initUserInfo();
        initListData();
    }
    private void initLocation() {
        mLocationListener=new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
                double longitude = aMapLocation.getLongitude();
                double latitude = aMapLocation.getLatitude();
                Lon=longitude+"";
                MyUtils.writeLoc(getApplicationContext(),latitude+"",longitude+"");
                LAT=latitude+"";
                SharedUtils.saveLat(MainActivity.this,latitude+"");
                SharedUtils.saveLon(MainActivity.this,longitude+"");
                Contents.LOCATION=aMapLocation.getCity();
                Log.e(TAG, "onLocationChanged: "+longitude+"==="+latitude+"地点"+aMapLocation.getCity());
                OkGo.post(Urls.PUBLIC_URL+Urls.POST_LATLONG)
                        .params("lon",longitude)
                        .params("lat",latitude)
                        .params("username",userInfo.getUsername())
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 传经纬度开始" );
                            }
                        }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 穿经纬度返回" + s);
                        Gson g = new Gson();
                        aMapLocation.getCity();
                        UpdataPositionBean bean = g.fromJson(s, UpdataPositionBean.class);
                        if (bean.getCode()==1){
                            EventBus.getDefault().postSticky(new RefreshGuideListEvent());
                        }
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
    private void initListData() {

    }
    private void initUserInfo() {
        OkGo.post(Urls.PUBLIC_URL+Urls.INIT)
                .params("username",userInfo.getUsername())
                .params("myusername",userInfo.getUsername())
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
                        if (bean.getCode()==1){
                            Contents.USER=bean.getReturnArr();
                            Contents.GUIDE=bean.getReturnArr();
                            SharedUtils.saveUser(MainActivity.this,bean);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 初始化失败"+throwable.toString() );
                    }
                });
    }
    private RadioGroup.OnCheckedChangeListener tabListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch(group.getCheckedRadioButtonId()) {
                case R.id.bt_home:
                    switchPage(0);
                    break;
                case R.id.bt_community:
                    switchPage(1);
                    break;
                case R.id.bt_guide:
                    switchPage(2);
                    break;
                case R.id.bt_mine:
                    switchPage(3);
                    break;
            }
        }
    };
    
    private void switchPage(int position) {
        Fragment to = mFrags.get(position);
        if (to == mForm) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (to.isAdded()) {
            transaction.show(to);
        } else {
            transaction.add(R.id.rp,to);
        }
        if (mForm != null)
            transaction.hide(mForm);
        transaction.commit();
         mForm = to;
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handFinish(FinishMainEvent event){
        finish();
    }
}
