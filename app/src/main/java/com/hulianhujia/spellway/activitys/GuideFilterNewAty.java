package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.GuideFilterEvent;
import com.hulianhujia.spellway.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideFilterNewAty extends BaseActivity implements RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.rgAge)
    RadioGroup rgAge;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tgSex)
    RadioGroup tgSex;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.rgPrice)
    RadioGroup rgPrice;
    @Bind(R.id.btTopPrice)
    CheckBox btTopPrice;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.rgLevel)
    RadioGroup rgLevel;
    @Bind(R.id.btReset)
    TextView btReset;
    @Bind(R.id.btCommit)
    TextView btCommit;
    @Bind(R.id.age_18)
    RadioButton age18;
    @Bind(R.id.age_26)
    RadioButton age26;
    @Bind(R.id.age_31)
    RadioButton age31;
    @Bind(R.id.age_40)
    RadioButton age40;
    @Bind(R.id.sex_m)
    RadioButton sexM;
    @Bind(R.id.sex_f)
    RadioButton sexF;
    @Bind(R.id.price_30)
    RadioButton price30;
    @Bind(R.id.price_50)
    RadioButton price50;
    @Bind(R.id.price_100)
    RadioButton price100;
    @Bind(R.id.gold)
    RadioButton gold;
    @Bind(R.id.silver)
    RadioButton silver;
    @Bind(R.id.cu)
    RadioButton cu;
    @Bind(R.id.price_150)
    RadioButton price150;
    private String TAG = "info";
    @Override
    public int getContentId() {
        return R.layout.activity_guide_filter_new_aty;
    }
    @Override
    public void initView() {
        rgAge.setOnCheckedChangeListener(this);
        rgLevel.setOnCheckedChangeListener(this);
        tgSex.setOnCheckedChangeListener(this);
        rgPrice.setOnCheckedChangeListener(this);
        btTopPrice.setOnCheckedChangeListener(this);

        Log.e(TAG, "initView: "+Contents.GUIDE_FILTER_PRICE );
        switch (Contents.GUIDE_FILTER_AGE){
            case "18-25":
                age18.setChecked(true);
                break;
            case "26-30":
                age26.setChecked(true);
                break;
            case "31-40":
                age31.setChecked(true);
                break;
            case "40-60":
                age40.setChecked(true);
                break;
        }
        switch (Contents.GUIDE_FILTER_LVL){
            case "1":
                gold.setChecked(true);
                break;
            case "2":
                silver.setChecked(true);
                break;
            case "3":
                cu.setChecked(true);
                break;
        }
        switch (Contents.GUIDE_FILTER_PRICE){
            case "50-100":
                price50.setChecked(true);
                break;

            case "100-150":
                price100.setChecked(true);
                break;
            case "30-50":
                price30.setChecked(true);
                break;
            case "150-200":
                price150.setChecked(true);
                break;
            case "200-1000":
                btTopPrice.setChecked(true);
                break;
        }
        switch (Contents.GUIDE_FILTER_SEX){
            case "男":
                sexM.setChecked(true);
                break;
            case "女":
                sexF.setChecked(true);
                break;
        }
    }

    @OnClick({R.id.btExit, R.id.btReset, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btReset:
                Contents.GUIDE_FILTER_AGE = "";
                Contents.GUIDE_FILTER_LVL = "";
                Contents.GUIDE_FILTER_PRICE = "";
                Contents.GUIDE_FILTER_SEX = "";
                Log.e(TAG, "onViewClicked: "+Contents.GUIDE_FILTER_PRICE );
                rgAge.clearCheck();
                rgPrice.clearCheck();
                rgLevel.clearCheck();
                tgSex.clearCheck();
                btTopPrice.setChecked(false);
                break;
            case R.id.btCommit:
                EventBus.getDefault().post(new GuideFilterEvent());
                finish();
                break;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.age_18:
                if (age18.isChecked()){
                    Contents.GUIDE_FILTER_AGE = "18-25";
                }
                break;
            case R.id.age_26:
                if (age26.isChecked()){
                    Contents.GUIDE_FILTER_AGE = "26-30";
                }
                break;
            case R.id.age_31:
                if (age31.isChecked()){
                    Contents.GUIDE_FILTER_AGE = "31-40";
                }
                break;
            case R.id.age_40:
                if (age40.isChecked()){
                    Contents.GUIDE_FILTER_AGE = "40-60";
                }
                break;
            case R.id.sex_m:
                if (sexM.isChecked()){
                    Contents.GUIDE_FILTER_SEX = "男";
                }
                break;
            case R.id.sex_f:
                if (sexF.isChecked()){
                    Contents.GUIDE_FILTER_SEX = "女";
                }
                break;
            case R.id.price_30:
                if (price30.isChecked()) {
                    Contents.GUIDE_FILTER_PRICE = "30-50";
                    btTopPrice.setChecked(false);
                }
                break;
            case R.id.price_50:
                if (price50.isChecked()) {
                    Contents.GUIDE_FILTER_PRICE = "50-100";
                    btTopPrice.setChecked(false);
                }
                break;
            case R.id.price_100:
                if (price100.isChecked()) {
                    Contents.GUIDE_FILTER_PRICE = "100-150";
                    btTopPrice.setChecked(false);
                }
                break;
            case R.id.price_150:
                if (price150.isChecked()){
                    Contents.GUIDE_FILTER_PRICE = "150-200";
                    btTopPrice.setChecked(false);
                }
                break;
            case R.id.gold:
                if (gold.isChecked()){
                    Contents.GUIDE_FILTER_LVL = "1";
                }
                break;
            case R.id.silver:
                if (silver.isChecked()){
                    Contents.GUIDE_FILTER_LVL = "2";
                }
                break;
            case R.id.cu:
                if (cu.isChecked()){
                    Contents.GUIDE_FILTER_LVL = "3";
                }
                break;
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Contents.GUIDE_FILTER_PRICE = "200-1000";
            rgPrice.clearCheck();
        } else {
//            Contents.GUIDE_FILTER_PRICE = "";
        }
    }
}
