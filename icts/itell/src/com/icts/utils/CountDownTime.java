package com.icts.utils;

import java.util.ArrayList;

import android.os.Handler;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.widget.TextView;

/**
 * This class to count down of time 
 * It uses to count down the time that friends update itel
 * @author Luong
 */
public class CountDownTime {
	private final int INTERVAL_UPDATE = 1000;
	private TextView tvTime;
	private long time;
	private long timePass = 0;
	private boolean update = false;
	private Handler mHandler;
	private long mStarttime;
	private long timestampServer;
	private long timeUser;
	private long timeRemain;
	private ArrayList<UpdateTimeListener> updateTimeArr = new ArrayList<CountDownTime.UpdateTimeListener>();
	private ArrayList<TimeOutListener> timeOutArr = new ArrayList<TimeOutListener>();
	private ArrayList<ChangeMenuListener> changeMenuArr = new ArrayList<ChangeMenuListener>();
	private UpdateTimeListViewListener updateListViewArr;
	
	public CountDownTime(){
		this.mHandler = new Handler();
	}
	
	/**
	 * @param timeRemain
	 * @param tv
	 */
	public CountDownTime(long timeRemain,TextView tv){
		this.tvTime = tv;
		this.time = timeRemain;
		this.mHandler = new Handler();
	}
	
	public long getTimeServer(){
		return timestampServer;
	}
	public void setTimeServer(long timeServer){
		this.timestampServer = timeServer;
	}
	
	public void setUpdate(boolean update){
		this.update = update;
	}
	public void setTimeUser(long time){
		this.timeUser = time;
	}
	
	public void addUpdateLVListener(UpdateTimeListViewListener update){
		updateListViewArr = update;
	}
	public boolean addUpdateTimeListener(UpdateTimeListener update){
		return this.updateTimeArr.add(update);
	}
	
	public boolean removeUpdateTimeListener(UpdateTimeListener update){
		return this.updateTimeArr.remove(update);
	}
	
	public boolean addTimeOutListener(TimeOutListener update){
		return this.timeOutArr.add(update);
	}
	
	public boolean removeTimeOutListener(TimeOutListener update){
		return this.timeOutArr.remove(update);
	}
	
	public boolean addChangeMenuListener(ChangeMenuListener update){
		return this.changeMenuArr.add(update);
	}
	
	public boolean removeChangeMenuListener(TimeOutListener update){
		return this.changeMenuArr.remove(update);
	}
	
	public boolean hasChangeMenuListener(ChangeMenuListener menu){
		return this.changeMenuArr.contains(menu);
	}
	
	public void clearChangeMenuListener(){
		this.changeMenuArr.clear();
	}
	
	public void clearTimeOutListener(){
		this.timeOutArr.clear();
	}
	
	public void clearUpdateTimeListener(){
		this.updateTimeArr.clear();
	}
	
	public void setTextView(TextView tv){
		this.tvTime = tv;
	}
	public void start(){
		calculate();
		mStarttime = System.currentTimeMillis();
		mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 0);
	}
	
	public void start(long timeServer,long timeUser){
		calculate(timeServer,timeUser);
		mStarttime = System.currentTimeMillis();
		mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 0);
	}
	
	public void cancel(){
		 //mHandler.removeCallbacks(mUpdateTimeTask);	
		 mHandler.removeCallbacks(mUpdateTimeTask);	
	}
	
	public void calculate(long timestamp,long current){
		mHandler.removeCallbacks(mUpdateTimeTask);
		mStarttime = System.currentTimeMillis();
		timestampServer = timestamp;
		timeUser = current;
		long t = timestampServer-timeUser;
		if (t>=Constant.THREE_HOUR_MILIS){
			time = 0;
		}
		else {
			time = t<0?Constant.THREE_HOUR_MILIS:Constant.THREE_HOUR_MILIS-t;
		}
		mHandler.postDelayed(mUpdateTimeTask, 0);
	}
	public long getFrame(){
		if (time==0){
			return 29;
		}
		final long start = mStarttime;
		long thisTime = System.currentTimeMillis();
		long millis = thisTime - start;
		long remain = time - millis;
		long minuteTotal = remain/(1000*60);
		long frame = (long)Math.ceil((double)minuteTotal/6);
		if (frame>=30){
			frame = 30;
		}
		if (frame ==0){
			frame =1;
		}
		return (30-frame);
	}
	public void calculate(){
		mHandler.removeCallbacks(mUpdateTimeTask );
		long t = timestampServer-timeUser;
		if (t>=Constant.THREE_HOUR_MILIS){
			time = 0;
		}
		else {
			time = t<0?Constant.THREE_HOUR_MILIS:Constant.THREE_HOUR_MILIS-t;
		}
		mStarttime = System.currentTimeMillis();
		mHandler.postDelayed(mUpdateTimeTask, 0);
	}
	
	public long getTimeRemain(){
		return timeRemain;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public long getTime() {
		return time;
	}
	public long getTimePass(){
		return timePass;
	}
	private Runnable mUpdateTimeTask = new Runnable() {
	   public void run() {
		   final long start = mStarttime;
		   long thisTime = System.currentTimeMillis();
		   long millis = thisTime - start;
		   timePass = millis;
		   if (updateListViewArr!=null){
			   updateListViewArr.update(timestampServer, millis);
		   }
		   
		   //Count down from 3h
		   long mTime =time - millis;
		   timeRemain = mTime;
		   if (mTime<=0){
			   timeRemain = 0;
			   if (timeOutArr!=null){
				   for (TimeOutListener tOut : timeOutArr) {
					   tOut.timeOut();
				   }
				   
			   }
			   
			   if (changeMenuArr!=null){
				   for (ChangeMenuListener changeMenu : changeMenuArr) {
					   changeMenu.changeMenu(29);
				   }
				   
			   }
			   //tvTime.setText("00:00:00");
			   mHandler.postDelayed(mUpdateTimeTask, INTERVAL_UPDATE);
			   return;
		   }
  
		   //calculate minute
		   long minuteTotal = mTime/(1000*60);
		   if (minuteTotal%36==0){
			   if (changeMenuArr!=null){
				   for (ChangeMenuListener changeMenu : changeMenuArr) {
					   long frame = (long)Math.ceil((double)minuteTotal/6);
					   if (frame>=30){
							frame = 30;
					   }
					   if (frame ==0){
						   frame =1;
					   }
					   changeMenu.changeMenu(30-frame);
				   }
				   
			   }
		   }
		   else {
			   if (update){
				   update = false;
				   if (changeMenuArr!=null){
					   long frame = (long)Math.ceil((double)minuteTotal/6);
					   if (frame>=30){
							frame = 30;
					   }
					   if (frame ==0){
						   frame =1;
					   }
					   for (ChangeMenuListener changeMenu : changeMenuArr) {
						    changeMenu.changeMenu(30-frame);
					   }
					   
				   }
			   }
				   
		   }
		   
		   long l= mTime;
		   int hour = (int) (l/(1000*60*60));
		   l = l%(1000*60*60);
				
		   //get minute 
		   int minute = (int) (l/(1000*60));
		   l = l%(1000*60);
				
		   //get second
		   int second = (int) (l/(1000));
		   String s = String.format("%02d:%02d:%02d", hour,minute,second);
		   if (updateTimeArr!=null){
			   for (UpdateTimeListener updateTimeListener : updateTimeArr) {
				   updateTimeListener.updateText(s, timeRemain, tvTime);
			   }
			   
		   }
		   //tvTime.setText(s);
		   mHandler.postDelayed(mUpdateTimeTask, INTERVAL_UPDATE);
	   }
	};
		
	public interface UpdateTimeListViewListener{
		public abstract void update(long timeServer,long minute);
	}
	public interface UpdateTimeListener{
		public abstract void updateText(String s,long timeRemain,TextView tv);
	}

	public interface ChangeMenuListener{
		public abstract void changeMenu(long frame);
	}
	
	public interface TimeOutListener{
		public abstract void timeOut();
	}
}
