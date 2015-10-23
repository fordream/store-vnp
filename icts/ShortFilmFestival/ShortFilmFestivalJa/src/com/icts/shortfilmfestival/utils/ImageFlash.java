package com.icts.shortfilmfestival.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class ImageFlash extends View{
	private float x = 0;
	private float y = 0;
	private int width;
	private final Bitmap mBackground, mGlow;
	
	/**
	 * Move Glow on Background
	 * @param context
	 * @param pBackground
	 * @param pGlow
	 */
	
	public ImageFlash(Context context, int pBackground, int pGlow) {
		super(context);
		mBackground = BitmapFactory.decodeResource(getResources(),
				pBackground);
		mGlow = BitmapFactory.decodeResource(getResources(), pGlow);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (width == 0) {
			width = mBackground.getWidth();
		}
		x += 5;
		if (x > (width + mGlow.getWidth())) {
			x = - mGlow.getWidth();
		}

		canvas.drawBitmap(mBackground, 0, 0, null);
		canvas.drawBitmap(mGlow, x, y, null);
		canvas.translate(0f, 1.0f);
		invalidate();
	}
}
