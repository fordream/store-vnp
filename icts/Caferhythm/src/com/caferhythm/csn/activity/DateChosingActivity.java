package com.caferhythm.csn.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.caferhythm.csn.R;

public class DateChosingActivity extends Activity {
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	// view on screen
	
	private Button backButton;
	private WheelView yearWV;
	private WheelView monthWV;
	private WheelView dayWV;
	
	private SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.date_chosing_screen);
		Calendar calendar = Calendar.getInstance();
		backButton = (Button) findViewById(R.id.bt_date_chosing_back);
		yearWV = (WheelView) findViewById(R.id.wv_year_piker);
		monthWV = (WheelView) findViewById(R.id.wv_month_picker);
		dayWV = (WheelView) findViewById(R.id.wv_day_picker);
		Date d = new Date();
		if(getIntent().getStringExtra("date")!=null){
			try {
				Log.i("test",""+getIntent().getStringExtra("date"));
				d = s1.parse(getIntent().getStringExtra("date"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//findViewById(R.id.tv_date_chosing_content)
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(yearWV, monthWV, dayWV);
			}
		};

		// month
		int curMonth = d.getMonth();
		monthWV.setViewAdapter(new DateNumericAdapter(this, 1, 12,0));
		monthWV.setCurrentItem(curMonth);
		monthWV.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		yearWV.setViewAdapter(new DateNumericAdapter(this, curYear,
				curYear + 10, 0));
		yearWV.setCurrentItem(d.getYear()-curYear+1900);
		yearWV.addChangingListener(listener);

		// day
		updateDays(yearWV, monthWV, dayWV);
		dayWV.setCurrentItem(d.getDate()-1);
		findViewById(R.id.bt_date_chosing_ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra(YEAR, yearWV.getCurrentItem() + 2012);
				i.putExtra(MONTH, monthWV.getCurrentItem() + 1);
				String t;
				if(dayWV.getCurrentItem()+1<10)
					t = "0"+(dayWV.getCurrentItem()+1);
				else
				{
					t = ""+(dayWV.getCurrentItem()+1);
				}
				i.putExtra(DAY, t);
				Log.i("test","current item year:"+(yearWV.getCurrentItem()+2012));
				// Activity finished ok, return the data
				setResult(RESULT_OK, i);
				finish();
			}
		});
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent returnIntent = new Intent();
				setResult(RESULT_CANCELED, returnIntent);        
				finish();
			}
		});
	}

	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar
				.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	private class DateNumericAdapter extends NumericWheelAdapter {
		int currentItem;
		int currentValue;

		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				//view.setTextColor(0xFF0000F0);
			}
			//view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}
}