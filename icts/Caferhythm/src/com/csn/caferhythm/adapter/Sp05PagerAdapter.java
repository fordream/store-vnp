package com.csn.caferhythm.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.data.S0032Entity;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.fragment.LoadNewFeedFragment;

public class Sp05PagerAdapter extends PagerAdapter {
	
	private ArrayList<S0032Entity> listEntities;
	private FragmentActivity activity;
	private String[] screen = {"diet_factor","phys_factor","skin_factor","pms_factor"};
	
	public Sp05PagerAdapter(FragmentActivity activity,ArrayList<S0032Entity> listEntities) {
		super();
		this.listEntities = listEntities;
		this.activity = activity;
	}
	
	

	@Override
	public int getCount() {
		return listEntities.size();
	}
	
	@Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
		 
	}
	
	
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.sp05_pager_item, null);
		((TextView)view.findViewById(R.id.sp05_tv_content)).setText(listEntities.get(position).getMessage());
		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
		ratingBar.setRating((float) listEntities.get(position).getStar());
		TextView t = (TextView)view.findViewById(R.id.sp05_tv_title);
		t.setText(listEntities.get(position).getTitle());
		BitmapDrawable bitmapDrawable = new BitmapDrawable(listEntities.get(position).getLeft());
		t.setCompoundDrawablesWithIntrinsicBounds(bitmapDrawable, null, null, null);
		
		LoadNewFeedFragment loadNewFeedFragment =  new LoadNewFeedFragment();
		
		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.newsarea, loadNewFeedFragment, "News"+position);
		AdsFragment adsFragment = new AdsFragment(screen[position]);
		ft.add(R.id.adsarea, adsFragment, "Ads"+position);
		ft.commit();
		((ViewPager) container).addView(view, 0);
		return view;
	}
	
	
}
