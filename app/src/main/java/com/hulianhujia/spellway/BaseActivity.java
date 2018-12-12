package com.hulianhujia.spellway;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.hulianhujia.spellway.activitys.InitAty;
import com.hulianhujia.spellway.activitys.InitAty2;
import com.hulianhujia.spellway.activitys.LoginAty;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.FinishMainEvent;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.untils.AppManager;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.StatusBarManager;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
public class BaseActivity extends AppCompatActivity {
    public Context context;
    private RelativeLayout loTitle;
    public LoadingDialog loadingDialog;
    private String TAG="info";
    private EMConnectionListener emListener;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        ButterKnife.bind(this);
        context=this;
        loadingDialog=new LoadingDialog(this);
        MyUtils.AtyContainer.getInstance().addActivity(this);
 /*       loadingDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = 0.7f;
                getWindow().setAttributes(lp2);
            }
        });
        loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = 1f;
                getWindow().setAttributes(lp2);
            }
        });*/
            if (Build.VERSION.SDK_INT>Build.VERSION_CODES.KITKAT){
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                );
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                StatusBarManager.getInstance().setStatusBarTextColor(getWindow(),true);
            }else {
                loTitle = ((RelativeLayout) findViewById(R.id.loTitle));
                StatusBarManager.getInstance().setStatusBarTextColor(getWindow(),false);
                if (loTitle!=null){
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) loTitle.getLayoutParams();
                    lp.height=getResources().getDimensionPixelOffset(R.dimen.height);
                }
                MyUtils.setWindowStatusBarColor(this,R.color.black);
            }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(false);
//            SystemBarTintManager mTintManager = new SystemBarTintManager(this);
//            mTintManager.setStatusBarTintEnabled(true);
//            mTintManager.setStatusBarTintDrawable(getDrawable(R.drawable.star_color));
//            mTintManager.setStatusBarTintResource(R.color.dracula_page_bg);//通知栏所需颜色
//        }
        initView();
        initData();
        initEMListener();
    }
    private void initEMListener() {
        emListener=new EMConnectionListener() {
            @Override
            public void onConnected() {
                Log.e(TAG, "onConnected:正常连接的情况 " );
            }
            @Override
            public void onDisconnected(final int error) {
                Log.e(TAG, "onDisconnected: 错误了" );
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(error == EMError.USER_REMOVED){
                            // 显示帐号已经被移除
                        }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                            ConfirmDialogN cn = new ConfirmDialogN(context);
                            cn.setCanceledOnTouchOutside(false);
                            cn.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }
                            });
                            cn.setFlag(1);
                            cn.setYesListener(new YesListener() {
                                @Override
                                public void yes(int flag) {
                                }
                            });
                            cn.show();
                            cn.setTitle("您的帐号在别的设备上登录\n如非本人操作请尽快修改密码"
                                    );
                            ToastUtils.toast("app将在5秒后退出登录");
                            TimerTask tm = new TimerTask() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().postSticky(new FinishMainEvent());
                                    startActivity(new Intent(BaseActivity.this, InitAty.class));
                                }
                            };
                            Timer t = new Timer();
                            t.schedule(tm,4000);
                            // 显示帐号在其他设备登录
                        } else {
                        }
                    }
                });
            }
        };
        EMClient.getInstance().addConnectionListener(emListener);
    }
    public void initData() {
    }
    public int getContentId(){
        return 0;
    }
    public void initView() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EMClient.getInstance().removeConnectionListener(emListener);
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
