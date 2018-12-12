package com.hulianhujia.spellway.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.DiaryAty;
import com.hulianhujia.spellway.activitys.HomeFilterAty;
import com.hulianhujia.spellway.activitys.SearchAty;
import com.hulianhujia.spellway.adpaters.HomeListViewAdp;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.HomeFilterEvent;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.HomeBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.SearchEvent;
import com.hulianhujia.spellway.untils.DataFactory;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.StatusBarManager;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by FHP on 2017/5/23.
 */

public class HomeFgm extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2, View.OnClickListener {
    @Bind(R.id.btSearch)
    ImageView btSearch;
    @Bind(R.id.homeListView)
    PullToRefreshListView homeListView;
    @Bind(R.id.tvTitle)
    ImageView tvTitle;
    @Bind(R.id.avi)
    AVLoadingIndicatorView avi;
    @Bind(R.id.btFiter)
    ImageView btFiter;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    private int page = 1;
    private String TAG = "info";
    private HomeListViewAdp adp;
    private List<HomeBean.ReturnArrBean> datas = new ArrayList<>();
    private LoginBean.UserInfoBean userInfo;
    private LoadingDialog dialog;
    private boolean fiterFlag = true;
    private LoadingDialog loadingDialog;
    private String currentId;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgm_home, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        loadingDialog=new LoadingDialog(getContext());
        getUser();
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
        }catch (Exception e){
            Log.e(TAG, "设置错误 "+e.toString() );
        }
        initView();
        initData();
        return view;
    }

    private void initData() {
        getListData();
        Log.e(TAG, "initData: " + datas.size());
        homeListView.setOnItemClickListener(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getContext());
    }
    /*获取ListView的Dta数据*/
    private void getListData() {
        OkGo.post(Urls.PUBLIC_URL + Urls.GETHOME)
                .params("p", page)
                .params("username", userInfo.getUsername())
                .params("sortType", fiterFlag ? "time" : "collection")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        dialog.show();
                        Log.e(TAG, "call: 获取列表数据");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call:列表数据 " + s);
                        Gson g = new Gson();
                        HomeBean bean = g.fromJson(s, HomeBean.class);
                        if (bean.getCode() == 1) {
                            dialog.dismiss();
                            currentId=bean.getReturnArr().get(0).getNews_id();
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call:列表失败" + throwable.toString());
                    }
                });
    }
    private void setSwip() {
    }
    private void initView() {
        dialog = new LoadingDialog(getContext());
        adp = new HomeListViewAdp(datas, getContext());
        homeListView.setAdapter(adp);
        homeListView.setMode(PullToRefreshBase.Mode.BOTH);
        ILoadingLayout pullDown = homeListView.getLoadingLayoutProxy(true, false);
        pullDown.setPullLabel("下拉刷新");
        pullDown.setReleaseLabel("放开刷新");
        homeListView.setOnRefreshListener(this);
        btFiter.setOnClickListener(this);
        btSearch.setOnClickListener(this);
        setSwip();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DiaryAty.class);
        Log.e(TAG, "onItemClick: " + position);
        intent.putExtra("nid", datas.get(position - 1).getNews_id());
        startActivity(intent);
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        Log.e(TAG, "onPullDownToRefresh: "+Contents.SEARCH_TYPE );
        if (Contents.SEARCH_TYPE==1){
            if (Contents.SEARCH==null){
                if (Contents.CITY==null&&Contents.TAG==null){
                    OkGo.get(Urls.PUBLIC_URL + Urls.GETHOME)
                            .params("p", page)
                            .params("username", userInfo.getUsername())
                            .params("sortType", fiterFlag ? "time" : "collection")
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call: 获取列表数据");
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表数据 " + s);
                                    Gson g = new Gson();
                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                    if (baseBean.getCode()==1){
                                        String s1 = g.toJson(baseBean.getReturnArr());
                                        ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                        datas.clear();
                                        if (currentId.equals(returnArrBeen.get(0).getNews_id())){
                                            ToastUtils.toast("没有更新文章了");
                                        }
                                        datas.addAll(returnArrBeen);
                                        adp.notifyDataSetChanged();
                                    }else {
                                        datas.clear();
                                        adp.notifyDataSetChanged();
                                        ToastUtils.toast("没有符合条件的结果");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表失败" + throwable.toString());
                                }
                            });
                }else {
                    OkGo.get(Urls.PUBLIC_URL + Urls.HOME_FILTER_RESULT)
                            .params("username",userInfo.getUsername())
                            .params("where",Contents.CITY)
                            .params("tag",Contents.TAG)
                            .params("p",page)
                            .params("sortType","time")
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call: 获取列表数据");
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表数据 " + s);
                                    Gson g = new Gson();
                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                    if (baseBean.getCode()==1){
                                        String s1 = g.toJson(baseBean.getReturnArr());
                                        ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                        datas.clear();
                                        if (currentId.equals(returnArrBeen.get(0).getNews_id())){
                                            ToastUtils.toast("没有更新文章了");
                                        }
                                        datas.addAll(returnArrBeen);
                                        adp.notifyDataSetChanged();
                                    }else {
                                        datas.clear();
                                        adp.notifyDataSetChanged();
                                        ToastUtils.toast("没有符合条件的结果");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表失败" + throwable.toString());
                                }
                            });
                }
            }else{
                Log.e(TAG, "onPullDownToRefresh: 进入搜索" );
                OkGo.get(Urls.PUBLIC_URL+Urls.HOME_FILTER_RESULT)
                        .params("username",userInfo.getUsername())
                        .params("sortType","time")
                        .params("title",Contents.SEARCH)
                        .params("p",page)
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 筛选开始"+Contents.SEARCH );
                                loadingDialog.show();
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call: 筛选获得" + s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                if (baseBean.getCode()==1){
                                    String s1 = g.toJson(baseBean.getReturnArr());
                                    ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                    datas.clear();
                                    if (currentId.equals(returnArrBeen.get(0).getNews_id())){
                                        ToastUtils.toast("没有更新文章了");
                                    }
                                    datas.addAll(returnArrBeen);
                                    adp.notifyDataSetChanged();
                                }else {
                                    datas.clear();
                                    adp.notifyDataSetChanged();
                                    ToastUtils.toast("没有符合条件的结果");
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
        if (Contents.SEARCH_TYPE==2){
            if (Contents.CITY==null&&Contents.TAG==null){
                OkGo.get(Urls.PUBLIC_URL + Urls.GETHOME)
                        .params("p", page)
                        .params("username", userInfo.getUsername())
                        .params("sortType", fiterFlag ? "time" : "collection")
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 获取列表数据");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表数据 " + s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                if (baseBean.getCode()==1){
                                    String s1 = g.toJson(baseBean.getReturnArr());
                                    ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                    datas.clear();
                                    if (currentId.equals(returnArrBeen.get(0).getNews_id())){
                                        ToastUtils.toast("没有更新文章了");
                                    }
                                    datas.addAll(returnArrBeen);
                                    adp.notifyDataSetChanged();
                                }else {
                                    datas.clear();
                                    adp.notifyDataSetChanged();
                                    ToastUtils.toast("没有符合条件的结果");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表失败" + throwable.toString());
                            }
                        });
            }else {
                OkGo.get(Urls.PUBLIC_URL + Urls.HOME_FILTER_RESULT)
                        .params("username",userInfo.getUsername())
                        .params("where",Contents.CITY)
                        .params("tag",Contents.TAG)
                        .params("p",page)
                        .params("sortType","time")
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 获取列表数据");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表数据 " + s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                if (baseBean.getCode()==1){
                                    String s1 = g.toJson(baseBean.getReturnArr());
                                    ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                    datas.clear();
                                    if (currentId.equals(returnArrBeen.get(0).getNews_id())){
                                        ToastUtils.toast("没有更新文章了");
                                    }
                                    datas.addAll(returnArrBeen);
                                    adp.notifyDataSetChanged();
                                }else {
                                    datas.clear();
                                    adp.notifyDataSetChanged();
                                    ToastUtils.toast("没有符合条件的结果");
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表失败" + throwable.toString());
                            }
                        });
            }
        }
        if (Contents.SEARCH_TYPE==3){
            OkGo.get(Urls.PUBLIC_URL + Urls.GETHOME)
                    .params("p", page)
                    .params("username", userInfo.getUsername())
                    .params("sortType", fiterFlag ? "time" : "collection")
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 获取列表数据");
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            homeListView.onRefreshComplete();
                            Log.e(TAG, "call:列表数据 " + s);
                            Gson g = new Gson();
                            BaseBean baseBean = g.fromJson(s, BaseBean.class);
                            if (baseBean.getCode()==1){
                                String s1 = g.toJson(baseBean.getReturnArr());
                                ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                datas.clear();
                                if (currentId.equals(returnArrBeen.get(0).getNews_id())){
                                    ToastUtils.toast("没有更新文章了");
                                }
                                datas.addAll(returnArrBeen);
                                adp.notifyDataSetChanged();
                            }else {
                                datas.clear();
                                adp.notifyDataSetChanged();
                                ToastUtils.toast("没有符合条件的结果");
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            homeListView.onRefreshComplete();
                            Log.e(TAG, "call:列表失败" + throwable.toString());
                        }
                    });
        }
    }
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (Contents.SEARCH_TYPE==1){
            if (Contents.SEARCH==null){
                if (Contents.TAG==null&&Contents.CITY==null){
                    page++;
                    OkGo.post(Urls.PUBLIC_URL + Urls.GETHOME)
                            .params("p", page)
                            .params("username", userInfo.getUsername())
                            .params("sortType", fiterFlag ? "time" : "collection")
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call: 获取列表数据");
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表数据 " + s);
                                    Gson g = new Gson();
                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                    if (baseBean.getCode()==1){
                                        String s1 = g.toJson(baseBean.getReturnArr());
                                        ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                        datas.addAll(returnArrBeen);
                                        adp.notifyDataSetChanged();
                                    }else {
                                        ToastUtils.toast("没有更多了");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表失败" + throwable.toString());
                                }
                            });
                }else {
                    page++;
                    OkGo.get(Urls.PUBLIC_URL + Urls.HOME_FILTER_RESULT)
                            .params("username",userInfo.getUsername())
                            .params("where",Contents.CITY)
                            .params("tag",Contents.TAG)
                            .params("p",page)
                            .params("sortType","time")
                            .getCall(StringConvert.create(), RxAdapter.<String>create())
                            .doOnSubscribe(new Action0() {
                                @Override
                                public void call() {
                                    Log.e(TAG, "call: 获取列表数据");
                                }
                            }).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表数据 " + s);
                                    Gson g = new Gson();
                                    BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                    if (baseBean.getCode()==1){
                                        String s1 = g.toJson(baseBean.getReturnArr());
                                        ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                        datas.addAll(returnArrBeen);
                                        adp.notifyDataSetChanged();
                                    }else {
                                        ToastUtils.toast("没有更多了");
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    homeListView.onRefreshComplete();
                                    Log.e(TAG, "call:列表失败" + throwable.toString());
                                }
                            });
                }
            }else {
                page++;
                OkGo.get(Urls.PUBLIC_URL+Urls.HOME_FILTER_RESULT)
                        .params("username",userInfo.getUsername())
                        .params("sortType","time")
                        .params("title",Contents.SEARCH)
                        .params("p",page)
                        .getCall(StringConvert.create(),RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                homeListView.onRefreshComplete();
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 筛选获得" + s);
                                Gson g = new Gson();
                                BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                if (baseBean.getCode()==1){
                                    String s1 = g.toJson(baseBean.getReturnArr());
                                    ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                    datas.addAll(returnArrBeen);
                                    adp.notifyDataSetChanged();
                                }else {
                                    ToastUtils.toast("没有更多了");
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
        }else if (Contents.SEARCH_TYPE==2){
            if (Contents.TAG==null&&Contents.CITY==null){
                page++;
                OkGo.post(Urls.PUBLIC_URL + Urls.GETHOME)
                        .params("p", page)
                        .params("username", userInfo.getUsername())
                        .params("sortType", fiterFlag ? "time" : "collection")
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 获取列表数据");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表数据 " + s);
                                Gson g = new Gson();
                                HomeBean bean = g.fromJson(s, HomeBean.class);
                                if (bean.getCode() == 1) {
                                    datas.addAll(bean.getReturnArr());
                                    adp.notifyDataSetChanged();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表失败" + throwable.toString());
                            }
                        });
            }else {
                page++;
                OkGo.get(Urls.PUBLIC_URL + Urls.HOME_FILTER_RESULT)
                        .params("username",userInfo.getUsername())
                        .params("where",Contents.CITY)
                        .params("tag",Contents.TAG)
                        .params("p",page)
                        .params("sortType","time")
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                Log.e(TAG, "call: 获取列表数据");
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表数据 " + s);
                                Gson g = new Gson();
                                HomeBean bean = g.fromJson(s, HomeBean.class);
                                if (bean.getCode() == 1) {
                                    datas.addAll(bean.getReturnArr());
                                    adp.notifyDataSetChanged();
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                homeListView.onRefreshComplete();
                                Log.e(TAG, "call:列表失败" + throwable.toString());
                            }
                        });
            }
        }else if (Contents.SEARCH_TYPE==3){
            page++;
            OkGo.post(Urls.PUBLIC_URL + Urls.GETHOME)
                    .params("p", page)
                    .params("username", userInfo.getUsername())
                    .params("sortType", fiterFlag ? "time" : "collection")
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: 获取列表数据");
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            homeListView.onRefreshComplete();
                            Log.e(TAG, "call:列表数据 " + s);
                            Gson g = new Gson();
                            BaseBean baseBean = g.fromJson(s, BaseBean.class);
                            if (baseBean.getCode()==1){
                                String s1 = g.toJson(baseBean.getReturnArr());
                                ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                datas.addAll(returnArrBeen);
                                adp.notifyDataSetChanged();
                            }else {
                                ToastUtils.toast("没有更多了");
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            homeListView.onRefreshComplete();
                            Log.e(TAG, "call:列表失败" + throwable.toString());
                        }
                    });
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btFiter:
                startActivity(new Intent(getActivity(), HomeFilterAty.class));
                break;
            case R.id.btSearch:
                startActivity(new Intent(getActivity(), SearchAty.class));
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handlFilter(final HomeFilterEvent event){
        page=1;
        Contents.SEARCH_TYPE=2;
        OkGo.get(Urls.PUBLIC_URL+Urls.HOME_FILTER_RESULT)
                .params("username",userInfo.getUsername())
                .params("where",event.getCity())
                .params("tag",Contents.TAG)
                .params("p",page)
                .params("sortType","time")
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: 开始筛选"+userInfo.getUsername()+"地点"+event.getCity()+"tag"+event.getTag() );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 筛选获得" + s);
                        Gson g = new Gson();
                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                        if (baseBean.getCode()==1){
                            String s1 = g.toJson(baseBean.getReturnArr());
                            ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                            datas.clear();
                            currentId=returnArrBeen.get(0).getNews_id();
                            datas.addAll(returnArrBeen);
                            adp.notifyDataSetChanged();
                        }else {
                            datas.clear();
                            adp.notifyDataSetChanged();
                            ToastUtils.toast("没有符合条件的结果");
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
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void handSearch(final SearchEvent event){
        Contents.SEARCH=event.getContent();
        if (Contents.SEARCH.equals("")){
            Contents.SEARCH_TYPE=3;
            OkGo.post(Urls.PUBLIC_URL + Urls.GETHOME)
                    .params("p", page)
                    .params("username", userInfo.getUsername())
                    .params("sortType", fiterFlag ? "time" : "collection")
                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            dialog.show();
                            Log.e(TAG, "call: 获取列表数据");
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            Log.e(TAG, "call:列表数据 " + s);
                            Gson g = new Gson();
                            HomeBean bean = g.fromJson(s, HomeBean.class);
                            if (bean.getCode() == 1) {
                                dialog.dismiss();
                                currentId=bean.getReturnArr().get(0).getNews_id();
                                datas.clear();
                                datas.addAll(bean.getReturnArr());
                                adp.notifyDataSetChanged();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.e(TAG, "call:列表失败" + throwable.toString());
                        }
                    });
        }else {
            Log.e(TAG, "onPullDownToRefresh: 进入搜索" );
            Contents.SEARCH_TYPE=1;
            OkGo.post(Urls.PUBLIC_URL+Urls.HOME_FILTER_RESULT)
                    .params("username",userInfo.getUsername())
                    .params("sortType","time")
                    .params("title",Contents.SEARCH)
                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            Log.e(TAG, "call: "+event.getContent()+userInfo.getUsername());
                            loadingDialog.show();
                        }
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            loadingDialog.dismiss();
                            Log.e(TAG, "call: 筛选获得" + s);
                            Gson g = new Gson();
                            BaseBean baseBean = g.fromJson(s, BaseBean.class);
                            if (baseBean.getCode()==1){
                                String s1 = g.toJson(baseBean.getReturnArr());
                                ArrayList<HomeBean.ReturnArrBean> returnArrBeen = DataFactory.jsonToArrayList(s1, HomeBean.ReturnArrBean.class);
                                datas.clear();
                                currentId=returnArrBeen.get(0).getNews_id();
                                datas.addAll(returnArrBeen);
                                adp.notifyDataSetChanged();
                            }else {
                                datas.clear();
                                adp.notifyDataSetChanged();
                                ToastUtils.toast("没有符合条件的结果");
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
