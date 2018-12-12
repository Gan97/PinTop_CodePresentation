package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisUserAty extends BaseActivity {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.a)
    RelativeLayout a;
    @Bind(R.id.dv1)
    TextView dv1;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btSend)
    TextView btSend;
    @Bind(R.id.etJym)
    EditText etJym;

    @Override
    public int getContentId() {
        return R.layout.activity_regis_user_aty;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btExit, R.id.tvTitle, R.id.a, R.id.dv1, R.id.etPhone, R.id.etPwd, R.id.etYzm, R.id.btSend, R.id.etJym})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.tvTitle:
                break;
            case R.id.a:
                break;
            case R.id.dv1:
                break;
            case R.id.etPhone:
                break;
            case R.id.etPwd:
                break;
            case R.id.etYzm:
                break;
            case R.id.btSend:
                break;
            case R.id.etJym:
                break;
        }
    }
}
