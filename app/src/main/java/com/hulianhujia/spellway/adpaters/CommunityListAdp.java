package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.ChatAty;
import com.hulianhujia.spellway.activitys.CommunityDetailAty;
import com.hulianhujia.spellway.activitys.InviteWeChatAty;
import com.hulianhujia.spellway.activitys.OtherUserInfoAty;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.customViews.MultiImageView;
import com.hulianhujia.spellway.customViews.MyListView;
import com.hulianhujia.spellway.ease.EaseUt;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.BigBaseBean;
import com.hulianhujia.spellway.javaBeans.CommentBaseBean;
import com.hulianhujia.spellway.javaBeans.CommunityCommentListBean;
import com.hulianhujia.spellway.javaBeans.CommunityListBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.javaBeans.ZanBean;
import com.hulianhujia.spellway.untils.DataFactory;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.hyphenate.easeui.domain.EaseUser;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
/**
 * Created by FHP on 2017/8/1.
 */
public class CommunityListAdp extends BaseAdapter {
    private Context context;
    private List<CommunityListBean.ReturnArrBean> fake;
    private UserInfoBean userInfo;
    private String TAG = "info";
    private HashMap<Integer,Integer> pageMap = new HashMap<>();
    private LoadingDialog loadingDialog;
    private int index;
    public CommunityListAdp(Context context, List<CommunityListBean.ReturnArrBean> fake) {
        this.context = context;
        this.fake = fake;
        userInfo = (UserInfoBean) SharedUtils.readUserInfo(context);
        loadingDialog=new LoadingDialog(context);
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for (int i = 0; i < getCount(); i++) {
            pageMap.put(i,1);
        }
    }
    @Override
    public int getCount() {
        return fake.size();
    }
    @Override
    public Object getItem(int position) {
        return fake.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.community_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommunityDetailAty.class);
                intent.putExtra("cid", fake.get(position).getCmnt_id());
                intent.putExtra("uid", fake.get(position).getUser_id());
                context.startActivity(intent);
            }
        });
        final CommunityListBean.ReturnArrBean bean = fake.get(position);
        final List<CommunityCommentListBean.ReturndataBean> listdatas = new ArrayList<>();
        final CommunityCommentListAdp adp = new CommunityCommentListAdp(context,listdatas);
        holder.commentListView.setAdapter(adp);
        String userPicture = bean.getUserPicture();
        if (userPicture != null && userPicture.length() != 0) {
            Glide.with(context).load(userPicture).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.userIcon);
        }
        EaseUser easeUser = EaseUt.userMap.get(bean.getUsername());
        if (easeUser==null){
            easeUser=new EaseUser(bean.getUsername());
            easeUser.setAvatar(bean.getUserPicture());
            easeUser.setNickname(bean.getNickname());
            EaseUt.userMap.put(bean.getUsername(),easeUser);
        }
        switch (bean.getIs_zan()){
            case "0":
                Glide.with(context).load(R.drawable.like_grey).asBitmap().into(holder.btZan);
                break;
            case "1":
                Glide.with(context).load(R.drawable.like_red).asBitmap().into(holder.btZan);
                break;
        }
        holder.btZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.get(Urls.PUBLIC_URL+Urls.ZAN)
                        .params("article_id",bean.getCmnt_id())
                        .params("type",1)
                        .params("username",userInfo.getReturnArr().getUsername())
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 点赞开始" );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 点赞获得" + s);
                                Gson g = new Gson();
                                ZanBean zanBean = g.fromJson(s, ZanBean.class);
                                Log.e(TAG, "call: 咱"+zanBean.getCode() );
                                if (zanBean.getCode()==1){
                                    Log.e(TAG, "call:赞 "+bean.getZan() );
                                    switch (bean.getIs_zan()){
                                        case "0":
                                            bean.setIs_zan("1");
                                            Log.e(TAG, "call: 红" );
                                            Glide.with(context).load(R.drawable.like_red).asBitmap().into(holder.btZan);
                                            break;
                                        case "1":
                                            bean.setIs_zan("0");
                                            Log.e(TAG, "call: 灰" );
                                            Glide.with(context).load(R.drawable.like_grey).asBitmap().into(holder.btZan);
                                            break;
                                    }
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 点赞错误"+throwable.toString() );
                            }
                        });
            }
        });
        holder.userName.setText(bean.getNickname());
        holder.loc.setText(bean.getWhere());
        holder.tvContent.setText(bean.getContent());
        holder.btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(bean.getCmnt_id(),bean.getContent());
            }
        });
        holder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherUserInfoAty.class);
                intent.putExtra("userName", bean.getUsername());
                intent.putExtra("toNick",bean.getNickname());
                context.startActivity(intent);
            }
        });
        Log.e(TAG, "getView:" + bean.getUser_id() + "======" + userInfo.getReturnArr().getUser_id());
        if (!bean.getUser_id().equals(userInfo.getReturnArr().getUser_id())) {
            holder.btInvite.setVisibility(View.VISIBLE);
            holder.btInvite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatAty.class);
                    intent.putExtra("userId", bean.getUsername());
                    intent.putExtra("toNick",bean.getNickname());
                    intent.putExtra("chatType", Contents.CHATTYPE_SINGLE);
                    context.startActivity(intent);
                }
            });
        } else {
            holder.btInvite.setVisibility(View.GONE);
        }
        String picture = bean.getPicture();
        List<ImageInfo> imageInfos = new ArrayList<>();
        if (picture != null && picture.length() != 0) {
            String[] split = picture.split(",");
            for (int i = 0; i < split.length; i++) {
                String s = split[0];
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setThumbnailUrl(split[i]);
                imageInfo.setBigImageUrl(split[i]);
                imageInfos.add(imageInfo);
            }
            holder.nineGrideView.setAdapter(new NineGridViewClickAdapter(context, imageInfos));
        }else {
            holder.nineGrideView.setAdapter(new NineGridViewClickAdapter(context, imageInfos));
        }
        holder.btTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etTalk.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.etTalk, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        holder.btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etTalk.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.etTalk, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        holder.etTalk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    holder.btSend.setVisibility(View.VISIBLE);
                }else {
                    holder.btSend.setVisibility(View.GONE);
                }
            }
        });
        holder.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.post(Urls.PUBLIC_URL+Urls.SEND_COMMENT)
                        .params("jgd_id",bean.getCmnt_id())
                        .params("content",holder.etTalk.getText().toString())
                        .params("cateid",1)
                        .params("username",Contents.USER.getUsername())
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 开始评论" );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 评论获得"+s );
                                Gson g = new Gson();
                                BigBaseBean baseBean = g.fromJson(s, BigBaseBean.class);
                                if (baseBean.getCode()==1){
                                    CommunityCommentListBean.ReturndataBean tb = new CommunityCommentListBean.ReturndataBean();
                                    tb.setNickname(Contents.USER.getNickname());
                                    tb.setContent(holder.etTalk.getText().toString());
                                    tb.setJg_time(System.currentTimeMillis()+"");
                                    listdatas.add(0,tb);
                                    holder.etTalk.setText("");
                                    adp.notifyDataSetChanged();
                                }
                            }
                        });
            }
        });
        holder.tvHowLong.setText(MyUtils.convertTimeToFormat(Long.parseLong(bean.getCreate_time())));
        int page=1;
        Log.e(TAG, "onClick: "+pageMap.toString() );
        for (int i = 0; i < pageMap.size(); i++) {
            if (i==position){
                page=pageMap.get(i);
            }
        }
        /*查看更多按钮*/
        for (int i = page; i >0; i--) {
            final int finalI = i;
            final int finalPage = page;
            OkGo.get(Urls.PUBLIC_URL+ Urls.GET_TALK)
                    .params("jgd_id",bean.getCmnt_id())
                    .params("cateid",1)
                    .params("p",i)
                    .params("pageSize",5)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 获取评论开始"+ finalI);
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Log.e(TAG, "call: 评论获得" + bean.getCmnt_id() + "的评论为" + s);
                            Gson g = new Gson();
                            CommentBaseBean baseBean = g.fromJson(s, CommentBaseBean.class);
                            if (baseBean.getCode() == 1) {
                                ArrayList<CommunityCommentListBean.ReturndataBean> returndataBeens =
                                        DataFactory.jsonToArrayList(g.toJson(baseBean.getReturndata()), CommunityCommentListBean.ReturndataBean.class);
                                listdatas.addAll(returndataBeens);
                                adp.notifyDataSetChanged();
                                Log.e(TAG, "call: " + baseBean.getTotalPage() + finalPage);
                                if (baseBean.getTotalPage() > finalPage) {
                                    holder.btCheckMore.setVisibility(View.VISIBLE);
                                    holder.btCheckMore.setText("查看更多");
                                    Log.e(TAG, "call: 设置点击事件+++++++++++++++++++++++++++++++");
                                    holder.btCheckMore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int lastPage = pageMap.get(position);
                                            lastPage++;
                                            final int finalLastPage = lastPage;
                                            OkGo.get(Urls.PUBLIC_URL + Urls.GET_TALK)
                                                    .params("jgd_id", bean.getCmnt_id())
                                                    .params("cateid", 1)
                                                    .params("pageSize", 5)
                                                    .params("p", lastPage)
                                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                                    .doOnSubscribe(new Action0() {
                                                        @Override
                                                        public void call() {
                                                            loadingDialog.show();
                                                            Log.e(TAG, "call: 下页评论开始laspage");
                                                        }
                                                    }).observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<String>() {
                                                        @Override
                                                        public void call(String s) {
                                                            Log.e(TAG, "call: 下页评论获得" + s);
                                                            Gson g = new Gson();
                                                            loadingDialog.dismiss();
                                                            CommentBaseBean o = g.fromJson(s, CommentBaseBean.class);
                                                            Log.e(TAG, "call: 鸡肋" + o.getReturndata().toString());
                                                            if (o.getCode() == 1) {
                                                                ArrayList<CommunityCommentListBean.ReturndataBean> list =
                                                                        DataFactory.jsonToArrayList(g.toJson(o.getReturndata()), CommunityCommentListBean.ReturndataBean.class);
                                                                listdatas.addAll(list);
                                                                adp.notifyDataSetChanged();
                                                                pageMap.put(position, finalLastPage);
                                                                if (o.getTotalPage() == finalLastPage) {
                                                                    holder.btCheckMore.setVisibility(View.GONE);
                                                                    holder.btCheckMore.setClickable(false);
                                                                }
                                                            }
                                                            /*else {
                                                                holder.btCheckMore.setText("没有更多了");
                                                                holder.btCheckMore.setClickable(false);
                                                            }*/
                                                        }
                                                    }, new Action1<Throwable>() {
                                                        @Override
                                                        public void call(Throwable throwable) {
                                                            Log.e(TAG, "call: 下页评论错误" + throwable.toString());
                                                        }
                                                    });
                                        }
                                    });
                                } else {
                                    holder.btCheckMore.setVisibility(View.GONE);
                                }
                            } else {
                                holder.btCheckMore.setVisibility(View.GONE);
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e(TAG, "call: 评论错误"+throwable.toString() );
                            throwable.printStackTrace();
                        }
                    });
        }
        return convertView;
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
    static class ViewHolder {
        @Bind(R.id.userIcon)
        CircleImageView userIcon;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.tvHowLong)
        TextView tvHowLong;
        @Bind(R.id.user_iv_detail)
        RelativeLayout userIvDetail;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.nineGrideView)
        NineGridView nineGrideView;
        @Bind(R.id.btInvite)
        TextView btInvite;
        @Bind(R.id.commentListView)
        MyListView commentListView;
        @Bind(R.id.etTalk)
        EditText etTalk;
        @Bind(R.id.btTalk)
        ImageView btTalk;
        @Bind(R.id.btCheckMore)
                TextView btCheckMore;
        @Bind(R.id.btZan)
                ImageView btZan;
        @Bind(R.id.loc)
                TextView loc;
        @Bind(R.id.btSend)
                TextView btSend;
        @Bind(R.id.btShow)
        FrameLayout btShow;
        @Bind(R.id.btShare)
                ImageView btShare;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
