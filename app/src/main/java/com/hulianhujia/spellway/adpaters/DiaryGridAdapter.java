package com.hulianhujia.spellway.adpaters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.untils.AppManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017\6\15 0015.
 */

public class DiaryGridAdapter extends BaseAdapter {

    private List<String> currentImgs;

    public DiaryGridAdapter(List<String> list) {
        this.currentImgs = list;
    }

    @Override
    public int getCount() {
        return currentImgs.size();
    }

    @Override
    public Object getItem(int position) {
        return currentImgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(AppManager.appContext()).inflate(R.layout.diary_grid_adapter_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        Log.d("=======","currentImgs====>" + currentImgs.get(position));
        Glide.with(AppManager.appContext()).load(currentImgs.get(position)).placeholder(R.mipmap.mountain).into(holder.diaryAdapterItem);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.diary_adapter_item)
        ImageView diaryAdapterItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
