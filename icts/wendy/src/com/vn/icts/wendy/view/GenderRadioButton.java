/**
 * 
 */
package com.vn.icts.wendy.view;

import com.vn.icts.wendy.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * @author tvuong1pc
 *
 */
public class GenderRadioButton extends RadioButton {

	/**
	 * @param context
	 */
	public GenderRadioButton(Context context) {
		super(context);
		
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public GenderRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public GenderRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		setButtonDrawable(R.drawable.transfer);
		Drawable right = getResources().getDrawable(R.drawable.tvuong_checkbox_male_female);
		setCompoundDrawables(null, null, right, null);
		setTextColor(Color.WHITE);
		setTextSize(getResources().getDimension(R.dimen.sp15));
	}

}
