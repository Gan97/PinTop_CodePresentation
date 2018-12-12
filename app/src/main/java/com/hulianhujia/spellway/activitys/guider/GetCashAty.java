package com.hulianhujia.spellway.activitys.guider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.GetCashBean;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GetCashAty extends BaseActivity implements YesListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.wxCheck)
    CheckBox wxCheck;
    @Bind(R.id.loWe)
    RelativeLayout loWe;
    @Bind(R.id.textView8)
    TextView textView8;
    @Bind(R.id.zfbCheck)
    CheckBox zfbCheck;
    @Bind(R.id.loAli)
    RelativeLayout loAli;
    @Bind(R.id.loType)
    LinearLayout loType;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.etClient)
    EditText etClient;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.etClientAg)
    EditText etClientAg;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.etMoney)
    EditText etMoney;
    @Bind(R.id.btCommit)
    TextView btCommit;
    private int payFlag=1;
    private String TAG="info";
    @Override
    public int getContentId() {
        return R.layout.activity_get_cash_aty;
    }
    @OnClick({R.id.btExit, R.id.loWe, R.id.loAli, R.id.btCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.loWe:
                payFlag=2;
                Log.e(TAG, "onViewClicked: " );
                wxCheck.setChecked(true);
                zfbCheck.setChecked(false);
                break;
            case R.id.loAli:
                payFlag=1;
                wxCheck.setChecked(false);
                zfbCheck.setChecked(true);
                break;
            case R.id.btCommit:
                if (etClient.getText().toString().length()==0||etClientAg.getText().toString().length()==0||etMoney.getText().toString().length()==0){
                    ToastUtils.toast("请完整填写内容");
                }else {
                    if (etClient.getText().toString().equals(etClientAg.getText().toString())){
                        if (Double.parseDouble(Contents.GUIDE.getAmount())<Double.parseDouble(etMoney.getText().toString())){
                            ToastUtils.toast("您的余额没有那么多啦！");
                        }else {
                            ConfirmDialogN confirmDialogN= new ConfirmDialogN(this);
                            confirmDialogN.setFlag(1);
                            confirmDialogN.setYesListener(this);
                            confirmDialogN.show();
                            confirmDialogN.setTitle("确认提现？");
                        }
                    }else {
                        ToastUtils.toast("两次输入的账号不相同，请重新输入");
                    }
                }

                break;
        }
    }

    @Override
    public void yes(int flag) {
        OkGo.get(Urls.PUBLIC_URL+Urls.GETCASH)
                .params("username", Contents.GUIDE.getUsername())
                .params("password",getSharedPreferences("login",MODE_PRIVATE).getString("pwd",null))
                .params("type",payFlag)
                .params("amount",etMoney.getText().toString())
                .params("account",etClient.getText().toString())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始提现" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 提现获得" + s);
                        Gson g = new Gson();
                        BaseBean bean = g.fromJson(s, BaseBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode()==1){
                            DecimalFormat df = new DecimalFormat("0.00");
                            String format = df.format((Double.parseDouble(Contents.GUIDE.getAmount())-Double.parseDouble(etMoney.getText().toString())));
                            Contents.GUIDE.setAmount(format);
                            finish();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 提现错误"+throwable.toString());
                    }
                });
    }
}
