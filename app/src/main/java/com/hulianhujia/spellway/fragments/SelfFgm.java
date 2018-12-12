package com.hulianhujia.spellway.fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.MessageAty;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.MyColloctionAty;
import com.hulianhujia.spellway.activitys.MyFollowAty;
import com.hulianhujia.spellway.activitys.MyInvitationAty;
import com.hulianhujia.spellway.activitys.MyOrderAty;
import com.hulianhujia.spellway.activitys.MyRedAty;
import com.hulianhujia.spellway.activitys.PubDiaryAty;
import com.hulianhujia.spellway.activitys.SettingsAty;
import com.hulianhujia.spellway.activitys.TravelDiaryAty;
import com.hulianhujia.spellway.activitys.UserInfoAty;
import com.hulianhujia.spellway.activitys.UserNewsAty;
import com.hulianhujia.spellway.activitys.WalletAty;
import com.hulianhujia.spellway.javaBeans.AddFocusBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

import static android.app.Activity.RESULT_OK;
/**
 * Created by FHP on 2017/5/23.
 */
public class SelfFgm extends Fragment {
    private static final int PICK_PHOTO = 3;
    @Bind(R.id.btSetting)
    ImageView btSetting;
    @Bind(R.id.selfIcon)
    CircleImageView selfIcon;
    @Bind(R.id.useName_useless)
    TextView useNameUseless;
    @Bind(R.id.selfMotto)
    TextView selfMotto;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.input_use)
    LinearLayout inputUse;
    @Bind(R.id.btTralDiary)
    TextView btTralDiary;
    @Bind(R.id.travel_use)
    LinearLayout travelUse;
    @Bind(R.id.btMyRed)
    TextView btMyRed;
    @Bind(R.id.btMyNews)
    TextView btMyNews;
    @Bind(R.id.btMyOrders)
    TextView btMyOrders;
    @Bind(R.id.btMyInvites)
    TextView btMyInvites;
    @Bind(R.id.btMyFollow)
    TextView btMyFollow;
    @Bind(R.id.btMyColloction)
    TextView btMyColloction;
    @Bind(R.id.tv_7)
    TextView tv7;
    @Bind(R.id.b)
    ImageView b;
    @Bind(R.id.btMyMessage)
    RelativeLayout btMyMessage;
    @Bind(R.id.tv_8)
    TextView tv8;
    @Bind(R.id.btPresent)
    RelativeLayout btPresent;
    @Bind(R.id.btWallet)
    TextView btWallet;
    @Bind(R.id.redBot)
    TextView redBot;
    private File file;
    private LoginBean.UserInfoBean userInfo;
    private String TAG = "info";
    private UserInfoBean user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgm_self, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getContext()).load(Contents.USER.getPicture()).asBitmap().into(selfIcon);
        selfMotto.setText(Contents.USER.getAutograph());
        useNameUseless.setText(Contents.USER.getNickname());
    }
    private void initView() {
        user = (UserInfoBean) SharedUtils.readUserInfo(getContext());
        Glide.with(getContext()).load(Contents.USER.getPicture()).asBitmap().into(selfIcon);
        selfMotto.setText(Contents.USER.getAutograph());
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getContext());
        useNameUseless.setText(Contents.USER.getNickname());
        EMClient.getInstance().chatManager().loadAllConversations();
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(Contents.USER.getUsername());
        Log.e(TAG, "initView: "+(conversation==null)+Contents.USER.getUsername() );
/*        int unreadMsgCount = conversation.getUnreadMsgCount();
        if (unreadMsgCount>0){
            redBot.setVisibility(View.VISIBLE);
        }else {
            redBot.setVisibility(View.GONE);
        }*/
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("邀请您参加拼途旅游");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://pintu.schlhjnetworktesturl.com/index.php?m=home&c=user&a=inviteCode&username="+Contents.USER.getUsername());
        // text是分享文本，所有平台都需要这个字段
        oks.setText("扫描获得邀请码，注册成功后，邀请你的用户将获得红包奖励！");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://pintu.schlhjnetworktesturl.com/index.php?m=home&c=user&a=inviteCode&username="+Contents.USER.getUsername());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://pintu.schlhjnetworktesturl.com/index.php?m=home&c=user&a=inviteCode&username="+Contents.USER.getUsername());
        // 启动分享GUI
        oks.show(getContext());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @OnClick({R.id.btSetting,  R.id.selfMotto, R.id.input_use, R.id.travel_use,
            R.id.btMyRed, R.id.btMyNews, R.id.btMyOrders, R.id.btMyInvites, R.id.btMyFollow, R.id.btMyColloction,
            R.id.btMyMessage, R.id.btPresent, R.id.selfIcon,R.id.btWallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btWallet:
                startActivity(new Intent(getActivity(), WalletAty.class));
                break;
            case R.id.selfIcon:
                startActivity(new Intent(getActivity(),UserInfoAty.class));
/*                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(1)
                        .spanCount(3)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .theme(R.style.Matisse_Dracula)
                        .imageEngine(new GlideEngine())
                        .forResult(PICK_PHOTO);*/
                break;
            case R.id.btSetting:
                startActivity(new Intent(getActivity(), SettingsAty.class));
                break;
            case R.id.selfMotto:
                break;
            case R.id.input_use:
                startActivity(new Intent(getActivity(), PubDiaryAty.class));
                break;
            case R.id.travel_use:
                Intent intent = new Intent(getActivity(), TravelDiaryAty.class);
                intent.putExtra("username", user.getReturnArr().getUsername());
                startActivity(intent);
                break;
            case R.id.btMyRed:
                startActivity(new Intent(getActivity(), MyRedAty.class));
                break;
            case R.id.btMyNews:
                Intent intent1 = new Intent(getActivity(), UserNewsAty.class);
                intent1.putExtra("type",1);
                startActivity(intent1);
                break;
            case R.id.btMyOrders:
                startActivity(new Intent(getActivity(), MyOrderAty.class));
                break;
            case R.id.btMyInvites:
                startActivity(new Intent(getActivity(), MyInvitationAty.class));
                break;
            case R.id.btMyFollow:
                startActivity(new Intent(getActivity(), MyFollowAty.class));
                break;
            case R.id.btMyColloction:
                startActivity(new Intent(getActivity(), MyColloctionAty.class));
                break;
            case R.id.btMyMessage:
                startActivity(new Intent(getActivity(), MessageAty.class));
                break;
            case R.id.btPresent:
                showShare();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_PHOTO:
                if (resultCode == RESULT_OK) {
                    List<Uri> uris = Matisse.obtainResult(data);
                    try {
                        /*获取图骗路径*/
                        String path = MyUtils.getPath(getContext(), uris.get(0));
                        file = new File(path);
                        Glide.with(getContext()).load(uris.get(0)).asBitmap().into(selfIcon);
                        OkGo.post(Urls.PUBLIC_URL + Urls.EDIT_USERINFO)
                                .params("username", userInfo.getUsername())
                                .params("picture", file)
                                .getCall(StringConvert.create(), RxAdapter.<String>create())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        Log.e(TAG, "call: 穿头像开始" + (file == null) + file.getAbsolutePath());
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        Log.e(TAG, "call:头像获得 0" + s);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e(TAG, "call: 头像错误" + throwable.toString());
                                    }
                                });
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
