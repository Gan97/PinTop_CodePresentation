package com.hulianhujia.spellway.activitys;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hulianhujia.spellway.BaseActivity;
import com.hulianhujia.spellway.R;
import com.hulianhujia.spellway.adpaters.MyFragmentPagerAdapter;
import com.hulianhujia.spellway.fragments.GuideFragment;
import com.hulianhujia.spellway.fragments.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;

public class RegisAty extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @Bind(R.id.btExit)
    ImageView btExit;
    private ViewPager myviewpager;

    //选项卡中的按钮
    private Button btn_first;
    private Button btn_second;
    //作为指示标签的按钮
    private ImageView cursor;
    //标志指示标签的横坐标
    float cursorX = 0;

    private ArrayList<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;


    //所有按钮的宽度的集合
    private int[] widthArgs;
    //所有按钮的集合
    private Button[] btnArgs;

    @Override
    public void initView() {
        myviewpager = (ViewPager) this.findViewById(R.id.myviewpager);

        btn_first = (Button) this.findViewById(R.id.btn_first);
        btn_second = (Button) this.findViewById(R.id.btn_second);
        btExit = (ImageView) this.findViewById(R.id.btExit);

        //初始化按钮数组
        btnArgs = new Button[]{btn_first, btn_second};
        //指示标签设置为粉底红色
        cursor = (ImageView) this.findViewById(R.id.cursor_btn);
        cursor.setBackgroundColor(getResources().getColor(R.color.indicator));

        btn_first.setOnClickListener(this);
        btn_second.setOnClickListener(this);
        btExit.setOnClickListener(this);

        //先重置所有按钮颜色
        resetButtonColor();
        //再将第一个按钮字体设置为红色，表示默认选中第一个
        btn_first.setTextColor(getResources().getColor(R.color.indicator));


        initFragment();

        myviewpager.setAdapter(adapter);
        myviewpager.setOnPageChangeListener(this);

        btn_first.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor.getLayoutParams();
                //减去边距*2，以对齐标题栏文字
                lp.width = btn_first.getWidth() - btn_first.getPaddingLeft() * 2;
                cursor.setLayoutParams(lp);
                cursor.setX(btn_first.getPaddingLeft());
            }
        });
    }

    private void initFragment() {
        //fragment的集合，对应每个子页面

        fragments = new ArrayList<Fragment>();
        fragments.add(new UserFragment());
        fragments.add(new GuideFragment());

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);

    }

    //重置所有按钮的颜色
    public void resetButtonColor() {
//        btn_first.setBackgroundColor(Color.parseColor("#DCDCDC"));
//        btn_second.setBackgroundColor(Color.parseColor("#DCDCDC"));
        btn_first.setTextColor(Color.BLACK);
        btn_second.setTextColor(Color.BLACK);
    }


    @Override
    public int getContentId() {
        return R.layout.activity_regis_aty;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        if (widthArgs == null) {
            widthArgs = new int[]{btn_first.getWidth(), btn_second.getWidth()};
        }


        //每次滑动首先重置所有按钮的颜色
        resetButtonColor();
        //将滑动到的当前按钮颜色设置为红色
        btnArgs[position].setTextColor(Color.RED);

        cursorAnim(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_first:
                myviewpager.setCurrentItem(0);
                cursorAnim(0);
                break;
            case R.id.btn_second:
                myviewpager.setCurrentItem(1);
                cursorAnim(1);
                break;
            case R.id.btExit:
                finish();

        }
    }

    //指示器的跳转，传入当前所处的页面的下标
    public void cursorAnim(int curItem) {
        //每次调用，就将指示器的横坐标设置为0，即开始的位置
        cursorX = 0;
        //再根据当前的curItem来设置指示器的宽度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) cursor.getLayoutParams();
        //减去边距*2，以对齐标题栏文字
        lp.width = widthArgs[curItem] - btnArgs[0].getPaddingLeft() * 2;
        cursor.setLayoutParams(lp);
        //循环获取当前页之前的所有页面的宽度
        for (int i = 0; i < curItem; i++) {
            cursorX = cursorX + btnArgs[i].getWidth();
        }
        //再加上当前页面的左边距，即为指示器当前应处的位置
        cursor.setX(cursorX + btnArgs[curItem].getPaddingLeft());
    }


}
