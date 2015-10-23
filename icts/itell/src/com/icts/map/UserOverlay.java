package com.icts.map;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public abstract class UserOverlay<Item extends OverlayItem> extends ItemizedOverlay<Item>  {

	public UserOverlay(Drawable defaultMarker) {
		super(defaultMarker);
	}
	
	@Override
	protected boolean onTap(int index) {
		return super.onTap(index);
	}


}
