package com.hulianhujia.spellway.adpaters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by FHP on 2017/5/25.
 */

public class GuiderViewPagerAdp extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public GuiderViewPagerAdp(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragmentList=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
