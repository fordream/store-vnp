package org.com.vnp.thioi.o;

import java.util.Random;

import org.com.vnp.thioi.view.PlayView;

import com.ict.library.common.CommonResize;

import android.content.Context;
import android.graphics.Point;

public class Item {
	protected Context context;
	private int score = 0;
	private int type;
	private Point position = new Point();
	private int speed = 4;

	public Item(Context context) {
		this.context = context;
		reset();
		speed = CommonResize.getSizeByScreen960(context, 4);
	}

	public int getScore() {
		if (type == 0 || type == 3) {
			return 10;
		} else if (type == 1) {
			return -5;
		} else if (type == 2) {
			return +7;
		}
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public void update() {
		position.y = speed + position.y;
	}

	public void reset() {
		Random random = new Random();
		score = random.nextInt(10) + 5;
		if (random.nextInt(100) <= 3) {
			score += 17;
		}

		type = random.nextInt(PlayView.SIZE);

		if (type == 1) {
			type = random.nextInt(PlayView.SIZE);
		}
		position = new Point(CommonResize.getSizeByScreen960(context,
				random.nextInt(900)), -CommonResize.getSizeByScreen960(context,
				random.nextInt(80)));
	}

	private boolean isDie = false;

	public boolean isDie() {
		if (isDie) {
			return true;
		}

		if (position.y > CommonResize.getSizeByScreen960(context, 640)) {
			return true;
		}
		return false;
	}
}