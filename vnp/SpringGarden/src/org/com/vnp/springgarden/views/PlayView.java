package org.com.vnp.springgarden.views;

import java.util.ArrayList;
import java.util.List;

import org.com.vnp.springgarden.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PlayView extends View {
	private List<Point> list = new ArrayList<Point>();
	private Bitmap bitmap;
	private Bitmap bgGame;
	private Paint paint = new Paint();

	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	private void init() {
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hoa);
		bgGame = BitmapFactory.decodeResource(getResources(),
				R.drawable.garden_game);
		
		paint.setColor(Color.RED);
	}

	public PlayView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PlayView(Context context) {
		super(context);
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hoa);
		init();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Matrix matrix = new Matrix();
		float sx = (float) getHeight() / (float) bgGame.getHeight();
		matrix.postScale(sx, sx);
		canvas.drawBitmap(bgGame, matrix, paint);

		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 1) {
				Point p = list.get(i);
				canvas.drawBitmap(bitmap, p.x - bitmap.getWidth() / 2, p.y
						- bitmap.getHeight() / 2, new Paint());
			}
		}
	}

	public void setNewPoint(MotionEvent event) {
		Point point = new Point();
		point.x = (int) event.getX();
		point.y = (int) event.getY();
		boolean check = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).x == point.x && list.get(i).y == point.y) {
				check = true;
			}
		}

		if (!check) {
			list.add(point);
		}

		invalidate();
	}

	public void clear() {
		list.clear();
		invalidate();
	}
}
