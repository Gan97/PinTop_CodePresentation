package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.javaBeans.GuideDetailBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class GuideStyleAty extends BaseActivity {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.btPub)
    TextView btPub;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.webView)
    WebView webView;
    private String TAG = "info";
    private String url;
    private String sId;
    @Override
    public int getContentId() {
        return R.layout.activity_guide_style_aty;
    }

    @Override
    public void initData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.GUIDE_DETAIL)
                .params("guidename", Contents.GUIDE.getUsername())
                .params("username",Contents.GUIDE.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 风采开始");
                        loadingDialog.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 风采获得" + s);
                        Gson g = new Gson();
                        GuideDetailBean bean = g.fromJson(s, GuideDetailBean.class);
                        if (bean.getCode() == 1) {
                                webView.loadUrl(bean.getReturnArr().getFengcai_url());
                                url=bean.getReturnArr().getFengcai_url();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 风采错误" + throwable.toString());
                    }
                });

    }

    @Override
    public void initView() {

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
    protected void onResume() {
        super.onResume();
        OkGo.post(Urls.PUBLIC_URL + Urls.GUIDE_DETAIL)
                .params("guidename", Contents.GUIDE.getUsername())
                .params("username",Contents.GUIDE.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 风采开始");
                        loadingDialog.show();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 风采获得" + s);
                        Gson g = new Gson();
                        GuideDetailBean bean = g.fromJson(s, GuideDetailBean.class);
                        if (bean.getCode() == 1) {
                            webView.loadUrl(bean.getReturnArr().getFengcai_url());
                            url=bean.getReturnArr().getFengcai_url();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 风采错误" + throwable.toString());
                    }
                });
    }

    @OnClick({R.id.btExit, R.id.btPub})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btExit:
                finish();
                break;
            case R.id.btPub:
                Intent intent = new Intent(this, PubStyleAty.class);
                intent.putExtra("url",url);
                startActivity(intent);

                break;
        }
    }

}
