package com.caferhythm.csn.activity;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.caferhythm.csn.R;

public class DataChosingActivity extends Activity {
	// view on screen
	public static final String DAYSEIRI = "dayseiri";
	private Button backBT;
	protected WheelView dayWV;
	protected String data[];
	protected String currentAction;
	protected TextView contentTV;
	protected TextView titleTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.day_chosing_screen);
		backBT = (Button) findViewById(R.id.bt_day_chosing_back);
		dayWV = (WheelView) findViewById(R.id.wv_day_chosing);
		contentTV = (TextView)findViewById(R.id.tv_data_chosing_content);
		titleTV = (TextView)findViewById(R.id.tv_data_chosing_title);
		genData();
		findViewById(R.id.bt_day_chosing_ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra(currentAction, dayWV.getCurrentItem());
				// Activity finished ok, return the data
				setResult(RESULT_OK, i);
				finish();
			}
		});
		backBT.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				// Activity finished ok, return the data
				setResult(RESULT_CANCELED, i);
				finish();
			}
		});
	}

	protected void genData() {
		titleTV.setText(getResources().getString(R.string.data_chosing_title));
		contentTV.setText(getResources().getString(R.string.seirikikan_screen_content));
		data = new String[15];
		currentAction = DAYSEIRI;
		for (int i = 0; i < 15; i++) {
			data[i] = "" + (i+1) + getResources().getString(R.string.day);
		}
		dayWV.setViewAdapter(new ArrayWheelAdapter<String>(this, data));
		if(getIntent().getStringExtra("extra")!=null){
			dayWV.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("extra"))-1);
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		super.finish();
	}
}
