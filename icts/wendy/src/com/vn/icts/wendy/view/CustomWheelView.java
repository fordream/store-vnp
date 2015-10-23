/**
 * 
 */
package com.vn.icts.wendy.view;

import kankan.wheel.widget.WheelView;
import android.content.Context;
import android.util.AttributeSet;

import com.vn.icts.wendy.R;

/**
 * @author tvuong1pc
 * 
 */
// com.vn.icts.wendy.view.CustomWheelView
public class CustomWheelView extends WheelView {

	/**
	 * @param context
	 */
	public CustomWheelView(Context context) {
		super(context);
		setCenterDrawable(R.drawable.wheel_val);
		setBackgroundResource(R.drawable.wheel_bg);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomWheelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCenterDrawable(R.drawable.wheel_val);
		setBackgroundResource(R.drawable.wheel_bg);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomWheelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCenterDrawable(R.drawable.wheel_val);
		setBackgroundResource(R.drawable.wheel_bg);
	}
}