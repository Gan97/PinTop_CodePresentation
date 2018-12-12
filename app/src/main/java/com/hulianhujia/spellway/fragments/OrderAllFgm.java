package com.hulianhujia.spellway.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
 * Created by FHP on 2017/8/9.
 */

public class OrderAllFgm extends Fragment implements AdapterView.OnItemClickListener {
    @Bind(R.id.listView)
    ListView listView;

    private LoginBean.UserInfoBean userInfo;
    private String TAG = "info";
    private List<OrderBean.ReturnArrBean> datas = new ArrayList<>();
    private GuiderOrderListViewAdp adp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_all_fgm, null);
        ButterKnife.bind(this,view);

        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(getContext());
        adp = new GuiderOrderListViewAdp(datas, getContext());
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
        getData();
        return view;
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
}
