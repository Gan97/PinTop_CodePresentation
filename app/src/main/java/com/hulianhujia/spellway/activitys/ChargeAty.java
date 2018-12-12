package com.hulianhujia.spellway.activitys;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.OrderDetailAty;
import com.hulianhujia.spellway.event.WxPaySuccessEvent;
import com.hulianhujia.spellway.javaBeans.WxBean;
import com.hulianhujia.spellway.untils.OrderInfoUtil2_0;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
public class ChargeAty extends BaseActivity {
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
    @Bind(R.id.etMoney)
    EditText etMoney;
    @Bind(R.id.lo)
    RelativeLayout lo;
    @Bind(R.id.btCommit)
    TextView btCommit;
    private int payFlag=1;
    private String TAG="info";
    private int SDK_PAY_FLAG = 2;
    private Bitmap b;
    private IWXAPI api;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Map<String, String> result = (Map<String, String>) msg.obj;
            String status = result.get("resultStatus");
            Log.e(TAG, "handleMessage: " + status);
            if (status.equals("9000")) {
                ToastUtils.toast("支付成功");
//                Contents.USER.setAmount(Double.parseDouble((Contents.USER.getAmount())+Double.parseDouble(etMoney.getText().toString()))+"");
                Contents.USER.setAmount((Double.parseDouble(Contents.USER.getAmount())+Double.parseDouble(etMoney.getText().toString()))+"");
                finish();
            }
        }
    };
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wx425716eb8696ec69");
    }
    @Override
    public int getContentId() {
        return R.layout.activity_charge_aty;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
    }
    @OnClick({R.id.btExit, R.id.btCommit,R.id.loWe,R.id.loAli,R.id.lo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lo:
                etMoney.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etMoney, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.btExit:
                finish();
                break;
            case R.id.btCommit:
                if (etMoney.getText().toString().length()==0){
                    ToastUtils.toast("请输入充值金额");
                }else {
                    if (Double.parseDouble(etMoney.getText().toString())>20000){
                        ToastUtils.toast("充值金额一次不能超过2W元");
                    }else if (Double.parseDouble(etMoney.getText().toString())<=0){
                        ToastUtils.toast("充值金额不符合规范");
                    } else {
                        if (payFlag==1){
                            String orderMap = OrderInfoUtil2_0.builBizcontent("充值余额", "旅游", payFlag+"", "0.01");
                            String timestr = String.valueOf(System.currentTimeMillis());
                            Log.e(TAG, "onClick: " + timestr);
                            long timeStamp = 0;
                            if (timestr.length() >= 11) {
                                timeStamp = Long.parseLong(timestr) / (long) 1000;
                            }
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = sdf.format(timeStamp * 1000);
                            Log.e(TAG, "onClick: 时间" + date);
                            Map<String, String> zfp = OrderInfoUtil2_0.buildOrderParamMap(date, orderMap);
                            final String orderParam = OrderInfoUtil2_0.buildOrderParam(zfp);
                            OkGo.post(Urls.PUBLIC_URL + Urls.CHARGE)
                                    .params("username", Contents.USER.getUsername())
                                    .params("pay_fee",etMoney.getText().toString())
                                    .params("type",payFlag)
                                    .params("body", "充值余额")
                                    .params("subject", "旅游")
                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            loadingDialog.show();
                                            Log.e(TAG, "call: 支付加签开始");
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(final String s) {
                                            loadingDialog.dismiss();
                                            Log.e(TAG, "call: 支付加签signData                          " + s);
                                            Log.e(TAG, "call: 支付加签orderParam                        " + orderParam);
                                            final String orderInfo = orderParam + "&sign=" + s;
                                            Log.e(TAG, "call: " + orderInfo);
                                            Runnable r = new Runnable() {
                                                @Override
                                                public void run() {
                                                    PayTask alipay = new PayTask(ChargeAty.this);
                                                    Map<String, String> result = alipay.payV2(orderInfo, true);
                                                    Message msg = new Message();
                                                    msg.what = SDK_PAY_FLAG;
                                                    msg.obj = result;
                                                    handler.sendMessage(msg);
                                                }
                                            };
                                            Thread t = new Thread(r);
                                            t.start();
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            loadingDialog.dismiss();
                                            Log.e(TAG, "call: 支付加签错误" + throwable.toString());
                                        }
                                    });
                        }else if (payFlag==2){
                            OkGo.post(Urls.PUBLIC_URL + Urls.CHARGE_WX)
                                    .params("username",Contents.USER.getUsername())
                                    .params("type", 2)
                                    .params("pay_fee", etMoney.getText().toString())
                                    .params("body", "充值余额")
                                    .params("subject", "旅游")
                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            Log.e(TAG, "call: 微信支付开始访问后台" );
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            Log.e(TAG, "call: 微信支付后台返回" + s);
                                            Gson g = new Gson();
                                            WxBean wxBean = g.fromJson(s, WxBean.class);
                                            PayReq req = new PayReq();
                                            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                                            req.appId = wxBean.getAppid();
                                            req.partnerId=wxBean.getPartnerid();
                                            req.prepayId		=wxBean.getPrepayid();
                                            req.nonceStr		= wxBean.getNoncestr();
                                            req.timeStamp		= wxBean.getTimestamp()+"";
                                            req.packageValue	= wxBean.getPackageX();
                                            req.sign			= wxBean.getSign();
//                                                                        req.extData			= "app data"; // optional
//                                                                        Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                                            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                            api.sendReq(req);
/*                                    req.partnerId=wxBean.getPartnerid()		= json.getString("partnerid");
                                    req.prepayId		= json.getString("prepayid");
                                    req.nonceStr		= json.getString("noncestr");
                                    req.timeStamp		= json.getString("timestamp");
                                    req.packageValue	= json.getString("package");
                                    req.sign			= json.getString("sign");
                                    req.extData			= "app data"; // optional
                                    Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                    api.sendReq(req);*/
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            Log.e(TAG, "call: 错误" + throwable.toString());
                                        }
                                    });
                        }
                    }

                }
                break;
            case R.id.loWe:
                payFlag=2;
                Log.e(TAG, "onViewClicked:");
                wxCheck.setChecked(true);
                zfbCheck.setChecked(false);
                break;
            case R.id.loAli:
                payFlag=1;
                wxCheck.setChecked(false);
                zfbCheck.setChecked(true);
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
  public void handWxPay(WxPaySuccessEvent event){
      Contents.USER.setAmount(Double.parseDouble((Contents.USER.getAmount())+Double.parseDouble(etMoney.getText().toString()))+"");
      finish();
    }
}
