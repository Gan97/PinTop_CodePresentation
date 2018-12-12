package com.hulianhujia.spellway.activitys.guider;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.ChangeIntroduceEvent;
import com.hulianhujia.spellway.event.ChangeSkillsEvent;
import com.hulianhujia.spellway.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeIntroduceAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btSave)
    TextView btSave;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.etSkill)
    EditText etSkill;

    @Override
    public int getContentId() {
        return R.layout.activity_change_introduce_aty;
    }


    @OnClick({R.id.btExit, R.id.btSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btSave:
                if (etSkill.getText().length()!=0&&etSkill.getText()!=null){
                    EventBus.getDefault().post(new ChangeIntroduceEvent(etSkill.getText().toString()));
                    finish();
                }else {
                    ToastUtils.toast("不能为空");
                }
                break;
        }
    }
}
