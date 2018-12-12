package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.MessageListBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/8/3.
 */

public class MessageListAdp extends BaseAdapter {
    private Context context;
    private List<MessageListBean.ReturnArrBean> datas;
    private UserInfoBean userInfoBean;
    public MessageListAdp(Context context, List<MessageListBean.ReturnArrBean> datas) {
        userInfoBean= (UserInfoBean) SharedUtils.readUserInfo(context);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.message_list_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        MessageListBean.ReturnArrBean bean = datas.get(position);

        Glide.with(context).load(bean.getHepicture()).asBitmap().into(holder.itemIcon);
        holder.tvTitle.setText(bean.getHename());
        holder.tvContent.setText(bean.getMessage());
        holder.tvTime.setText(MyUtils.convertTimeToFormat(Long.parseLong(bean.getTime())));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.itemIcon)
        CircleImageView itemIcon;
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.tvTime)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
