package com.vnp.shortfirmfestival_rework.view;

import com.vnp.shortfirmfestival_rework.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

//com.vnp.shortfirmfestival_rework.view.HeaderView
public class FooterView extends LinearLayout {
	private View list_footer;

	public FooterView(Context context) {
		super(context);

		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_loader, this);
		list_footer = findViewById(R.id.list_footer);
	}

	public FooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

}
