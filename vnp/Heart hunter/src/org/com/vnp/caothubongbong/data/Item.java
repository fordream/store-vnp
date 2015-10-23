package org.com.vnp.caothubongbong.data;

import java.util.Random;

import org.com.vnp.shortheart.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;

public class Item {
	private static final int SIZE = 4;
	private static final int COUNT_MAX = 10;
	private static final int DELAY = 1;
	private static final int DELAY_COUNT_DIE = 20;
	private Bitmap item[] = new Bitmap[SIZE];
	private Bitmap itemDie[] = new Bitmap[2];
	private int count = 0;
	private int next = 0;
	private boolean isLife = true;
	private int x = new Random().nextInt(320);
	private int y = -20;
	private int countdie = 0;
	private int score = 100;
	// type = 0 => normal
	// type = 1 => dong ho
	// type = 2 => bom
	private int type = 0;

	public Item(Context context) {
		Display display = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		int res_width = display.getWidth();
		x = new Random().nextInt(res_width);
		Resources resources = context.getResources();
		Random random = new Random();
		int check = random.nextInt(1000);
		if (check <= 600) {
			score = 100;
			itemDie[0] = BitmapFactory.decodeResource(resources,
					R.drawable.m1001);
			itemDie[1] = BitmapFactory.decodeResource(resources,
					R.drawable.m1002);
		} else if (check <= 900) {
			score = 200;
			itemDie[0] = BitmapFactory.decodeResource(resources,
					R.drawable.m2001);
			itemDie[1] = BitmapFactory.decodeResource(resources,
					R.drawable.m2002);
		} else {
			score = 300;
			itemDie[0] = BitmapFactory.decodeResource(resources,
					R.drawable.m3001);
			itemDie[1] = BitmapFactory.decodeResource(resources,
					R.drawable.m3002);
		}

		int type = random.nextInt(1000);
		if (type < 930) {
			this.type = 0;
		} 
//		else if (type < 930) {
//			this.type = 2;
//		}
		else {
			this.type = 1;
		}
		if (this.type == 0) {
			item[0] = BitmapFactory.decodeResource(resources, R.drawable.item1);
			item[1] = BitmapFactory.decodeResource(resources, R.drawable.item2);
			item[2] = BitmapFactory.decodeResource(resources, R.drawable.item3);
			item[3] = BitmapFactory.decodeResource(resources, R.drawable.item4);
		} else if (this.type == 1) {
			item[0] = BitmapFactory.decodeResource(resources,
					R.drawable.oclock1);
			item[1] = BitmapFactory.decodeResource(resources,
					R.drawable.oclock2);
			item[2] = BitmapFactory.decodeResource(resources,
					R.drawable.oclock3);
			item[3] = BitmapFactory.decodeResource(resources,
					R.drawable.oclock4);

			itemDie[0] = BitmapFactory
					.decodeResource(resources, R.drawable.s51);
			itemDie[1] = BitmapFactory
					.decodeResource(resources, R.drawable.s52);
		} else {
			item[0] = BitmapFactory.decodeResource(resources,
					R.drawable.bombang1);
			item[1] = BitmapFactory.decodeResource(resources,
					R.drawable.bombang2);
			item[2] = BitmapFactory.decodeResource(resources,
					R.drawable.bombang3);
			item[3] = BitmapFactory.decodeResource(resources,
					R.drawable.bombang4);
		}

	}

	public int getScore() {
		return score;
	}

	public Bitmap getBitmapDie() {
		countdie++;
		y--;
		int index = countdie / DELAY_COUNT_DIE;
		try {
			return itemDie[index];
		} catch (Exception e) {
			return null;
		}
	}

	public boolean isLife() {
		return isLife;
	}

	public void setLife(boolean isLife) {
		this.isLife = isLife;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isNext() {
		next++;
		if (next >= DELAY) {
			next = 0;
			y++;
			return true;
		}
		return false;
	}

	public void nextCount() {
		if (count > COUNT_MAX * SIZE) {
			count = 0;
		}

		count++;
	}

	public Bitmap getBitmap() {
		int index = count / COUNT_MAX;
		try {
			return item[index];
		} catch (Exception e) {
			return item[0];
		}
	}

	public void die(int leftCenter, int topCenter) {
		if (x + getBitmap().getWidth() / 2 > leftCenter
				&& leftCenter > x - getBitmap().getWidth() / 2) {
			if (y + getBitmap().getHeight() / 2 > topCenter
					&& topCenter > y - getBitmap().getHeight() / 2) {
				isLife = false;
			}
		}
	}

	public boolean canRemove(int height) {
		return y >= height;
	}

	public int getType() {
		return type;
	}
}
