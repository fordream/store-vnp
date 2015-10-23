package org.com.vnp.caothubongbong.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.com.vnp.caothubongbong.data.ClickItem;
import org.com.vnp.caothubongbong.data.Item;
import org.com.vnp.shortheart.CaoThuBongBongScreen;
import org.com.vnp.shortheart.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class PlayView extends LinearLayout {
	private List<Item> lItems = new ArrayList<Item>();
	private ClickItem clickItem;
	private int left = 0;
	private int top = 0;
	private boolean first = true;
	private boolean isPause = false;

	public PlayView(Context context) {
		super(context);
		config();
	}

	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	private void config() {
		LayoutInflater li;
		String service = Context.LAYOUT_INFLATER_SERVICE;
		li = (LayoutInflater) getContext().getSystemService(service);
		setWillNotDraw(false);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Random random = new Random();

		if (random.nextInt(100) <= 5 && !isPause) {
			Item item = new Item(getContext());
			lItems.add(item);
		}

		for (int i = 0; i < lItems.size(); i++) {
			Item item = lItems.get(i);
			if (item.isLife()) {
				item.nextCount();
				if (!isPause) {
					item.isNext();
				}

				Bitmap bitmap = item.getBitmap();
				canvas.drawBitmap(bitmap, item.getX(), item.getY(), new Paint());
			} else {
				Bitmap bitmap = item.getBitmapDie();
				if (bitmap != null) {
					canvas.drawBitmap(bitmap, item.getX(), item.getY(),
							new Paint());
				}
			}
		}
		if (isPause) {
			clickItem = null;
		}
		if (clickItem != null && !isPause) {
			Bitmap bitmap = clickItem.getBitmap();
			int centerX = left - bitmap.getWidth() / 2;
			int centerY = top - bitmap.getHeight() / 2;
			canvas.drawBitmap(bitmap, centerX, centerY, new Paint());
			clickItem.nextCount();
			for (int i = 0; i < lItems.size(); i++) {
				Item item = lItems.get(i);
				if (item.isLife() && first) {
					item.die(centerX, centerY);
					if (!item.isLife() && item.getType() == 0) {
						((CaoThuBongBongScreen) getContext()).updateScore(item
								.getScore());
						first = false;
					} else if (!item.isLife() && item.getType() == 1) {
						((CaoThuBongBongScreen) getContext()).updateTime(5);
						first = false;
					} else if (!item.isLife() && item.getType() == 2) {
						first = false;
					}
				}
			}

			if (clickItem.isEnd()) {
				clickItem = null;
				first = true;
			}
		}
		for (int i = 0; i < lItems.size(); i++) {
			Item item = lItems.get(i);
			boolean isRemove = false;
			if (!item.isLife() && item.getBitmapDie() == null) {
				lItems.remove(item);
				isRemove = true;
			}

			if (!isRemove && item.canRemove(getHeight())) {
				lItems.remove(item);
			}

		}

		invalidate();
	}

	MediaPlayer mediaPlayer1;

	public boolean onTouchEvent(MotionEvent event) {
		if (clickItem == null) {
			top = (int) event.getY();
			left = (int) event.getX();
			clickItem = new ClickItem(getContext());
			if (mediaPlayer1 == null)
				mediaPlayer1 = MediaPlayer
						.create(getContext(), R.raw.ecrossbow);
			mediaPlayer1.start();
		}
		return super.onTouchEvent(event);
	}

	public void setIspause(boolean pause) {
		isPause = pause;
	}

	public boolean isPause() {
		return isPause;
	}
}
