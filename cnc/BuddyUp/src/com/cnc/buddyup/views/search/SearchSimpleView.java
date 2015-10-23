package com.cnc.buddyup.views.search;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.search.SearchRequestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class SearchSimpleView extends LinearLayout implements OnClickListener {
	protected Handler handler;
	public class Filed{
		public TextView fTVSport;
		public TextView fTVDay;
		public TextView fTVTime;
		public RadioButton fCBIndividuals;
		
		public Button fBtnSearch;
		public Button fBtnAdvancedSearch;
	}
	
	private Filed filed = new Filed();
	
	public Filed getFiled() {
		return filed;
	}

	public void setFiled(Filed filed) {
		this.filed = filed;
	}

	public SearchSimpleView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.searchsimple);
	}

	public SearchSimpleView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.searchsimple);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		filed.fBtnAdvancedSearch = getButton(R.id.search_simple_btnadvancedsearch);
		filed.fBtnSearch = getButton(R.id.search_simple_btnsearch);
		filed.fCBIndividuals = getRadioButton(R.id.search_simple_cBIndividuals);
		filed.fTVDay = getTextView(R.id.search_simple_spDay);
		filed.fTVSport = getTextView(R.id.search_simple_spSportActivity);
		filed.fTVTime = getTextView(R.id.search_simple_spTime);
		
		getButton(R.id.search_simple_btnsearch).setOnClickListener(this);
		getButton(R.id.search_simple_btnadvancedsearch)
				.setOnClickListener(this);
	}

	public void hiddenAllKey() {
	}

	public void onClick(View arg0) {
		if (R.id.search_simple_btnsearch == arg0.getId()) {
			handler.sendMessage(createMessage(Common.MESSAGE_WHAT_0));
		} else if (R.id.search_simple_btnadvancedsearch == arg0.getId()) {
			handler.sendMessage(createMessage(Common.MESSAGE_WHAT_1));
		}
	}

	private Message createMessage(int what) {
		SearchRequestParcelable parcelable = new SearchRequestParcelable();
		parcelable
				.setSport_activity(getText(getTextView(R.id.search_simple_spSportActivity)));
		Message message = Common.createMessage(what);
		parcelable.setDay(getText(getTextView(R.id.search_simple_spDay)));
		parcelable.setTime(getText(getTextView(R.id.search_simple_spTime)));
		parcelable.setIndividuals(getRadioButton(
				R.id.search_simple_cBIndividuals).isChecked());
		Bundle daBundle = new Bundle();
		daBundle.putParcelable(Common.ARG0, parcelable);
		message.setData(daBundle);
		return message;

	}
}