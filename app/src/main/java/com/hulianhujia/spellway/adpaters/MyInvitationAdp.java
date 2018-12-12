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
import com.hulianhujia.spellway.javaBeans.MyInviteEvent;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by FHP on 2017/5/27.
 */
public class MyInvitationAdp extends BaseAdapter {
    private Context context;
    private List<MyInviteEvent.ReturnArrBean> fake;
    public MyInvitationAdp(Context context, List<MyInviteEvent.ReturnArrBean> fake) {
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
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.myinvitation_item, null);
            holder= new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        MyInviteEvent.ReturnArrBean data = fake.get(position);
        Glide.with(context).load(data.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.userIcon);
        switch (data.getSex()){
            case "男":
                holder.userSex.setImageResource(R.mipmap.male);
                break;
            case "女":
                holder.userSex.setImageResource(R.mipmap.female);
                break;
        }
        holder.userName.setText(data.getNickname());
        holder.tvContent.setText(data.getAutograph());
        return convertView;
    }
     class ViewHolder {
        @Bind(R.id.userIcon)
        CircleImageView userIcon;
        @Bind(R.id.userSex)
        ImageView userSex;
        @Bind(R.id.lo)
        RelativeLayout lo;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.tvTime)
        TextView tvTime;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}