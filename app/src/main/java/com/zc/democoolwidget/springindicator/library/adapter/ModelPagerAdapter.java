package com.zc.democoolwidget.springindicator.library.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ModelPagerAdapter extends FragmentStatePagerAdapter {
    protected PagerModelManager pagerModelManager;

    public ModelPagerAdapter(FragmentManager paramFragmentManager, PagerModelManager paramPagerModelManager) {
        super(paramFragmentManager);
        this.pagerModelManager = paramPagerModelManager;
    }

    public int getCount() {
        return this.pagerModelManager.getFragmentCount();
    }

    public Fragment getItem(int paramInt) {
        return this.pagerModelManager.getItem(paramInt);
    }

    public CharSequence getPageTitle(int paramInt) {
        if (this.pagerModelManager.hasTitles())
            return this.pagerModelManager.getTitle(paramInt);
        return super.getPageTitle(paramInt);
    }
}