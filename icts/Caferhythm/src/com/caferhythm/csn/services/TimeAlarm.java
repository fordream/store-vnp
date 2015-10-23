package com.caferhythm.csn.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.caferhythm.csn.activity.ConfirmAlarmActivity;

public class TimeAlarm extends BroadcastReceiver {
	NotificationManager nm;
	public static MediaPlayer mPlayer;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		/*
		nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		
		CharSequence from = context.getResources().getString(R.string.CafeRhythm);
		CharSequence message = context.getResources().getString(R.string.Notification_alarm);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				new Intent(context,ConfirmAlarmActivity.class), 0);
		Notification notif = new Notification(R.drawable.logo_114,
				context.getResources().getString(R.string.Notification_alarm), System.currentTimeMillis());
		//notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notif.sound = (Uri) Uri.parse("android.resource://com.caferhythm.csn/raw/alarm");
		notif.setLatestEventInfo(context, from, message, contentIntent);
		nm.notify(1, notif);		 
		*/
		
		
		Intent alarmScreenIntent = new Intent(context,ConfirmAlarmActivity.class);  
		  
        // the flag used here makes the alarm run in its own activity history  
        // so the user cant click back button to go back to a running alarm  
        // (disconnects it from the rest of your application)  
        alarmScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
  
        context.startActivity(alarmScreenIntent);  
	}
}