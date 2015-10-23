package com.icts.shortfilmfestival.push;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.icts.shortfilmfestivalJa.MainTabActivity;
import com.icts.shortfilmfestivalJa.R;

public class C2DMMessageReceiver extends BroadcastReceiver {
	
	private String message; // Noi dung tin nhan tu server
	public static int id=0;
	private Notification notification;
	private String contentTitle; // title hien thi khi keo thanh notification xuong
	private String contentText; // text hien thi khi keo notification xuong
	private String newsID;
	private String type;
	private String language;
	private String url;
	public static ArrayList<Message> listMessages = new ArrayList<Message>();
	
	NotificationManager notificationManager;
	@Override
	public void onReceive(Context context, Intent intent) {

		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		
		String action = intent.getAction();
		if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
			Message messageObj = new Message();
			message = intent.getStringExtra("message");
			newsID = intent.getStringExtra("newsid");
			type = intent.getStringExtra("types");
			language = intent.getStringExtra("language");
			contentTitle = intent.getStringExtra("title");
			url = intent.getStringExtra("url");
			contentText = message;
			createNotification(context, message,newsID,type);
			messageObj.setMessage(message);
			messageObj.setLanguage(language);
			messageObj.setNewsId(newsID);
			messageObj.setTitle(contentTitle);
			messageObj.setTypes(type);
			messageObj.setUrl(url);
			Log.d( "URL",newsID + "");
			listMessages.add(messageObj);
		}
		
	}
	
	public void createNotification(Context context, String message,String newID, String type) {
		notification = new Notification(R.drawable.ic_launcher,"New message from ShortFileFestival", System.currentTimeMillis()); 
		// Hide the notification after its selected
		notification.tickerText = "ShortFilmFestival";
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		id++;
		notification.number = id;
		Intent intent = new Intent(context, MainTabActivity.class);
		
		intent.putExtra("message", message);
		intent.putExtra("newsID", newsID);
		intent.putExtra("type", type);
		intent.putExtra("notification", true);
		intent.putExtra("url", url);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent,/* PendingIntent.FLAG_CANCEL_CURRENT*/ 0);
		notification.setLatestEventInfo(context, contentTitle,contentText, pendingIntent);
		Intent newIntent = new Intent(context,ResetDataActivity.class);
		notification.deleteIntent = PendingIntent.getActivity(context, 1, newIntent, 0);
		notificationManager.notify(0, notification);
	}
}