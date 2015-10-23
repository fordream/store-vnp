package com.caferhythm.csn.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.fragment.AdsFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CalendarContentActivity extends FragmentActivity {
	// view on screen
	private TextView calendarTV;
	private EditText calendarET;
	private Button cancelBT;
	private Button finishBT;

	private String dateString;
	private Button c_back_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_content_screen);
		calendarTV = (TextView) findViewById(R.id.tv_calendar_content);
		calendarET = (EditText) findViewById(R.id.et_calendar_content);
		cancelBT = (Button) findViewById(R.id.bt_calendar_content_cancel);
		finishBT = (Button) findViewById(R.id.bt_calendar_content_ok);
		c_back_button = (Button) findViewById(R.id.c_back_button);
		c_back_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		// if(getIntent().getStringExtra(""))
		dateString = getIntent().getStringExtra("date");
		SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat s2 = new SimpleDateFormat("yyyy"
				+ getResources().getString(R.string.year) + "MM"
				+ getResources().getString(R.string.month) + "dd"
				+ getResources().getString(R.string.day));
		try {
			calendarTV.setText(s2.format(s1.parse(dateString)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// getIntent().getStr
		if (getIntent().getStringExtra("note").length() > 0) {
			calendarET.setText(getIntent().getStringExtra("note"));
		}
		calendarET.setSelection(calendarET.getText().length());
		cancelBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		finishBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postMemo(calendarET.getText().toString(), dateString);
			}
		});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		AdsFragment adsFragment = new AdsFragment("mypage");
		ft.add(R.id.adsarea, adsFragment, "Ads");
		ft.commit();
	}

	private void postMemo(String memo, String date) {
		RequestParams params = new RequestParams();
		params.put("token", FlashScreenActivity.token);
		params.put("date", date);
		params.put("memo", memo);
		Connection.get(API.API_S008, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.i("test", "" + arg0);
				finish();
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Log.i("test", "" + arg0);
				finish();
			}
		});
	}
}
