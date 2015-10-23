package org.com.vnp.defenserun.data;

import java.util.Random;

import android.graphics.Point;

public class Shoot {
	private int radian = 0;
	private float speed = 5;
	private Point curentPoint = new Point();
	private Point dich = new Point();
	private int power = 100;
	private boolean isBogiap = false;
	
	public boolean isBogiap() {
		return isBogiap;
	}
	public void setBogiap(boolean isBogiap) {
		this.isBogiap = isBogiap;
	}
	public int getPower() {
		boolean havemanalot = (new Random().nextInt(100) < 20);
		return power * (havemanalot ? 2 : 1);
	}
	public boolean haveNotAmor(){
		return new Random().nextInt(100) < 10;
	}
	public void setPower(int power) {
		this.power = power;
	}

	public int getRadian() {
		return radian;
	}

	public void setRadian(int radian) {
		this.radian = radian;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Point getCurentPoint() {
		return curentPoint;
	}

	public void setCurentPoint(Point curentPoint) {
		this.curentPoint = curentPoint;
	}

	public Point getDich() {
		return dich;
	}

	public void setDich(Point dich) {
		this.dich = dich;
	}

	public void update() {
		float dx = -BitmapCommon.POINT.x + dich.x;
		float dy = -BitmapCommon.POINT.y + dich.y;
		if (dy == 0) {
			curentPoint.x = curentPoint.x - (int) speed;
		} else if (dx == 0) {
			curentPoint.y = curentPoint.y + (dx > 0 ? -1 : +1) * (int) speed;
		} else {
			float speed = (float) (this.speed * Math.abs(dy) / Math.sqrt(dx
					* dx + dy * dy));
			if (speed > 1) {
				curentPoint.y = curentPoint.y - (int) speed;
				curentPoint.x = BitmapCommon.POINT.x
						+ (int) ((curentPoint.y - BitmapCommon.POINT.y) * dx / dy);
			} else {
				speed = (float) (this.speed * Math.abs(dx) / Math.sqrt(dx * dx
						+ dy * dy));
				int chieu = 1;
				if (dich.x >= BitmapCommon.POINT.x) {
					chieu = -1;
				}
				curentPoint.x = curentPoint.x - (int) (chieu * speed);
				curentPoint.y = BitmapCommon.POINT.y
						+ (int) ((curentPoint.x - BitmapCommon.POINT.x) * dy / dx);
			}
		}
	}

	public boolean isDie() {
		if (curentPoint.y < 0 || curentPoint.x < 0) {
			return true;
		}

		if (curentPoint.x > BitmapCommon.POINT.x * 2) {
			return true;
		}

		return false;
	}
}
