package com.vn.icts.wendy.view;

import com.vn.icts.wendy.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

//com.vn.icts.wendy.view.CustomImageButton
public class CustomImageButton extends ImageButton {
	private boolean isChecked = true;

	public CustomImageButton(Context context) {
		super(context);
		init();
	}

	public CustomImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomImageButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		checkedChange();
	}

	public void checkedChange() {
		isChecked = !isChecked;
		setBackgroundResource(isChecked ? R.drawable._button_click_selected
				: R.drawable._button_click);
	}

	public boolean isChecked() {
		return isChecked;
	}
}