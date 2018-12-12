package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.activitys.OtherUserInfoAty;
import com.hulianhujia.spellway.javaBeans.CommunityDetailBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/5/23.
 */

public class CommunityDetailCommentListViewAdp extends BaseAdapter {
    private List<CommunityDetailBean.ReturnArrBean.JudgeBean> fake;
    private Context context;
    private String TAG="info";

    public CommunityDetailCommentListViewAdp(List<CommunityDetailBean.ReturnArrBean.JudgeBean> fake, Context context) {
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
        CommunityCommentListViewHolder holder ;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_diary_comment_item, null);
            holder=new CommunityCommentListViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (CommunityCommentListViewHolder) convertView.getTag();
        }
        final CommunityDetailBean.ReturnArrBean.JudgeBean judgeBean = fake.get(position);
        holder.commentUserName.setText(judgeBean.getNickname());
        Log.e(TAG, "getView: "+judgeBean.getUserpicture() );
        Glide.with(context).load(judgeBean.getUserpicture()).asBitmap().into(holder.commentUserIcon);
        holder.commentContent.setText(judgeBean.getContent());
        holder.commentTime.setText(MyUtils.timeStampToStr(Long.parseLong(judgeBean.getJg_time())));
        holder.commentUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OtherUserInfoAty.class);
                intent.putExtra("userName", judgeBean.getUsername());
                intent.putExtra("toNick",judgeBean.getNickname());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

     class CommunityCommentListViewHolder {
        @Bind(R.id.commentUserIcon)
        CircleImageView commentUserIcon;
        @Bind(R.id.commentUserName)
        TextView commentUserName;
        @Bind(R.id.commentContent)
        TextView commentContent;
        @Bind(R.id.commentTime)
        TextView commentTime;

        CommunityCommentListViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
