package com.hulianhujia.spellway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.hulianhujia.spellway.activitys.ChatAty;
import com.hulianhujia.spellway.activitys.InviteWeChatAty;
import com.hulianhujia.spellway.adpaters.MessageListAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.ease.EaseUt;
import com.hulianhujia.spellway.javaBeans.MessageListBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class MessageAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.lo)
    FrameLayout lo;
    private UserInfoBean userInfoBean=null;
    private String TAG = "userinfo";
    private EMMessageListener emMessageListener;
    private  EaseConversationListFragment conversationListFragment;
    private         int i=1;
    @Override
    public int getContentId() {
        return R.layout.activity_message_aty;
    }
    @Override
    public void initData() {
        getUser();
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        final Set<String> strings = conversations.keySet();
        for (final String str : strings){
            if (EaseUt.getUserInfo(str)==null){
                OkGo.get(Urls.PUBLIC_URL+Urls.INIT)
                        .params("username",str)
                        .params("myusername",str)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 拉取用户信息开始"+str );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                i++;
                                Log.e(TAG, "call: 拉去用户获得" + s);
                                Gson g = new Gson();
                                UserInfoBean bean = g.fromJson(s, UserInfoBean.class);
                                if (bean.getCode() == 1) {
                                    EaseUser u = new EaseUser(str);
                                    u.setAvatar(bean.getReturnArr().getPicture());
                                    u.setNickname(bean.getReturnArr().getNickname());
                                    EaseUt.userMap.put(str,u);
                                }
                                if (i==strings.size()){
                                    conversationListFragment.refresh();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call: 拉取错误"+throwable.toString() );
                            }
                        });
            }
        }
        Log.e(TAG, "initData: 拉取完毕" );
    }
    private void getUser() {
        userInfoBean = (UserInfoBean) SharedUtils.readUserInfo(this);
    }
    @Override
    public void initView() {
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(MessageAty.this, ChatAty.class).
                        putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()).putExtra("toNick",EaseUt.userMap.get(conversation.conversationId()).getNickname()));
            }
        });

        getSupportFragmentManager().beginTransaction().add(R.id.lo, conversationListFragment).commit();
        emMessageListener= new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                conversationListFragment.refresh();
            }
            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {

            }

            @Override
            public void onMessageRead(List<EMMessage> list) {
            }
            @Override
            public void onMessageDelivered(List<EMMessage> list) {
            }
            @Override
            public void onMessageRecalled(List<EMMessage> list) {
            }
            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(emMessageListener);
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }
}
