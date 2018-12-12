package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.javaBeans.MessageBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangChang on 2016/4/28.
 */
public class ChatAdapter extends RecyclerView.Adapter {

    private List<MessageBean.ReturnArrBean> dataList = new ArrayList<>();
    private Context context;
    private UserInfoBean userInfoBean;
    private String TAG="info";

    public ChatAdapter(List<MessageBean.ReturnArrBean> dataList, Context context) {
        userInfoBean = (UserInfoBean) SharedUtils.readUserInfo(context);
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View view = LayoutInflater.from(context).inflate(R.layout.chat_b, parent, false);
                AHolder holder = new AHolder(view);
                return holder;
            case 2:
                View toView = LayoutInflater.from(context).inflate(R.layout.chat_a, parent, false);
                ToHolder toHolder = new ToHolder(toView);
                return toHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageBean.ReturnArrBean bean = dataList.get(position);
        if (holder instanceof AHolder) {
            ((AHolder) holder).tv.setText(bean.getMessage());
            Log.e(TAG, "onBindViewHolder: 位置为"+position+"头像M"+bean.getMepicture()+"头像H"+bean.getHepicture() );
            String mepicture = bean.getMepicture();
                Glide.with(context).load(mepicture).asBitmap().into(((AHolder) holder).icUser);
        } else if (holder instanceof ToHolder) {
            ((ToHolder) holder).tv.setText(bean.getMessage());
            String hepicture = bean.getHepicture();
                Glide.with(context).load(bean.getMepicture()).asBitmap().into(((ToHolder) holder).icUser);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getUser1_id().equals(userInfoBean.getReturnArr().getUser_id())) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }


    static class ToHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ic_user)
        CircleImageView icUser;
        @Bind(R.id.tv)
        TextView tv;

        ToHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class AHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.ic_user)
        CircleImageView icUser;
        @Bind(R.id.tv)
        TextView tv;

        AHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
