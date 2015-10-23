package com.cnc.buddyup.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.buddyup.Activity;
import com.cnc.buddyup.R;

public class ScheduleDayView extends LinearLayout {

	public ScheduleDayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.scheduledayview);
	}

	public ScheduleDayView(Context context) {
		super(context);
		config(R.layout.scheduledayview);
	}

	public void setShowHeader() {
		getLinearLayout(R.id.profile_sport_day_item_llheader).setVisibility(
				VISIBLE);
		getLinearLayout(R.id.linearLayout3).setVisibility(
				VISIBLE);
		getLinearLayout(R.id.LinearLayout15).setVisibility(
				VISIBLE);
	}

	private LinearLayout getLinearLayout(int profileSportDayItemLlheader) {
		return (LinearLayout) findViewById(profileSportDayItemLlheader);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, this);
	}

	public void config(int resLayout, String text) {
		config(resLayout);

	}

	public void setText(String text) {
		getTextView(R.id.profile_sport_day_item_etDay).setText(text);
	}

	private TextView getTextView(int profileSportDayItemEtday) {
		return (TextView) findViewById(profileSportDayItemEtday);
	}

	public void configValue(boolean... arg) {
		getCheckBox(R.id.profile_sport_day_item_llheader_cb1)
				.setChecked(arg[0]);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb2)
				.setChecked(arg[1]);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb3)
				.setChecked(arg[2]);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb4)
				.setChecked(arg[3]);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb5)
				.setChecked(arg[4]);
	}
	
	public void reloadAll(){
		configValue(false,false,false,false,false);
		changType(1, true);
		changType(2, true);
		changType(3, true);
		changType(4, true);
		changType(5, true);
	}
	public void changType(int index, boolean isOld){
		int rerource = R.id.profile_sport_day_item_llheader_cb1;
		if(index <= 5&& index >=1){
			if(index == 1)  rerource = R.id.profile_sport_day_item_llheader_cb1;
			else if(index == 2)  rerource = R.id.profile_sport_day_item_llheader_cb2;
			else if(index == 3)  rerource = R.id.profile_sport_day_item_llheader_cb3;
			else if(index == 4)  rerource = R.id.profile_sport_day_item_llheader_cb4;
			else if(index == 5)  rerource = R.id.profile_sport_day_item_llheader_cb5;
			getCheckBox(rerource).setButtonDrawable(isOld ? R.drawable.profile_checkbox1:R.drawable.profile_checkbox2);
		}
	}

	private CheckBox getCheckBox(int profileSportDayItemLlheaderCb1) {

		return (CheckBox) findViewById(profileSportDayItemLlheaderCb1);
	}

	public boolean[] getValue() {
		return new boolean[] {
				getCheckBox(R.id.profile_sport_day_item_llheader_cb1)
						.isChecked(),
				getCheckBox(R.id.profile_sport_day_item_llheader_cb2)
						.isChecked(),
				getCheckBox(R.id.profile_sport_day_item_llheader_cb3)
						.isChecked(),
				getCheckBox(R.id.profile_sport_day_item_llheader_cb4)
						.isChecked(),
				getCheckBox(R.id.profile_sport_day_item_llheader_cb5)
						.isChecked() };
	}

	public void setEnabled() {
		getCheckBox(R.id.profile_sport_day_item_llheader_cb1).setEnabled(false);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb2).setEnabled(false);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb3).setEnabled(false);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb4).setEnabled(false);
		getCheckBox(R.id.profile_sport_day_item_llheader_cb5).setEnabled(false);
	}
}