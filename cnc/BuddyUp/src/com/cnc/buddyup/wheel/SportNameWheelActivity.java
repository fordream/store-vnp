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
import com.cnc.buddyup.request.RequestSportName;
import com.cnc.buddyup.response.ResponseSportName;
import com.cnc.buddyup.response.item.SportName;

public class SportNameWheelActivity extends Activity implements OnClickListener {
	private WheelView city;
	private String cities1[];
	private LoadingView loadingView;
	private String id = "";
	private String idOfView = "";
	private ResponseSportName responseSkillLevel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheel);
		loadingView = (LoadingView) findViewById(R.id.loadingView1);
		city = (WheelView) findViewById(R.id.signup_wheel_country_wheel);
		try {
			id = getIntent().getExtras().getString(Common.ARG0);
		} catch (Exception e) {
			id = "";
		}

		try {
			idOfView = getIntent().getExtras().getString(Common.ARG1);
		} catch (Exception e) {
			idOfView = "";
		}

		city.addScrollingListener(new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {

			}

			public void onScrollingFinished(WheelView wheel) {
				try {
					int index = city.getCurrentItem();
					SportName skill = responseSkillLevel.getlSkillLevels().get(
							index);

					Intent intent = new Intent();
					Bundle daBundle = new Bundle();
					daBundle.putString(Common.ARG0, skill.getId());
					daBundle.putString(Common.ARG1, skill.getName());
					daBundle.putString(Common.ARG2, idOfView);
					intent.putExtras(daBundle);
					setResult(RESULT_OK, intent);
				} catch (Exception e) {
				}
				finish();
			}
		});
		new AsynAge().execute("");
	}

	private void showLoadingView(final boolean isShow) {
		loadingView.post(new Runnable() {
			public void run() {
				loadingView.setVisibility(isShow ? View.VISIBLE : View.GONE);
			}
		});
	}

	private class AsynAge extends AsyncTask<String, String, String> {
		private ResponseSportName responseAge;

		protected String doInBackground(String... params) {
			RequestSportName requestAge = new RequestSportName();
			responseAge = ResponseSportName.getData(requestAge);

			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateCities1(responseAge);
			showLoadingView(false);

		}

	}

	public void updateCities1(ResponseSportName responseAge) {
		responseSkillLevel = responseAge;
		if ("0".equals(responseAge.getStatus())) {
			List<SportName> lAges = responseAge.getlSkillLevels();
			cities1 = new String[lAges.size()];
			int index = -1;
			for (int i = 0; i < lAges.size(); i++) {
				SportName skillLevel = lAges.get(i);
				cities1[i] = skillLevel.getName();
				if (skillLevel.getId().equals(id)) {
					index = i;
				}
			}
			ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
					this, cities1);
			adapter.setTextSize(18);
			city.setViewAdapter(adapter);
			city.setCurrentItem(index);
		} else {
			String title = getResources().getString(R.string.message1);
			Common.builder(this, title, responseAge.getMessage());
		}
	}

	public void onClick(View v) {
		//String message = cities1[city.getCurrentItem()];
	}
}
