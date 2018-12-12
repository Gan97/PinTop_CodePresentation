package com.hulianhujia.spellway.activitys;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.Contents;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.Urls;
import com.hulianhujia.spellway.customViews.ChangeAddressPopwindow;
import com.hulianhujia.spellway.customViews.LoadingDialog;
import com.hulianhujia.spellway.event.HomeFilterEvent;
import com.hulianhujia.spellway.javaBeans.TagBean;
import com.hulianhujia.spellway.javaBeans.UserInfoBean;
import com.hulianhujia.spellway.untils.MyUtils;
import com.hulianhujia.spellway.untils.SharedUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx.RxAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import me.kaede.tagview.OnTagClickListener;
import me.kaede.tagview.Tag;
import me.kaede.tagview.TagView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
public class HomeFilterAty extends BaseActivity implements OnTagClickListener, View.OnClickListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.loTitle)
    RelativeLayout loTitle;
    @Bind(R.id.tvState)
    TextView tvState;
    @Bind(R.id.tvLoc)
    TextView tvLoc;
    @Bind(R.id.dv1)
    TextView dv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tvCity)
    TextView tvCity;
    @Bind(R.id.lo1)
    LinearLayout lo1;
    @Bind(R.id.btChooseLoc)
    TextView btChooseLoc;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tagView)
    TagView tagView;
    @Bind(R.id.btCommit)
    TextView btCommit;
    @Bind(R.id.btClear)
    TextView btClear;
    private boolean isOnly=true;
    private boolean isContain=true;
    private List<String> n_tags=new ArrayList<>();
    private String TAG="info";
    private UserInfoBean userInfo;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener ;
    //监听配置
    public AMapLocationClientOption mLocationOption = null;
    private LoadingDialog loadingDialog;
    private List<TagBean.ReturnArrBean> tags=new ArrayList<>();
    private String tagStr="";
    private String city_temp;
    @Override
    public int getContentId() {
        return R.layout.activity_home_filter_aty;
    }
    private void location(){
        mLocationListener=new AMapLocationListener() {
            @Override
            public void onLocationChanged(final AMapLocation aMapLocation) {
                loadingDialog.dismiss();
                double longitude = aMapLocation.getLongitude();
                double latitude = aMapLocation.getLatitude();
                MyUtils.writeLoc(getApplicationContext(),latitude+"",longitude+"");
                Contents.LOCATION=aMapLocation.getCity();
                Log.e(TAG, "onLocationChanged: "+longitude+"==="+latitude+"地点"+aMapLocation.getCity() );
                tvState.setText("定位成功");
                tvLoc.setText(Contents.LOCATION);
                tvLoc.setClickable(false);
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        mLocationOption=new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);
//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
    }
    @Override
    public void initView() {
        userInfo= (UserInfoBean) SharedUtils.readUserInfo(this);
        loadingDialog=new LoadingDialog(this);
        location();
        if (Contents.SELECT_LOC==null||Contents.SELECT_LOC.length()==0){
            lo1.setVisibility(View.GONE);
            btChooseLoc.setText("点击选择");
        }else {
            lo1.setVisibility(View.VISIBLE);
            tvCity.setText(Contents.SELECT_LOC);
            btClear.setVisibility(View.VISIBLE);
            btChooseLoc.setText("重新选择");
        }
        if (Contents.LOCATION==null||Contents.LOCATION.length()==0){
            tvState.setText("定位失败");
            tvLoc.setText("重新定位");
            tvLoc.setClickable(true);
            tvLoc.setOnClickListener(this);
        }else {
            tvState.setText("定位成功");
            tvLoc.setText(Contents.LOCATION);
        }
        tagView.setLineMargin(20f);//dp
        tagView.setTagMargin(20f);
        tagView.setTextPaddingLeft(20f);
        tagView.setTextPaddingRight(20f);
        tagView.setOnTagClickListener(this);
    }

    @Override
    public void initData() {
        OkGo.get(Urls.PUBLIC_URL+Urls.HOME_FILTER_TAG)
                .getCall(StringConvert.create(),RxAdapter.<String>create())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e(TAG, "call: 获取标签开始" );
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: 获取标签获得" + s);
                        Gson g = new Gson();
                        TagBean tagBean = g.fromJson(s, TagBean.class);
                        if (tagBean.getCode()==1){
                            tags.clear();
                            tags.addAll(tagBean.getReturnArr());
                            for (TagBean.ReturnArrBean rb:tags){
                                n_tags.add(rb.getTag());
                            }

                            if (Contents.TAG!=null&&n_tags.contains(Contents.TAG)){
                                for (int i = 0; i < tags.size(); i++) {
                                    if (tags.get(i).getTag().equals(Contents.TAG)){
                                        if (isOnly){
                                            Tag tag = new Tag(tags.get(i).getTag());
                                            tag.tagTextColor = Color.parseColor("#333333");
                                            tag.tagTextSize = 12f;
                                            tag.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_yellow);
                                            tagView.addTag(tag);
                                            isOnly=false;
                                        }
                                    }else {
                                        Tag tag = new Tag(tags.get(i).getTag());
                                        tag.tagTextColor = Color.parseColor("#333333");
                                        tag.tagTextSize = 12f;
                                        tag.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_grey);
                                        tagView.addTag(tag);
                                    }

                                }
                            }else {
                                if (Contents.TAG!=null){
                                    Tag tagn = new Tag(Contents.TAG);
                                    tagn.tagTextColor = Color.parseColor("#333333");
                                    tagn.tagTextSize = 12f;
                                    tagn.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_yellow);
                                    tagView.addTag(tagn);
                                    isContain=false;
                                }
                                for (int i = 0; i < tags.size(); i++) {
                                        Tag tag = new Tag(tags.get(i).getTag());
                                        tag.tagTextColor = Color.parseColor("#333333");
                                        tag.tagTextSize = 12f;
                                        tag.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_grey);
                                        tagView.addTag(tag);
                                    }


                            }

                        }
                    }
                });
    }
    @OnClick({R.id.btExit, R.id.btChooseLoc, R.id.btCommit,R.id.btClear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btClear:
                Contents.CITY="";
                Contents.SELECT_LOC="";
                btChooseLoc.setText("选择城市");
                lo1.setVisibility(View.GONE);
                btClear.setVisibility(View.GONE);
                break;
            case R.id.btExit:
                finish();
                break;
            case R.id.btChooseLoc:
                    ChangeAddressPopwindow mChangeAddressPopwindow = new ChangeAddressPopwindow(getApplicationContext());
                    mChangeAddressPopwindow.showAtLocation(tvCity, Gravity.BOTTOM, 0, 0);
                    mChangeAddressPopwindow
                            .setAddresskListener(new ChangeAddressPopwindow.OnAddressCListener() {
                                @Override
                                public void onClick(String province, String city, String area) {
                                    // TODO Auto-generated method stub
                                    tvCity.setText(province +"-"+ city);
                                    Contents.SELECT_LOC=province+"-"+city;
                                    Contents.CITY=city+"市";
                                    lo1.setVisibility(View.VISIBLE);
                                    btClear.setVisibility(View.VISIBLE);
                                    btChooseLoc.setText("重新选择");

                                }
                            });
                break;
            case R.id.btCommit:
                if (Contents.CITY==null||Contents.CITY.equals("")){
                    Log.e(TAG, "onViewClicked: 传loc" );
                    EventBus.getDefault().post(new HomeFilterEvent(Contents.LOCATION,tagStr));
                }else {
                    EventBus.getDefault().post(new HomeFilterEvent(Contents.CITY,tagStr));
                }
                    finish();
                break;
        }
    }
    @Override
    public void onTagClick(int position, Tag tag) {
        tagView.removeAllTags();
        Log.e(TAG, "onTagClick: 点标签" );
/*        if (!isContain){
            n_tags.add(Contents.TAG);
        }*/
        for (int i = 0; i < this.n_tags.size() ; i++) {
            Tag tag1 = new Tag(this.n_tags.get(i));
            tag1.tagTextColor = Color.parseColor("#333333");
            tag1.tagTextSize = 12f;
            if (i==position){
                Log.e(TAG, "onTagClick: 1");
                if (tag1.text.equals(Contents.TAG)){
                    Log.e(TAG, "onTagClick: 2");
                    tag1.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_grey);
                    tagStr= this.n_tags.get(i);
                    Contents.TAG=null;
                }else {
                    Log.e(TAG, "onTagClick: 3");
                    tag1.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_yellow);
                    tagStr= this.n_tags.get(i);
                    Contents.TAG=tagStr;
                }
            }else {
                tag1.background = HomeFilterAty.this.getResources().getDrawable(R.drawable.lable_grey);
            }
            tagView.addTag(tag1);
        }
    }
    @Override
    public void onClick(View v) {
        loadingDialog.show();
        mLocationClient.startLocation();
    }
}
