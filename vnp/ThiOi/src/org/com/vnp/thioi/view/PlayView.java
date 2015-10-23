/**
 * 
 */
package org.com.vnp.thioi.view;

import java.util.ArrayList;
import java.util.List;

import org.com.vnp.thioi2.R;
import org.com.vnp.thioi.o.Item;
import org.com.vnp.thioi.o.Player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.MotionEvent;

import com.ict.library.anetwork.CommonBitmap;
import com.ict.library.common.CommonResize;
import com.ict.library.view.CustomLinearLayoutView;
import com.vnp.core.basegame.CommonBaseScore;

/**
 * @author tvuong1pc
 * 
 */
public abstract class PlayView extends CustomLinearLayoutView {
	public static final int SIZE = 4;
	private Handler handler = new Handler();
	public long score = 0;
	private int delay = 0;
	private static final int MAXDELAY = 10;
	private static int MAXITEM = 20;
	private List<Item> list = new ArrayList<Item>();
	ArrayList<Item> lRemove = new ArrayList<Item>();
	private Bitmap bitmap[] = new Bitmap[SIZE];

	private Bitmap bitmapBagia[] = new Bitmap[8];
	private StatusGame statusGame = StatusGame.CREATEGAME;
	private Player player;
	private Bitmap b_player;
	private Bitmap bCar;
	private Paint paint = new Paint();
	private int time = 60;

	/**
	 * @param context
	 */
	public PlayView(Context context) {
		super(context);
		player = new Player(getContext());

		bitmap[0] = BitmapFactory
				.decodeResource(getResources(), R.drawable.thi);
		bitmap[1] = BitmapFactory
				.decodeResource(getResources(), R.drawable.sau);
		bitmap[2] = BitmapFactory.decodeResource(getResources(), R.drawable.a7);
		bitmap[3] = BitmapFactory
				.decodeResource(getResources(), R.drawable.thi);
		float new_Width = CommonResize.getSizeByScreen960(getContext(), 40);

		bitmap[0] = CommonBitmap.reSizeBitmap(bitmap[0], (int) new_Width);
		bitmap[1] = CommonBitmap.reSizeBitmap(bitmap[1], (int) new_Width);
		bitmap[2] = CommonBitmap.reSizeBitmap(bitmap[2], (int) new_Width);
		bitmap[3] = CommonBitmap.reSizeBitmap(bitmap[3], (int) new_Width);

		b_player = CommonBitmap
				.reSizeBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.player), (int) (CommonResize
						.getSizeByScreen960(getContext(), 300)));

		bCar = CommonBitmap.reSizeBitmap(
				BitmapFactory.decodeResource(getResources(), R.drawable.tran),
				(int) (CommonResize.getSizeByScreen960(getContext(), 40)));

		paint.setColor(Color.BLACK);
		paint.setTextSize(CommonResize.getSizeByScreen960(getContext(), 30));
		paint.setFakeBoldText(true);

		bitmapBagia[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia1);
		bitmapBagia[1] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia2);
		bitmapBagia[2] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia3);
		bitmapBagia[3] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia4);

		bitmapBagia[4] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia1copy);
		bitmapBagia[5] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia2copy);
		bitmapBagia[6] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia3copy);
		bitmapBagia[7] = BitmapFactory.decodeResource(getResources(),
				R.drawable.bagia4copy);

		float new_WidthBagia = CommonResize.getSizeByScreen960(getContext(),
				300);

		bitmapBagia[0] = CommonBitmap.reSizeBitmap(bitmapBagia[0],
				(int) new_WidthBagia);
		bitmapBagia[1] = CommonBitmap.reSizeBitmap(bitmapBagia[1],
				(int) new_WidthBagia);
		bitmapBagia[2] = CommonBitmap.reSizeBitmap(bitmapBagia[2],
				(int) new_WidthBagia);
		bitmapBagia[3] = CommonBitmap.reSizeBitmap(bitmapBagia[3],
				(int) new_WidthBagia);

		bitmapBagia[4] = CommonBitmap.reSizeBitmap(bitmapBagia[4],
				(int) new_WidthBagia);
		bitmapBagia[5] = CommonBitmap.reSizeBitmap(bitmapBagia[5],
				(int) new_WidthBagia);
		bitmapBagia[6] = CommonBitmap.reSizeBitmap(bitmapBagia[6],
				(int) new_WidthBagia);
		bitmapBagia[7] = CommonBitmap.reSizeBitmap(bitmapBagia[7],
				(int) new_WidthBagia);

		setWillNotDraw(false);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!((Activity) getContext()).isFinishing()
						&& statusGame != StatusGame.END) {
					if (statusGame == StatusGame.START) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
						time--;

						if (time == 0) {
							statusGame = StatusGame.END;
							handler.post(new Runnable() {
								@Override
								public void run() {
									CommonBaseScore.getInstance().init(
											getContext());
									CommonBaseScore.getInstance().save(
											System.currentTimeMillis() + "",
											score);
									callBackEndGame();
								}
							});

						}
					}
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ict.library.view.CustomLinearLayoutView#setGender()
	 */
	@Override
	public void setGender() {

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (statusGame == StatusGame.CREATEGAME) {
		} else if (statusGame == StatusGame.START) {
			for (Item item : list) {
				if (item.isDie()) {
					lRemove.add(item);
				} else if (isDie(item)) {
					// an diem
					lRemove.add(item);
					openmediplayer();
					score += item.getScore();
				}
			}

			for (Item item : lRemove) {
				list.remove(item);
			}

			lRemove.clear();

			for (Item item : list) {
				item.update();
			}

			player.update();

			delay++;
			if (delay == MAXDELAY) {
				if (list.size() < MAXITEM) {
					Item item = new Item(PlayView.this.getContext());
					list.add(item);
				}
				delay = 0;
			}
		} else if (statusGame == StatusGame.PAUSE) {
		}

		drawItem(canvas);

		invalidate();
	}

	private boolean isDie(Item item) {
		Point point = item.getPosition();

		Point point2 = null;
		if (player.isRight()) {
			point2 = new Point((int) (player.getPosition().x
					+ bitmapBagia[indexBagia].getWidth() - bCar.getWidth()),
					getHeight() - bitmapBagia[indexBagia].getHeight() / 2
							+ bCar.getHeight() / 2);
		} else {
			point2 = new Point((int) (player.getPosition().x - 0), getHeight()
					- bitmapBagia[indexBagia].getHeight() / 2
					+ bCar.getHeight() / 2);
		}

		if ((point2.x < point.x && point.x < point2.x + bCar.getWidth())
				|| (point2.x < (point.x + bitmap[0].getWidth()) && (point.x + bitmap[0]
						.getWidth()) < point2.x + bCar.getWidth())) {

			if ((point2.y < point.y && point.y < point2.y + bCar.getHeight())
					|| (point2.y < point.y + bitmap[0].getHeight() && point.y
							+ bitmap[0].getHeight() < point2.y
							+ bCar.getHeight())) {
				return true;
			}

		}

		return false;
	}

	private int indexBagia = 0;
	private int count = 0;

	private void drawItem(Canvas canvas) {
		for (Item item : list) {
			Point point = item.getPosition();
			canvas.drawBitmap(bitmap[item.getType()], point.x, point.y, paint);
		}

		// show player
		if (!player.isRight()) {
			canvas.drawBitmap(bitmapBagia[indexBagia], player.getPosition().x,
					getHeight() - b_player.getHeight(), paint);
		} else {
			canvas.drawBitmap(bitmapBagia[indexBagia + 4],
					player.getPosition().x, getHeight() - b_player.getHeight(),
					paint);
		}
		count++;
		if (count == 10) {
			if (indexBagia <= 2) {
				indexBagia++;
			} else {
				indexBagia = 0;
			}
			count = 0;
		}

		// draw car
		canvas.drawBitmap(
				bCar,
				player.getPosition().x
						+ (player.isRight() ? bitmapBagia[indexBagia]
								.getWidth() - bCar.getWidth() : 0),
				getHeight() - bitmapBagia[indexBagia].getHeight() / 2
						+ bCar.getHeight() / 2, paint);

		// draw score
		canvas.drawText("Score : " + score + "",
				CommonResize.getSizeByScreen960(getContext(), 10),
				CommonResize.getSizeByScreen960(getContext(), 80), paint);

		// draw Time
		canvas.drawText("Time : " + time + "",
				CommonResize.getSizeByScreen960(getContext(), 10),
				CommonResize.getSizeByScreen960(getContext(), 130), paint);
	}

	public StatusGame getStatusGame() {
		return statusGame;
	}

	public void setStatusGame(StatusGame statusGame) {
		this.statusGame = statusGame;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (StatusGame.START == statusGame) {
			int x = (int) event.getX();
			player.setX(x);
		}
		return true;
	}

	public abstract void callBackEndGame();

	private void openmediplayer() {
		MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),
				R.raw.mawattack2);
		mediaPlayer.start();
	}
}