package org.com.cnc.rosemont.views.widgets;

import org.com.cnc.rosemont.activity.commom.CommonApp;

import android.content.Context;
import android.util.AttributeSet;

public class TextView extends android.widget.TextView {

	public TextView(Context context) {
		super(context);
		config();
	}

	public TextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public TextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		config();
	}

	private void config() {
		setTextSize(CommonApp.SIZE_OF_TEXT);
	}
}