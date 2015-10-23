/**
 * 
 */
package com.ict.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author tvuong1pc
 * 
 */
public class CustomLinearLayoutView extends LinearLayout {

	/**
	 * @param context
	 */
	public CustomLinearLayoutView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomLinearLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(int res) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(res, this);
	}

	/**
	 * convert view from resource
	 * 
	 * @param res
	 * @return
	 */
	public <T extends View> T getView(int res) {
		@SuppressWarnings("unchecked")
		T view = (T) findViewById(res);
		return view;
	}

}
