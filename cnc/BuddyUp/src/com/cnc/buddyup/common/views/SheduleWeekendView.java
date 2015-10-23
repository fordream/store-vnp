package com.cnc.buddyup.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cnc.buddyup.Activity;
import com.cnc.buddyup.R;

public class SheduleWeekendView extends LinearLayout {
	private ScheduleDayView days[] = new ScheduleDayView[7];

	public SheduleWeekendView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.sheduleweekendview);
	}

	public SheduleWeekendView(Context context) {
		super(context);
		config(R.layout.sheduleweekendview);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, this);
		days[0] = getProfileSportDayItemView(R.id.day1);
		days[0].setShowHeader();
		days[1] = getProfileSportDayItemView(R.id.day2);
		days[2] = getProfileSportDayItemView(R.id.day3);
		days[3] = getProfileSportDayItemView(R.id.day4);
		days[4] = getProfileSportDayItemView(R.id.day5);
		days[5] = getProfileSportDayItemView(R.id.day6);
		days[6] = getProfileSportDayItemView(R.id.day7);
		
		days[0].setEnabled();
		days[1].setEnabled();
		days[2].setEnabled();
		days[3].setEnabled();
		days[4].setEnabled();
		days[5].setEnabled();
		days[6].setEnabled();
		days[0].setText("Sunday");
		days[1].setText("Monday");
		days[2].setText("Tuesday");
		days[3].setText("Wednesday");
		days[4].setText("Thurday");
		days[5].setText("Friday");
		days[6].setText("Satuday");
		days[0].configValue(true,true,false,false,true);
		days[0].changType(1, false);
	}

	private ScheduleDayView getProfileSportDayItemView(int res) {
		return (ScheduleDayView) findViewById(res);
	}
}
