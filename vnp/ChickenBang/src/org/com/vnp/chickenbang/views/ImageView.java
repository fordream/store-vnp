package org.com.vnp.chickenbang.views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

public class ImageView extends android.widget.ImageView {
	private Point point = new Point();
	private int dx = 0, dy = 0;
	public static final int G = 9;
	private int delay = 0;
	private int timecurent = 0;

	public ImageView(Context context) {
		super(context);
	}

	public ImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

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

	public void update() {
		if (timecurent >= delay) {
			point.x = point.x + dx;
			point.y = point.y - G;
			timecurent = 0;
		}else{
			timecurent ++;
		}
	}
}
