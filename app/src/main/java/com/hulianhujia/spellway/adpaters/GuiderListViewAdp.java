package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.activitys.GuiderDetailActivity;
import com.hulianhujia.spellway.javaBeans.GuideListBean;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FHP on 2017/5/23.
 */

public class GuiderListViewAdp extends BaseAdapter {
    private Context context;
    private List<GuideListBean.ReturnArrBean> fake;
    private String TAG="info";

    public GuiderListViewAdp(Context context, List<GuideListBean.ReturnArrBean> fake) {
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
        GudierViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fgm_guide_list_item, null);
            holder = new GudierViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GudierViewHolder) convertView.getTag();
        }
        GuideListBean.ReturnArrBean bean = fake.get(position);
        if (bean.getNickname().length()==0||bean.getNickname()==null){
            holder.name.setText(bean.getUsername());
        }else {
            holder.name.setText(bean.getNickname());
        }
        holder.guiderPrice.setText(bean.getBase_fee());
        holder.guiderHourPrice.setText(bean.getTime_fee());
        holder.guiderMotto.setText(bean.getAutograph());
        holder.guiderTotal.setText("已接"+bean.getTotal_finish_order()+"单");
        String average_score = bean.getAverage_score();
        holder.rating.setRating(Float.parseFloat(average_score));
        holder.guiderName.setText(bean.getAge());
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
        double distance = bean.getDistance();
        final String s1 = String.format("%.1f", distance);
        holder.lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GuiderDetailActivity.class);
                intent.putExtra("guiderName", fake.get(position).getUsername());
                intent.putExtra("dis", s1);
                context.startActivity(intent);
            }
        });
        if (bean.getPicture() != null && bean.getPicture().length() != 0) {
            Glide.with(context).load(bean.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.guiderIcon);
        }
        return convertView;
    }
    static class GudierViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.guiderIcon)
        RoundedImageView guiderIcon;
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
        @Bind(R.id.rating)
        SimpleRatingBar rating;
        @Bind(R.id.guiderType)
        TextView guiderType;
        @Bind(R.id.guiderTotal)
        TextView guiderTotal;
        @Bind(R.id.guiderMotto)
        TextView guiderMotto;
        @Bind(R.id.lo)
        RelativeLayout lo;
        GudierViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
