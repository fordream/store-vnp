package org.com.vnp.caothubongbong.data;

import android.graphics.Point;

public class Score {
	private int score = 0;
	private Point point = new Point();
	private int life = 10;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public boolean isDie() {
		life--;
		if (life < 0) {
			life = 0;
		}

		point.y = point.y - 1;

		return life == 0;
	}

}