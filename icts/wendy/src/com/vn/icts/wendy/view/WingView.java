package com.vn.icts.wendy.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;

import com.ict.library.service.LocationParacelable;
import com.ict.library.view.CustomLinearLayoutView;
import com.vn.icts.wendy.UiApplication;
import com.vn.icts.wendy.model.Wing;

public class WingView extends CustomLinearLayoutView {
	private List<Wing> lWings = new ArrayList<Wing>();
	private int x, y, z;

	public WingView(Context context) {
		super(context);
		init();
	}

	public WingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		Wing.init(getContext());
		setWillNotDraw(false);

		lWings.add(new Wing("21.0072682","105.8031223"));
		lWings.add(new Wing("21.0072692","105.8031223"));
		lWings.add(new Wing("21.0072692","105.8031225"));
		lWings.add(new Wing("21.0072642","105.8031227"));
		lWings.add(new Wing("21.0072662","105.8031229"));
		lWings.add(new Wing("21.0072672","105.8031233"));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		LocationParacelable location = ((UiApplication) getContext()
				.getApplicationContext()).getLocation();
		for (Wing wing : lWings) {
			wing.update(x, y, z);
			wing.onDraw(canvas);
			wing.update(location);
		}
		invalidate();
	}

	public void updateWing(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		
	}

}
