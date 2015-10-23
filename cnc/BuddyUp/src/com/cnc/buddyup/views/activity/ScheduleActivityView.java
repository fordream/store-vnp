package com.cnc.buddyup.views.activity;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;
import com.cnc.buddyup.views.common.CommonListView;

public class ScheduleActivityView extends LinearLayout {
	protected Handler handler;
	private CommonListView commonList;

	public ScheduleActivityView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.activity_main);
	}

	public ScheduleActivityView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.activity_main);
	}

	public void setListparcelable(List<CommonItemResquestParcelable> list) {
		commonList.setListParcelable(list);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		commonList = new CommonListView(getContext(), handler);
		getLinearLayout(R.id.activity_main_llContent).addView(commonList);
		// add Data temp
		setListparcelable(Common.createListparcel3());
	}
}