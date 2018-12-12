package com.hulianhujia.spellway.activitys;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.ChangeHobbyEvent;
import com.hulianhujia.spellway.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;

public class ChangeHobbyAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btSave)
    TextView btSave;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.etNick)
    EditText etNick;
    @Override
    public int getContentId() {
        return R.layout.changehobby_aty;
    }
    @OnClick({R.id.btExit, R.id.btSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btSave:
                if (etNick.getText().toString()!=null&&etNick.getText().toString().length()!=0){
                    EventBus.getDefault().post(new ChangeHobbyEvent(etNick.getText().toString()));
                    finish();
                }else {
                    ToastUtils.toast("不能为空");
                }
                break;
        }
    }
}
