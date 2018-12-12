package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.OverOrderBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/8/10.
 */

public class OverOrderListAdp extends BaseAdapter {
    private Context context;
    private List<OverOrderBean.ReturnArrBean.JuegeBean> datas;

    public OverOrderListAdp(Context context, List<OverOrderBean.ReturnArrBean.JuegeBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.over_order_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        OverOrderBean.ReturnArrBean.JuegeBean bean = datas.get(position);
        holder.commentContent.setText(bean.getContent());
        holder.commentUserName.setText(bean.getNickname());
        holder.commentTime.setText(MyUtils.convertTimeToFormat(Long.parseLong(bean.getJg_time())));
        holder.ratingBar.setRating(Integer.parseInt(bean.getStar()));
        Glide.with(context).load(bean.getPicture()).asBitmap().into(holder.commentUserIcon);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.commentUserIcon)
        CircleImageView commentUserIcon;
        @Bind(R.id.commentUserName)
        TextView commentUserName;
        @Bind(R.id.ratingBar)
        RatingBar ratingBar;
        @Bind(R.id.commentContent)
        TextView commentContent;
        @Bind(R.id.commentTime)
        TextView commentTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
