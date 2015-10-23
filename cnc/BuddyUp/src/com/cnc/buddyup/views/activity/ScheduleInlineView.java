package com.cnc.buddyup.views.activity;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class ScheduleInlineView extends LinearLayout {
	protected Handler handler;

	public ScheduleInlineView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.shecule_inline);
	}

	public ScheduleInlineView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.shecule_inline);
	}

	public void config(int resLayout) {
		super.config(resLayout);
	}
}