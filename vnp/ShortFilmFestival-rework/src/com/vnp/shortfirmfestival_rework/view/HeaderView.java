package com.vnp.shortfirmfestival_rework.view;

import com.vnp.shortfirmfestival_rework.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
//com.vnp.shortfirmfestival_rework.view.HeaderView
public class HeaderView extends LinearLayout {

	public HeaderView(Context context) {
		super(context);

		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, this);
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

}
