package com.hulianhujia.spellway.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.CasualTalkAty;
import com.hulianhujia.spellway.adpaters.CommunityListAdp;
import com.hulianhujia.spellway.javaBeans.CommunityListBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;
import com.wang.avi.AVLoadingIndicatorView;

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
public class CommunityFgm extends Fragment implements View.OnClickListener ,PullToRefreshBase.OnRefreshListener2{
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.btPub)
    ImageView btPub;
    @Bind(R.id.communityRecyclerView)
    PullToRefreshListView listView;
    @Bind(R.id.avi)
    AVLoadingIndicatorView avi;
    private LinearLayoutManager mLayoutManager;
    private CommunityListAdp adp;
    private List<CommunityListBean.ReturnArrBean> datas=new ArrayList<>();
    private int page = 1;
    private String TAG="info";
    private LoginBean.UserInfoBean userInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgm_community, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
    private void initView() {
        getUser();
        adp=new CommunityListAdp(getContext(),datas);
        listView.setAdapter(adp);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        OkGo.post(Urls.PUBLIC_URL + Urls.COMMUNITY_LIST)
                .params("username", userInfo.getUsername())
                .params("p", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 开始" );
                        avi.setVisibility(View.VISIBLE);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        avi.setVisibility(View.GONE);
                        Log.e(TAG, "call: 社区" + s);
                        Gson g = new Gson();
                        CommunityListBean bean = g.fromJson(s, CommunityListBean.class);
                        datas.clear();
                        datas.addAll(bean.getReturnArr());
                        adp.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 失败社区"+throwable.toString() );
                    }
                });
        btPub.setOnClickListener(this);
    }

    private void getUser() {
        userInfo= (LoginBean.UserInfoBean) SharedUtils.readObject(getContext());
    }
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), CasualTalkAty.class));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page=1;
        OkGo.post(Urls.PUBLIC_URL + Urls.COMMUNITY_LIST)
                .params("username", userInfo.getUsername())
                .params("p", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 开始" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 社区" + s);
                        Gson g = new Gson();
                        CommunityListBean bean = g.fromJson(s, CommunityListBean.class);
                        datas.clear();
                        datas.addAll(bean.getReturnArr());
                        adp.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 失败社区"+throwable.toString() );
                        listView.onRefreshComplete();
                    }
                });
    }
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        OkGo.post(Urls.PUBLIC_URL + Urls.COMMUNITY_LIST)
                .params("username", userInfo.getUsername())
                .params("p", page)
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 开始" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 社区" + s);
                        Gson g = new Gson();
                        CommunityListBean bean = g.fromJson(s, CommunityListBean.class);
                        datas.addAll(bean.getReturnArr());
                        adp.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 失败社区"+throwable.toString() );
                        listView.onRefreshComplete();
                    }
                });
    }
}
