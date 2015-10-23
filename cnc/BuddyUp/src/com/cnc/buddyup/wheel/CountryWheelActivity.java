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
import com.cnc.buddyup.sign.paracelable.CountryParcacelable;

public class CountryWheelActivity extends Activity implements OnClickListener {
	private WheelView city;
	private CountryParcacelable countryParcacelable;
	private String cities1[] = new String[] { "New York", "Washington",
			"Chicago", "Atlanta", "Orlando" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_wheel_country);
		city = (WheelView) findViewById(R.id.signup_wheel_country_wheel);

		countryParcacelable = getIntent().getExtras()
				.getParcelable(Common.ARG0);
		String id = getIntent().getExtras().getString(Common.ARG1);
		city.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {

			}

			public void onScrollingFinished(WheelView wheel) {
				int index = city.getCurrentItem();
				Country id = countryParcacelable.getLCountries().get(index);
				Intent intent = new Intent();
				Bundle daBundle = new Bundle();
				daBundle.putParcelable(Common.ARG0, id);
				intent.putExtras(daBundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		findViewById(R.id.button1).setOnClickListener(this);
		updateCities1(countryParcacelable,id);
	}

	private void updateCities1(CountryParcacelable countryParcacelable,String id) {
		int index = -1;
		
		cities1 = new String[countryParcacelable.getLCountries().size()];
		for (int i = 0; i < countryParcacelable.getLCountries().size(); i++) {
			if (countryParcacelable.getLCountries().get(i).getId().equals(id)){
				index = i;
			}
			cities1[i] = countryParcacelable.getLCountries().get(i).getName();
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
