package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.CommunityCommentListBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ShuaiTao
 * data: on 2017\9\6 0006 17:33
 * E-Mail: bill_dream@sina.com
 */

public class CommunityCommentListAdp extends BaseAdapter {
    private Context context;
    private List<CommunityCommentListBean.ReturndataBean> datas;

    public CommunityCommentListAdp(Context context, List<CommunityCommentListBean.ReturndataBean> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.talk_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        CommunityCommentListBean.ReturndataBean bean = datas.get(position);
        holder.name.setText(bean.getNickname()+":");
        holder.content.setText(bean.getContent());
        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.content)
        TextView content;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
