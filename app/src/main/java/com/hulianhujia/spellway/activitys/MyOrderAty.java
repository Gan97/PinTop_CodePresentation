package com.hulianhujia.spellway.activitys;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.activitys.guider.OrderDetailAty;
import com.hulianhujia.spellway.adpaters.MyOrderListViewAdp;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.LoginBean;
import com.hulianhujia.spellway.javaBeans.OrderDetailBean;
import com.hulianhujia.spellway.javaBeans.UserOriderListBean;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class MyOrderAty extends BaseActivity implements AdapterView.OnItemClickListener, SpringView.OnFreshListener {
    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.listView)
    SwipeListView listView;
    @Bind(R.id.spView)
    SpringView spView;
    private LoginBean.UserInfoBean userInfo;
    private MyOrderListViewAdp adp;
    private String TAG = "info";
    private List<UserOriderListBean.ReturnArrBean> datas = new ArrayList<>();
    private int page=1;
    @Override
    public void initView() {
        getUser();
        adp = new MyOrderListViewAdp(this, datas);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(this);
        listView.setSwipeListViewListener(new BaseSwipeListViewListener(){
            @Override
            public void onListChanged() {
                super.onListChanged();
                listView.closeOpenedItems();
            }
            @Override
            public void onClickFrontView(final int position) {
                super.onClickFrontView(position);
                OkGo.post(Urls.PUBLIC_URL + Urls.ORDER_DETAIL)
                        .params("order_id", datas.get(position).getOrder_id())
                        .getCall(StringConvert.create(), RxAdapter.<String>create())
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                loadingDialog.show();
                                Log.e(TAG, "call: " );
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: ");
                                Log.e(TAG, "call: 订单详情获得" + s);
                                Gson g = new Gson();
                                OrderDetailBean bean = g.fromJson(s, OrderDetailBean.class);
                                if (bean.getReturnArr().get(0).getStatus().equals("2")){
                                    ToastUtils.toast("您已被导游拒绝");
                                }else if (bean.getReturnArr().get(0).getStatus().equals("6")){
                                    Intent i = new Intent(MyOrderAty.this,OverOrderAty.class);
                                    i.putExtra("oid",datas.get(position).getOrder_id());
                                    i.putExtra("bf",datas.get(position).getBase_fee());
                                    i.putExtra("tf",datas.get(position).getTime_fee());
                                    startActivity(i);
                                }else if (bean.getReturnArr().get(0).getStatus().equals("4")&&bean.getReturnArr().get(0).getPaystatus().equals("2")){
                                    Log.e(TAG, "call: 从外边进入" );
                                    Intent i = new Intent(MyOrderAty.this,OverOrderAty.class);
                                    i.putExtra("oid",datas.get(position).getOrder_id());
                                    i.putExtra("bf",datas.get(position).getBase_fee());
                                    i.putExtra("tf",datas.get(position).getTime_fee());
                                    startActivity(i);
                                }else {
                                    Intent intent = new Intent(MyOrderAty.this, OrderDetailAty.class);
                                    intent.putExtra("orderId", datas.get(position).getOrder_id());
                                    intent.putExtra("type", 1);
                                    startActivity(intent);
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                loadingDialog.dismiss();
                                Log.e(TAG, "call: 订单详情错误"+throwable.toString());
                            }
                        });
            }

            @Override
            public void onClickBackView(final int position) {
                super.onClickBackView(position);
                ConfirmDialogN c = new ConfirmDialogN(MyOrderAty.this);
                c.setFlag(1);
                c.setYesListener(new YesListener() {
                    @Override
                    public void yes(int flag) {
                        OkGo.post(Urls.PUBLIC_URL+Urls.DELET_ORDER)
                                .params("username", Contents.USER.getUsername())
                                .params("password",getSharedPreferences("login",MODE_PRIVATE).getString("pwd",null))
                                .params("order_id",datas.get(position).getOrder_id())
                                .getCall(StringConvert.create(),RxAdapter.<String>create())
                                .doOnSubscribe(new Action0() {
                                    @Override
                                    public void call() {
                                        Log.e(TAG, "call: 删除开始" );
                                    }
                                }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        Log.e(TAG, "call: 删除获得" + s);
                                        Gson g = new Gson();
                                        BaseBean baseBean = g.fromJson(s, BaseBean.class);
                                        if (baseBean.getCode() == 1) {
                                            datas.remove(position);
                                            adp.notifyDataSetChanged();
                                            listView.closeOpenedItems();
                                        }
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Log.e(TAG, "call: 删除错误"+throwable.toString() );
                                    }
                                });
                    }
                });
                c.show();
                c.setTitle("确认删除此订单？");
            }
        });
        spView.setFooter(new DefaultFooter(this));
        spView.setHeader(new DefaultHeader(this));
        spView.setListener(this);
        OkGo.get(Urls.PUBLIC_URL + Urls.GET_USERORDERLIST)
                .params("username", userInfo.getUsername())
                .params("p",page)
//                .params("username","13028194826")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call:获取订单列表开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 订单列表获得" + s);
                        Gson g = new Gson();
                        UserOriderListBean bean = g.fromJson(s, UserOriderListBean.class);
                        if (bean.getCode() == 1) {
                            datas.clear();
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 订单列表错误" + throwable.toString());
                    }
                });

    }

    private void getUser() {
        userInfo = (LoginBean.UserInfoBean) SharedUtils.readObject(this);
    }

    @Override
    public int getContentId() {
        return R.layout.activity_my_order_aty;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e(TAG, "onItemClick: " + datas.size() + "p" + position);
        OkGo.post(Urls.PUBLIC_URL + Urls.ORDER_DETAIL)
                .params("order_id", datas.get(position).getOrder_id())
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        loadingDialog.show();
                        Log.e(TAG, "call: " );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: ");
                        Log.e(TAG, "call: 订单详情获得" + s);
                        Gson g = new Gson();
                        OrderDetailBean bean = g.fromJson(s, OrderDetailBean.class);
                        Log.e(TAG, "睁大你的双眼 "+bean.getReturnArr().get(0).getStatus()+bean.getReturnArr().get(0).getPaystatus());
                        if (bean.getReturnArr().get(0).getStatus().equals("2")){
                            ToastUtils.toast("您已被导游拒绝");
                        }else if (bean.getReturnArr().get(0).getStatus().equals("6")){
                            Intent i = new Intent(MyOrderAty.this,OverOrderAty.class);
                            i.putExtra("oid",datas.get(position).getOrder_id());
                            i.putExtra("bf",datas.get(position).getBase_fee());
                            i.putExtra("tf",datas.get(position).getTime_fee());
                            startActivity(i);
                        }else if (bean.getReturnArr().get(0).getStatus().equals("4")&&bean.getReturnArr().get(0).getPaystatus().equals("2")){
                            Log.e(TAG, "call: 从外边进入" );
                            Intent i = new Intent(MyOrderAty.this,OverOrderAty.class);
                            i.putExtra("oid",datas.get(position).getOrder_id());
                            i.putExtra("bf",datas.get(position).getBase_fee());
                            i.putExtra("tf",datas.get(position).getTime_fee());
                            startActivity(i);
                        }
                        else {
                            Intent intent = new Intent(MyOrderAty.this, OrderDetailAty.class);
                            intent.putExtra("orderId", datas.get(position).getOrder_id());
                            intent.putExtra("type", 1);
                            startActivity(intent);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "call: 订单详情错误"+throwable.toString());
                    }
                });

    }


    @OnClick(R.id.btExit)
    public void onViewClicked() {
        finish();
    }



    @Override
    public void onRefresh() {
        page=1;
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_USERORDERLIST)
                .params("username", userInfo.getUsername())
                .params("p",page)
//                .params("username","13028194826")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call:获取订单列表开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 订单列表获得" + s);
                        spView.onFinishFreshAndLoad();
                        Gson g = new Gson();
                        UserOriderListBean bean = g.fromJson(s, UserOriderListBean.class);
                        if (bean.getCode() == 1) {
                            datas.clear();
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 订单列表错误" + throwable.toString());
                        spView.onFinishFreshAndLoad();
                    }
                });
    }

    @Override
    public void onLoadmore() {
        page++;
        OkGo.post(Urls.PUBLIC_URL + Urls.GET_USERORDERLIST)
                .params("username", userInfo.getUsername())
                .params("p",page)
//                .params("username","13028194826")
                .getCall(StringConvert.create(), RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call:获取订单列表开始 ");
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        spView.onFinishFreshAndLoad();
                        Log.e(TAG, "call: 订单列表获得" + s);
                        Gson g = new Gson();
                        UserOriderListBean bean = g.fromJson(s, UserOriderListBean.class);
                        if (bean.getCode() == 1) {
                            datas.addAll(bean.getReturnArr());
                            adp.notifyDataSetChanged();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: 订单列表错误" + throwable.toString());
                        spView.onFinishFreshAndLoad();
                    }
                });
    }
}
