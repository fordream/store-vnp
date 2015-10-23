package com.cnc.android.timtalk.ui.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * The <code>PagerAdapter</code> serves the fragments when paging.
 * @author namnd
 * 
 */
public class PagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	/**
	 * @param fm
	 * @param fragments
	 */

	public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.fragments = fragments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return this.fragments.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.size();
	}

}
