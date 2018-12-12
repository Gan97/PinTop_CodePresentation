package com.lzy.ninegrid.preview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.ninegrid.R;

/**
 * author: ShuaiTao
 * data: on 2017\9\18 0018 15:38
 * E-Mail: bill_dream@sina.com
 */

public class DownPop extends PopupWindow implements View.OnClickListener {
    private Context context;
    private String ur;
    private View view;
    private TextView btSave;

    public DownPop(Context context,String url){
        this.context=context;
        this.ur=url;
        initPop();
        setPop();
    }

    private void setPop() {
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

    }

    private void initPop() {
        view= LayoutInflater.from(context).inflate(R.layout.downpop,null);
        setContentView(view);
        btSave = ((TextView) view.findViewById(R.id.btSave));
        btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SDFileHelper helper=new SDFileHelper(context);
        helper.savePicture(System.currentTimeMillis()+".jpg",ur);
        dismiss();
    }
}
