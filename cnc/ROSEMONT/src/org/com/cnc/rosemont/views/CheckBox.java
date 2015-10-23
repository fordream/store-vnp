package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R.drawable;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;

public class CheckBox extends android.widget.CheckBox implements IView {

	public CheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		config();
	}

	public CheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public CheckBox(Context context) {
		super(context);
		config();
	}

	private void config() {
		setButtonDrawable(drawable.tranfer);
		setBackgroundResource(drawable.checkbox_adverser_event_report1);
		setWidth(50);
		setHeight(50);

	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}