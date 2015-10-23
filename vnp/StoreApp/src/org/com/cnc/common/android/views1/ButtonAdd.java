package org.com.cnc.common.android.views1;

import org.com.vnp.storeapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonAdd extends Button {

	public ButtonAdd(Context context) {
		super(context);
		config();
	}

	public ButtonAdd(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public ButtonAdd(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		config();
	}

	private void config() {
		setBackgroundResource(R.drawable.add_xml);
	}

}
