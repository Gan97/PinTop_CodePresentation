package com.hulianhujia.spellway;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.customViews.GlideImageLoader;
import com.hulianhujia.spellway.ease.EaseUt;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
/**
 * Created by FHP on 2017/5/2.
 */
public class MyApplication extends Application {
    private static Context context;
    private Context appContext;
    private EaseUser currentUser;
    private String TAG2="infooo";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        initHX();
        initGally();
        ToastUtils.initToastUtils(this);
        context = getApplicationContext();
        Log.e(TAG, "onCreate: 初始化SMs" );
        ShareSDK.initSDK(this);
/*        SMSSDK.initSDK(this,"1f4152f059162", "b55b060e4090e3e0ae116a2df7dac302");*/
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
        OkGo.init(this);
        OkGo.getInstance().setConnectTimeout(10000)
                .setRetryCount(3)
        .setCookieStore(new PersistentCookieStore());
        NineGridView.setImageLoader(new NineGridView.ImageLoader() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                Glide.with(context).load(url).placeholder(R.drawable.loading)
                        .into(imageView);
            }
            @Override
            public Bitmap getCacheImage(String url) {
                return null;
            }
        });
    }
    private void initGally() {
        ThemeConfig theme = new ThemeConfig.Builder()
        .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(false)
                .setEnableCrop(true)
                .setCropSquare(false)
                .setEnableRotate(true)
                .setEnablePreview(true)
                .build();
        GlideImageLoader imageLoader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this,imageLoader,theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }
    private void initHX() {
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAutoLogin(false);
//初始化
        appContext = this;
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
// 如果APP启用了远程的service，此application:onCreate会被调用2次
// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null ||!processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EaseUI.getInstance().init(this,options);
        EMClient.getInstance().init(this, options);
        EaseUt.userMap=new HashMap<>();
        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                Log.e(TAG2, "getUser: "+username );
                return EaseUt.getUserInfo(username);
            }
        });
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
    public static Context getContext(){
        return context;
    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {

            }
        }
        return processName;
    }
}
