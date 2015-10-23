package com.caferhythm.csn.activity;

import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.os.Bundle;

import com.caferhythm.csn.R;

public class AnteiseiChosingActivity extends DataChosingActivity {
	public static final String ANTEISEI = "ANTEISEI";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void genData() {
		// TODO Auto-generated method stub
		titleTV.setText(getResources().getString(R.string.anteisei_chosing_title));
		contentTV.setText(getResources().getString(R.string.anteisei_screen_content));
		data = getResources().getStringArray(R.array.anteisei_array);
		currentAction = ANTEISEI;
		dayWV.setViewAdapter(new ArrayWheelAdapter<String>(this, data));
		if(getIntent().getStringExtra("extra")!=null){
			dayWV.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("extra"))-1);
		}
	}
}
