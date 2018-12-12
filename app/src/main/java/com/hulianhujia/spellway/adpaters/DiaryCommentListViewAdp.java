package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.DiaryCommentBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/5/23.
 */

public class DiaryCommentListViewAdp extends BaseAdapter {
    private Context context;
    private List<DiaryCommentBean.ReturnArrBean.JudgeBean> fake;
    private String TAG="info";

    public DiaryCommentListViewAdp(Context context, List<DiaryCommentBean.ReturnArrBean.JudgeBean> fake) {
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
        DiaryCommentViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_diary_comment_item, null);
            holder=new DiaryCommentViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (DiaryCommentViewHolder) convertView.getTag();
        }
        DiaryCommentBean.ReturnArrBean.JudgeBean judgeBean = fake.get(position);

        holder.commentUserName.setText(judgeBean.getNickname());
        holder.commentContent.setText(judgeBean.getContent());
        long time = Long.parseLong(judgeBean.getJg_time());
        holder.commentTime.setText(MyUtils.convertTimeToFormat(time));
        if (judgeBean.getUserPicture().length()!=0&&judgeBean.getUserPicture()!=null){
            Glide.with(context).load(judgeBean.getUserPicture()).asBitmap().into(holder.commentUserIcon);
        }
        return convertView;
    }

     class DiaryCommentViewHolder {
        @Bind(R.id.commentUserIcon)
        CircleImageView commentUserIcon;
        @Bind(R.id.commentUserName)
        TextView commentUserName;
        @Bind(R.id.commentContent)
        TextView commentContent;
        @Bind(R.id.commentTime)
        TextView commentTime;

        DiaryCommentViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
