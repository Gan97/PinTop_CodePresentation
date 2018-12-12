package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hulianhujia.spellway.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FHP on 2017/5/27.
 */

public class TravelDiaryListViewAdp extends BaseAdapter {
    private List<String> fake;
    private Context context;
    public TravelDiaryListViewAdp(List<String> fake, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TravelViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fgm_home_list_item, null);
            holder=new TravelViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (TravelViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(fake.get(position));
        return convertView;
    }
    class TravelViewHolder {
        @Bind(R.id.itemBg)
        ImageView itemBg;
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvLocation)
        TextView tvLocation;
        @Bind(R.id.tvNum)
        TextView tvNum;
        TravelViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
