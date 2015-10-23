package com.icts.utils;

import java.util.Calendar;
import java.util.Locale;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

/**
 * This class to count time up and down 
 * It uses to count down itel time and count time that friends update itel
 * @author Luong
 *
 */
public class CountTime {
	public static final int INTERVAL_COUNT = 1000;//60000
	private ListView lv;
	private Handler mHandler;
	
	
	private OnChangeTimeHandle changeTime;
	public void setEventChangetime(OnChangeTimeHandle changeTime){
		this.changeTime = changeTime;
		
	}
	
	
	public CountTime(ListView lv){
		this.lv = lv;
		this.mHandler = new Handler();
	}
	
	public void start(){
		mHandler.removeCallbacks(mCountTimeTask);
        mHandler.postDelayed(mCountTimeTask, 0);
	}
	
	public void cancel(){
		 mHandler.removeCallbacks(mCountTimeTask);	
	}
	
	private Runnable mCountTimeTask = new Runnable() {
		   public void run() {
			    long thisTime = Calendar.getInstance(Locale.JAPAN).getTimeInMillis();
			    if (changeTime!=null){
			    	changeTime.onChangeTime(lv,thisTime);
			    }
				mHandler.postDelayed(mCountTimeTask, INTERVAL_COUNT);
		   }
		};
		
	public interface OnChangeTimeHandle{
		public abstract void onChangeTime(ListView lv,long time);
		
	}
}
