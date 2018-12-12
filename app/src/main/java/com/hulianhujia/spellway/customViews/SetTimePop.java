package com.hulianhujia.spellway.customViews;

import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.activitys.GuiderDetailActivity;
import com.hulianhujia.spellway.javaBeans.BookTimeEvent;
import com.hulianhujia.spellway.untils.MyUtils;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ShuaiTao
 * data: on 2017\9\5 0005 11:50
 * E-Mail: bill_dream@sina.com
 */

public class SetTimePop extends PopupWindow implements OnDateSetListener {
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.lo)
    LinearLayout lo;
    @Bind(R.id.btCommit)
    TextView btCommit;
    @Bind(R.id.btExit)
    ImageView btExit;
    private Context context;
    private View view;
    private TimePickerDialog timePickerDialog;
    private String timeData;
    private String TAG="info";
    private int flag=1;
    public SetTimePop(Context context,int flag) {
        this.context = context;
        this.flag=flag;
        initPop();
        setPop();
    }
    private void setPop() {
        setContentView(view);
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show(((GuiderDetailActivity) context).getSupportFragmentManager(),"month_day_hour_minute");
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeData!=null){
                    EventBus.getDefault().post(new BookTimeEvent(MyUtils.timeStampToStrY(System.currentTimeMillis())+"-"+timeData,flag));
                    Log.e(TAG, "onClick: " +MyUtils.timeStampToStrY(System.currentTimeMillis()));
                    dismiss();
                }
            }
        });
    }
    private void initPop() {
        view = LayoutInflater.from(context).inflate(R.layout.settime_pop, null);
        ButterKnife.bind(this,view);
        long ct = System.currentTimeMillis();
        String dt = MyUtils.timeStampToMD(ct);
        String tm = MyUtils.timeStampToHM(ct);
        date.setText(dt);
        time.setText(tm);
        timePickerDialog = new TimePickerDialog.Builder()
                .setTitleStringId("预约时间")
                .setToolBarTextColor(R.color.text_color_gray_5)
                .setThemeColor(R.color.barColor)
                .setType(Type.MONTH_DAY_HOUR_MIN)
                .setCallBack(this)
                .build();
    }
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        date.setText(MyUtils.timeStampToMD(millseconds));
        time.setText(MyUtils.timeStampToHM(millseconds));
        timeData=MyUtils.timeStampToMD(millseconds)+"  "+MyUtils.timeStampToHM(millseconds);
    }
}
