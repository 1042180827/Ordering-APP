package com.saxiao.orderinghelpapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class TabVpAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mtitleList;
    private ArrayList<Fragment> mfmList;

    public TabVpAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fmList) {
        super(fm);
        mtitleList = titleList;
        mfmList = fmList;
    }

    @Override
    public Fragment getItem(int position) {
        return mfmList.get(position);
    }

    @Override
    public int getCount() {
        return mfmList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mtitleList.get(position);
    }
}
