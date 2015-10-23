package org.cnc.smashpaddles.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Achievements extends Activity{

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.achievements_screen);
		
		Button backAchievement=(Button) findViewById(R.id.backAchievements);
		backAchievement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	

}
