package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.OverOrderListAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.javaBeans.OverOrderBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class OverOrderAty extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.listView)
    ListView listView;
    private LoadingDialog loadingDialog;
    private String oid;
    private String TAG = "info";
    private OverOrderListAdp adp;
    private List<OverOrderBean.ReturnArrBean.JuegeBean> datas = new ArrayList<>();
    private CircleImageView guideIcon;
    private TextView guideName;
    private TextView guidePrice;
    private TextView guiderHourPrice;
    private TextView orderNum;
    private TextView btGrade;
    private String guiderName;
    private TextView phone;
    private TextView travelTime;
    private TextView travelPrice;
    private String status;
    public static OverOrderAty instance;
    private String baseFee;
    private String timeFee;

    @Override
    public int getContentId() {
        return R.layout.activity_over_order_aty;
    }

    @Override
    public void initView() {
        if (instance==null){
            instance=this;
        }
        loadingDialog = new LoadingDialog(this);
        oid = getIntent().getStringExtra("oid");
        baseFee = getIntent().getStringExtra("bf");
        timeFee = getIntent().getStringExtra("tf");
        View head = LayoutInflater.from(this).inflate(R.layout.overhead, null);
        btGrade = ((TextView) head.findViewById(R.id.btEva));
        guideIcon = ((CircleImageView) head.findViewById(R.id.userIcon));
        guideName = ((TextView) head.findViewById(R.id.guiderName));
        guidePrice = ((TextView) head.findViewById(R.id.guiderPrice));
        guiderHourPrice = ((TextView) head.findViewById(R.id.guideHourPrice));
        phone = ((TextView) head.findViewById(R.id.userPhone));
        travelTime = ((TextView) head.findViewById(R.id.travel_Time));
        orderNum = ((TextView) head.findViewById(R.id.tvOrderNum));
        travelPrice = ((TextView) head.findViewById(R.id.travelPrice));
        adp = new OverOrderListAdp(this, datas);
        listView.setAdapter(adp);
        listView.addHeaderView(head);


    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.OVER_ORDER_DETAIL)
                .params("order_id", oid)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call:详情开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call:详情获得 " + s);
                        Gson g = new Gson();
                        OverOrderBean bean = g.fromJson(s, OverOrderBean.class);
                        if (bean.getCode() == 1) {
                            OverOrderBean.ReturnArrBean arrBean = bean.getReturnArr().get(0);
                            orderNum.setText("订单号：" + arrBean.getOrder_no());
                            guideName.setText(arrBean.getGuidenick());
                            Log.e(TAG, "call: "+arrBean.getBase_fee()+"基础费用" );
                            guidePrice.setText(baseFee);
                            guiderHourPrice.setText(timeFee);
                            guiderName = arrBean.getGuidename();
                            String coupon_amount = arrBean.getCoupon_amount();
                            String total_fee = arrBean.getTotal_fee();
                            DecimalFormat df = new DecimalFormat("0.00");
                            String format = df.format(Double.parseDouble(total_fee)-Double.parseDouble(coupon_amount));
                            travelPrice.setText(format);
                            String startTime = arrBean.getUpdate_time();
                            if (arrBean.getStatus().equals("6")){
                                btGrade.setClickable(false);
                                btGrade.setText("已评价");
                            }else {
                                btGrade.setOnClickListener(OverOrderAty.this);
                                btGrade.setClickable(true);
                            }
                            String endTime = arrBean.getEnd_time();
                            long sl = Long.parseLong(startTime);
                            long el = Long.parseLong(endTime);
                            long totalSec = el - sl;
                            long h = totalSec / 3600;
                            long min = totalSec / 60 % 60;
                            travelTime.setText(h + "小时" + min + "分");
                            phone.setText(arrBean.getGuidename());
                            if (arrBean.getGuidepic().length() != 0 && arrBean.getGuidepic() != null) {
                                Glide.with(getApplicationContext()).load(arrBean.getGuidepic()).asBitmap().into(guideIcon);
                            }
                            datas.clear();
                            datas.addAll(arrBean.getJuege());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 详情错误" + throwable.toString());
                    }
                });
    }
    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(OverOrderAty.this, GradeAty.class);
        intent.putExtra("guidename", guiderName);
        intent.putExtra("odId", oid);
        startActivity(intent);
    }
}
