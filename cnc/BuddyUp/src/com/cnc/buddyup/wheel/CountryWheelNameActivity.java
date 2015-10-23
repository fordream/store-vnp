package com.cnc.buddyup.wheel;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.request.RequestCountryList;
import com.cnc.buddyup.response.ResponseCountryList;
import com.cnc.buddyup.sign.paracelable.Country;
import com.cnc.buddyup.sign.paracelable.CountryParcacelable;

public class CountryWheelNameActivity extends Activity {
	private WheelView city;
	private String cities1[] = new String[] { "New York", "Washington",
			"Chicago", "Atlanta", "Orlando" };
	private LoadingView loadingView;
	private CountryParcacelable countryParcacelable = new CountryParcacelable();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheel_name_country);
		city = (WheelView) findViewById(R.id.signup_wheel_country_wheel);

		String id = getIntent().getExtras().getString(Common.ARG1);

		loadingView = (LoadingView) findViewById(R.id.loadingView1);
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
		new DownLoad().execute(id);
	}

	private class DownLoad extends AsyncTask<String, String, String> {
		ResponseCountryList response;

		protected String doInBackground(String... params) {
			RequestCountryList request = new RequestCountryList();
			response = ResponseCountryList.getResponseCountryList(request);

			loadingView.post(new Runnable() {
				public void run() {
					loadingView.setVisibility(View.GONE);
				}
			});

			return params[0];
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateCities(response, result);
		}

	}

	public void updateCities1(CountryParcacelable countryParcacelable, String id) {
		int index = -1;

		cities1 = new String[countryParcacelable.getLCountries().size()];
		for (int i = 0; i < countryParcacelable.getLCountries().size(); i++) {
			if (countryParcacelable.getLCountries().get(i).getId().equals(id)) {
				index = i;
			}
			cities1[i] = countryParcacelable.getLCountries().get(i).getName();
		}

		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities1);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(index);

	}

	public void updateCities(final ResponseCountryList response, final String id) {
		int index = -1;
		cities1 = new String[response.getlCountries().size()];
		for (int i = 0; i < response.getlCountries().size(); i++) {
			Country country = response.getlCountries().get(i);
			countryParcacelable.add(country.getId() + "", country.getName() + "");
		}

		for (int i = 0; i < countryParcacelable.getLCountries().size(); i++) {
			if (countryParcacelable.getLCountries().get(i).getName().equals(id)) {
				index = i;
			}
			cities1[i] = countryParcacelable.getLCountries().get(i).getName();
		}

		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
				CountryWheelNameActivity.this, cities1);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(index);

	}

}
