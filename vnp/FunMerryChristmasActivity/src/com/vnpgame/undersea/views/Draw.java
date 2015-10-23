package com.vnpgame.undersea.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;

import com.vnpgame.undersea.R;

public class Draw extends View {
	Paint paint;
	Bitmap bitmap;
	Bitmap map;
	Bitmap charactor;
	int xmap = 0, ymap = 0;
	boolean isFirst = true;
	boolean isChangle = false;
	Handler handler = new Handler();

	public Draw(Context context) {
		super(context);
		paint = new Paint();
		map = BitmapFactory.decodeResource(getContext().getResources(),
				R.drawable.map1);
		bitmap = BitmapFactory.decodeResource(getContext().getResources(),
				R.drawable.point);
		charactor = BitmapFactory.decodeResource(getContext().getResources(),
				R.drawable.ca3);

	}

	int x = 0, y = 0, curentx = 0, curenty = 0;
	int dx = 0;
	int dy = 0;
	int xcharctor = 0, ycharactor = 0;

	public void setPosition(int dx, int dy) {
		isChangle = true;

		this.dx = dx;
		this.dy = dy;
		invalidate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isFirst) {
			xmap = 0;
			ymap = getHeight() - map.getHeight();
			// xcharctor = getWidth() / 2 - charactor.getWidth() / 2;
			xcharctor = 0;
			ycharactor = getHeight() / 2 - charactor.getHeight() / 2;
			isFirst = false;
			run.execute("");

		}

		if (dx < 0) {
			charactor = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.ca3);
		} else if (dx > 0) {
			charactor = BitmapFactory.decodeResource(getContext().getResources(),
					R.drawable.ca4);
		}

		if (xmap > 0) {
			xmap = 0;
		}
		//
		if (xmap + map.getWidth() - getWidth() < 0) {
			xmap = getWidth() - map.getWidth();
		}

		if (isChangle) {

			// xmap = xmap - dx;
			// ymap = ymap - dy;
			//
			// if (xmap > 0) {
			// xmap = 0;
			// // // xcharctor = xcharctor + dx;
			// // // if (xcharctor <= 0) {
			// // // xcharctor = 0;
			// // // }
			// //
			// }
			// //
			// if (xmap + map.getWidth() - getWidth() < 0) {
			// xmap = getWidth() - map.getWidth();
			// //
			// // // xcharctor = xcharctor + dx;
			// // if (xcharctor > getWidth() - charactor.getWidth()) {
			// // // xcharctor = getWidth() - charactor.getWidth();
			// // // }
			// //
			// }
			//
			// if (ymap > 0) {
			// ymap = 0;
			// // ycharactor = ycharactor + dy;
			// // if (ycharactor <= 0) {
			// // ycharactor = 0;
			// // }
			// }
			//
			// if (ymap + map.getHeight() - getHeight() < 0) {
			// ymap = getHeight() - map.getHeight();
			// // ycharactor = ycharactor + dy;
			// // if (ycharactor > getHeight() - charactor.getHeight()) {
			// // ycharactor = getHeight() - charactor.getHeight();
			// // }
			// }

			xcharctor = xcharctor + dx;
			ycharactor = ycharactor + dy;

			if (xcharctor < 0) {
				xcharctor = 0;
			}

			if (xcharctor >= getWidth() - charactor.getWidth()) {
				xcharctor = getWidth() - charactor.getWidth();
			}

			if (ycharactor < 0) {
				ycharactor = 0;
			}

			if (ycharactor > getHeight() - charactor.getHeight()) {
				ycharactor = getHeight() - charactor.getHeight();
			}
			isChangle = false;
		}

		canvas.drawBitmap(map, xmap, ymap, paint);

		canvas.drawBitmap(charactor, xcharctor, ycharactor, paint);
		invalidate();
	}

	AsyncTask<String, String, String> run = new AsyncTask<String, String, String>() {

		@Override
		protected String doInBackground(String... params) {
			boolean flag = true;
			while (flag) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				xmap--;
			}
			return null;
		}
	};
}