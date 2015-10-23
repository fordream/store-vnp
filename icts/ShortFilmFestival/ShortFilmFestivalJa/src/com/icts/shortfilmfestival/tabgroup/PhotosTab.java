package com.icts.shortfilmfestival.tabgroup;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.icts.shortfilmfestivalJa.R;

public class PhotosTab extends FragmentActivity{
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.main_photo);
	 }
	
	 @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
		 	System.gc();
			super.onDestroy();
		}
}
