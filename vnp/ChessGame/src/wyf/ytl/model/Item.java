package wyf.ytl.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class Item {
	Point position = new Point();
	Bitmap bitmap;

	public Item(int res, Resources resources, Point point) {
		bitmap = BitmapFactory.decodeResource(resources, res);// ����ťͼƬ
		position = point;
	}

	public void onDraw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(bitmap, position.x, position.y, paint);
	}

	public void onDraw(Canvas canvas, Paint paint, boolean canDraw) {
		if (canDraw)
			canvas.drawBitmap(bitmap, position.x, position.y, paint);
	}

	public boolean isClickMe(MotionEvent event) {
		if (event.getX() > position.x
				&& event.getX() < position.x + bitmap.getWidth()
				&& event.getY() > position.y
				&& event.getY() < position.y + bitmap.getHeight()) {
			return true;
		}
		return false;
	}

	public Bitmap getBitMap() {
		return bitmap;
	}

	public void setPosition(Point point) {
		position = point;
	}
}