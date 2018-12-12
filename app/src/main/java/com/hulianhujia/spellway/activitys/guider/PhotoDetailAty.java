package com.hulianhujia.spellway.activitys.guider;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.adpaters.PhotosPageerAdp;
import com.hulianhujia.spellway.javaBeans.PhotosBean;
import com.hulianhujia.spellway.untils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class PhotoDetailAty extends BaseActivity implements ViewPager.OnPageChangeListener {
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.num)
    TextView num;
    private int position;
    private PhotosPageerAdp adp;
    private String TAG="info";
    private List<PhotosBean.ReturnArrBean> datas = new ArrayList<>();
    @Override
    public int getContentId() {
        return R.layout.activity_photo_detail_aty;
    }

    @Override
    public void initView() {
        MyUtils.setWindowStatusBarColor(this,R.color.black);
        Intent intent = getIntent();
        datas= (List<PhotosBean.ReturnArrBean>) intent.getSerializableExtra("datas");
        Log.e(TAG, "initView: "+datas.get(1).getPicture() );
        position=intent.getIntExtra("position",0);
        adp=new PhotosPageerAdp(this,datas);
        num.setText((position+1)+"/"+datas.size());
        viewPager.setAdapter(adp);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        num.setText((position+1)+"/"+datas.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
