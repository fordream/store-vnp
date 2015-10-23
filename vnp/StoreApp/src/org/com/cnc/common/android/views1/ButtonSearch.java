package org.com.cnc.common.android.views1;

import org.com.vnp.storeapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonSearch extends Button {

	public ButtonSearch(Context context) {
		super(context);
		init();
	}

	public ButtonSearch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ButtonSearch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		setBackgroundResource(R.drawable.search_xml);
	}

}
