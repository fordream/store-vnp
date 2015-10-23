package com.vnpgame.undersea.nextlevel.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;

import com.vnpgame.chickenmerrychristmas.R;
import com.vnpgame.undersea.nextlevel.activity.NextLevelScreen;

public class NextLevelView extends View {
	int x = 0, y = 0;
	Bitmap bitmap;
	private NextLevelScreen nextLevelScreen;
	public NextLevelView(Context context) {
		super(context);
		nextLevelScreen = (NextLevelScreen)context;
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.nextlevel);
		x = -bitmap.getWidth();
	}

	public void set(int x) {
		this.x = x;
		invalidate();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		y = getHeight() / 2 - bitmap.getHeight() / 2;

		canvas.drawBitmap(bitmap, x, y, new Paint());

		new AsyncTask<String, String, String>() {
			private Handler handler = new Handler();

			protected String doInBackground(String... params) {

				while (x < getWidth()) {
					x = x + 1;
					handler.post(new Runnable() {
						public void run() {
							invalidate();
						}
					});

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
					}
				}
				return null;
			}

			protected void onPostExecute(String result) {
				nextLevelScreen.setResult(Activity.RESULT_OK);
				nextLevelScreen.finish();
			};
		}.execute("");
	}

	public boolean canRun() {
		return x < getWidth();
	}
}
