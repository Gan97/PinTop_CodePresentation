package com.hulianhujia.spellway.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.MainActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.GuideFilterNewAty;
import com.hulianhujia.spellway.adpaters.GuiderListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.GuideFilterEvent;
import com.hulianhujia.spellway.event.RefreshGuideListEvent;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.CommentBaseBean;
import com.hulianhujia.spellway.javaBeans.GuideListBean;
import com.hulianhujia.spellway.untils.DataFactory;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.StatusBarManager;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by FHP on 2017/5/23.
 */

public class GuideFgm extends Fragment implements PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btPub)
    ImageView btPub;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.gridView)
    PullToRefreshGridView gridView;
    private GuiderListViewAdp adp;
    private String TAG = "info";
    private List<GuideListBean.ReturnArrBean> datas = new ArrayList<>();
    private LoadingDialog loadingDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgm_guide, null);
        ButterKnife.bind(this, view);
        loadingDialog=new LoadingDialog(getContext());
        EventBus.getDefault().register(this);
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                View decorView = getActivity().getWindow().getDecorView();
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                );
                getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
                StatusBarManager.getInstance().setStatusBarTextColor(getActivity().getWindow(), true);
            } else {
                StatusBarManager.getInstance().setStatusBarTextColor(getActivity().getWindow(), false);
                if (loTitle != null) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) loTitle.getLayoutParams();
                    lp.height = getResources().getDimensionPixelOffset(R.dimen.height);
                }
                MyUtils.setWindowStatusBarColor(getActivity(), R.color.black);
            }
        } catch (Exception e) {
            Log.e(TAG, "设置错误 " + e.toString());
        }
        initView();
        return view;
    }
    private void initView() {
        adp = new GuiderListViewAdp(getContext(), datas);
        gridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        gridView.setOnRefreshListener(this);
        gridView.setAdapter(adp);
        Log.e(TAG, "initView:经纬度 lat=" + MainActivity.LAT + "Long" + MainActivity.Lon);
        getLoc();
        btPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), GuideFilterNewAty.class);
                getActivity().startActivity(i);
            }
        });
    }
    private void getLoc() {
        String lat = MyUtils.readLat(getContext());
        String lon = MyUtils.readLon(getContext());
        if (lat == null || lat.length() == 0) {
            ToastUtils.toast("位置信息获取中，请下拉刷新");
        } else {
            getData();
        }
    }
    private void getData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_NEARGUIDE)
                .params("lat", MyUtils.readLat(getContext()))
                .params("lon", MyUtils.readLon(getContext()))
                .params("distance",30000)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 导游列表开始");
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e(TAG, "call: 导游列表获得" + s);
                Gson g = new Gson();
                GuideListBean bean = g.fromJson(s, GuideListBean.class);
                ToastUtils.toast(bean.getMsg());
                if (bean.getCode() == 1) {
                    datas.clear();
                    datas.addAll(bean.getReturnArr());
                    adp.notifyDataSetChanged();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "call: 导游列表失败" + throwable.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void RefreshTheList(RefreshGuideListEvent event) {
        getData();
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        gridView.onRefreshComplete();
        refreshList();
    }
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handFilter(GuideFilterEvent event){
        if (Contents.GUIDE_FILTER_SEX==""&&Contents.GUIDE_FILTER_PRICE==""
                &&Contents.GUIDE_FILTER_LVL==""&&Contents.GUIDE_FILTER_AGE==""){
            OkGo.post(Urls.PUBLIC_URL + Urls.GET_NEARGUIDE)
                    .params("lat", MyUtils.readLat(getContext()))
                    .params("lon", MyUtils.readLon(getContext()))
                    .params("distance",30000)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 导游列表开始");
                        }
                    }).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.e(TAG, "call: 导游列表获得" + s);
                    Gson g = new Gson();
                    GuideListBean bean = g.fromJson(s, GuideListBean.class);
                    ToastUtils.toast(bean.getMsg());
                    if (bean.getCode() == 1) {
                        datas.clear();
                        datas.addAll(bean.getReturnArr());
                        adp.notifyDataSetChanged();
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.e(TAG, "call: 导游列表失败" + throwable.toString());
                }
            });
        }else {
            OkGo.get(Urls.PUBLIC_URL+Urls.SERACH_GUIDE)
                    .params("age",Contents.GUIDE_FILTER_AGE)
                    .params("sex",Contents.GUIDE_FILTER_SEX)
                    .params("time_fee",Contents.GUIDE_FILTER_PRICE)
                    .params("level",Contents.GUIDE_FILTER_LVL)
                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 筛选开始"+Contents.GUIDE_FILTER_AGE+Contents.GUIDE_FILTER_SEX+Contents.GUIDE_FILTER_PRICE+Contents.GUIDE_FILTER_LVL );
                            loadingDialog.show();
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Gson g = new Gson();
                            BaseBean commentBaseBean = g.fromJson(s, BaseBean.class);
                            Log.e(TAG, "call: 筛选获得"+s);
                            if (commentBaseBean.getCode()==1){
                                ToastUtils.toast(commentBaseBean.getMsg());
                                Log.e(TAG, "call: 数据json=-"+ g.toJson(commentBaseBean.getReturnArr()));
                                ArrayList<GuideListBean.ReturnArrBean> lists
                                        =
                                        DataFactory.jsonToArrayList(g.toJson(commentBaseBean.getReturnArr()), GuideListBean.ReturnArrBean.class);
                                datas.clear();
                                datas.addAll(lists);
                                adp.notifyDataSetChanged();
                            }else {
                                ToastUtils.toast("没有符合条件的导游");
                                datas.clear();
                                adp.notifyDataSetChanged();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 筛选错误"+throwable.toString() );
                        }
                    });
        }

    }
    private void refreshList(){
        if (Contents.GUIDE_FILTER_SEX==""&&Contents.GUIDE_FILTER_PRICE==""
                &&Contents.GUIDE_FILTER_LVL==""&&Contents.GUIDE_FILTER_AGE==""){
            OkGo.post(Urls.PUBLIC_URL + Urls.GET_NEARGUIDE)
                    .params("lat", MyUtils.readLat(getContext()))
                    .params("lon", MyUtils.readLon(getContext()))
                    .params("distance",30000)
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 导游列表开始");
                        }
                    }).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.e(TAG, "call: 导游列表获得" + s);
                    Gson g = new Gson();
                    GuideListBean bean = g.fromJson(s, GuideListBean.class);
                    ToastUtils.toast(bean.getMsg());
                    if (bean.getCode() == 1) {
                        datas.clear();
                        datas.addAll(bean.getReturnArr());
                        adp.notifyDataSetChanged();
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.e(TAG, "call: 导游列表失败" + throwable.toString());
                }
            });
        }else {
            OkGo.get(Urls.PUBLIC_URL+Urls.SERACH_GUIDE)
                    .params("age",Contents.GUIDE_FILTER_AGE)
                    .params("sex",Contents.GUIDE_FILTER_SEX)
                    .params("time_fee",Contents.GUIDE_FILTER_PRICE)
                    .params("level",Contents.GUIDE_FILTER_LVL)
                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 筛选开始" );
                            loadingDialog.show();
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Gson g = new Gson();
                            BaseBean commentBaseBean = g.fromJson(s, BaseBean.class);
                            Log.e(TAG, "call: 筛选获得"+s+"Code"+commentBaseBean.toString());
                            if (commentBaseBean.getCode()==1){
                                ToastUtils.toast(commentBaseBean.getMsg());
                                ArrayList<GuideListBean.ReturnArrBean> lists
                                        =
                                        DataFactory.jsonToArrayList(commentBaseBean.getReturnArr().toString(), GuideListBean.ReturnArrBean.class);
                                datas.clear();
                                datas.addAll(lists);
                                adp.notifyDataSetChanged();
                            }else {
                                ToastUtils.toast("没有符合条件的导游");
                                datas.clear();
                                adp.notifyDataSetChanged();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 筛选错误"+throwable.toString() );
                        }
                    });
        }
    }
}
