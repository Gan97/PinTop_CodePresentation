package com.hulianhujia.spellway.activitys.guider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.event.ChangeHPEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ShuaiTao
 * data: on 2017\9\15 0015 14:50
 * E-Mail: bill_dream@sina.com
 */

public class ChangeHPop extends PopupWindow {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.etMoney)
    EditText etMoney;
    @Bind(R.id.btCancel)
    TextView btCancel;
    @Bind(R.id.btCommit)
    TextView btCommit;
    private Context context;
    private View view;
    private String curNum;

    public ChangeHPop(Context context,String curNum) {
        this.context = context;
        this.curNum=curNum;
        initPop();
        setPop();
    }

    private void setPop() {

        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initPop() {
        view = LayoutInflater.from(context).inflate(R.layout.change_h_pop, null);
        setContentView(view);
        ButterKnife.bind(this,view);
        etMoney.setText(curNum);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChangeHPEvent(etMoney.getText().toString()));
                dismiss();
            }
        });
    }

}
