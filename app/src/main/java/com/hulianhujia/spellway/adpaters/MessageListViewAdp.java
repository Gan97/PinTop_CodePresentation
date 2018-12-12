package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hulianhujia.spellway.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/8/1.
 */

public class MessageListViewAdp extends BaseAdapter {
    private List<String> fake;
    private Context context;

    public MessageListViewAdp(List<String> fake, Context context) {
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
        MessageHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_item, null);
            holder=new MessageHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (MessageHolder) convertView.getTag();
        }


        return convertView;
    }

    static class MessageHolder {
        @Bind(R.id.itemIcon)
        CircleImageView itemIcon;
        @Bind(R.id.messageSource)
        TextView messageSource;
        @Bind(R.id.messageContent)
        TextView messageContent;
        @Bind(R.id.time)
        TextView time;

        MessageHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
