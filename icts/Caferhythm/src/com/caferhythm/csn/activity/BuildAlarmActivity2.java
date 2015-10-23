package com.caferhythm.csn.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.json.JsonPaser;
import com.caferhythm.csn.services.TimeAlarm;
import com.caferhythm.csn.services.TimeAlarm2;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class BuildAlarmActivity2 extends BaseActivityWithHeadtab {
	// view on screen
	private WheelView hourWV;
	private WheelView minuteWV;
	private CheckBox setUpAlarmBT;
	private Button saveBT;
	private boolean isAlarm;
	AlarmManager am;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentTab(getString(R.string.alarm),
				R.layout.build_alarm_screen);
		super.onCreate(savedInstanceState);
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		hourWV = (WheelView) findViewById(R.id.wv_build_alarm_hour);
		minuteWV = (WheelView) findViewById(R.id.wv_build_alarm_minute);
		sp = getSharedPreferences(Configure.SHARED_PREFERENCES, MODE_PRIVATE);
		setUpAlarmBT = (CheckBox) findViewById(R.id.bt_set_alarm);
		saveBT = (Button) findViewById(R.id.bt_head_item_menu2);
		saveBT.setBackgroundResource(R.drawable.save_button_file);
		saveBT.setVisibility(View.VISIBLE);
		findViewById(R.id.img_build_alarm).setBackgroundResource(R.drawable.clock_bg);
		
		genData();
		if (!haveNetworkConnection()) {
			Toast.makeText(this, getResources().getString(R.string.ms_no_internet), Toast.LENGTH_LONG)
					.show();
			return;
		}
		getToken(sp.getString(Configure.UUID_STRING, ""));
		saveBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (setUpAlarmBT.isChecked()) {
					Log.i("test", "hour:" + hourWV.getCurrentItem() + " minu:"
							+ minuteWV.getCurrentItem());
					setOneTimeAlarm(hourWV.getCurrentItem(),
							minuteWV.getCurrentItem());
					Editor ed = sp.edit();
					ed.putInt("minute2", minuteWV.getCurrentItem());
					ed.putBoolean("isalarm2", true);
					ed.putInt("hour2", hourWV.getCurrentItem());
					ed.commit();
					
				}
				Toast.makeText(getApplicationContext(),
						getResources().getString(R.string.toast_alarm),
						Toast.LENGTH_SHORT).show();
			}
		});
		AdsFragment adsFragment = new AdsFragment("clock_alerm");
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.adsarea, adsFragment, "Ads");
		ft.commit();
	}

	public void setOneTimeAlarm(int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy MM dd hh:mm:ss");
		Date stamp = cal.getTime();
		Log.i("test", "------date1:" + stamp.getDate());
		stamp.setHours(hour);
		stamp.setMinutes(minute);

		stamp.setSeconds(0);

		// In case it's too late notify user today
		if (stamp.getTime() < System.currentTimeMillis()) {
			Log.i("test",
					"run here:"
							+ (System.currentTimeMillis() - stamp.getTime()));
			stamp.setTime(stamp.getTime() + AlarmManager.INTERVAL_DAY);
		}
		Intent intent = new Intent(this, TimeAlarm2.class);
		Log.i("test", "------date1:" + " date2:" + s1.format(stamp));
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		am.set(AlarmManager.RTC_WAKEUP, stamp.getTime(), pendingIntent);
	}

	public void setRepeatingAlarm() {
		Intent intent = new Intent(this, TimeAlarm2.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent, PendingIntent.FLAG_CANCEL_CURRENT);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				(5 * 1000), pendingIntent);
	}

	private void genData() {
		int hour=0,minute = 0;
		hourWV.setViewAdapter(new NumericWheelAdapter(this, 0, 23));
		String listMinute[] = new String[60];
		for (int i = 0; i < 60; i++) {
			listMinute[i] = "" + i;
		}
		minuteWV.setViewAdapter(new ArrayWheelAdapter<String>(this, listMinute));
		if (sp.getInt("minute2", -1) > -1) {
			minute = sp.getInt("minute2", 0);
			minuteWV.setCurrentItem(sp.getInt("minute2", -1));
		}
		if (sp.getInt("hour", -1) > -1) {
			hour =sp.getInt("hour2", 0);
			hourWV.setCurrentItem(sp.getInt("hour2", -1));
		}
		if (sp.getBoolean("isalarm2", false)) {
			setUpAlarmBT.setChecked(true);
			Log.i("test","hour:"+hour+"minute:"+minute);
			setOneTimeAlarm(hour, minute);
		}
	}

	private String getToken(String uuid) {
		String result = "";
		Connection.get(API.API_GET_TOKEN + uuid, null,
				new AsyncHttpResponseHandler() {
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Log.e("E", arg0);
						FlashScreenActivity.token = JsonPaser
								.PaserAccesstoken(arg0);
						Log.e("test", "reslult:----:" + arg0);
						Log.e("test", "reslult:----:"
								+ FlashScreenActivity.token);
					}

					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						Log.i("test", "reslult failure:" + arg1);
					}
				});
		return result;
	}

	private boolean haveNetworkConnection() {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		return haveConnectedWifi || haveConnectedMobile;
	}

}
