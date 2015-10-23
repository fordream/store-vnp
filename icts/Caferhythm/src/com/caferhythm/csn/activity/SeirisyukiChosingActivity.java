package com.caferhythm.csn.activity;

import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.os.Bundle;

import com.caferhythm.csn.R;

public class SeirisyukiChosingActivity extends DataChosingActivity {
	public static final String SEIRISYUKI ="seirisyuki";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
	}
	@Override
	public void genData() {
		// TODO Auto-generated method stub
		titleTV.setText(getResources().getString(R.string.seirisuuki_chosing_title));
		contentTV.setText(getResources().getString(R.string.seirisyuki_screen_content));
		data=new String[21];
		currentAction = SEIRISYUKI;
		for(int i = 0;i<=20;i++){
			data[i] = ""+(i+20);
		}
		dayWV.setViewAdapter(new ArrayWheelAdapter<String>(this, data));
		if(getIntent().getStringExtra("extra")!=null){
			dayWV.setCurrentItem(Integer.parseInt(getIntent().getStringExtra("extra"))-20);
		}
	}
}
