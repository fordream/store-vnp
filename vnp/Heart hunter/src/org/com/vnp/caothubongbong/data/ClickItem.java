package org.com.vnp.caothubongbong.data;

import org.com.vnp.shortheart.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ClickItem {
	private Bitmap click1, click2;
	private static final int COUNT_MAX = 3;
	private int count1 = 0, count2 = 0;

	public ClickItem(Context context) {
		Resources resources = context.getResources();
		click1 = BitmapFactory.decodeResource(resources, R.drawable.bom7);
		click2 = BitmapFactory.decodeResource(resources, R.drawable.bom8);
	}

	public Bitmap getClick1() {
		return click1;
	}

	public Bitmap getClick2() {
		return click2;
	}

	public void nextCount() {
		if (count1 < COUNT_MAX) {
			count1++;
		} else {
			count2++;
		}
	}

	public Bitmap getBitmap() {
		if (count1 < COUNT_MAX) {
			return click1;
		}

		return click2;
	}

	public boolean isEnd() {
		return count1 >= COUNT_MAX && count2 >= COUNT_MAX;
	}

	public void reset() {
		count1 = 0;
		count2 = 0;
	}

}