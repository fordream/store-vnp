package org.com.vnp.defenserun.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.com.vnp.defenserun.DefenseRunActivity;
import org.com.vnp.defenserun.data.BitmapCommon;
import org.com.vnp.defenserun.data.Charactor;
import org.com.vnp.defenserun.data.HpX;
import org.com.vnp.defenserun.data.NormalQuaivat;
import org.com.vnp.defenserun.data.Quaivat;
import org.com.vnp.defenserun.data.Shoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class PlayView extends LinearLayout {
	private Charactor charactor = BitmapCommon.CHARACTOR;
	private List<Shoot> lShots = new ArrayList<Shoot>();
	private List<HpX> lHpXs = new ArrayList<HpX>();
	private List<HpX> lMoneys = new ArrayList<HpX>();
	private List<Quaivat> lNormalQuaivats = new ArrayList<Quaivat>();
	private Paint paint = new Paint();
	private Paint paint1 = new Paint();

	public PlayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);

		paint.setColor(Color.WHITE);
		paint1.setColor(Color.YELLOW);
	}

	public PlayView(Context context) {
		super(context);
		setWillNotDraw(false);
		paint.setColor(Color.WHITE);
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (lNormalQuaivats.size() < 15) {
			Random random = new Random();
			if (random.nextInt(100) <= 10) {
				NormalQuaivat normalQuaivat = new NormalQuaivat();
				normalQuaivat.setPoint(new Point(-20, random.nextInt(200)));
				normalQuaivat.setHp(random.nextInt(2) + 1);
				normalQuaivat.setMoney(random.nextInt(10000000) + 1000000);
				// if (random.nextInt(100) <= 50) {
				normalQuaivat.setAmor(random.nextInt(50));
				// }
				normalQuaivat.setSd(random.nextInt(10));
				normalQuaivat.setSpeed(random.nextInt(4) + 3);
				lNormalQuaivats.add(normalQuaivat);
			}
		}

		// draw
		for (int i = 0; i < lNormalQuaivats.size(); i++) {
			NormalQuaivat shoot = (NormalQuaivat) lNormalQuaivats.get(i);
			Bitmap bitmap = BitmapCommon.getBitmapBoss(shoot.getType());
			Point point = shoot.getPoint();
			int x = point.x - bitmap.getWidth() / 2;
			int y = point.y - bitmap.getHeight() / 2;
			canvas.drawBitmap(bitmap, x, y, paint);
		}

		for (int i = 0; i < lShots.size(); i++) {
			Shoot shoot = lShots.get(i);
			Bitmap bitmap = BitmapCommon.getBitmap(1);
			Point point = shoot.getCurentPoint();
			int x = point.x - bitmap.getWidth() / 2;
			int y = point.y - bitmap.getHeight() / 2;
			canvas.drawBitmap(bitmap, x, y, paint);
		}

		for (int i = 0; i < lHpXs.size(); i++) {
			HpX hpX = lHpXs.get(i);
			String text = hpX.getHp() + "";

			if (hpX.getHp() <= 0) {
				text = "Miss";
			}

			Point point = hpX.getPoint();
			canvas.drawText(text, point.x, point.y, paint);
		}

		for (int i = 0; i < lMoneys.size(); i++) {
			HpX hpX = lMoneys.get(i);
			String text = hpX.getMoney() + "$";

			Point point = hpX.getPoint();
			canvas.drawText(text, point.x, point.y, paint1);
		}

		for (int i = 0; i < lShots.size(); i++) {
			Shoot shoot = lShots.get(i);
			for (int j = 0; j < lNormalQuaivats.size(); j++) {
				NormalQuaivat quaivat = (NormalQuaivat) lNormalQuaivats.get(j);
				Bitmap bitmap = BitmapCommon.getBitmapBoss(quaivat.getType());
				Point point = quaivat.getPoint();
				int x = point.x - bitmap.getWidth() / 2;
				int y = point.y - bitmap.getHeight() / 2;

				Point point2 = shoot.getCurentPoint();

				if (x <= point2.x
						&& point2.x <= point.x + bitmap.getWidth() / 2) {
					if (y <= point2.y
							&& point2.y <= point.y + bitmap.getHeight() / 2) {
						lShots.remove(shoot);
						int hpx = quaivat.updateHP(shoot.getPower(),
								shoot.isBogiap());
						Point point3 = shoot.getCurentPoint();
						if (hpx >= 0) {
							HpX hpX = new HpX();
							hpX.setHp(hpx);
							hpX.setMoney(0);
							hpX.setPoint(point3);
							lHpXs.add(hpX);
						}
					}
				}
			}
		}

		for (int i = 0; i < lNormalQuaivats.size(); i++) {
			if (lNormalQuaivats.get(i).getHp() <= 0) {
				HpX money = new HpX();
				money.setMoney(lNormalQuaivats.get(i).getMoney());
				money.setPoint(lNormalQuaivats.get(i).getPoint());
				lMoneys.add(money);
				((DefenseRunActivity) getContext()).updateScore(lNormalQuaivats
						.get(i).getMoney());
				lNormalQuaivats.remove(lNormalQuaivats.get(i));

			}
		}
		for (int i = 0; i < lHpXs.size(); i++) {
			HpX hpX = lHpXs.get(i);
			hpX.setMoney(hpX.getMoney() + 1);

			Point point = hpX.getPoint();
			if (hpX.getMoney() % 10 == 0) {
				point.y = point.y - 10;
			}

			if (hpX.getMoney() >= 50) {
				lHpXs.remove(hpX);
			}
		}

		for (int i = 0; i < lMoneys.size(); i++) {
			HpX hpX = lMoneys.get(i);
			hpX.setHp(hpX.getHp() + 1);

			Point point = hpX.getPoint();
			if (hpX.getHp() % 10 == 0) {
				point.y = point.y - 10;
			}

			if (hpX.getHp() >= 100) {
				lMoneys.remove(hpX);
			}
		}
		// update
		for (int i = 0; i < lNormalQuaivats.size(); i++) {
			lNormalQuaivats.get(i).updatePosition();
		}
		for (int i = 0; i < lShots.size(); i++) {
			Shoot shoot = lShots.get(i);
			shoot.update();
		}

		for (int i = 0; i < lShots.size(); i++) {
			Shoot shoot = lShots.get(i);
			if (shoot.isDie()) {
				lShots.remove(shoot);
			}
		}
		invalidate();
	}

	public boolean onTouchEvent(MotionEvent event) {
		// post(new AddShoot((int) event.getX(), (int) event.getY()));
		BitmapCommon.POINT.x = getWidth() / 2;
		BitmapCommon.POINT.y = getHeight();
		int x1 = (int) event.getX();
		int y1 = (int) event.getY();
		// for (int i = 0; i < 3; i++) {
		int i = 3;
		Point dich = new Point(x1 - (i - 3) * 10, y1);
		int x = getWidth() / 2;
		int y = getHeight();
		Point pointCu = new Point(x, y);
		lShots.add(createShoot(pointCu, dich));
		// }
		return super.onTouchEvent(event);
	}

	private Shoot createShoot(Point cu, Point dich) {
		Shoot shoot = new Shoot();
		shoot.setDich(dich);
		shoot.setCurentPoint(cu);
		shoot.setPower(charactor.realPower());
		shoot.setBogiap(charactor.isBogiap());
		return shoot;
	}
}