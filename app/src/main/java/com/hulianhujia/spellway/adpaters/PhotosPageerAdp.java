package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.PhotosBean;

import java.util.List;

/**
 * Created by FHP on 2017/8/7.
 */

public class PhotosPageerAdp extends PagerAdapter{

    private Context context;
    private List<PhotosBean.ReturnArrBean> datas;

    public PhotosPageerAdp(Context context, List<PhotosBean.ReturnArrBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.photos_item, null);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        Glide.with(context).load(datas.get(position).getPicture()).asBitmap().into(img);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
