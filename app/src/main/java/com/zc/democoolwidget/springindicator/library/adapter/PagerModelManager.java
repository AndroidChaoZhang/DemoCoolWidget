package com.zc.democoolwidget.springindicator.library.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagerModelManager {
    private List<Fragment> fragmentList = new ArrayList();
    private List<CharSequence> titleList = new ArrayList();

    public PagerModelManager addCommonFragment(Class<?> paramClass, List<? extends Serializable> paramList) {
        int i = 0;
        try {
            while (i < paramList.size()) {
                Fragment localFragment = (Fragment)paramClass.newInstance();
                Bundle localBundle = new Bundle();
                localBundle.putSerializable("data", (Serializable)paramList.get(i));
                localFragment.setArguments(localBundle);
                this.fragmentList.add(localFragment);
                i++;
            }
        }
        catch (InstantiationException localInstantiationException) {
            localInstantiationException.printStackTrace();
            return this;
        } catch (IllegalAccessException localIllegalAccessException) {
            localIllegalAccessException.printStackTrace();
        }
        return this;
    }

    public PagerModelManager addCommonFragment(Class<?> paramClass, List<? extends Serializable> paramList, List<String> paramList1) {
        this.titleList.addAll(paramList1);
        addCommonFragment(paramClass, paramList);
        return this;
    }

    public PagerModelManager addCommonFragment(List<? extends Fragment> paramList) {
        this.fragmentList.addAll(paramList);
        return this;
    }

    public PagerModelManager addCommonFragment(List<? extends Fragment> paramList, List<String> paramList1) {
        this.titleList.addAll(paramList1);
        addCommonFragment(paramList);
        return this;
    }

    public PagerModelManager addFragment(Fragment paramFragment) {
        this.fragmentList.add(paramFragment);
        return this;
    }

    public PagerModelManager addFragment(Fragment paramFragment, CharSequence paramCharSequence) {
        this.titleList.add(paramCharSequence);
        addFragment(paramFragment);
        return this;
    }

    public int getFragmentCount() {
        return this.fragmentList.size();
    }

    public Fragment getItem(int paramInt) {
        return (Fragment)this.fragmentList.get(paramInt);
    }

    public CharSequence getTitle(int paramInt) {
        return (CharSequence)this.titleList.get(paramInt);
    }

    public boolean hasTitles() {
        return this.titleList.size() != 0;
    }
}