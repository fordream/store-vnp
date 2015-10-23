package org.com.vnp.chickenbang.views;

import java.util.Random;

import org.com.vnp.chickenbang.R;
import org.com.vnp.chickenbang.p.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapStore {
	public static final int TYPE_LEFT = 0;
	public static final int TYPE_RIGHT = 1;
	public static final int TYPE_BOTTOM_LEFT = 2;
	public static final int TYPE_BOTTOM_RIGHT = 3;
	private Bitmap bitmap;
	private Point point = new Point();
	private int dx = 0, dy = 0;
	public static final int G = 1;
	private int delay = 0;
	private int timecurent = 0;
	private int type;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getTimecurent() {
		return timecurent;
	}

	public void setTimecurent(int timecurent) {
		this.timecurent = timecurent;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void create(Context context, int W, int H) {
		Random random = new Random();
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.oclock);
		type = random.nextInt(2);
		if (type == TYPE_LEFT) {
			point.x = -bitmap.getWidth() / 2;
			point.y = random.nextInt(H / 2) + H / 2;
			dx = W/2/10 + 0;
			
		} else if (type == TYPE_RIGHT) {
			point.x = W + bitmap.getWidth() / 2;
			point.y = random.nextInt(H / 2) + H / 2;
			dx = -(W/2/10 );
//			dy = -H/2/10;
		}
		dy = -(H/2/10 + random.nextInt(70));
		delay = 3000;
		timecurent = 0;
	}

	public void update() {
		if (timecurent >= delay) {
			point.x = point.x + dx;
			dy = dy + G;
			point.y = point.y + dy;
			timecurent = 0;
		} else {
			timecurent++;
		}
	}

	public boolean compare(Point point2) {
		if (point.x < point2.x && point2.x < point.x + bitmap.getWidth()) {
			if (point.y < point2.y && point2.y < point.y + bitmap.getHeight()) {
				return true;
			}
		}
		return false;
	}

	public boolean check(int W, int H) {
		if (point.y > H) {
			return true;
		}
		if (point.x < -bitmap.getWidth() / 2) {
			return true;
		}

		if (point.x > W + bitmap.getWidth() / 2) {
			return true;
		}
		return false;
	}
}
