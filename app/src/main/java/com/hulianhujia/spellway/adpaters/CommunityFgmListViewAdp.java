package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.activitys.CommunityDetailAty;
import com.hulianhujia.spellway.activitys.InviteWeChatAty;
import com.hulianhujia.spellway.activitys.OtherUserInfoAty;
import com.hulianhujia.spellway.javaBeans.CommunityListBean;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FHP on 2017/5/23.
 */

public class CommunityFgmListViewAdp extends RecyclerView.Adapter<CommunityFgmListViewAdp.CommunityViewHolder> {

    private Context context;
    private List<CommunityListBean.ReturnArrBean> fake;

    public CommunityFgmListViewAdp(Context context, List<CommunityListBean.ReturnArrBean> fake) {
        this.context = context;
        this.fake = fake;
    }

    @Override
    public CommunityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fgm_community_list_item, parent, false);
        CommunityViewHolder vh = new CommunityViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(CommunityViewHolder holder, final int position) {
        holder.userIvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, OtherUserInfoAty.class));
            }
        });
        holder.btInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "=====222222=====" + position, Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, InviteWeChatAty.class));
            }
        });

        holder.payAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "=====关注====" + position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.talkNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "=====评论======", Toast.LENGTH_SHORT).show();
            }
        });

        holder.likeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "=====点赞======", Toast.LENGTH_SHORT).show();
            }
        });

        holder.lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,CommunityDetailAty.class);
                intent.putExtra("cid",fake.get(position).getCmnt_id());
                context.startActivity(intent);
            }
        });
        CommunityListBean.ReturnArrBean bean = fake.get(position);
        holder.userName.setText(bean.getUsername());
        holder.tvContent.setText(bean.getContent());
        holder.talkNum.setText(bean.getComment());
        holder.likeNum.setText(bean.getZan());


        String picture = bean.getPicture();
        if (picture!=null&&picture.length()!=0){
            String[] split = picture.split(",");
            List<ImageInfo> imageInfos=new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                String s = split[0];
                ImageInfo imageInfo=new ImageInfo();
                imageInfo.setThumbnailUrl(split[i]);
                imageInfo.setBigImageUrl(split[i]);
                imageInfos.add(imageInfo);
            }
            holder.nineGrideView.setAdapter(new NineGridViewClickAdapter(context,imageInfos));
        }
    }

    @Override
    public int getItemCount() {
        return fake.size();
    }


    public static class CommunityViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.userIcon)
        CircleImageView userIcon;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.image_useless)
        ImageView imageUseless;
        @Bind(R.id.tvHowLong)
        TextView tvHowLong;
        @Bind(R.id.user_iv_detail)
        RelativeLayout userIvDetail;
        @Bind(R.id.tvContent)
        TextView tvContent;
        @Bind(R.id.nineGrideView)
        NineGridView nineGrideView;
        @Bind(R.id.talkNum)
        TextView talkNum;
        @Bind(R.id.likeNum)
        TextView likeNum;
        @Bind(R.id.pay_attention)
        TextView payAttention;
        @Bind(R.id.btInvite)
        TextView btInvite;
        @Bind(R.id.commentUserName)
        TextView commentUserName;
        @Bind(R.id.commentContent)
        TextView commentContent;
        @Bind(R.id.comment_rl)
        RelativeLayout commentRl;
        @Bind(R.id.lo)
        LinearLayout lo;
        CommunityViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
