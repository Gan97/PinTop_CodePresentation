package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.ColloctionBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/5/23.
 */

public class ColloctionListViewAdp extends BaseAdapter {

    private List<ColloctionBean.ReturnArrBean> fake;

    private Context context;
    public ColloctionListViewAdp(List<ColloctionBean.ReturnArrBean> fake, Context context) {
        this.fake = fake;
        this.context = context;
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
        HomeViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_item, null);
            holder = new HomeViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (HomeViewHolder) convertView.getTag();
        }
        ColloctionBean.ReturnArrBean bean = fake.get(position);

        holder.itemTitle.setText(bean.getTitle());
        holder.tvLoc.setText(bean.getWhere());
        holder.userNick.setText(bean.getNickname());
        holder.likeNum.setText(bean.getZan());
        holder.dis.setVisibility(View.GONE);
        Glide.with(context).load(bean.getUserpicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.userIcon);
        if (bean.getPicture().length()>1){
            Glide.with(context).load(bean.getPicture().split(",")[0]).asBitmap().into(holder.itemBack);
        }else {
            Glide.with(context).load(R.mipmap.timg).asBitmap().into(holder.itemBack);
        }
        if (bean.getLat()!=null&&!bean.getLat().equals("")&&!bean.getLng().equals("")&&bean.getLng()!=null
                &&!bean.getLat().equals("0")&&!bean.getLng().equals("0")){
            double lat = Double.parseDouble(bean.getLat());
            double lon = Double.parseDouble(bean.getLng());
            if (!SharedUtils.readLat(context).equals("")){
                String distance = MyUtils.distanceOfTwoPoints(lat, lon, Double.parseDouble(SharedUtils.readLat(context)), Double.parseDouble(SharedUtils.readLon(context)));
                holder.dis.setText(distance+"");
            }else {
                holder.dis.setText("未知");
            }
        }else {
            holder.dis.setText("未知");
        }
        return convertView;
    }
    static class HomeViewHolder {
        @Bind(R.id.userIcon)
        CircleImageView userIcon;
        @Bind(R.id.userNick)
        TextView userNick;
        @Bind(R.id.itemBack)
        ImageView itemBack;
        @Bind(R.id.shadowBack)
        ImageView shadowBack;
        @Bind(R.id.itemTitle)
        TextView itemTitle;
        @Bind(R.id.itemInfo)
        TextView itemInfo;
        @Bind(R.id.imgLike)
        ImageView imgLike;
        @Bind(R.id.likeNum)
        TextView likeNum;
        @Bind(R.id.btLike)
        LinearLayout btLike;
        @Bind(R.id.loc)
        ImageView loc;
        @Bind(R.id.tvLoc)
        TextView tvLoc;
        @Bind(R.id.dis)
        TextView dis;

        HomeViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
