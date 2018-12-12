package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.GetFocusBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/5/24.
 */

public class MyFollowListViewAdp extends BaseAdapter {
    private Context context;
    private List<GetFocusBean.ReturnArrBean> fake;

    public MyFollowListViewAdp(Context context, List<GetFocusBean.ReturnArrBean> fake) {
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
        MyFollowViewHolder holder;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.activity_myfollow_item, null);
            holder=new MyFollowViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (MyFollowViewHolder) convertView.getTag();
        }
        GetFocusBean.ReturnArrBean bean = fake.get(position);
        if (bean.getNickname()==null||bean.getNickname().equals("")){
            holder.userName.setText(bean.getUsername());
        }else {
            holder.userName.setText(bean.getNickname());
        }
        if (bean.getSex()!=null){
            switch (bean.getSex()){
                case "男":
                    holder.userSex.setImageResource(R.mipmap.male);
                    break;
                case "女":
                    holder.userSex.setImageResource(R.mipmap.female);
                    break;
            }
        }

        holder.userMotto.setText(bean.getAutograph());
        Glide.with(context).load(bean.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.userIcon);
        return convertView;
    }
     class MyFollowViewHolder {
        @Bind(R.id.userIcon)
        CircleImageView userIcon;
        @Bind(R.id.userSex)
        ImageView userSex;
        @Bind(R.id.lo)
        RelativeLayout lo;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.userMotto)
                TextView userMotto;
        MyFollowViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
