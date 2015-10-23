package org.com.vnp.chickenbang.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.adnroid.views.CommonLinearLayout;
import org.com.vnp.chickenbang.R;
import org.com.vnp.chickenbang.actions.OnTouchListenerController;
import org.com.vnp.chickenbang.asyn.PlayAsyn;
import org.com.vnp.chickenbang.charactors.Charactor;
import org.com.vnp.chickenbang.p.Point;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class DrawView extends CommonLinearLayout {
	private List<BitmapStore> lImageViews = new ArrayList<BitmapStore>();
	private Context context;
	private PlayAsyn playAsyn;
	private LinearLayout control;
	double dx;
	private double dy;

	private Charactor charactor = new Charactor();

	public DrawView(Context context) {
		super(context);
		config(R.layout.play);
		this.context = context;
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.play);
		this.context = context;
	}

	public void config(int resource_layouy) {
		super.config(resource_layouy);
		setWillNotDraw(false);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon);
		charactor.setBitmap(bitmap);
		charactor.setHp(1000);
		Point point = new Point();
		point.set(100, 100);
		charactor.setPoint(point);
		control = (LinearLayout) findViewById(R.id.linearLayout1);
		control.setOnTouchListener(new OnTouchListenerController(this));
		findViewById(R.id.ll).setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent arg1) {
				Point point = new Point((int) arg1.getX(), (int) arg1.getY());
				for (int i = 0; i < lImageViews.size(); i++) {
					try {
						BitmapStore bitmapStore = lImageViews.get(i);
						if (bitmapStore.compare(point)) {
							lImageViews.remove(bitmapStore);
						}
					} catch (Exception e) {
					}
				}

				return true;
			}
		});
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();

		canvas.drawBitmap(charactor.getBitmap(), (int) charactor.getPoint().x,
				(int) charactor.getPoint().y, paint);
	}

	public List<BitmapStore> getlImageViews() {
		return lImageViews;
	}

	public void setlImageViews(List<BitmapStore> lImageViews) {
		this.lImageViews = lImageViews;
	}

	public void invalidate(int dx2, int dy2) {
		double d = Math.sqrt(dx2 * dx2 + dy2 * dy2);
		double speed = charactor.getSpeed();
		dx = speed * dx2 / (d == 0 ? Math.abs(dx2) : d);
		dy = speed * dy2 / (d == 0 ? Math.abs(dy2) : d);
		double checkx = charactor.getPoint().x - dx;
		if (0 <= checkx
				&& checkx <= getWidth() - charactor.getBitmap().getWidth()) {
			charactor.getPoint().x = checkx;
		}
		checkx = charactor.getPoint().y - dy;
		if (0 <= checkx
				&& checkx <= getHeight() - charactor.getBitmap().getHeight()) {
			charactor.getPoint().y = checkx;
		}
		// EditText editText = (EditText) findViewById(R.id.editText1);
		// editText.setText("" + dx + " \n  " + dy + "\n" + dx2 + "\n" + dy2);
		invalidate();
	}
}