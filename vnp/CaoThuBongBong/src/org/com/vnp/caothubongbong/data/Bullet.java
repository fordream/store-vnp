package org.com.vnp.caothubongbong.data;

import org.com.vnp.caothubongbong.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet extends Charactor {
	private int timeLife = 20;

	public void minusTime() {
		timeLife--;
	}

	public boolean isDie() {
		return timeLife <= 0;
	}

	public Bullet(Context context, int x, int y) {
		Resources rs = context.getResources();
		addBitmap(BitmapFactory.decodeResource(rs, drawable.bom7));
		addBitmap(BitmapFactory.decodeResource(rs, drawable.bom8));
		getPoint().x = x;
		getPoint().y = y;
		setDelayBitmap(timeLife / 2);
	}

	public void onDraw(Canvas canvas, Paint paint) {
		minusTime();
		plusCountDelayBitmap();
		super.onDraw(canvas, paint);
	}
}
