package org.com.cnc.rosemont.views.widgets;

import org.com.cnc.rosemont.R.drawable;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

public class RadioButtonSettingLeft extends RadioButton {

	public RadioButtonSettingLeft(Context context, AttributeSet attrs,
			int defStyle) {

		super(context, attrs, defStyle);
		config();
	}

	public RadioButtonSettingLeft(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public RadioButtonSettingLeft(Context context) {
		super(context);
		config();
	}

	private void config() {
		setBackgroundResource(drawable.checkbox_setting_left);
		setButtonDrawable(drawable.tranfer);
		setGravity(Gravity.CENTER);
		
	}

	public void setChecked(boolean checked) {
		super.setChecked(checked);
		setTextColor(checked ? Color.WHITE : Color.GRAY);
		
		if (isEnabled()) {
			setTextColor(isChecked() ? Color.WHITE : Color.GRAY);
		} else {
			setTextColor(Color.GRAY);
		}
		
	}

	public void setEnabled(boolean enabled) {
		setBackgroundResource(enabled ? drawable.checkbox_setting_left
				: drawable.left_1);

		if (enabled) {
			setTextColor(isChecked() ? Color.WHITE : Color.GRAY);
		} else {
			setTextColor(Color.GRAY);
		}

		super.setEnabled(enabled);
	}

}
