package com.icts.shortfilmfestival.tabgroup;

import com.icts.shortfilmfestival_en.FestivalTabActivity;
import com.vnp.shortfilmfestival.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class ScheduleTab extends FragmentActivity {
		@Override
		protected void onCreate(Bundle arg0) {
			super.onCreate(arg0);
			setContentView(R.layout.festival_schedule);
		}
		
		@Override
		public void onBackPressed() {
			FestivalTabActivity.onKeyBack();
			super.onBackPressed();
		}
		
		 @Override
			protected void onDestroy() {
				// TODO Auto-generated method stub
			 	System.gc();
				super.onDestroy();
			}
}
