package com.hulianhujia.spellway.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.hulianhujia.spellway.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ShuaiTao
 * data: on 2017\9\2 0002 10:52
 * E-Mail: bill_dream@sina.com
 */

public class UserLoginFgm extends Fragment {
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPwd)
    EditText etPwd;
    @Bind(R.id.etYzm)
    EditText etYzm;
    @Bind(R.id.btGetYzm)
    TextView btGetYzm;
    @Bind(R.id.btLogin)
    TextView btLogin;
    @Bind(R.id.btForget)
    TextView btForget;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login_fgm, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
