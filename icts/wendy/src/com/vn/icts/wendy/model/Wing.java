package com.vn.icts.wendy.model;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import com.ict.library.service.LocationParacelable;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.async.WendyAsyn;
import com.vn.icts.wendy.async.WendyAsyn.CallBack;

public class Wing {
	private static Bitmap[] bitmap = new Bitmap[6];
	private int index = 0;
	private int count = 0;
	// private Point point = new Point();
	private int x, y, z;
	private LocationParacelable location;

	// distinct max 1000
	private double distinct = 10000;
	private String lat;
	private String log;

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

	public Wing(String lat, String log) {
		this.lat = lat;
		this.log = log;

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

	public void onDraw(Canvas canvas) {
		try {
			Matrix matrix = new Matrix();
			Bitmap bitmap = getBitmap();
			if (distinct < 1000) {

				float sx = ((float) (1000 - distinct)) / 1000f;
				sx = 1f;
				matrix.postScale(sx, sx);
				matrix.postTranslate(100, 100);
				canvas.drawBitmap(bitmap, matrix, new Paint());
			}
		} catch (Exception e) {
		}
	}

	public void update(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void update(LocationParacelable location) {
		if (this.location == null
				|| location != null
				&& (this.location.getLatitude() != location.getLatitude() || this.location
						.getLongitude() != location.getLongitude())) {
			this.location = location;

			Bundle extras = new Bundle();
			if (this.location == null) {
				return;
			}
			extras.putString("origin", this.location.getLatitude() + ","
					+ this.location.getLongitude());
			extras.putString("destination", lat + "," + log);
			new WendyAsyn(WendyAsyn.TYPE_DISTINCT_MAP, new CallBack() {
				@Override
				public void callBack(List<Object> lDatas) {
					if (lDatas.size() > 0) {
						try {
							distinct = Double.parseDouble(lDatas.get(0)
									.toString());
						} catch (Exception e) {

						}
					}
				}
			}, extras).execute("");
		}
	}
}