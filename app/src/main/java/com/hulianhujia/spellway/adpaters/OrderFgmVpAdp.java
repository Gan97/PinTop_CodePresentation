package com.hulianhujia.spellway.adpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by FHP on 2017/8/9.
 */

public class OrderFgmVpAdp extends FragmentPagerAdapter {
    private List<Fragment> datas;
    public OrderFgmVpAdp(FragmentManager fm,List<Fragment> datas) {
        super(fm);
        this.datas=datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
