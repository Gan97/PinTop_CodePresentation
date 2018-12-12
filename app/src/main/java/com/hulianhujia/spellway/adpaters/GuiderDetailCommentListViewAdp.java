package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.GuideDetailBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/5/23.
 */

public class GuiderDetailCommentListViewAdp extends BaseAdapter {
    private List<GuideDetailBean.ReturnArrBean.JudgeBean> fake;
    private Context context;

    public GuiderDetailCommentListViewAdp(List<GuideDetailBean.ReturnArrBean.JudgeBean> fake, Context context) {
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
        GuiderDetailCommentListViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_guider_comment_item, null);
            holder = new GuiderDetailCommentListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GuiderDetailCommentListViewHolder) convertView.getTag();
        }

        GuideDetailBean.ReturnArrBean.JudgeBean judgeBean = fake.get(position);
        holder.commentUserName.setText(judgeBean.getNickname());
        holder.commentContent.setText(judgeBean.getContent());
        if (judgeBean.getJg_time()!=null&&judgeBean.getJg_time().length()!=0){
            holder.commentTime.setText(MyUtils.convertTimeToFormat(Long.parseLong(judgeBean.getJg_time())));
        }
        Glide.with(context).load(judgeBean.getPicture()).asBitmap().into(holder.commentUserIcon);
        return convertView;
    }
     class GuiderDetailCommentListViewHolder {
         @Bind(R.id.commentUserIcon)
        CircleImageView commentUserIcon;
        @Bind(R.id.commentUserName)
        TextView commentUserName;
        @Bind(R.id.commentContent)
        TextView commentContent;
        @Bind(R.id.commentTime)
        TextView commentTime;
        GuiderDetailCommentListViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
