package com.icts.shortfilmfestival.zzz.t.api;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

//com.icts.shortfilmfestival.zzz.t.api.MyGallery
public class MyGallery extends ImageView {

	public MyGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAdjustViewBounds(true);
		setScaleType(ScaleType.FIT_CENTER);
	}
	
	public MyGallery(Context context) {
		super(context);
		setAdjustViewBounds(true);
		setScaleType(ScaleType.FIT_CENTER);
	}


}