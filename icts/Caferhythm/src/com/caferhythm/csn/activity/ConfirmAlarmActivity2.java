package com.caferhythm.csn.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.Configure;
import com.caferhythm.csn.services.TimeAlarm2;

public class ConfirmAlarmActivity2 extends BaseActivityWithHeadtab {
	// view on screen
	private LinearLayout timeLO;
	AlarmManager am;
	public MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Window window = getWindow();

		// when set the window will cause the keyguard to be dismissed, only if
		// it
		// is not a secure lock keyguard.
		window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		// when set as a window is being added or made visible, once the window
		// has been shown then the system will poke the power manager's
		// user activity (as if the user had woken up the device) to turn the
		// screen on.
		window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

		// as long as this window is visible to the user, keep the device's
		// screen turned on and bright.
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentTab(getResources().getString(R.string.alarm),
				R.layout.confirm_alarm_screen2);
		super.onCreate(savedInstanceState);
		
		


		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
		mPlayer.setLooping(true);
		mPlayer.start();
		
		
		am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// ((TextView)findViewById(R.id.tv_head_name)).setTextSize(TypedValue.COMPLEX_UNIT_SP,
		// 14);
		// setContentView(R.layout.confirm_alarm_screen);
		timeLO = (LinearLayout) findViewById(R.id.lo_confirm_alarm_time);
		// SimpleDateFormat s1 = new SimpleDateFormat("h:mm a");
		// String time = s1.format(date)
		// timeLO.setText(s1.format(new Date()));
		int minute = 0;
		int hour = 0;
		SharedPreferences sp = getSharedPreferences(
				Configure.SHARED_PREFERENCES, MODE_PRIVATE);
		if (sp.getInt("minute2", -1) > -1) {
			minute = sp.getInt("minute2", 0);
		}
		if (sp.getInt("hour2", -1) > -1) {
			hour = sp.getInt("hour2", 0);
		}
		setViewForTimeView(hour, minute);
		findViewById(R.id.bt_confirm_alarm_wake_up).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mPlayer.stop();
						Intent i = new Intent(getApplicationContext(),
								BuildAlarmActivity2.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(i);
					}
				});
		findViewById(R.id.bt_confirm_alarm_snooze).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mPlayer.stop();
						setOneTimeAlarm();
						finish();
					}
				});
	}

	public void setOneTimeAlarm() {
		Intent intent = new Intent(this, TimeAlarm2.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000*60,
				pendingIntent);
	}

	private void setViewForTimeView(int hour, int minute) {
		timeLO.addView(getViewFromText(hour / 10));
		timeLO.addView(getViewFromText(hour % 10));
		timeLO.addView(getViewFromText(11));
		timeLO.addView(getViewFromText(minute / 10));
		timeLO.addView(getViewFromText(minute % 10));
	}

	private View getViewFromText(int t) {
		ImageView result = new ImageView(this);
		switch (t) {
		case 0:
			result.setImageResource(R.drawable.num_0);
			break;
		case 1:
			result.setImageResource(R.drawable.num_1);
			break;
		case 2:
			result.setImageResource(R.drawable.num_2);
			break;
		case 3:
			result.setImageResource(R.drawable.num_3);
			break;
		case 4:
			result.setImageResource(R.drawable.num_4);
			break;
		case 5:
			result.setImageResource(R.drawable.num_5);
			break;
		case 6:
			result.setImageResource(R.drawable.num_6);
			break;
		case 7:
			result.setImageResource(R.drawable.num_7);
			break;
		case 8:
			result.setImageResource(R.drawable.num_8);
			break;
		case 9:
			result.setImageResource(R.drawable.num_9);
			break;
		default:
			result.setImageResource(R.drawable.dot);
			break;
		}
		return result;
	}
}
