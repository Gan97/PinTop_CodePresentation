package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.C;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.HomeFilterEvent;
import com.hulianhujia.spellway.javaBeans.SearchEvent;
import com.hulianhujia.spellway.untils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchAty extends BaseActivity implements TextWatcher {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.btSearch)
    TextView btSearch;
    @Bind(R.id.btClear)
    ImageView btClear;
    @Override
    public int getContentId() {
        return R.layout.activity_search_aty;
    }

    @Override
    public void initView() {
        etSearch.setText(Contents.SEARCH);
        etSearch.addTextChangedListener(this);
        if (Contents.SEARCH!=null&&Contents.SEARCH.length()>0){
            btClear.setVisibility(View.VISIBLE);
        }
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
    }
    @OnClick({R.id.btExit, R.id.btSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btSearch:
                    EventBus.getDefault().post(new SearchEvent(etSearch.getText().toString()));
                    finish();
                break;
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().length()>0){
            btClear.setVisibility(View.VISIBLE);
        }else {
            btClear.setVisibility(View.GONE);
        }
    }
}
