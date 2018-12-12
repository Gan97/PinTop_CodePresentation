package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.PhotosBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FHP on 2017/8/4.
 */

public class GuidePhotosAdp extends BaseAdapter {
    private Context context;
    private List<PhotosBean.ReturnArrBean> datas;

    public GuidePhotosAdp(Context context, List<PhotosBean.ReturnArrBean> datas) {
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
        ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.guide_photos_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(datas.get(position).getPicture()).into(holder.img);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img)
        ImageView img;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
