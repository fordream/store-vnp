package com.cnc.buddyup.views.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class ScheduleBackpackingView extends LinearLayout {
	protected Handler handler;
	public class Filed{
		public TextView fFlexibility;
	}
	
	private Filed filed = new Filed();
	
	
	public Filed getFiled() {
		return filed;
	}

	public void setFiled(Filed filed) {
		this.filed = filed;
	}

	public ScheduleBackpackingView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.shecule_backpacking);
	}

	public ScheduleBackpackingView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.shecule_backpacking);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		filed.fFlexibility = getTextView(R.id.TextView1);
	}
}