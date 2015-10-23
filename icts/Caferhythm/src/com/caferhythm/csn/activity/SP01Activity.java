package com.caferhythm.csn.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caferhythm.csn.R;
import com.caferhythm.csn.calender.CalenderListViewActivity;
import com.caferhythm.csn.configure.API;
import com.caferhythm.csn.connect.Connection;
import com.caferhythm.csn.data.SP04Entity;
import com.caferhythm.csn.data.TaionRow;
import com.caferhythm.csn.fragment.AdsFragment;
import com.caferhythm.csn.json.JsonPaser;
import com.caferhythm.csn.view.TemperatureGraphView;
import com.csn.caferhythm.adapter.TaionRowAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class SP01Activity extends BaseActivityWithHeadtab {
	// data
	private ArrayList<TaionRow> temperatureList;
	private TaionRowAdapter adapter;
	private SP04Entity period;

	private String month = "";
	private Date date;
	private Calendar cal;

	private SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM");
	private SimpleDateFormat s2;

	// view on screen
	private LinearLayout taionArea;
	private LinearLayout viewGraph;
	private TextView monthTV;
	private Button leftBT;
	private Button rightBT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentTab(getString(R.string.sp01title), R.layout.sp01_screen);
		super.onCreate(savedInstanceState);
		s2 = new SimpleDateFormat("yyyy"
				+ getResources().getString(R.string.year) + "MM"
				+ getResources().getString(R.string.month));
		taionArea = (LinearLayout) findViewById(R.id.lo_sp01_add);
		viewGraph = (LinearLayout) findViewById(R.id.lo_sp01_graph);
		monthTV = (TextView) findViewById(R.id.tv_sp01_month);
		leftBT = (Button) findViewById(R.id.bt_sp01_left);
		rightBT = (Button) findViewById(R.id.bt_sp01_right);
		temperatureList = new ArrayList<TaionRow>();

		date = new Date();
		month = s1.format(date);
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		leftBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cal.add(Calendar.MONTH, -1);
				date = cal.getTime();
				month = s1.format(date);
				monthTV.setText(s2.format(cal.getTime()));
				getDataTemperature(month);
			}
		});
		rightBT.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cal.add(Calendar.MONTH, 1);
				date = cal.getTime();
				month = s1.format(date);
				monthTV.setText(s2.format(date));
				getDataTemperature(month);
			}
		});
		findViewById(R.id.bt_head_item_menu1).setVisibility(View.VISIBLE);
		findViewById(R.id.bt_head_item_menu2).setVisibility(View.VISIBLE);
		findViewById(R.id.bt_head_item_menu1).setBackgroundResource(R.drawable.sidemenu0);
		findViewById(R.id.bt_head_item_menu1).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								MyPageActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
		findViewById(R.id.bt_head_item_menu2).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								CalenderListViewActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		AdsFragment adsFragment = new AdsFragment("mypage");
		ft.add(R.id.adsarea, adsFragment, "Ads");
		ft.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		monthTV.setText(s2.format(date));
		getDataTemperature(month);
		super.onResume();
	}

	private void getDataTemperature(String monthInput) {
		RequestParams params = new RequestParams();
		params.put("month", monthInput);
		params.put("token", FlashScreenActivity.token);
		Connection.get(API.API_S005, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Log.i("test", "return:" + arg0);
				temperatureList.clear();
				temperatureList = JsonPaser.getTemperature(arg0);
				if(temperatureList.size()<1)
					return;
				adapter = new TaionRowAdapter(getApplicationContext(),
						R.layout.taion_row, temperatureList);
				Log.i("test", "size list:" + temperatureList.size());
				boolean isDrawHeart = false;
				isDrawHeart = (s1.format(new Date())).equals(month);
				TemperatureGraphView g = new TemperatureGraphView(
						getApplicationContext(), getWindowManager()
								.getDefaultDisplay().getWidth(),
						temperatureList,isDrawHeart);
				// LayoutParams p = new Lay
				viewGraph.removeAllViews();
				viewGraph.addView(g);
				taionArea.removeAllViews();
				for (int i = 0; i < temperatureList.size(); i++) {
					taionArea.addView(adapter.getView(i, null, null));
				}
			}

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Log.i("test", "return failure:" + arg0);
			}
		});
	}

}
