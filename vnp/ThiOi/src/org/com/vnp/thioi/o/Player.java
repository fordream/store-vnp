package org.com.vnp.thioi.o;

import com.ict.library.common.CommonResize;

import android.content.Context;

public class Player extends Item {

	public Player(Context context) {
		super(context);
	}

	@Override
	public void reset() {
		super.reset();
		getPosition().x = CommonResize.getSizeByScreen960(context, 480);
	}

	public boolean isRight() {
		return x > getPosition().x;
	}

	private int x;

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void update() {
		super.update();

		int speed = CommonResize.getSizeByScreen960(context, 6);

		if (x > getPosition().x && x - getPosition().x > speed) {
			getPosition().x = getPosition().x + speed;
		} else if (x < getPosition().x && getPosition().x - x > speed) {
			getPosition().x = getPosition().x - speed;
		}
	}
}