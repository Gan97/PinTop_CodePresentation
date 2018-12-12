package com.hulianhujia.spellway.adpaters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.RedBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FHP on 2017/5/24.
 */

public class MyRedListViewAdp extends BaseAdapter {
    private List<RedBean.ReturnArrBean> fake;
    private Context context;
    private String TAG="info";

    public MyRedListViewAdp(List<RedBean.ReturnArrBean> fake, Context context) {
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
        RedViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.myred_item, null);
            holder=new RedViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (RedViewHolder) convertView.getTag();
        }
        RedBean.ReturnArrBean data = fake.get(position);
        String amount = data.getAmount();
        holder.redPrice.setText("￥"+amount);
        long mTime = Long.parseLong(data.getMax_use_time());
        long cTime = System.currentTimeMillis()/1000;
        Log.e(TAG, "getView: m"+mTime+"c"+cTime );
        if (cTime>mTime){
            holder.isOver.setVisibility(View.VISIBLE);
        }else {
            holder.isOver.setVisibility(View.GONE);
        }
        holder.redTime.setText(MyUtils.timeStampToStrYMD(mTime));
        return convertView;
    }
     class RedViewHolder {
        @Bind(R.id.dv1)
        TextView dv1;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.redPrice)
        TextView redPrice;
        @Bind(R.id.redContent)
        TextView redContent;
        @Bind(R.id.redTime)
        TextView redTime;
        @Bind(R.id.isOver)
                ImageView isOver;
        RedViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
