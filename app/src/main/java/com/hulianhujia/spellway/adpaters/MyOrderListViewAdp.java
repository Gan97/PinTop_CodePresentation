package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.event.RefreshOrderList;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.UserOriderListBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by FHP on 2017/5/24.
 */

public class MyOrderListViewAdp extends BaseAdapter {
    private Context context;
    private List<UserOriderListBean.ReturnArrBean> fake;
    private String TAG="info";

    public MyOrderListViewAdp(Context context, List<UserOriderListBean.ReturnArrBean> fake) {
        this.context = context;
        this.fake = fake;
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
        MyOrderViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_myorder_item, null);
            holder = new MyOrderViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyOrderViewHolder) convertView.getTag();
        }
        final UserOriderListBean.ReturnArrBean bean = fake.get(position);

        holder.guiderPrice.setText(bean.getBase_fee());
        holder.guiderHourPrice.setText(bean.getTime_fee());
        holder.guiderMotto.setText("订单号："+bean.getOrder_no());
        holder.name.setText(bean.getNickname());
        Glide.with(context).load(bean.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.guiderIcon);
        holder.guiderName.setText(bean.getAge());
        holder.guiderPrice.setText("￥"+bean.getBase_fee());
        holder.guiderHourPrice.setText("￥"+bean.getTime_fee());
        holder.guiderMotto.setText("订单号：  "+bean.getOrder_no());
        switch (bean.getLevel()){
            case "1":
                holder.imageViewUselessOther.setImageResource(R.mipmap.goldp);
                holder.guiderType.setText("金牌导游");
                break;
            case "2":
                holder.imageViewUselessOther.setImageResource(R.mipmap.silverp);
                holder.guiderType.setText("银牌导游");
                break;
            case "3":
                holder.imageViewUselessOther.setImageResource(R.mipmap.ironp);
                holder.guiderType.setText("铜牌导游");
                break;
        }
        if (bean.getSex()!=null){
            switch (bean.getSex()){
                case "男":
                    holder.guiderSex.setImageResource(R.mipmap.male);
                    break;
                case "女":
                    holder.guiderSex.setImageResource(R.mipmap.female);
                    break;
            }
        }
        switch (bean.getStatus()){
            case "0":
                holder.guiderTotal.setText("等待导游接单");
                break;
            case "1":
                holder.guiderTotal.setText("导游已接单");
                break;
            case "2":
                holder.guiderTotal.setText("导游已拒绝");
                break;
            case "3":
                holder.guiderTotal.setText("旅行已开始");
                break;
            case "4":
                if (bean.getPaystatus().equals("2")){
                    holder.guiderTotal.setText("订单已完成");
                }else{
                    holder.guiderTotal.setText("等待游客付款");
                }
                break;
            case "6":
                holder.guiderTotal.setText("订单已完成");
                break;
        }
        return convertView;
    }

     class MyOrderViewHolder {
        @Bind(R.id.tv_useless_other)
        TextView tvUselessOther;
        @Bind(R.id.guiderIcon)
        CircleImageView guiderIcon;
        @Bind(R.id.guiderSex)
        ImageView guiderSex;
        @Bind(R.id.iconLo)
        RelativeLayout iconLo;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.guiderName)
        TextView guiderName;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.guiderPrice)
        TextView guiderPrice;
        @Bind(R.id.hourPrice)
        TextView hourPrice;
        @Bind(R.id.guiderHourPrice)
        TextView guiderHourPrice;
        @Bind(R.id.imageView_useless_other)
        ImageView imageViewUselessOther;
        @Bind(R.id.guiderType)
        TextView guiderType;
        @Bind(R.id.guiderTotal)
        TextView guiderTotal;
        @Bind(R.id.text_useless)
        TextView textUseless;
        @Bind(R.id.guiderMotto)
        TextView guiderMotto;
        @Bind(R.id.backk)
                RelativeLayout btDelet;
         @Bind(R.id.lo)
                 RelativeLayout lo;
        MyOrderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
