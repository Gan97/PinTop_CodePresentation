package com.hulianhujia.spellway.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.DiaryCommentListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.ColloctBean;
import com.hulianhujia.spellway.javaBeans.CommentBean;
import com.hulianhujia.spellway.javaBeans.DiaryCommentBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.ZanBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.tencent.smtt.sdk.WebChromeClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class DiaryAty extends BaseActivity implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2 {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.etMessage)
    EditText etMessage;
    @Bind(R.id.btSend)
    TextView btSend;
    @Bind(R.id.editLo)
    LinearLayout editLo;
    @Bind(R.id.btCollect)
    ImageView btCollect;
    @Bind(R.id.listView)
    PullToRefreshListView diaryCommentListView;
    private LoginBean.UserInfoBean userInfo;
    private DiaryCommentListViewAdp adp;
    private List<DiaryCommentBean.ReturnArrBean.JudgeBean> datas = new ArrayList<>();
    private String TAG = "info";
    private String nid;
    private LoadingDialog dialog;
    private int page = 1;
    private WebView webView;
    private LinearLayout likeNum;
    private TextView talkNum;
    private TextView tvNum;
    private ImageView imgZan;
    @Override
    public void initView() {
        getUser();
        dialog = new LoadingDialog(this);
        View head = LayoutInflater.from(this).inflate(R.layout.activity_diary_head, null);
        likeNum = ((LinearLayout) head.findViewById(R.id.like_num));
        tvNum = ((TextView) head.findViewById(R.id.tvNum));
        talkNum = ((TextView) head.findViewById(R.id.talk_num));
        webView = ((WebView) head.findViewById(R.id.webView));
        imgZan = ((ImageView) head.findViewById(R.id.imgZan));
        initWeb();
        btSend.setOnClickListener(this);
        adp = new DiaryCommentListViewAdp(this, datas);
        diaryCommentListView.setAdapter(adp);
        ListView view = diaryCommentListView.getRefreshableView();
        view.addHeaderView(head, null, false);
        diaryCommentListView.setOnRefreshListener(this);
        diaryCommentListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        Intent intent = getIntent();
        nid = intent.getStringExtra("nid");
        initList();
    }
    private void initWeb(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(false);
        settings.setDefaultFontSize(50);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String s) {
                Log.e(TAG, "shouldOverrideUrlLoading: "+s);
                String userName = s.split("http://pintu.schlhjnetworktesturl.com/")[1];
                Intent i  = new Intent(DiaryAty.this,OtherUserInfoAty.class);
                i.putExtra("userName",userName);
                startActivity(i);
                return true;
            }
        });
    }
    /*使能评论*/
    private void initList() {
        OkGo.post(Urls.PUBLIC_URL + Urls.DIARY_DETAIL)
                .params("news_id", nid)
                .params("username", userInfo.getUsername())
                .params("judgePage", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call:日记详情开始  日记id " + nid);
                        dialog.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        Log.e(TAG, "call: 日记详情获得" + s);
                        Gson g = new Gson();
                        final DiaryCommentBean bean = g.fromJson(s, DiaryCommentBean.class);
                        if (bean.getCode() == 1) {
                            final DiaryCommentBean.ReturnArrBean data = bean.getReturnArr();
                            webView.loadUrl(data.getUrl()+"&userId="+data.getUsername());
                            Log.e(TAG, "call: "+data.getUrl()+"&userId="+data.getUsername() );
                            talkNum.setText(data.getJudgeCount());
                            tvNum.setText(data.getZan());
                            if (data.getCollected()==0){
                                btCollect.setImageResource(R.mipmap.ic_collect);
                            }else {
                                btCollect.setImageResource(R.mipmap.ic_collect_choose);
                            }
                            if (data.getIs_zan()==1){
                                Glide.with(context).load(R.drawable.like_red).asBitmap().into(imgZan);
                            }else {
                                Glide.with(context).load(R.drawable.like_grey).asBitmap().into(imgZan);
                            }

                            likeNum.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OkGo.get(Urls.PUBLIC_URL+Urls.ZAN)
                                            .params("article_id",data.getNews_id())
                                            .params("type",2)
                                            .params("username", Contents.USER.getUsername())
                                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                                            .doOnSubscribe(new Action0() {
                                                @Override
                                                public void call() {
                                                    loadingDialog.show();
                                                }
                                            }).observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Action1<String>() {
                                                @Override
                                                public void call(String s) {
                                                    loadingDialog.dismiss();
                                                    Log.e(TAG, "call: 赞获得" + s);
                                                    Gson g = new Gson();
                                                    ZanBean zanBean = g.fromJson(s, ZanBean.class);
                                                    if (zanBean.getCode() == 1) {
                                                        Log.e(TAG, "call: 赞获得" + data.getIs_zan());
                                                        switch (data.getIs_zan()) {
                                                            case 0:
                                                                Log.e(TAG, "call: 设置赞1" );
                                                                data.setIs_zan(1);
                                                                int i = Integer.parseInt(data.getZan())+1;
                                                                data.setZan(i+"");
                                                                tvNum.setText(i+"");
                                                                Glide.with(context).load(R.drawable.like_red).asBitmap().into(imgZan);
                                                                break;
                                                            case 1:
                                                                Log.e(TAG, "call: 设置赞2" );
                                                                data.setIs_zan(0);
                                                                int i1 = Integer.parseInt(data.getZan())-1;
                                                                data.setZan(i1+"");
                                                                tvNum.setText(i1+"");
                                                                Glide.with(context).load(R.drawable.like_grey).asBitmap().into(imgZan);
                                                                break;
                                                        }
                                                    }
                                                }
                                            }, new Action1<Throwable>() {
                                                @Override
                                                public void call(Throwable throwable) {
                                                    loadingDialog.dismiss();
                                                    Log.e(TAG, "call: 赞错"+throwable.toString() );
                                                }
                                            });
                                }
                            });
                            btCollect.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (data.getCollected()==1){
                                        OkGo.post(Urls.PUBLIC_URL+Urls.CANCEL_COLLECT)
                                                .params("username", Contents.USER.getUsername())
                                                .params("diary_id",data.getNews_id())
                                                .getCall(StringConvert.create(),RxAdapter.<String>create())
                                                .doOnSubscribe(new Action0() {
                                                    @Override
                                                    public void call() {
                                                        loadingDialog.show();
                                                        Log.e(TAG, "call: 取消收藏开始" );
                                                    }
                                                }).observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Action1<String>() {
                                                    @Override
                                                    public void call(String s) {
                                                        loadingDialog.dismiss();
                                                        Log.e(TAG, "call: 取消收藏收藏获得"+s);
                                                        Gson g = new Gson();
                                                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                                        if (baseBean.getCode() == 1) {
                                                            data.setCollected(0);
                                                            btCollect.setImageResource(R.mipmap.ic_collect);
                                                        }else {
                                                            ToastUtils.toast("收藏错误，请您稍后再试");
                                                        }
                                                    }
                                                }, new Action1<Throwable>() {
                                                    @Override
                                                    public void call(Throwable throwable) {
                                                        loadingDialog.dismiss();
                                                        Log.e(TAG, "call: 错误"+throwable.toString() );
                                                    }
                                                });
                                    }else {
                                        OkGo.post(Urls.PUBLIC_URL+Urls.ADD_COLLOCTION)
                                                .params("username", Contents.USER.getUsername())
                                                .params("diary_id",data.getNews_id())
                                                .getCall(StringConvert.create(),RxAdapter.<String>create())
                                                .doOnSubscribe(new Action0() {
                                                    @Override
                                                    public void call() {
                                                        loadingDialog.show();
                                                        Log.e(TAG, "call: 收藏开始" );
                                                    }
                                                }).observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Action1<String>() {
                                                    @Override
                                                    public void call(String s) {
                                                        loadingDialog.dismiss();
                                                        Log.e(TAG, "call: 收藏获得"+s);
                                                        Gson g = new Gson();
                                                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                                        if (baseBean.getCode() == 1) {
                                                                data.setCollected(1);
                                                            btCollect.setImageResource(R.mipmap.ic_collect_choose);
                                                        }else {
                                                            ToastUtils.toast("收藏错误，请您稍后再试");
                                                        }
                                                    }
                                                }, new Action1<Throwable>() {
                                                    @Override
                                                    public void call(Throwable throwable) {
                                                        loadingDialog.dismiss();
                                                        Log.e(TAG, "call: 收藏错误"+throwable.toString());
                                                    }
                                                });
                                    }

                                }
                            });
                            if (data.getJudge() == null || data.getJudge().size() == 0) {
                                diaryCommentListView.setMode(PullToRefreshBase.Mode.DISABLED);
                            } else {
                                diaryCommentListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                            }
                            datas.addAll(data.getJudge());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call:日记详情错误 " + throwable.toString());
                    }
                });
    }
    private void getUser() {
        LoginBean.UserInfoBean infoBean = (LoginBean.UserInfoBean) SharedUtils.readObject(getApplicationContext());
        userInfo = infoBean;
    }
    @Override
    public int getContentId() {
        return R.layout.activity_diary_aty;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lo:
//                Intent i = new Intent(DiaryAty.this, OtherUserInfoAty.class);
//                i.putExtra("userName", oName);
//                startActivity(i);
                break;
            case R.id.btSend:
                OkGo.post(Urls.PUBLIC_URL + Urls.SEND_COMMENT)
                        .params("jgd_id", nid)
                        .params("content", etMessage.getText().toString())
                        .params("cateid", 2)
                        .params("username", userInfo.getUsername())
                        .getCall(new StringConvert(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 评论开始");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                Log.e(TAG, "call: 评论获得" + s);
                                Gson g = new Gson();
                                CommentBean bean = g.fromJson(s, CommentBean.class);
                                if (bean.getCode() == 1) {
                                    etMessage.setText("");
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(etMessage.getWindowToken(), 0);
                                    refreshList();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(TAG, "call: 评论错误" + throwable);
                            }
                        });
                break;
        }
    }
    private void refreshList() {
        OkGo.post(Urls.PUBLIC_URL + Urls.DIARY_DETAIL)
                .params("news_id", nid)
                .params("username", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call:日记详情开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 日记详情获得" + s);
                        Gson g = new Gson();
                        DiaryCommentBean bean = g.fromJson(s, DiaryCommentBean.class);
                        if (bean.getCode() == 1) {
                            List<DiaryCommentBean.ReturnArrBean.JudgeBean> judge = bean.getReturnArr().getJudge();
                            if (judge == null || judge.size() == 0) {
                                diaryCommentListView.setMode(PullToRefreshBase.Mode.DISABLED);
                            } else {
                                diaryCommentListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                            }
                            datas.clear();
                            datas.addAll(judge);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call:日记详情错误 " + throwable.toString());
                    }
                });
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        OkGo.post(Urls.PUBLIC_URL + Urls.DIARY_DETAIL)
                .params("news_id", nid)
                .params("username", userInfo.getUsername())
                .params("judgePage", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call:日记详情开始  日记id " + nid);
                        dialog.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dialog.dismiss();
                        diaryCommentListView.onRefreshComplete();
                        Log.e(TAG, "call: 日记详情获得" + s);
                        Gson g = new Gson();
                        DiaryCommentBean bean = g.fromJson(s, DiaryCommentBean.class);
                        if (bean.getReturnArr().getJudge().size()!=0){
                            datas.addAll(bean.getReturnArr().getJudge());
                            adp.notifyDataSetChanged();
                        }else {
                            ToastUtils.toast("没有更多了");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        dialog.dismiss();
                        Log.e(TAG, "call:日记详情错误 " + throwable.toString());
                    }
                });
    }
}
