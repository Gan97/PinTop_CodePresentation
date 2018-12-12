package com.hulianhujia.spellway.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.interfaces.NoListener;
import com.hulianhujia.spellway.interfaces.YesListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FHP on 2017/8/4.
 */

public class ConfirmDialog extends Dialog {
    @Bind(R.id.btNo)
    TextView btNo;
    @Bind(R.id.btYes)
    TextView btYes;
    private Context context;
    private YesListener yesListener;
    private NoListener noListener;
    private String TAG = "info";

    public ConfirmDialog(Context context) {
        super(context);
    }

    public void setYesListener(YesListener yesListener) {
        this.yesListener = yesListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confrimdialog);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btNo, R.id.btYes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btNo:
                dismiss();
                break;
            case R.id.btYes:
                break;
        }
    }
}
