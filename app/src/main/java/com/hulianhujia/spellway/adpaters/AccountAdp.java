package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.AccountDetailBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/24/024.
 */

public class AccountAdp extends BaseAdapter {
    private Context context;
    private List<AccountDetailBean.ReturnArrBean> datas;

    public AccountAdp(Context context, List<AccountDetailBean.ReturnArrBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.account_item, null);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        AccountDetailBean.ReturnArrBean data = datas.get(i);

        holder.tv1.setText(data.getChange_desc());
        holder.detail.setText(data.getUser_yue());
        holder.time.setText(data.getChange_time());

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.tv1)
        TextView tv1;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.detail)
        TextView detail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
