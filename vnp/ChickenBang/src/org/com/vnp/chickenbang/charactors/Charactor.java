package org.com.vnp.chickenbang.charactors;

import org.com.vnp.chickenbang.p.Point;

import android.graphics.Bitmap;

public class Charactor {
	private double speed = 5;
	private int hp;
	private Bitmap bitmap;
	Point point = new Point();

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	
}