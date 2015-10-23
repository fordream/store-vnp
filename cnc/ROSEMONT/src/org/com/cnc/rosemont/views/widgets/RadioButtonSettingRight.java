package org.com.cnc.rosemont.views.widgets;

import org.com.cnc.rosemont.R.drawable;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

public class RadioButtonSettingRight extends RadioButton {

	public RadioButtonSettingRight(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		config();
	}

	public RadioButtonSettingRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public RadioButtonSettingRight(Context context) {
		super(context);
		config();
	}

	public void setChecked(boolean checked) {
		super.setChecked(checked);
		if (checked) {
			setTextColor(Color.WHITE);
		} else {
			setTextColor(Color.GRAY);
		}
		
		if (isEnabled()) {
			setTextColor(isChecked() ? Color.WHITE : Color.GRAY);
		} else {
			setTextColor(Color.GRAY);
		}
	}

	private void config() {
		setBackgroundResource(drawable.checkbox_setting_right);
		setButtonDrawable(drawable.tranfer);
		setGravity(Gravity.CENTER);
	}

	public void setEnabled(boolean enabled) {
		setBackgroundResource(enabled ? drawable.checkbox_setting_right
				: drawable.right_1);
		if (enabled) {
			setTextColor(isChecked() ? Color.WHITE : Color.GRAY);
		} else {
		//	setTextColor(Color.GRAY);
			setTextColor(isChecked() ? Color.GRAY : Color.GRAY);
		}
		super.setEnabled(enabled);
	}

}
