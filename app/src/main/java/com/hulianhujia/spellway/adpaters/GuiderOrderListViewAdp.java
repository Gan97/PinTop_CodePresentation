package com.hulianhujia.spellway.adpaters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.ConfirmDialog;
import com.hulianhujia.spellway.customViews.ConfirmDialogN;
import com.hulianhujia.spellway.event.RefreshGuideListEvent;
import com.hulianhujia.spellway.interfaces.YesListener;
import com.hulianhujia.spellway.javaBeans.AcceptBean;
import com.hulianhujia.spellway.javaBeans.BaseBean;
import com.hulianhujia.spellway.javaBeans.OrderBean;
import com.hulianhujia.spellway.untils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by FHP on 2017/5/25.
 */
public class GuiderOrderListViewAdp extends BaseAdapter {
    private List<OrderBean.ReturnArrBean> fake;
    private Context context;
    private String TAG="info";

    public GuiderOrderListViewAdp(List<OrderBean.ReturnArrBean> fake, Context context) {
        this.fake = fake;
        this.context = context;
    }
    @Override
    public int getCount() {
        return fake.size();
    }
    @Override
    public Object getItem(int position) {
        return fake.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GuiderOrderViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.fgm_guiderorder_item, null);
            holder=new GuiderOrderViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (GuiderOrderViewHolder) convertView.getTag();
        }
        final OrderBean.ReturnArrBean bean = fake.get(position);
        holder.orderNum.setText(bean.getOrder_no());
        holder.name.setText(bean.getNickname());
        holder.userPhone.setText(bean.getUsername());
        holder.bookTime.setText("预约时间："+bean.getBook_time());
        holder.userPhone.setText(bean.getUsername());
        Glide.with(context).load(bean.getPicture()).asBitmap().placeholder(R.mipmap.head_portrait).into(holder.userIcon);
        if (bean.getStatus().equals("0")){
            holder.btGet.setVisibility(View.VISIBLE);
            holder.btRefuse.setVisibility(View.VISIBLE);
            holder.tvState.setVisibility(View.GONE);
            holder.btGet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmDialogN confirmDialogN=new ConfirmDialogN(context);
                    confirmDialogN.setFlag(1);
                    confirmDialogN.setYesListener(new YesListener() {
                        @Override
                        public void yes(int flag) {
                            OkGo.post(Urls.PUBLIC_URL+Urls.GUIDE_CONFIRMORDER)
                                    .params("order_id",bean.getOrder_id())
                                    .getCall(StringConvert.create(), RxAdapter.<String>create())
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            Log.e(TAG, "call: 接受订单开始订单id"+bean.getOrder_id());
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            Log.e(TAG, "call: 接受订单返回" + s);
                                            Gson g = new Gson();
                                            AcceptBean acceptBean = g.fromJson(s, AcceptBean.class);
                                            Log.e(TAG, "call: 接受订单成功" +acceptBean.getCode());

                                            if (acceptBean.getCode()==1){
                                                EventBus.getDefault().post(new RefreshGuideListEvent());
                                                ToastUtils.toast("接受订单成功");
                                            }else {
                                                ToastUtils.toast(acceptBean.getMsg());
                                            }
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            Log.e(TAG, "call: 接受订单错误" +throwable.toString());
                                        }
                                    });
                        }
                    });
                    confirmDialogN.show();
                    confirmDialogN.setTitle("确认接单？");

                }
            });
            holder.btRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmDialogN confirmDialogN = new ConfirmDialogN(context);
                    confirmDialogN.setFlag(1);
                    confirmDialogN.setYesListener(new YesListener() {
                        @Override
                        public void yes(int flag) {
                            OkGo.post(Urls.PUBLIC_URL+Urls.REFUSE_ORDER)
                                    .params("order_id",bean.getOrder_id())
                                    .getCall(StringConvert.create(),RxAdapter.<String>create())
                                    .doOnSubscribe(new Action0() {
                                        @Override
                                        public void call() {
                                            Log.e(TAG, "call: 拒绝订单" );
                                        }
                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<String>() {
                                        @Override
                                        public void call(String s) {
                                            Log.e(TAG, "call: 拒绝订单"+s );
                                            Gson g = new Gson();
                                            AcceptBean acceptBean = g.fromJson(s, AcceptBean.class);
                                            if (acceptBean.getCode()==1){
                                                EventBus.getDefault().post(new RefreshGuideListEvent());
                                                ToastUtils.toast("已拒绝");
                                            }
                                        }
                                    });
                        }
                    });
                    confirmDialogN.show();
                    confirmDialogN.setTitle("拒绝此单？");
                }
            });
        }else if (bean.getStatus().equals("1")){
            holder.btGet.setVisibility(View.GONE);
            holder.btRefuse.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText("已接单");
        }else if (bean.getStatus().equals("2")){
            holder.btGet.setVisibility(View.GONE);
            holder.btRefuse.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText("已拒绝");
        }else if (bean.getStatus().equals("3")){
            holder.btGet.setVisibility(View.GONE);
            holder.btRefuse.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText("旅行已开始");
        }else if (bean.getStatus().equals("4")){
            if (bean.getPaystatus().equals("2")){
                holder.btGet.setVisibility(View.GONE);
                holder.btRefuse.setVisibility(View.GONE);
                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvState.setText("游客已付款");
            }else {
                holder.btGet.setVisibility(View.GONE);
                holder.btRefuse.setVisibility(View.GONE);
                holder.tvState.setVisibility(View.VISIBLE);
                holder.tvState.setText("等待游客付款");
            }
        }else if (bean.getStatus().equals("6")){
            holder.btGet.setVisibility(View.GONE);
            holder.btRefuse.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText("订单已完成");
        }
        return convertView;
    }
     class GuiderOrderViewHolder {
        @Bind(R.id.userIcon)
        CircleImageView userIcon;
        @Bind(R.id.userSex)
        ImageView userSex;
        @Bind(R.id.lo)
        RelativeLayout lo;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.userName)
        TextView userName;
        @Bind(R.id.phone)
        TextView phone;
        @Bind(R.id.userPhone)
        TextView userPhone;
        @Bind(R.id.orderNum)
        TextView orderNum;
        @Bind(R.id.btRefuse)
        TextView btRefuse;
        @Bind(R.id.btGot)
        TextView btGot;
        @Bind(R.id.btStarted)
        TextView btStarted;
        @Bind(R.id.btWaitPay)
        TextView btWaitPay;
        @Bind(R.id.btOver)
        TextView btOver;
         @Bind(R.id.btGetOrder)
                 TextView btGet;
         @Bind(R.id.tvState)
                 TextView tvState;
         @Bind(R.id.bookTime)
                 TextView bookTime;
        GuiderOrderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
