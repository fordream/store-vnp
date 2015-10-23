package com.cnc.buddyup.common.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.buddyup.Activity;
import com.cnc.buddyup.R;

public class LoadingView extends LinearLayout {
	private TextView textView;

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.loading);
	}

	public LoadingView(Context context) {
		super(context);
		config(R.layout.loading);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Activity.LAYOUT_INFLATER_SERVICE);
		li.inflate(resLayout, this);
		textView = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.linearlayout).setOnClickListener(null);
	}

	public void setTitle(String title) {
		if (title != null) {
			textView.setText(title);
		}
	}
}