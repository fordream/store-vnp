package com.cnc.buddyup.wheel;

import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.common.views.LoadingView;
import com.cnc.buddyup.request.RequestAge;
import com.cnc.buddyup.response.ResponseAge;
import com.cnc.buddyup.response.item.Age;
import com.cnc.buddyup.sign.paracelable.CountryParcacelable;

public class AgeWheelActivity extends Activity implements OnClickListener {
	private WheelView city;
	@SuppressWarnings("unused")
	private CountryParcacelable countryParcacelable;
	private String cities1[];
	private LoadingView loadingView;
	private String id = "";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheel);
		loadingView = (LoadingView)findViewById(R.id.loadingView1);
		city = (WheelView) findViewById(R.id.signup_wheel_country_wheel);

		countryParcacelable = getIntent().getExtras()
				.getParcelable(Common.ARG0);
		id = getIntent().getExtras().getString(Common.ARG1);
		
		city.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {

			}

			public void onScrollingFinished(WheelView wheel) {
				int index = city.getCurrentItem();
				Intent intent = new Intent();
				Bundle daBundle = new Bundle();
				daBundle.putString(Common.ARG0, cities1[index]);
				intent.putExtras(daBundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		new AsynAge().execute("");
		//updateCities1(countryParcacelable, id);
	}
	private void showLoadingView(final boolean isShow){
		loadingView.post(new Runnable() {
			public void run() {
				loadingView.setVisibility(isShow? View.VISIBLE : View.GONE);
			}
		});
	}
	
	private class AsynAge extends AsyncTask<String, String, String>{
		private ResponseAge responseAge;
		protected String doInBackground(String... params) {
			RequestAge requestAge = new RequestAge();
			responseAge = ResponseAge.getData(requestAge);
			
			return null;
		}
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateCities1(responseAge);
			showLoadingView(false);
			
		}
		
	}
	
//	private void updateCities1(CountryParcacelable countryParcacelable,
//			String id) {
//		List<Age> lAges = new DBAdapter(this).getLAge();
//		cities1 = new String[lAges.size()];
//
//		int index = -1;
//		for (int i = 0; i < lAges.size(); i++) {
//			cities1[i] = lAges.get(i).getValue();
//			if (cities1[i].equals(id)) {
//				index = i;
//			}
//		}
//		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
//				cities1);
//		adapter.setTextSize(18);
//		city.setViewAdapter(adapter);
//		city.setCurrentItem(index);
//
//	}

	public void updateCities1(ResponseAge responseAge) {
		if("0".equals(responseAge.getStatus())){
			List<Age> lAges = responseAge.getListAge();
			cities1 = new String[lAges.size()];

			int index = -1;
			for (int i = 0; i < lAges.size(); i++) {
				cities1[i] = lAges.get(i).getValue();
				if (cities1[i].equals(id)) {
					index = i;
				}
			}
			ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
					cities1);
			adapter.setTextSize(18);
			city.setViewAdapter(adapter);
			city.setCurrentItem(index);
			
		}else{
			String title = getResources().getString(R.string.message1);
			Common.builder(this, title, responseAge.getMessage());
		}
	}
	public void onClick(View v) {
		//String message = cities1[city.getCurrentItem()];
	}
}
