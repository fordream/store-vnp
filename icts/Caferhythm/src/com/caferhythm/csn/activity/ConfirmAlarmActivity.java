package com.caferhythm.csn.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.Configure;

public class ConfirmAlarmActivity extends BaseActivityWithHeadtab {
	// view on screen
	private LinearLayout timeLO;
	public MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

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
		
		// TODO Auto-generated method stub
		setContentTab(getResources().getString(R.string.confirm_alarm_title),
				R.layout.confirm_alarm_screen);
		
		
		super.onCreate(savedInstanceState);
		


		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
		mPlayer.setLooping(true);
		mPlayer.start();
		 
		/*
		if(TimeAlarm.mPlayer.isPlaying())
			TimeAlarm.mPlayer.stop();
			*/
		((TextView)findViewById(R.id.tv_head_name)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		// setContentView(R.layout.confirm_alarm_screen);
		timeLO = (LinearLayout) findViewById(R.id.lo_confirm_alarm_time);
		//SimpleDateFormat s1 = new SimpleDateFormat("h:mm a");
		//String time = s1.format(date)
		// timeLO.setText(s1.format(new Date()));
		int minute =0;
		int hour =0;
		SharedPreferences sp = getSharedPreferences(Configure.SHARED_PREFERENCES, MODE_PRIVATE);
		if (sp.getInt("minute", -1) > -1) {
			minute = sp.getInt("minute", 0);
		}
		if (sp.getInt("hour", -1) > -1) {
			hour =sp.getInt("hour", 0);
		}
		findViewById(R.id.bt_confirm_alarm).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mPlayer.stop();
						Intent i = new Intent(getApplicationContext(),
								BuildAlarmActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
						startActivity(i);
					}
				});
		setViewForTimeView(hour, minute);
	}

	private void setViewForTimeView(int hour,int minute) {
		timeLO.addView(getViewFromText(hour/10));
		timeLO.addView(getViewFromText(hour%10));
		timeLO.addView(getViewFromText(11));
		timeLO.addView(getViewFromText(minute/10));
		timeLO.addView(getViewFromText(minute%10));
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
