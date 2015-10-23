package org.com.vnp.caothubongbong.views;

import org.com.vnp.shortheart.R;
import org.com.vnp.shortheart.HeartHunterSplashScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class SplashView extends LinearLayout {
	private static final int SIZE = 3;
	private static final int DELAY = 40;
	private Bitmap[] bitmaps = new Bitmap[SIZE];
	private int count = 0;
	//private boolean isRound = false;

	public SplashView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public SplashView(Context context) {
		super(context);
		config();
	}

	private void config() {
		LayoutInflater li;
		String service = Context.LAYOUT_INFLATER_SERVICE;
		li = (LayoutInflater) getContext().getSystemService(service);
		setWillNotDraw(false);
		bitmaps[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.splash1);
		bitmaps[1] = BitmapFactory.decodeResource(getResources(),
				R.drawable.splash2);
		bitmaps[2] = BitmapFactory.decodeResource(getResources(),
				R.drawable.splash3);
	}

	private Bitmap getBitmap() {
		count++;
		if (count >= SIZE * DELAY) {
			count = 0;
			//isRound = true;
			((HeartHunterSplashScreen)getContext()).update();
		}

		int index = count / DELAY;

		return bitmaps[index];
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(getBitmap(), 0, 0, new Paint());
		invalidate();
	}

	public void configRec() {
		Runnable action = new Runnable() {
			public void run() {
				int w = getWidth();
				int h = getHeight();
				bitmaps[0] = convertBitmap(bitmaps[0], w, h);
				bitmaps[1] = convertBitmap(bitmaps[1], w, h);
				bitmaps[2] = convertBitmap(bitmaps[2], w, h);
			}
		};
		post(action);
	}

	private Bitmap convertBitmap(Bitmap img, int w, int h) {
		int width = img.getWidth();
		int height = img.getHeight();
		int newWidth = w;
		int newHeight = h;
		newWidth = (int) (((float) newHeight) / ((float) height) * ((float) width));
		float scaleWidth = (float) newWidth / width;
		float scaleHeight = (float) newHeight / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(img, 0, 0, width, height,
				matrix, true);
		return resizedBitmap;
	}

}
