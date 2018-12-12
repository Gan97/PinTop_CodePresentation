package com.hulianhujia.spellway.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.ChatAdapter;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.MessageBean;
import com.hulianhujia.spellway.javaBeans.SendMessageBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class InviteWeChatAty extends BaseActivity {


    @Bind(R.id.btSearch)
    ImageView btSearch;
    @Bind(R.id.chat_recyclerView)
    RecyclerView chatRecyclerView;
    @Bind(R.id.et)
    EditText et;
    @Bind(R.id.tvSend)
    TextView tvSend;
    private String content;
    private String toId;
    private UserInfoBean userInfo;
    private LoadingDialog dialog;
    private String TAG="info";
    private ChatAdapter adapter;
    private int type=0;
    private int handFlag=1;
    private List<MessageBean.ReturnArrBean> datas= new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (type==1){
                OkGo.post(Urls.PUBLIC_URL+Urls.MESSAGE_DETAIL)
                        .params("user1_id",toId)
                        .params("user2_id",userInfo.getReturnArr().getUser_id())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call:消息详情开始 "+toId+"====="+userInfo.getReturnArr().getUser_id());
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.e(TAG, "call: 消息详情获得" + s);
                                Gson g = new Gson();
                                MessageBean bean = g.fromJson(s, MessageBean.class);
                                if (bean.getCode()==1){
                                    datas.clear();
                                    datas.addAll(bean.getReturnArr());
                                    adapter.notifyDataSetChanged();
                                    chatRecyclerView.scrollToPosition(datas.size()-1);
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call:消息小青错误"+throwable.toString());
                            }
                        });
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        content = s.toString().trim();
                    }
                });

            }else {
                OkGo.post(Urls.PUBLIC_URL+Urls.MESSAGE_DETAIL)
                        .params("user1_id",userInfo.getReturnArr().getUser_id())
                        .params("user2_id",toId)
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call:消息详情开始 "+toId+"====="+userInfo.getReturnArr().getUser_id());
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.e(TAG, "call: 消息详情获得" + s);
                                Gson g = new Gson();
                                MessageBean bean = g.fromJson(s, MessageBean.class);
                                if (bean.getCode()==1){
                                    datas.clear();
                                    datas.addAll(bean.getReturnArr());
                                    adapter.notifyDataSetChanged();
                                    chatRecyclerView.scrollToPosition(datas.size()-1);
                                }

                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call:消息小青错误"+throwable.toString());
                            }
                        });
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        content = s.toString().trim();
                    }
                });
            }
            if (handFlag==1){
                handler.sendEmptyMessageDelayed(1,5000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handFlag=1;
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        getUser();
        handler.sendEmptyMessageDelayed(1,5000);
        getMsg();
    }

    private void getMsg() {
        if (type==1){
            OkGo.post(Urls.PUBLIC_URL+Urls.MESSAGE_DETAIL)
                    .params("user1_id",toId)
                    .params("user2_id",userInfo.getReturnArr().getUser_id())
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            dialog.show();
                            Log.e(TAG, "call:消息详情开始 "+toId+"====="+userInfo.getReturnArr().getUser_id());
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            dialog.dismiss();
                            Log.e(TAG, "call: 消息详情获得" + s);
                            Gson g = new Gson();
                            MessageBean bean = g.fromJson(s, MessageBean.class);
                            if (bean.getCode()==1){
                                datas.clear();
                                datas.addAll(bean.getReturnArr());
                                adapter.notifyDataSetChanged();
                                chatRecyclerView.scrollToPosition(datas.size()-1);
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            dialog.dismiss();
                            Log.e(TAG, "call:消息小青错误"+throwable.toString());
                        }
                    });
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    content = s.toString().trim();
                }
            });

        }else {
            OkGo.post(Urls.PUBLIC_URL+Urls.MESSAGE_DETAIL)
                    .params("user1_id",userInfo.getReturnArr().getUser_id())
                    .params("user2_id",toId)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            dialog.show();
                            Log.e(TAG, "call:消息详情开始 "+toId+"====="+userInfo.getReturnArr().getUser_id());
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            dialog.dismiss();
                            Log.e(TAG, "call: 消息详情获得" + s);
                            Gson g = new Gson();
                            MessageBean bean = g.fromJson(s, MessageBean.class);
                            if (bean.getCode()==1){
                                datas.clear();
                                datas.addAll(bean.getReturnArr());
                                adapter.notifyDataSetChanged();
                                chatRecyclerView.scrollToPosition(datas.size()-1);
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            dialog.dismiss();
                            Log.e(TAG, "call:消息小青错误"+throwable.toString());
                        }
                    });
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    content = s.toString().trim();
                }
            });
        }
    }

    private void getUser() {
        userInfo= (UserInfoBean) SharedUtils.readUserInfo(this);
    }

    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }

    @Override
    public int getContentId() {
        return R.layout.activity_invite_we_chat;
    }

    @Override
    public void initView() {
        dialog=new LoadingDialog(this);
        Intent intent = getIntent();
        toId= intent.getStringExtra("toId");
        type=intent.getIntExtra("type",0);

        adapter=new ChatAdapter(datas,this);
        chatRecyclerView.setHasFixedSize(true);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
        ButterKnife.unbind(this);

    }


    @OnClick({R.id.btSearch, R.id.et, R.id.tvSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btSearch:
                finish();
                break;
            case R.id.et:
                break;
            case R.id.tvSend:
                OkGo.post(Urls.PUBLIC_URL+Urls.SEND_MESSAGE)
                        .params("user1_id",userInfo.getReturnArr().getUser_id())
                        .params("user2_id",toId)
                        .params("message",et.getText().toString())
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                dialog.show();
                                Log.e(TAG, "call:发送消息开始" );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.e(TAG, "call: 发送消息获得" + s);
                                dialog.dismiss();
                                Gson g =new Gson();
                                SendMessageBean bean = g.fromJson(s, SendMessageBean.class);
                                if (bean.getCode()==1){
                                    MessageBean.ReturnArrBean addBean = new MessageBean.ReturnArrBean();
                                    addBean.setMepicture(userInfo.getReturnArr().getPicture());
                                    addBean.setUser1_id(userInfo.getReturnArr().getUser_id());
                                    addBean.setMessage(et.getText().toString());
                                    et.setText("");
                                    datas.add(addBean);
                                    adapter.notifyDataSetChanged();
                                    chatRecyclerView.scrollToPosition(datas.size()-1);
                                }else {
                                    ToastUtils.toast("发送失败");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                dialog.dismiss();
                                Log.e(TAG, "call: 发送消息错误"+throwable.toString() );
                            }
                        });
                break;
        }
    }

}
