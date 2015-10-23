package org.com.vnp.caothubongbong.data;

import org.com.vnp.caothubongbong.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Balloon extends Charactor {
	public boolean isLife = true;

	public boolean isLife() {
		return isLife;
	}

	public void setLife(boolean isLife) {
		this.isLife = isLife;
	}

	public Balloon(Context context, int type) {
		Resources rs = context.getResources();
		int res = drawable.balloons1;
		if (type == 1) {
			res = drawable.balloons1;
		} else {
			res = drawable.balloons2;
		}

		addBitmap(BitmapFactory.decodeResource(rs, res));

		setDelayBitmap(10);
	}

	public void onDraw(Canvas canvas, Paint paint) {
		super.onDraw(canvas, paint);
	}
}