package com.cnc.buddyup.wheel;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.sign.paracelable.Country;

public class SexWheelActivity extends Activity implements OnClickListener {
	private WheelView city;
	private String cities1[] = new String[] { "Male", "Fmale",
			 };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_wheel_country);
		city = (WheelView) findViewById(R.id.signup_wheel_country_wheel);

		String id = getIntent().getExtras().getString(Common.ARG0);
		city.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {

			}

			public void onScrollingFinished(WheelView wheel) {
				int index = city.getCurrentItem();
				Country id = new Country("id" + index, cities1[index]);
				Intent intent = new Intent();
				Bundle daBundle = new Bundle();
				daBundle.putParcelable(Common.ARG0, id);
				daBundle.putString(Common.ARG1, cities1[index]);
				intent.putExtras(daBundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		findViewById(R.id.button1).setOnClickListener(this);
		updateCities1(id);
	}

	private void updateCities1(String id) {
		int index = -1;
		
		for (int i = 0; i < cities1.length; i++) {
			if ((cities1[ i]).equals(id)){
				index = i;
			}
		}
		
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities1);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		//city.setVisibleItems(index);
		city.setCurrentItem(index);
		
	}

	public void onClick(View v) {
		//String message = cities1[city.getCurrentItem()];
	}
}
