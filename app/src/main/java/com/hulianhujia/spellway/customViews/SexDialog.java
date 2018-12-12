package com.hulianhujia.spellway.customViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.ChangeSexEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FHP on 2017/4/5.
 */

public class SexDialog extends Dialog {

    @Bind(R.id.btM)
    TextView btM;
    @Bind(R.id.btF)
    TextView btF;

    public SexDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_sex_dialog);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btM, R.id.btF})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btM:
                EventBus.getDefault().post(new ChangeSexEvent(1));
                dismiss();
                break;
            case R.id.btF:
                EventBus.getDefault().post(new ChangeSexEvent(2));
                dismiss();
                break;
        }
    }
}