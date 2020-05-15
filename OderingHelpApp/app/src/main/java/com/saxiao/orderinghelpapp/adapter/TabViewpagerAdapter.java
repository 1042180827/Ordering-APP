package com.saxiao.orderinghelpapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import java.util.List;

/**
 * 有Tab标签的viewpager
 */
public class TabViewpagerAdapter extends BaseFragmentStatePagerAdapter {

	private List<Fragment> fragments;
	public TabViewpagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments != null ? fragments.get(position) : null;
	}

	@Override
	public int getCount() {
		return fragments != null ? fragments.size() : 0;
	}

	@Nullable @Override public CharSequence getPageTitle(int position) {
		return super.getPageTitle(position);
	}

	@Override public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

	@Override public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}
}
