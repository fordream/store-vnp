package com.cnc.buddyup.buddy.views;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class HeaderView extends LinearLayout {

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.header);
	}

	public HeaderView(Context context) {
		super(context);
		config(R.layout.header);
	}

	public void config(int resLayout) {
		super.config(resLayout);

	}

}