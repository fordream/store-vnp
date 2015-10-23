package org.com.vnp.caothubongbong.data;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Charactor {
	private boolean canMove = false;
	private Point point = new Point();
	private int delayPoint = 0;
	private int delayBitmap = 1;
	private int countDelayBitmap = 1;
	private int countDelayPoint = 0;
	private List<Bitmap> lBitmaps = new ArrayList<Bitmap>();

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getDelayPoint() {
		return delayPoint;
	}

	public void setDelayPoint(int delayPoint) {
		this.delayPoint = delayPoint;
	}

	public int getDelayBitmap() {
		return delayBitmap;
	}

	public void setDelayBitmap(int delayBitmap) {
		this.delayBitmap = delayBitmap;
	}

	public void addBitmap(Bitmap bitmap) {
		lBitmaps.add(bitmap);
	}

	public Bitmap getBitmap() {
		int index = 0;
		index = countDelayBitmap / delayBitmap;
		if (index + 1 <= lBitmaps.size()) {
			return lBitmaps.get(index);
		}

		return null;
	}

	public void updatePoint() {

	}

	public void plusCountDelayPoint() {
		countDelayPoint++;
		if (countDelayPoint > delayPoint) {
			point.y = point.y - 1;
		}
	}

	public void plusCountDelayBitmap() {
		countDelayBitmap++;
		if (countDelayBitmap / delayBitmap >= lBitmaps.size()) {
			countDelayBitmap = 0;
		}
	}

	public void onDraw(Canvas canvas, Paint paint) {
		Bitmap bitmap = getBitmap();
		Point point = getPoint();
		int x = point.x - bitmap.getWidth() / 2;
		int y = point.y - bitmap.getHeight() / 2;
		canvas.drawBitmap(bitmap, x, y, paint);
	}
}
