package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.FilterEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

public class GuideFilterAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.btAge)
    TextView btAge;
    @Bind(R.id.btSex)
    TextView btSex;
    @Bind(R.id.btGrade)
    TextView btGrade;
    @Bind(R.id.btDis)
    TextView btDis;
    @Bind(R.id.lo)
    LinearLayout lo;
    @Bind(R.id.btReset)
    TextView btReset;
    @Bind(R.id.btCommit)
    TextView btCommit;
    private boolean flag1=true;
    private boolean flag2=true;
    @Override
    public int getContentId() {
        return R.layout.activity_guide_filter_aty;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.btExit, R.id.btAge, R.id.btSex, R.id.btGrade, R.id.btDis, R.id.btReset, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btAge:
                startActivity(new Intent(GuideFilterAty.this,FilterAgeAty.class));
                break;
            case R.id.btSex:
                startActivity(new Intent(GuideFilterAty.this,FilterSexAty.class));

                break;
            case R.id.btGrade:
                if (flag1){
                    btGrade.setText("最高");
                    flag1=!flag1;
                }else {
                    btGrade.setText("最低");
                    flag1=!flag1;
                }
                break;
            case R.id.btDis:
                if (flag2){
                    btDis.setText("最近");
                    flag2=!flag2;
                }else {
                    btDis.setText("最远");
                    flag2=!flag2;
                }
                break;
            case R.id.btReset:
                break;
            case R.id.btCommit:

                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handleEvent(FilterEvent event){
        int type = event.getType();
        switch (type){
            case 1:
                btAge.setText(event.getAge());
                break;
            case 2:
                btSex.setText(event.getAge());
                break;
        }
    }
}
