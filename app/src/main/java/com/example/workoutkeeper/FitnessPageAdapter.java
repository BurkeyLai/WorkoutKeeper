package com.example.workoutkeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

public class FitnessPageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> pageList;
    private List<String> pageTitles;

    public FitnessPageAdapter(FragmentManager fm, List<Fragment> pages, List<String> pagetitles) {
        super(fm);
        this.pageList = pages;
        this.pageTitles = pagetitles;
    }

    @Override
    public Fragment getItem(int position) {
        return pageList.get(position);
    }

    @Override
    public int getCount() {
        return pageList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {//初始子View方法
        return super.instantiateItem(container, position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles.get(position);
    }
}
