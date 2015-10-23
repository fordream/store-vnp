package com.icts.utils;

import android.os.Handler;
import android.widget.TextView;

/**
 * This class to count down of time 
 * It uses to count down the time that friends update itel
 * @author Luong
 */
public class CountTime4Textview {
	private final int INTERVAL_UPDATE = 100;
	private TextView mTvHour;
	private TextView mTvMinute;
	private TextView mTvSecond;
	private long time;
	private Handler mHandler;
	private boolean isOnlyMinute = false;
	
	/**
	 * This uses to count 
	 * @param time
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public CountTime4Textview(long time,TextView hour,TextView minute, TextView second){
		this.mTvHour = hour;
		this.time = time;
		this.mTvMinute = minute;
		this.mTvSecond = second;
		this.isOnlyMinute = false;
		this.mHandler = new Handler();
	}
	
	public void start(){
		if (time>0){
			mHandler.removeCallbacks(mUpdateTimeTask);
	        mHandler.postDelayed(mUpdateTimeTask, 0);
		}
	}
	
	public void cancel(){
		 //mHandler.removeCallbacks(mUpdateTimeTask);	
		 mHandler.removeCallbacks(mUpdateTimeTask);	
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getTime() {
		return time;
	}

	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			    long thisTime = Utils.getTimeStamp();
			    long millis = thisTime - time;
			    //Count down from 3h
			    long mTime = 10800000 - millis;
			    if (mTime<0)
			    	return;
	  
			    long l= mTime;
				//get hour 
			    if (!isOnlyMinute){
			    	int hour = (int) (l/(1000*60*60));
			    	mTvHour.setText(hour+"");
			    }
				l = l%(1000*60*60);
					
				//get minute 
				int minute = (int) (l/(1000*60));
				l = l%(1000*60);
					
				//get second
				if(!isOnlyMinute){
					int second = (int) (l/(1000));
					mTvSecond.setText(String.format("%02d", second));
				}
				mTvMinute.setText(String.format("%02d", minute));
				mHandler.postDelayed(mUpdateTimeTask, INTERVAL_UPDATE);
		   }
		};
		
	
}
