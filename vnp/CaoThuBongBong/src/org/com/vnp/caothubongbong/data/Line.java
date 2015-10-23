package org.com.vnp.caothubongbong.data;

import org.com.vnp.caothubongbong.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Line extends Charactor {
	public boolean isLife = true;

	public boolean isLife() {
		return isLife;
	}

	public void setLife(boolean isLife) {
		this.isLife = isLife;
	}

	public Line(Context context) {
		Resources rs = context.getResources();
		addBitmap(BitmapFactory.decodeResource(rs, drawable.debanten));
		setDelayBitmap(10);
	}
}