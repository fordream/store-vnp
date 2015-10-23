package org.com.vnp.defenserun.data;

import org.com.vnp.defenserun.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class BitmapCommon {
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	public static final int TYPE_3 = 3;
	public static final int TYPE_4 = 4;

	public static final int SIZE = 4;
	public static final int DELAY = 10;

	public static Point POINT = new Point();

	public static Charactor CHARACTOR = new Charactor();

	public static Bitmap[] bitmaps = new Bitmap[SIZE];
	public static Bitmap[] bitmaps2 = new Bitmap[SIZE];
	public static Bitmap[] bitmapsBoss = new Bitmap[SIZE];

	public static void create(Context context) {
		Resources resources = context.getResources();
		bitmaps[0] = getBMResouce(resources, drawable.pang1);
		bitmaps[1] = getBMResouce(resources, drawable.pang1);
		bitmaps[2] = getBMResouce(resources, drawable.pang1);
		bitmaps[3] = getBMResouce(resources, drawable.pang1);

		bitmapsBoss[0] = getBMResouce(resources, drawable.boss100);
		bitmapsBoss[1] = getBMResouce(resources, drawable.boss100);
		bitmapsBoss[2] = getBMResouce(resources, drawable.boss100);
		bitmapsBoss[3] = getBMResouce(resources, drawable.boss100);
	}

	public static Bitmap getBitmap(int type) {
		int index = 0;
		if (type == TYPE_1) {
			index = 0;
		} else if (type == TYPE_2) {
			index = 1;
		} else if (type == TYPE_3) {
			index = 2;
		} else if (type == TYPE_4) {
			index = 3;
		}

		return bitmaps[index];
	}

	public static Bitmap getBitmapBoss(int type) {
//		int index = 0;
//		if (type == TYPE_1) {
//			index = 0;
//		} else if (type == TYPE_2) {
//			index = 1;
//		} else if (type == TYPE_3) {
//			index = 2;
//		} else if (type == TYPE_4) {
//			index = 3;
//		}

		return bitmapsBoss[0];
	}

	private static Bitmap getBMResouce(Resources resources, int res) {
		return BitmapFactory.decodeResource(resources, res);
	}
}