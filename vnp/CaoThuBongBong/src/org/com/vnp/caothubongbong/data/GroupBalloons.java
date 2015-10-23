package org.com.vnp.caothubongbong.data;

import java.util.Random;

import org.com.cnc.common.adnroid16.CommonDeviceId;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class GroupBalloons {
	private Balloon balloon;
	private Destination destination;
	private int x = 0;
	private int y = 0;
	private boolean isDie = false;
	private int speed = 1;
	private Context context;
	private int ZISE_LINE = 25;

	public GroupBalloons(Context context, int type, int x, int y) {
		this.context = context;
		balloon = new Balloon(context, type);
		destination = new Destination(context);

		onCreatePosition(x, y);
	}

	public void onCreatePosition(int x, int y) {
		this.x = x;
		this.y = y;
		speed = new Random().nextInt(5);
		if (CommonDeviceId.isTablet((Activity) context)) {
			speed += 5;
		} else {
			speed += 2;
		}
		destination.getPoint().x = x;
		destination.getPoint().y = y;

		Bitmap bitmap = destination.getBitmap();
		Bitmap bitmap2 = balloon.getBitmap();
		int yBalloons = y - bitmap.getHeight() / 2 - ZISE_LINE;
		yBalloons -= bitmap2.getHeight() / 2;

		balloon.getPoint().x = x;
		balloon.getPoint().y = yBalloons;
	}

	public void reload() {
		isDie = false;
		onCreatePosition(x, y);
	}

	public void onDraw(Canvas canvas, Paint paint) {

		Point end = new Point();
		end.x = balloon.getPoint().x;
		end.y = balloon.getPoint().y + balloon.getBitmap().getHeight() / 2;

		Point start = new Point();
		start.x = end.x;
		start.y = end.y + ZISE_LINE;
		canvas.drawLine(start.x, start.y, end.x, end.y, paint);
		balloon.onDraw(canvas, paint);

		if (!isDie) {
			destination.onDraw(canvas, paint);
		}
	}

	public int getMinHeight() {
		if (balloon.getBitmap().getHeight() > destination.getBitmap()
				.getHeight()) {
			return destination.getBitmap().getHeight();
		} else {
			return balloon.getBitmap().getHeight();
		}
	}

	public int getMaxWidth() {
		if (balloon.getBitmap().getWidth() > destination.getBitmap().getWidth()) {
			return balloon.getBitmap().getWidth();
		} else {
			return destination.getBitmap().getWidth();
		}
	}

	public void setDie(boolean isDie) {
		this.isDie = isDie;
	}

	public boolean isDie() {
		return isDie;
	}

	public void update() {
		if (canNotMove()) {
			reload();
		} else {
			balloon.getPoint().y = balloon.getPoint().y - speed;
		}
	}

	private boolean canNotMove() {
		return balloon.getPoint().y + balloon.getBitmap().getHeight()
				+ ZISE_LINE < 0;
	}

	public int typeShoot(int x, int y) {
		Bitmap bitmap = balloon.getBitmap();
		Point point = balloon.getPoint();

		if (x > point.x - bitmap.getWidth() / 2
				&& x < point.x + bitmap.getWidth() / 2) {
			if (y > point.y - bitmap.getHeight() / 2
					&& y < point.y + bitmap.getHeight() / 2) {
				return -1;
			}
		}
		Bitmap bitmap1 = destination.getBitmap();
		Point point1 = destination.getPoint();
		if (x > point1.x - bitmap1.getWidth() / 2
				&& x < point1.x + bitmap1.getWidth() / 2) {
			if (y > point1.y - bitmap1.getHeight() / 2
					&& y < point1.y + bitmap1.getHeight() / 2) {
				return 1;
			}
		}

		return 0;
	}

	public int getScore() {
		return new Random().nextInt(51) + 50;
	}
}
