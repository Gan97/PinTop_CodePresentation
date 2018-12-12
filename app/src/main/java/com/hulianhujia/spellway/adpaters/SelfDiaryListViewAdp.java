package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.HomeBean;
import com.hulianhujia.spellway.javaBeans.ZanBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;


/**
 * Created by FHP on 2017/5/23.
 */

public class SelfDiaryListViewAdp extends BaseAdapter {

    private List<HomeBean.ReturnArrBean> fake;

    private Context context;
    private String TAG="info";
    private LoadingDialog loadingDialog;
    public SelfDiaryListViewAdp(List<HomeBean.ReturnArrBean> fake, Context context) {
        this.fake = fake;
        loadingDialog=new LoadingDialog(context);
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.self_home_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HomeBean.ReturnArrBean bean = fake.get(position);
        holder.likeNum.setText(bean.getZan());
        String picture = bean.getPicture();
        if (picture!=null&&picture.length()!=0){
            String[] split = picture.split(",");
            Glide.with(context).load(split[0]).asBitmap().placeholder(R.mipmap.timg).into(holder.itemBack);
        }else {
            Glide.with(context).load(R.mipmap.timg).into(holder.itemBack);
        }
        holder.itemTitle.setText(bean.getTitle());
        holder.tvLoc.setText(bean.getWhere());

        holder.btLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.get(Urls.PUBLIC_URL+Urls.ZAN)
                        .params("article_id",bean.getNews_id())
                        .params("type",2)
                        .params("username", Contents.USER.getUsername())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: 赞开始" );
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
                                    switch (bean.getIs_zan()) {
                                        case 0:
                                            bean.setIs_zan(1);
                                            int i = Integer.parseInt(bean.getZan())+1;
                                            bean.setZan(i+"");
                                            holder.likeNum.setText(i+"");
                                            Glide.with(context).load(R.mipmap.like_red).asBitmap().into(holder.imgLike);
                                            break;
                                        case 1:
                                            bean.setIs_zan(0);
                                            int i1 = Integer.parseInt(bean.getZan())-1;
                                            bean.setZan(i1+"");
                                            holder.likeNum.setText(i1+"");
                                            Glide.with(context).load(R.mipmap.like_grey).asBitmap().into(holder.imgLike);
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
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.itemBack)
        ImageView itemBack;
        @Bind(R.id.shadowBack)
        ImageView shadowBack;
        @Bind(R.id.itemTitle)
        TextView itemTitle;
        @Bind(R.id.itemInfo)
        TextView itemInfo;
        @Bind(R.id.likeNum)
        TextView likeNum;
        @Bind(R.id.btLike)
        LinearLayout btLike;
        @Bind(R.id.loc)
        ImageView loc;
        @Bind(R.id.tvLoc)
        TextView tvLoc;
        @Bind(R.id.imgLike)
                ImageView imgLike;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
