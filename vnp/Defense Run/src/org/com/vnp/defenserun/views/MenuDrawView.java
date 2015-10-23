package org.com.vnp.defenserun.views;

import java.util.Random;

import org.com.vnp.defenserun.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MenuDrawView extends LinearLayout {
	private Paint paint = new Paint();
	private Point p1 = new Point(-1, -1);
	private Point p2 = new Point(-1, -1);
	private Point p3 = new Point(-1, -1);
	private boolean isFirst = true;

	public MenuDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
	}

	public MenuDrawView(Context context) {
		super(context);
		setWillNotDraw(false);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Resources res = getResources();
		Bitmap b1 = BitmapFactory.decodeResource(res, R.drawable.ca1);
		Bitmap b2 = BitmapFactory.decodeResource(res, R.drawable.ca2);
		Bitmap b3 = BitmapFactory.decodeResource(res, R.drawable.ca3);
		Random random = new Random();
		int h = getHeight();
		int w = getWidth();
		if (p1 != null && p2 != null && p3 != null && b1 != null && b2 != null&& b3 != null) {
			if (isFirst) {
				p1.x = w + b3.getWidth();
				p1.y = random.nextInt(h - b3.getHeight() * 2);

				p2.x = w + b3.getWidth();
				p2.y = random.nextInt(h - b3.getHeight() * 2);

				p3.x = w + b3.getWidth();
				p3.y = random.nextInt(h - b3.getHeight() * 2);
				isFirst = false;
			}

			if (p1.x < -b1.getWidth() && random.nextInt(100) <= 1) {
				p1.x = w + b1.getWidth();
				p1.y = random.nextInt(h - b1.getHeight() * 2);
			}

			if (p2.x < -b2.getWidth() && random.nextInt(100) <= 2) {
				p2.x = w + b2.getWidth();
				p2.y = random.nextInt(h - b2.getHeight() * 2);
			}

			if (p3.x < -b3.getWidth() && random.nextInt(100) <= 3) {
				p3.x = w + b3.getWidth();
				p3.y = random.nextInt(h - b3.getHeight() * 2);
			}

			canvas.drawBitmap(b1, p1.x, p1.y, paint);
			canvas.drawBitmap(b2, p2.x, p2.y, paint);
			canvas.drawBitmap(b3, p3.x, p3.y, paint);

			p1.x = p1.x - 1;
			p2.x = p2.x - 1;
			p3.x = p3.x - 1;
		}

		invalidate();
	}
}