package com.cnc.buddyup.views.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.search.SearchRequestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class SearchAdvancedView extends LinearLayout implements OnClickListener {
	protected Handler handler;

	public class Filed {
		public TextView fTVSport;
		public TextView fTVDay;
		public TextView fTVTime;
		public TextView fTVPhilosophy;
		public TextView fTVSkillLevel;
		
		public EditText fETMaximumDistin;
		
		//public RadioButton fCBIndividuals;
		
		public Button fBtnSearch;
		
	}

	private Filed filed = new Filed();

	public Filed getFiled() {
		return filed;
	}

	public void setFiled(Filed filed) {
		this.filed = filed;
	}

	public SearchAdvancedView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.searchadvanced);
	}

	public SearchAdvancedView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.searchadvanced);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		filed.fBtnSearch = getButton(R.id.search_advanced_btnsearch);
		filed.fTVDay = getTextView(R.id.search_advanced_spDay);
		filed.fTVPhilosophy = getTextView(R.id.search_advanced_spPhilosophy);
		filed.fTVSkillLevel = getTextView(R.id.search_advanced_spSkillLevel);
		filed.fTVSport = getTextView(R.id.search_advanced_spSportActivity);
		filed.fTVTime = getTextView(R.id.search_advanced_spTime);
		getButton(R.id.search_advanced_btnsearch).setOnClickListener(this);
	}

	public void hiddenAllKey() {
	}

	public void onClick(View arg0) {
		if (R.id.search_advanced_btnsearch == arg0.getId()) {
			handler.sendMessage(createMessage(Common.MESSAGE_WHAT_2));
		}
	}

	private Message createMessage(int what) {
		SearchRequestParcelable parcelable = new SearchRequestParcelable();
		parcelable
				.setSport_activity(getText(getTextView(R.id.search_advanced_spSportActivity)));

		parcelable.setDay(getText(getTextView(R.id.search_advanced_spDay)));
		parcelable.setTime(getText(getTextView(R.id.search_advanced_spTime)));
		parcelable
				.setPhylosophy(getText(getTextView(R.id.search_advanced_spPhilosophy)));
		parcelable
				.setSkillLevel(getText(getTextView(R.id.search_advanced_spSkillLevel)));
		parcelable
				.setMaximumDistance(getText(getEditText(R.id.search_advanced_etMaximumDistance)));
		parcelable.setSkipDistance(getCheckBox(
				R.id.search_advanced_cBSkipDistance).isChecked());
		Bundle daBundle = new Bundle();
		daBundle.putParcelable(Common.ARG0, parcelable);
		Message message = Common.createMessage(what);
		message.setData(daBundle);
		return message;

	}
}