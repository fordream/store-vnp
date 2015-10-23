package org.com.vnp.defenserun.data;

import android.graphics.Point;

public class Quaivat {
	private int type = 0;
	private int hp;
	private int sd;
	private int amor;
	private int money;
	private Point point = new Point();
	private static final int DELAY = 5;
	private int count = 0;
	private int speed = 3;

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getSd() {
		return sd;
	}

	public void setSd(int sd) {
		this.sd = sd;
	}

	public int getAmor() {
		return amor;
	}

	public void setAmor(int amor) {
		this.amor = amor;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void updatePosition() {
		count++;
		if (count >= DELAY) {
			point.x = point.x + speed;
			count = 0;
		}

		if (point.x > BitmapCommon.POINT.x * 2 + 20) {
			point.x = -20;
		}
	}

	public int updateHP(int power, boolean haveNotAmor) {
		int hp2 = power - (haveNotAmor ? 0 : amor);
		if (hp2 > 0) {
			if (sd > 0) {
				sd = sd - hp2;
				if (sd < 0) {
					hp += sd / 2;
				}
			} else {
				hp = hp - hp2;
			}

			return hp2;
		}
		return 0;
	}
}
