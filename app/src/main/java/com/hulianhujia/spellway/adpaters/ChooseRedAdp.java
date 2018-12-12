package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.RedBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ShuaiTao
 * data: on 2017\10\12 0012 10:11
 * E-Mail: bill_dream@sina.com
 */

public class ChooseRedAdp extends BaseAdapter {
    private Context context;
    private List<RedBean.ReturnArrBean> datas;
    public static HashMap<Integer,Boolean> isCheckedMap=new HashMap<>();
    public static String checkedRedId;
    public static String amont;
    private String TAG="info";
    public ChooseRedAdp(Context context, List<RedBean.ReturnArrBean> datas) {
        this.context = context;
        this.datas = datas;
        for (int i = 0; i < datas.size(); i++) {
            isCheckedMap.put(i,false);
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.choosered_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        RedBean.ReturnArrBean data = datas.get(position);

        final long mTime = Long.parseLong(data.getMax_use_time());
        final long cTime = System.currentTimeMillis()/1000;
        holder.isChecked.setChecked(isCheckedMap.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onItemClick: 点击了" );
                if (cTime<mTime){
                    for (int i = 0; i < isCheckedMap.size(); i++) {
                        if (i==position){
                            isCheckedMap.put(position,true);
                        }else {
                            isCheckedMap.put(i,false);
                        }
                    }
                    ChooseRedAdp.checkedRedId=datas.get(position).getId();
                    ChooseRedAdp.amont=datas.get(position).getAmount();
                    notifyDataSetChanged();
                }else {

                }
            }
        });
        String amount = data.getAmount();
        holder.redPrice.setText("￥"+amount);
        Log.e(TAG, "getView: m"+mTime+"c"+cTime );
        if (cTime>mTime){
            holder.isOver.setVisibility(View.VISIBLE);
        }else {
            holder.isOver.setVisibility(View.GONE);
        }
        holder.redTime.setText(MyUtils.timeStampToStrYMD(mTime));
        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.dv1)
        TextView dv1;
        @Bind(R.id.isChecked)
        CheckBox isChecked;
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.isOver)
        ImageView isOver;
        @Bind(R.id.redPrice)
        TextView redPrice;
        @Bind(R.id.redContent)
        TextView redContent;
        @Bind(R.id.redTime)
        TextView redTime;
        @Bind(R.id.rl_useless)
        RelativeLayout rlUseless;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
