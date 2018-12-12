package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.adpaters.GuiderDetailCommentListViewAdp;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.customViews.SetTimePop;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.BookTimeEvent;
import com.hulianhujia.spellway.javaBeans.GuideDetailBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class GuiderDetailActivity extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.guiderIcon)
    CircleImageView guiderIcon;
    @Bind(R.id.guiderSex)
    ImageView guiderSex;
    @Bind(R.id.iconLo)
    RelativeLayout iconLo;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.guiderName)
    TextView guiderName;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.guiderPrice)
    TextView guiderPrice;
    @Bind(R.id.hourPrice)
    TextView hourPrice;
    @Bind(R.id.guiderHourPrice)
    TextView guiderHourPrice;
    @Bind(R.id.imgType)
    ImageView imgType;
    @Bind(R.id.guiderType)
    TextView guiderType;
    @Bind(R.id.orderState)
    TextView orderState;
    @Bind(R.id.ratingBar)
    SimpleRatingBar ratingBar;
    @Bind(R.id.guiderTotal)
    TextView guiderTotal;
    @Bind(R.id.text_useless)
    TextView textUseless;
    @Bind(R.id.guiderMotto)
    TextView guiderMotto;
    @Bind(R.id.dv)
    TextView dv;
    @Bind(R.id.jieshao)
    TextView jieshao;
    @Bind(R.id.dv2)
    TextView dv2;
    @Bind(R.id.tvUserTall)
    TextView tvUserTall;
    @Bind(R.id.userWeight)
    TextView userWeight;
    @Bind(R.id.tvUserWeight)
    TextView tvUserWeight;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.tvState)
    TextView tvState;
    @Bind(R.id.imageView4)
    ImageView imageView4;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.tvUserLoc)
    TextView tvUserLoc;
    @Bind(R.id.linearLayout_useless)
    LinearLayout linearLayoutUseless;
    @Bind(R.id.dv3)
    TextView dv3;
    @Bind(R.id.dv4)
    TextView dv4;
    @Bind(R.id.dv6)
    TextView dv6;
    @Bind(R.id.dv5)
    TextView dv5;
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.notice_tv)
    TextView noticeTv;
    @Bind(R.id.btTogether)
    TextView btTogether;
    @Bind(R.id.btInvite)
    TextView btInvite;
    @Bind(R.id.guide_introduce_ll)
    LinearLayout guideIntroduceLl;
    @Bind(R.id.cancelInvite)
    TextView cancelInvite;
    @Bind(R.id.tvLeftTime)
    TextView tvLeftTime;
    @Bind(R.id.inviteGuide_rl)
    RelativeLayout inviteGuideRl;
    @Bind(R.id.cancel_medley)
    TextView cancelMedley;
    @Bind(R.id.wait_medley)
    TextView waitMedley;
    @Bind(R.id.medley_guide_rl)
    RelativeLayout medleyGuideRl;
    @Bind(R.id.bottomBar)
    LinearLayout bottomBar;
    @Bind(R.id.father)
    RelativeLayout father;
    @Bind(R.id.btFollow)
    LinearLayout btFollow;
    @Bind(R.id.heartImg)
    ImageView heartImg;
    private String guideName;
    private LoginBean.UserInfoBean userInfo;
    private String TAG = "info";
    private LoadingDialog dialog;
    private GuiderDetailCommentListViewAdp adp;
    private List<GuideDetailBean.ReturnArrBean.JudgeBean> datas = new ArrayList<>();
    private String distance;
    private int page = 1;
    private String pNum;
    private String tP="0";
    @Override
    public int getContentId() {
        return R.layout.activity_guider_detail;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        dialog = new LoadingDialog(this);
        getUser();
        final Intent intent = getIntent();
        guideName = intent.getStringExtra("guiderName");
        distance = intent.getStringExtra("dis");
        initWeb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initWeb() {
        //WebView加载web资源
//        webView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent ev) {
//
//                ((WebView)v).requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e(TAG, "shouldOverrideUrlLoading: ");
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultFontSize(55);
        webView.getSettings().setJavaScriptEnabled(false);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setUseWideViewPort(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
/*        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });*/
    }

    @Override
    public void initData() {
        /*使能列表*/
        OkGo.post(Urls.PUBLIC_URL + Urls.GUIDE_DETAIL)
                .params("username", Contents.USER.getUsername())
                .params("guidename", guideName)
                .params("judgePage", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: +导游详情开始" + guideName);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 导游详情获得" + s);
                        Gson g = new Gson();
                        GuideDetailBean bean = g.fromJson(s, GuideDetailBean.class);
                        if (bean.getCode() == 1) {
                            final GuideDetailBean.ReturnArrBean returnArr = bean.getReturnArr();
                            name.setText(returnArr.getNickname() == null || equals("") ? returnArr.getUsername() : returnArr.getNickname());
                            guiderName.setText(returnArr.getAge());
                            guiderPrice.setText(returnArr.getBase_fee());
                            guiderHourPrice.setText(returnArr.getTime_fee());
                            guiderMotto.setText(returnArr.getAutograph());
                            tvUserTall.setText(distance + "km");
                            tvUserWeight.setText(returnArr.getHoby());
                            pNum=returnArr.getPindan_peoplenumber();
                            tP=returnArr.getPindan_permit();
                            switch (returnArr.getIs_guanzhu()) {
                                case 1:
                                    noticeTv.setText("已关注");
                                    Glide.with(context).load(R.mipmap.redbig_heart).asBitmap().into(heartImg);
                                    break;
                                case 0:
                                    noticeTv.setText("关注");
                                    Glide.with(context).load(R.mipmap.black_heart).asBitmap().into(heartImg);
                                    break;
                            }
                            btFollow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OkGo.post(Urls.PUBLIC_URL+Urls.ADD_FOCUS)
                                            .params("user_id",Contents.USER.getUser_id())
                                            .params("focus_uid",returnArr.getUser_id())
                                            .getCall(StringConvert.create(),RxAdapter.<String>create())
                                            .doOnSubscribe(new Action0() {
                                                @Override
                                                public void call() {
                                                    loadingDialog.show();
                                                    Log.e(TAG, "call: 开始关注" );
                                                }
                                            }).observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Action1<String>() {
                                                @Override
                                                public void call(String s) {
                                                    loadingDialog.dismiss();
                                                    Log.e(TAG, "call: 关注获得" + s);
                                                    Gson g = new Gson();
                                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                                    if (baseBean.getCode()==1){
                                                        Log.e(TAG, "call: 0"+baseBean.getMsg().substring(0,1) );
                                                        String substring = baseBean.getMsg().substring(0, 1);
                                                        if (substring.equals("取")){
                                                            Glide.with(context).load(R.mipmap.black_heart).asBitmap().into(heartImg);
                                                            noticeTv.setText("关注");
                                                        }
                                                        if (substring.equals("关")){
                                                            noticeTv.setText("已关注");
                                                            Glide.with(context).load(R.mipmap.redbig_heart).asBitmap().into(heartImg);
                                                        }

                                                    }
                                                }
                                            }, new Action1<Throwable>() {
                                                @Override
                                                public void call(Throwable throwable) {
                                                    loadingDialog.dismiss();
                                                    Log.e(TAG, "call: 关注错误"+throwable.toString() );
                                                }
                                            });
                                }
                            });
                            if (returnArr.getSkill() != null) {
                                tvState.setText(returnArr.getSkill());
                            }
                            if (returnArr.getIntroduce() != null) {
                                tvUserLoc.setText(returnArr.getIntroduce());
                            }
                            DecimalFormat df = new DecimalFormat("0.00");
                            String format = df.format(Double.parseDouble(returnArr.getAverage_score()));
                            orderState.setText(format);
                            ratingBar.setRating(Float.parseFloat(returnArr.getAverage_score()));
                            Glide.with(context).load(returnArr.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(guiderIcon);
                            webView.loadUrl(returnArr.getFengcai_url());
                            guiderTotal.setText("已接" + returnArr.getCountorder() + "单");
                            switch (returnArr.getLevel()) {
                                case "1":
                                    Glide.with(context).load(R.mipmap.goldp).asBitmap().into(imgType);
                                    guiderType.setText("金牌导游");
                                    break;
                                case "2":
                                    Glide.with(context).load(R.mipmap.silverp).asBitmap().into(imgType);
                                    guiderType.setText("银牌导游");
                                    break;
                                case "3":
                                    Glide.with(context).load(R.mipmap.ironp).asBitmap().into(imgType);
                                    guiderType.setText("铜牌导游");
                                    break;
                            }
                            switch (returnArr.getSex()) {
                                case "男":
                                    Log.e(TAG, "call:设置为男 ");
                                    guiderSex.setImageResource(R.mipmap.male);
                                    break;
                                case "女":
                                    Log.e(TAG, "call:设置为女 ");
                                    guiderSex.setImageResource(R.mipmap.female);
                                    break;
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 导游详情失败" + throwable.toString());
                    }
                });
    }

    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(this);
    }

    @OnClick({R.id.btExit, R.id.btInvite, R.id.btTogether, R.id.cancelInvite,
            R.id.tvLeftTime, R.id.inviteGuide_rl, R.id.cancel_medley, R.id.btFollow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btFollow:

                break;
            case R.id.btExit:
                finish();
                break;
            case R.id.btInvite:
                SetTimePop setTimePop = new SetTimePop(this, 0);
                setTimePop.setTouchable(true);
                setTimePop.showAtLocation(father, Gravity.CENTER, 0, 0);
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp);
                setTimePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });
                break;
            case R.id.btTogether:
                if (tP.equals("1")){
                    ConfirmDialogN dialogN = new ConfirmDialogN(this);
                    dialogN.setFlag(1);
                    dialogN.setYesListener(new YesListener() {
                        @Override
                        public void yes(int flag) {
                            SetTimePop setTimePop2 = new SetTimePop(GuiderDetailActivity.this, 1);
                            setTimePop2.setTouchable(true);
                            setTimePop2.showAtLocation(father, Gravity.CENTER, 0, 0);
                            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                            lp2.alpha = 0.7f;
                            getWindow().setAttributes(lp2);
                            setTimePop2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                                    lp.alpha = 1f;
                                    getWindow().setAttributes(lp);
                                }
                            });
                        }
                    });
                    dialogN.show();
                    dialogN.setTitle("该导游的拼单人数为"+pNum+"人，确定拼单？");
                }else {
                    ToastUtils.toast("对不起，该导游不接拼单");
                }
                break;
            case R.id.cancelInvite:
                guideIntroduceLl.setVisibility(View.VISIBLE);
                inviteGuideRl.setVisibility(View.GONE);
                break;
            case R.id.tvLeftTime:
                break;
            case R.id.inviteGuide_rl:
                break;
            case R.id.cancel_medley:
                guideIntroduceLl.setVisibility(View.VISIBLE);
                medleyGuideRl.setVisibility(View.GONE);
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handTime(BookTimeEvent event) {
        OkGo.post(Urls.PUBLIC_URL + Urls.INVITE_GUIDE)
                .params("guidename", guideName)
                .params("username", Contents.USER.getUsername())
                .params("book_time", event.getTime())
                .params("pindan", event.getFlag())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始预约");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 预约获得" + s);
                        Gson g = new Gson();
                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                        if (baseBean.getCode() == 1) {
                            ToastUtils.toast("成功");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 预约错误" + throwable.toString());
                    }
                });
    }
}
