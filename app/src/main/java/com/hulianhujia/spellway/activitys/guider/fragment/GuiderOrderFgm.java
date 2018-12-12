package com.hulianhujia.spellway.activitys.guider.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.OrderDetailAty;
import com.hulianhujia.spellway.adpaters.GuiderOrderListViewAdp;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.OrderBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by FHP on 2017/5/25.
 */

public class GuiderOrderFgm extends Fragment implements AdapterView.OnItemClickListener, TabLayout.OnTabSelectedListener {
    @Bind(R.id.btSelf)
    ImageView btSelf;
    @Bind(R.id.btMessage)
    ImageView btMessage;
    @Bind(R.id.servePoint)
    TextView servePoint;
    @Bind(R.id.todayNum)
    TextView todayNum;
    @Bind(R.id.todayMoney)
    TextView todayMoney;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.listView)
    ListView listView;
    private LoginBean.UserInfoBean userInfo;
    private String TAG = "info";
    private List<OrderBean.ReturnArrBean> datas = new ArrayList<>();
    private GuiderOrderListViewAdp adp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgm_guiderorder, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        getUser();
        adp = new GuiderOrderListViewAdp(datas, getContext());
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
        getData();

        tabLayout.addOnTabSelectedListener(this);
    }

    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getContext());
    }

    private void getData() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_ORDERLIST)
                .params("guidename", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获取订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call:获取订单列表返回 " + s);
                        Gson g = new Gson();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 获取订单列表错误" + throwable.toString());
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), OrderDetailAty.class);
        intent.putExtra("orderId", datas.get(position).getOrder_id());
        intent.putExtra("type", 2);
        startActivity(intent);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                getData();
                break;
            case 1:
                datas.clear();
                adp.notifyDataSetChanged();
                getCouldOrder();
                break;
            case 2:

                break;
            case 3:
                datas.clear();
                adp.notifyDataSetChanged();
                getOverData();
                break;
        }
    }

    private void getOverData() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.OVER_ORDER)
                .params("username", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获取完成订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call:获取完成订单列表返回 " + s);
                        Gson g = new Gson();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 获取完成订单列表错误" + throwable.toString());
                    }
                });
    }

    private void getCouldOrder() {
        Log.e(TAG, "getData: 入参" + userInfo.getUsername());
        OkGo.post(Urls.PUBLIC_URL + Urls.GOT_ORDERLIST)
                .params("guidename", userInfo.getUsername())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获取完成订单列表开始");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call:获取完成订单列表返回 " + s);
                        Gson g = new Gson();
                        OrderBean bean = g.fromJson(s, OrderBean.class);
                        ToastUtils.toast(bean.getMsg());
                        if (bean.getCode() == 1) {
                            List<OrderBean.ReturnArrBean> returnArr = bean.getReturnArr();
                            datas.clear();
                            datas.addAll(returnArr);
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 获取完成订单列表错误" + throwable.toString());
                    }
                });
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
