/**
 * 
 */
package com.vn.icts.wendy.view;

import android.content.Context;
import android.util.AttributeSet;

import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.R;

/**
 * @author tvuong1pc
 * 
 */
public class LoadingView extends CustomLinearLayoutView {

	/**
	 * @param context
	 */
	public LoadingView(Context context) {
		super(context);
		init(R.layout.loadding);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(R.layout.loadding);
	}

	@Override
	public void init(int res) {
		super.init(res);
		setOnClickListener(null);
	}
}
