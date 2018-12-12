package com.hulianhujia.spellway.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.interfaces.NoListener;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.untils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FHP on 2017/8/4.
 */

public class ConfirmDialogN extends Dialog {
    @Bind(R.id.btNo)
    TextView btNo;
    @Bind(R.id.btYes)
    TextView btYes;
    @Bind(R.id.tvTitle)
    TextView tv;
    private Context context;
    private YesListener yesListener;
    private NoListener noListener;



    private String TAG = "info";
    private String title;
    private int flag=0;
    public ConfirmDialogN(Context context) {
        super(context,R.style.CustomDialog);
    }

    public void setYesListener(YesListener yesListener) {
        this.yesListener = yesListener;
    }
    public NoListener getNoListener() {
        return noListener;
    }
    public void setNoListener(NoListener noListener) {
        Log.e(TAG, "setNoListener: 设置监听" );
        this.noListener = noListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confrimdialog);
        ButterKnife.bind(this);

    }
    public void setTitle(String str){
        tv.setText(str);
    }

    public void setFlag(int flag){
        this.flag=flag;
    }
    @OnClick({R.id.btNo, R.id.btYes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btNo:
                if (noListener==null){
                    dismiss();
                }else {
                    noListener.no();
                    dismiss();
                }
                break;
            case R.id.btYes:
                if (flag==0){
                    ToastUtils.toast("未设置flag");
                }else {
                    yesListener.yes(flag);
                    dismiss();
                }
                break;
        }
    }
}
