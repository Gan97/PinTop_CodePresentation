package com.hulianhujia.spellway.activitys;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.hulianhujia.spellway.adpaters.CommunityDetailCommentListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.CommunityDetailBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.SendCommentBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
public class CommunityDetailAty extends BaseActivity implements View.OnClickListener,PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.communityDetailCommentListView)
    PullToRefreshListView communityDetailCommentListView;
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.btSendComment)
    TextView btSendComment;
    @Bind(R.id.bottomBar)
    RelativeLayout bottomBar;
    private TextView talk;
    private TextView like;
    private String TAG = "info";
    private TextView tvUserName;
    private TextView tvTime;
    private TextView tvLoc;
    private TextView tvContent;
    private TextView tvCommentNum;
    private String cid;
    private CommunityDetailCommentListViewAdp adp;
    private LoginBean.UserInfoBean userInfo;
    private List<CommunityDetailBean.ReturnArrBean.JudgeBean> datas=new ArrayList<>();
    private NineGridView photos;
    private String uid;
    private UserInfoBean user;
    private CircleImageView userIcon;
    private LoadingDialog loadingDialog;
    private View btInvite;
    private int page=1;
    private ImageView btLTalk;
    private String talkNb;
    private ImageView btShare;
    private String id;
    private String content;
    @Override
    public int getContentId() {
        return R.layout.activity_community_detail_aty;
    }
    @Override
    public void initView() {
        loadingDialog=new LoadingDialog(this);
        getUser();
        Intent intent = getIntent();
        uid  = intent.getStringExtra("uid");
        cid  = intent.getStringExtra("cid");
        btExit.setOnClickListener(this);
        btSendComment.setOnClickListener(this);
        /*使能适配器*/
        adp = new CommunityDetailCommentListViewAdp(datas, this);
        communityDetailCommentListView.setAdapter(adp);
        View head = LayoutInflater.from(this).inflate(R.layout.community_detail_head, null);
        tvUserName = ((TextView) head.findViewById(R.id.userName));
        btShare = ((ImageView) head.findViewById(R.id.btShare));
        btLTalk = ((ImageView) head.findViewById(R.id.talk));
        btLTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etContent.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etContent, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        userIcon = ((CircleImageView) head.findViewById(R.id.userIcon));
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(id,content);
            }
        });
        tvTime = ((TextView) head.findViewById(R.id.tvHowLong));
        photos = ((NineGridView) head.findViewById(R.id.nineGrideView));
        tvCommentNum = ((TextView) head.findViewById(R.id.commentNum));
        tvLoc = ((TextView) head.findViewById(R.id.tvLoc));
        tvContent = ((TextView) head.findViewById(R.id.tvContent));
        ListView view = communityDetailCommentListView.getRefreshableView();
        view.addHeaderView(head);
        communityDetailCommentListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        communityDetailCommentListView.setOnRefreshListener(this);
        //旅游邀请点击跳转
        btInvite = head.findViewById(R.id.btInvite);
        /*获取传来intent里的数据*/
        setData();
    }

    private void setData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.COMMUNITY_DETAIL)
                .params("cmnt_id", cid)
                .params("judgePage",page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始访问动态详情id" + cid);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call:动态详情 " + s);
                        Gson gson = new Gson();
                        CommunityDetailBean bean = gson.fromJson(s, CommunityDetailBean.class);
                        if (bean.getCode() == 1) {
                            final CommunityDetailBean.ReturnArrBean data = bean.getReturnArr();
                            if (!uid.equals(user.getReturnArr().getUser_id())){
                                btInvite.setVisibility(View.VISIBLE);
                                btInvite.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, ChatAty.class);
                                        intent.putExtra("userId", data.getUsername());
                                        intent.putExtra("toNick",data.getUsernick());
                                        intent.putExtra("chatType", Contents.CHATTYPE_SINGLE);
                                        startActivity(intent);
                                    }
                                });
                            }else {
                                btInvite.setVisibility(View.GONE);
                            }
                            id=data.getCmnt_id();
                            content=data.getContent();
                            tvUserName.setText(data.getUsernick());
                            tvContent.setText(data.getContent());
                            talkNb=data.getJudgeCount();
                            tvCommentNum.setText(data.getJudgeCount()+"条评价");
                            tvTime.setText(MyUtils.timeStampToStr(Long.parseLong(data.getCreate_time())));
                            tvLoc.setText(data.getWhere().equals("")?"未知":data.getWhere());
                            Glide.with(context).load(data.getUserpicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(userIcon);
                            String picture = data.getPicture();
                            if (picture!=null&&picture.length()!=0){
                                String[] strs = picture.split(",");
                                List<ImageInfo> imageInfos=new ArrayList<>();
                                for (String str:strs){
                                    ImageInfo imageInfo = new ImageInfo();
                                    imageInfo.setBigImageUrl(str);
                                    imageInfo.setThumbnailUrl(str);
                                    imageInfos.add(imageInfo);
                                }
                                photos.setAdapter(new NineGridViewClickAdapter(CommunityDetailAty.this,imageInfos));
                            }
                            /*获取评论数据*/
                            datas.addAll(bean.getReturnArr().getJudge());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 错误动态详情" + throwable);
                    }
                });
    }

    private void getUser() {
        LoginBean.UserInfoBean userInfoBean = (LoginBean.UserInfoBean) SharedUtils.readObject(getApplicationContext());
        userInfo=userInfoBean;
        user= (UserInfoBean) SharedUtils.readUserInfo(this);
        Log.e(TAG, "getUser: "+(userInfoBean.getUsername()) );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btSendComment:
                OkGo.post(Urls.PUBLIC_URL+Urls.SEND_COMMENT)
                        .params("jgd_id",cid)
                        .params("content",etContent.getText().toString())
                        .params("cateid",1)
                        .params("username",userInfo.getUsername())
                        .getCall(new StringConvert(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 评论开始" );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 评论获得" + s);
                                Gson g = new Gson();
                                SendCommentBean bean = g.fromJson(s, SendCommentBean.class);
                                if (bean.getCode()==1){
                                    etContent.setText("");
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
                                    OkGo.post(Urls.PUBLIC_URL + Urls.COMMUNITY_DETAIL)
                                            .params("cmnt_id", cid)
                                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                                            .doOnSubscribe(new Action0() {
                                                @Override
                                                public void call() {
                                                    Log.e(TAG, "call: 开始访问动态详情id" + cid);
                                                }
                                            }).observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Action1<String>() {
                                                @Override
                                                public void call(String s) {
                                                    Log.e(TAG, "call:动态详情 " + s);
                                                    Gson gson = new Gson();
                                                    CommunityDetailBean bean = gson.fromJson(s, CommunityDetailBean.class);
                                                    if (bean.getCode() == 1) {
                                                        talkNb=(Integer.parseInt(talkNb)+1)+"";
                                                        tvCommentNum.setText(talkNb+"条评价");
                                                        CommunityDetailBean.ReturnArrBean data = bean.getReturnArr();
                                                        /*获取评论数据*/
                                                        datas.clear();
                                                        datas.addAll(bean.getReturnArr().getJudge());
                                                        adp.notifyDataSetChanged();
                                                    }
                                                }
                                            }, new Action1<Throwable>() {
                                                @Override
                                                public void call(Throwable throwable) {
                                                    Log.e(TAG, "call: 错误动态详情" + throwable);
                                                }
                                            });
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 评论错误"+throwable );
                            }
                        });
                break;
        }
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

    }

    private void showShare(String id,String content) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("拼途旅游");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://pintu.schlhjnetworktesturl.com/index.php?m=home&c=community&a=share_community&id="+id);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://pintu.schlhjnetworktesturl.com/index.php?m=home&c=community&a=share_community&id="+id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://pintu.schlhjnetworktesturl.com/index.php?m=home&c=community&a=share_community&id="+id);
        // 启动分享GUI
        oks.show(context);
    }
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        OkGo.post(Urls.PUBLIC_URL + Urls.COMMUNITY_DETAIL)
                .params("cmnt_id", cid)
                .params("judgePage",page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始访问动态详情id" + cid);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        communityDetailCommentListView.onRefreshComplete();
                        Log.e(TAG, "call:动态详情 " + s);
                        Gson gson = new Gson();
                        CommunityDetailBean bean = gson.fromJson(s, CommunityDetailBean.class);
                        if (bean.getCode() == 1) {
                            CommunityDetailBean.ReturnArrBean data = bean.getReturnArr();
                            tvUserName.setText(data.getUsernick());
                            tvContent.setText(data.getContent());
                            tvTime.setText(MyUtils.timeStampToStr(Long.parseLong(data.getCreate_time())));
                            tvCommentNum.setText(data.getJudgeCount()+"条评价");
                            Glide.with(context).load(data.getUserpicture()).asBitmap().into(userIcon);
                            String picture = data.getPicture();
                            if (picture!=null&&picture.length()!=0){
                                String[] strs = picture.split(",");
                                List<ImageInfo> imageInfos=new ArrayList<>();
                                for (String str:strs){
                                    ImageInfo imageInfo = new ImageInfo();
                                    imageInfo.setBigImageUrl(str);
                                    imageInfo.setThumbnailUrl(str);
                                    imageInfos.add(imageInfo);
                                }
                                photos.setAdapter(new NineGridViewClickAdapter(CommunityDetailAty.this,imageInfos));
                            }
                            /*获取评论数据*/
                            if (bean.getReturnArr().getJudge().size()!=0&&bean.getReturnArr().getJudge()!=null){
                                datas.addAll(bean.getReturnArr().getJudge());
                                adp.notifyDataSetChanged();
                            }
                            else {
                                ToastUtils.toast("没有更多了");
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 错误动态详情" + throwable);
                    }
                });
    }
}
