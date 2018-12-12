package com.hulianhujia.spellway.adpaters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.event.DeletEvent;
import com.hulianhujia.spellway.event.PubDiaryPhotoEvent;
import com.hulianhujia.spellway.interfaces.YesListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by FHP on 2017/7/21.
 */

public class PubDiaryAdp extends RecyclerView.Adapter {
    private Context context;
    private List<Uri> imgs;

    public PubDiaryAdp(Context context, List<Uri> imgs) {
        this.context = context;
        this.imgs = imgs;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == getItemCount() - 1) {
            View addView = LayoutInflater.from(context).inflate(R.layout.add, parent, false);
            AddHolder addHolder = new AddHolder(addView);
            return addHolder;
        } else {
            View itemView = LayoutInflater.from(context).inflate(R.layout.pubconmmunity_item, parent, false);
            ItemHolder holder = new ItemHolder(itemView);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof  AddHolder){
            ((AddHolder) holder).btAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new PubDiaryPhotoEvent());
                }
            });
        }else if (holder instanceof ItemHolder){
            Glide.with(context).load(imgs.get(position)).asBitmap().into(((ItemHolder) holder).img);
            ((ItemHolder) holder).img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ConfirmDialogN c = new ConfirmDialogN(context);
                    c.setFlag(1);
                    c.setYesListener(new YesListener() {
                        @Override
                        public void yes(int flag) {
                            EventBus.getDefault().post(new DeletEvent(position));
                        }
                    });
                    c.show();
                    c.setTitle("删除此图？");
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imgs.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class AddHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.btAdd)
        ImageView btAdd;

        AddHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img)
        ImageView img;

        ItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
