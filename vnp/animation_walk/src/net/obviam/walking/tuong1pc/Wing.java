package net.obviam.walking.tuong1pc;

import net.obviam.walking.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Wing {
	private static Bitmap[] bitmap = new Bitmap[6];
	private int index = 0;
	private int count = 0;
	private Point point = new Point();

	public static void init(Context context) {
		if (bitmap[0] == null) {
			bitmap[0] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.wing_1);
			bitmap[1] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.wing_2);
			bitmap[2] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.wing_3);
			bitmap[3] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.wing_4);
			bitmap[4] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.wing_5);
			bitmap[5] = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.wing_6);
		}
	}

	public Wing(final Context context, Point point) {
		this.point = point;
	}

	public Bitmap getBitmap() {
		count++;
		if (count >= 5) {
			count = 0;
			if (index < 5) {
				index++;
			} else {
				index = 0;
			}
		}
		return bitmap[index];
	}
}