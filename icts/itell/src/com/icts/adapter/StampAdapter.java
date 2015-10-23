package com.icts.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.icts.control.StampFragment;
import com.icts.control.StampFragment.OnClickStampImage;
import com.icts.object.StampObject;

public class StampAdapter extends FragmentPagerAdapter {
	private ArrayList<StampObject[]> arrStamp;
	private OnClickStampImage onClick;
	private FragmentManager fm;
	
	public StampAdapter(FragmentManager arg0) {
		super(arg0);
		fm = arg0;
	}
	public StampAdapter(FragmentManager arg0,ArrayList<StampObject[]> arr) {
		super(arg0);
		fm = arg0;
		this.arrStamp = arr;
	}
	
	
	public void setData(ArrayList<StampObject[]> arr){
		this.arrStamp.clear();
		this.arrStamp.addAll(arr);
		notifyDataSetChanged();
	}
	
	public void removeAll(){
		this.arrStamp.clear();
		notifyDataSetChanged();
	}
	@Override
	public Fragment getItem(int arg0) {
		StampFragment stamp = StampFragment.newInstance(arrStamp.get(arg0));
		stamp.setOnClickStampImage(onClick);
		return stamp;
	}
	
	public Fragment getActiveFragment(ViewPager container, int position) {
		String name = makeFragmentName(container.getId(), position);
		return  fm.findFragmentByTag(name);
	}

	
	
	@Override
	public Object instantiateItem(View container, int position) {
		return super.instantiateItem(container, position);
	}
	private static String makeFragmentName(int viewId, int index) {
	    return "android:switcher:" + viewId + ":" + index;
	}
	   /*ArticleFragment newFragment = new ArticleFragment();
       Bundle args = new Bundle();
       args.putInt(ArticleFragment.ARG_POSITION, position);
       newFragment.setArguments(args);
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

       // Replace whatever is in the fragment_container view with this fragment,
       // and add the transaction to the back stack so the user can navigate back
       transaction.replace(R.id.fragment_container, newFragment);
       transaction.addToBackStack(null);

       // Commit the transaction
       transaction.commit();*/
	
	public StampObject[] getStampAr(int pos){
		return arrStamp.get(pos);
	}
	
	@Override
	public void startUpdate(View container) {
		// TODO Auto-generated method stub
		super.startUpdate(container);
	}

	@Override
	public int getCount() {
		return arrStamp.size();
	}
	public void setOnClickStampImage(OnClickStampImage onClick){
		this.onClick = onClick;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
	
}
