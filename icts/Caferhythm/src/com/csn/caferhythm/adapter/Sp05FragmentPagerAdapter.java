package com.csn.caferhythm.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.caferhythm.csn.data.S0032Entity;
import com.caferhythm.csn.fragment.PageItemFragment;

public class Sp05FragmentPagerAdapter extends FragmentStatePagerAdapter {
	private ArrayList<S0032Entity> listEntities;
	private Activity activity;

	public Sp05FragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public Sp05FragmentPagerAdapter(FragmentManager fm,ArrayList<S0032Entity> listEntities, Activity activity) {
		super(fm);
		this.listEntities = listEntities;
		this.activity = activity;
	}
	

	@Override
	public Fragment getItem(int arg0) {
		PageItemFragment fragment = new PageItemFragment(arg0,listEntities,activity);
		Bundle args = new Bundle();
        fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return listEntities.size();
	}

	
}
