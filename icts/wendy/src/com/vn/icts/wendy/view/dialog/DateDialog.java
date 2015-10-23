package com.vn.icts.wendy.view.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vn.icts.wendy.R;
import com.vn.icts.wendy.model.Setting;

public class DateDialog extends Dialog {
	private WheelView day, month, year;
	private int curYear;
	private int cureantDate;

	public DateDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		setCancelable(false);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.date_layout);

		month = (WheelView) findViewById(R.id.month);
		year = (WheelView) findViewById(R.id.year);
		day = (WheelView) findViewById(R.id.day);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};

		// month
		Calendar calendar = Calendar.getInstance();
		Setting setting = new Setting();
		String date = setting.getDateOfBirth();
		curYear = calendar.get(Calendar.YEAR);
		int curMonth = calendar.get(Calendar.MONTH);
		cureantDate = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		if (!(TextUtils.isEmpty(date) || date == null)) {
			try {

				StringTokenizer stringTokenizer = new StringTokenizer(date, "-");
				cureantDate = Integer.parseInt(stringTokenizer.nextToken()) - 1;
				curMonth = Integer.parseInt(stringTokenizer.nextToken()) - 1;
				curYear = Integer.parseInt(stringTokenizer.nextToken());
			} catch (Exception e) {
			}
		}
		String months[] = new String[] { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		month.setViewAdapter(new DateArrayAdapter(getContext(), months,
				curMonth));

		month.addChangingListener(listener);

		// year

		year.setViewAdapter(new DateNumericAdapter(getContext(), curYear - 100,
				curYear + 100, 100));
		year.setCurrentItem(100);
		month.setCurrentItem(curMonth);

		year.addChangingListener(listener);
		// day
		updateDays(year, month, day);

		day.setCurrentItem(cureantDate);

		View.OnClickListener clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		};

		findViewById(R.id.date_cancel).setOnClickListener(clickListener);
		findViewById(R.id.date_main).setOnClickListener(clickListener);

		findViewById(R.id.date_ok).setOnClickListener(clickListenerOk);
	}

	/**
	 * Updates day wheel. Sets max days according to selected month and year
	 */
	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(getContext(), 1, maxDays,
				cureantDate));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}

			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	View.OnClickListener clickListenerOk;

	public void setOnClickOk(View.OnClickListener clickListener) {
		clickListenerOk = clickListener;

	}

	public int getYear() {
		return year.getCurrentItem() + curYear - 100;
	}

	public int getMonth() {
		return month.getCurrentItem() + 1;
	}

	public int getDay() {
		return day.getCurrentItem() + 1;
	}
}