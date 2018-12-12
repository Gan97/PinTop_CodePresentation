package com.hulianhujia.spellway.activitys;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.lo)
    FrameLayout lo;
    @Bind(R.id.toName)
    TextView toName;
    private String toUserId;
    private int chatType;
    private boolean isMessageListInited;
    private String titleName;

    @Override
    public int getContentId() {
        return R.layout.activity_chat_aty;
    }

    @Override
    public void initData() {
    }
    @Override
    public void initView() {
        toUserId = getIntent().getStringExtra("userId");
        if (getIntent().getStringExtra("toNick")!=null){
            toName.setText(getIntent().getStringExtra("toNick").equals("")?toUserId:getIntent().getStringExtra("toNick"));
        }
        EaseChatFragment fragment = new EaseChatFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.lo, fragment).commit();
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }

}
