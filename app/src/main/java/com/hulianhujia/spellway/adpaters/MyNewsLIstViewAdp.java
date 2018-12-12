package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.customViews.MyListView;
import com.hulianhujia.spellway.interfaces.YesListener;
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
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by FHP on 2017/5/24.
 */

public class MyNewsLIstViewAdp extends BaseAdapter {
    private List<CommunityListBean.ReturnArrBean> fake;
    private Context context;
    private String TAG = "info";
    private HashMap<Integer,Integer> pageMap = new HashMap<>();
    private LoadingDialog loadingDialog;
    private UserInfoBean userInfo;
    public MyNewsLIstViewAdp(List<CommunityListBean.ReturnArrBean> fake, Context context) {
        this.fake = fake;
        this.context = context;
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
        final MyNewsViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.mynews_item, null);
            holder = new MyNewsViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyNewsViewHolder) convertView.getTag();
        }

        //这里可以用holder.tvTime.setText("")来设置标题时间
        final CommunityListBean.ReturnArrBean bean = fake.get(position);
        holder.tvContent.setText(bean.getContent());
        String s1 = MyUtils.convertTimeToCustom(Long.parseLong(bean.getCreate_time()));
        String s2 = MyUtils.timeStampToHM(Long.parseLong(bean.getCreate_time()));
        holder.tvTime.setText(s1);
        holder.timeRecord.setText(s2);

        /*评论适配去*/
        final List<CommunityCommentListBean.ReturndataBean> listdatas = new ArrayList<>();
        final CommunityCommentListAdp adp = new CommunityCommentListAdp(context,listdatas);
        /*赞*/
        switch (bean.getZan()){
            case "0":
                Glide.with(context).load(R.drawable.like_grey).asBitmap().into(holder.btZan);
                break;
            case "1":
                Glide.with(context).load(R.drawable.like_red).asBitmap().into(holder.btZan);
                break;
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
                        .params("username", Contents.USER.getUsername())
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
        holder.btZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.get(Urls.PUBLIC_URL+Urls.ZAN)
                        .params("article_id",bean.getCmnt_id())
                        .params("type",1)
                        .params("username",userInfo.getReturnArr().getUsername())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
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
                                    switch (bean.getZan()){
                                        case "0":
                                            bean.setZan("1");
                                            Log.e(TAG, "call: 红" );
                                            Glide.with(context).load(R.drawable.like_red).asBitmap().into(holder.btZan);
                                            break;
                                        case "1":
                                            bean.setZan("0");
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
/*评论*/
        holder.btTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etTalk.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.etTalk, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        /*图片*/
        String picture = bean.getPicture();
        if (picture != null&&picture.length()!=0) {
            String[] strs = picture.split(",");
            List<ImageInfo> imageInfos = new ArrayList<>();
            for (String str : strs) {
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setBigImageUrl(str);
                imageInfo.setThumbnailUrl(str);
                imageInfos.add(imageInfo);
            }
            holder.photos.setAdapter(new NineGridViewClickAdapter(context, imageInfos));
            Log.e(TAG, "getView: " + holder.photos.getHeight());
        }
        /*删除*/
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialogN c =  new ConfirmDialogN(context);
                c.setFlag(2);
                c.setYesListener(new YesListener() {
                    @Override
                    public void yes(int flag) {
                        OkGo.post(Urls.PUBLIC_URL+Urls.DELETE_NEWS)
                                .params("cmnt_id",bean.getCmnt_id())
                                .params("username",Contents.USER.getUsername())
                                .params("password",context.getSharedPreferences("login",MODE_PRIVATE).getString("pwd",null))
                                .getCall(new StringConvert(),RxAdapter.<String>create())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        loadingDialog.show();
                                        Log.e(TAG, "call: 删除" );
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        loadingDialog.dismiss();
                                        Log.e(TAG, "call: 删除获得"+s );
                                        Gson g = new Gson();
                                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                        ToastUtils.toast(baseBean.getMsg());
                                        if (baseBean.getCode()==1){
                                            fake.remove(position);
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                    }
                });
                c.show();
                c.setTitle("确认删除此条动态？");
            }
        });
        /*查看评论*/
        int page=1;
        Log.e(TAG, "onClick: "+pageMap.toString() );
        for (int i = 0; i < pageMap.size(); i++) {
            if (i==position){
                page=pageMap.get(i);
            }
        }
        holder.tvLoc.setText(bean.getWhere());

        holder.commentListView.setAdapter(adp);
        /*查看更多按钮*/
        for (int i = page; i >0; i--) {
            final int finalI = i;
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
                            Log.e(TAG, "call: 评论获得"+bean.getCmnt_id()+"的评论为" + s);
                            Gson g = new Gson();
                            CommentBaseBean baseBean = g.fromJson(s, CommentBaseBean.class);
                            if (baseBean.getCode()==1){
                                ArrayList<CommunityCommentListBean.ReturndataBean> returndataBeens =
                                        DataFactory.jsonToArrayList(baseBean.getReturndata().toString(), CommunityCommentListBean.ReturndataBean.class);
                                listdatas.addAll(returndataBeens);
                                adp.notifyDataSetChanged();
                                if (returndataBeens.size()!=0){
                                    Log.e(TAG, "call: "+holder.commentListView.getFooterViewsCount());
                                    holder.btCheckMore.setVisibility(View.VISIBLE);
                                    holder.btCheckMore.setText("查看更多");
                                    Log.e(TAG, "call: 设置点击事件+++++++++++++++++++++++++++++++" );
                                    holder.btCheckMore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int lastPage = pageMap.get(position);
                                            lastPage++;
                                            final int finalLastPage = lastPage;
                                            OkGo.get(Urls.PUBLIC_URL+Urls.GET_TALK)
                                                    .params("jgd_id",bean.getCmnt_id())
                                                    .params("cateid",1)
                                                    .params("pageSize",5)
                                                    .params("p",lastPage)
                                                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                                                    .doOnSubscribe(new Action0() {
                                                        @Override
                                                        public void call() {
                                                            loadingDialog.show();
                                                            Log.e(TAG, "call: 下页评论开始laspage" );
                                                        }
                                                    }).observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new Action1<String>() {
                                                        @Override
                                                        public void call(String s) {
                                                            Log.e(TAG, "call: 下页评论获得" + s);
                                                            Gson g = new Gson();
                                                            loadingDialog.dismiss();
                                                            CommentBaseBean o = g.fromJson(s, CommentBaseBean.class);
                                                            Log.e(TAG, "call: 鸡肋"+o.getReturndata().toString() );
                                                            if (o.getCode()==1){
                                                                ArrayList<CommunityCommentListBean.ReturndataBean> list =
                                                                        DataFactory.jsonToArrayList(o.getReturndata().toString(), CommunityCommentListBean.ReturndataBean.class);
                                                                listdatas.addAll(list);
                                                                adp.notifyDataSetChanged();
                                                                pageMap.put(position, finalLastPage);
                                                            }
                                                            else {
                                                                holder.btCheckMore.setText("没有更多了");
                                                                holder.btCheckMore.setClickable(false);
                                                            }
                                                        }
                                                    }, new Action1<Throwable>() {
                                                        @Override
                                                        public void call(Throwable throwable) {
                                                            Log.e(TAG, "call: 下页评论错误"+throwable.toString() );
                                                        }
                                                    });
                                        }
                                    });

                                }else {
                                    holder.btCheckMore.setVisibility(View.GONE);
                                }
                            }else {
                                holder.btCheckMore.setVisibility(View.GONE);
                            }

                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e(TAG, "call: 评论获得错误"+throwable.toString() );
                        }
                    });
        }
        return convertView;
    }

    static class MyNewsViewHolder {
        @Bind(R.id.btDelete)
        ImageView btDelete;
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.time_record)
        TextView timeRecord;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.nineGrideView)
        NineGridView photos;
        @Bind(R.id.btZan)
        ImageView btZan;
        @Bind(R.id.btTalk)
        ImageView btTalk;
        @Bind(R.id.commentListView)
        MyListView commentListView;
        @Bind(R.id.btCheckMore)
        TextView btCheckMore;
        @Bind(R.id.etTalk)
        EditText etTalk;
        @Bind(R.id.loc)
                TextView tvLoc;
        @Bind(R.id.btShow)
        FrameLayout btShow;
        @Bind(R.id.btSend)
                TextView btSend;
        MyNewsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
