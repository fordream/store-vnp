package com.vnp.lappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PlayView extends View implements Runnable {
	public enum STATUS {
		START, END, PAUSE, BEGIN
	}

	private Handler handlerUpdate = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			invalidate();
		}
	};
	private STATUS status = STATUS.BEGIN;

	private Bitmap bitmap;

	public PlayView(Context context) {
		super(context);
		setWillNotDraw(false);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
	}

	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
	}

	private int curentX = 0, curentY = 0;

	private int g = -10;
	private static final int MAX = 5;
	private int time = MAX;

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int _height = getHeight() / 10;
		
		if (status == STATUS.BEGIN) {
			if (curentX == 0) {
				curentX = getWidth() / 2;
			}

			if (curentY == 0) {
				curentY = getHeight() / 2;
			}
		}

		if (bitmap != null) {
			g = -bitmap.getHeight() / 4;
		}

		if (g == 0) {
			g = -1;
		}

		if (status == STATUS.START) {
			if (bitmap != null) {
				int _curentY = curentY;
				time--;
				curentY = curentY + ((time >= 0) ? g : (-g));

				if (curentY - bitmap.getHeight() / 2 > getHeight()) {
					curentY = _curentY;
				}
			}
		}

		if (bitmap != null) {
			canvas.drawBitmap(bitmap, curentX - bitmap.getWidth() / 2, curentY
					- bitmap.getHeight() / 2, new Paint());
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (status == STATUS.BEGIN || status == STATUS.PAUSE) {
				status = STATUS.START;
				startGame();
				if (curentY > 0) {
					time = MAX;
				}
			} else if (status == STATUS.START) {
				if (curentY > 0) {
					time = MAX;
				}
			}
		}

		return super.onTouchEvent(event);
	}

	private void startGame() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (status != STATUS.END) {
			try {
				Thread.sleep(100);
				handlerUpdate.sendEmptyMessage(0);
			} catch (InterruptedException e) {
			}
		}
	}
}
