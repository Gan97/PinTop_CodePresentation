package com.hulianhujia.spellway.customViews;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.hulianhujia.spellway.R;

/**
 * Created by FHP on 2017/7/24.
 */

public class LoadingDialog extends Dialog {
    private Context context;
    public LoadingDialog(Context context){
        super(context,R.style.CustomDialog);
        this.context=context;
        this.setCanceledOnTouchOutside(false);
        init();
    }
    private void init() {
        View inflate = LayoutInflater.from(context).inflate(R.layout.loadingdialog, null);
        setContentView(inflate);
    }
    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams lp2 = getWindow().getAttributes();
        lp2.alpha = 0.7f;
        getWindow().setAttributes(lp2);
    }
    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }
}
