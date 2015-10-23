package org.com.vnp.tinhca.views;

import org.com.cnc.common.android.layout.CommonLinearLayout;
import org.com.vnp.tinhca.R.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class SplashView extends CommonLinearLayout {

	private int count = 0;

	private static final int TIME = 30;

	public SplashView(Context context) {
		super(context);
		config();
	}

	public SplashView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	private void config() {
		setWillNotDraw(false);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		count = (count >= TIME) ? 0 : (count + 1);

		int res = (count > TIME / 2) ? drawable.splash2 : drawable.splash1;
		setBackgroundResource(res);

		// reDraw
		invalidate();
	}
}