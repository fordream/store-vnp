package vn.vvn.bibook.item;

import vn.vvn.bibook.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView {

	private Bitmap background;
	private int screenWidth;
	private String mTitleCategory;
	private int from;
	private int to;

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		BitmapFactory.Options o = new BitmapFactory.Options();

		o.inPreferredConfig = Config.RGB_565;
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.gia_sach, o);
		screenWidth = ((Activity) context).getWindowManager()
				.getDefaultDisplay().getWidth();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		int backgroundHeight = background.getHeight();
		int height = getHeight();
		for (int y = 0; y < height; y += backgroundHeight) {
			// for (int x = 0; x < width; x += backgroundWidth) {
			// canvas.drawBitmap(background, x, y, null);
			canvas.drawBitmap(background, null, new Rect(0, y, screenWidth,
					(y + backgroundHeight)), null);
			// }
		}

		super.dispatchDraw(canvas);
	}

	public String getTitleCategory() {
		return mTitleCategory;
	}

	public void setTitleCategory(String mTitleCategory) {
		this.mTitleCategory = mTitleCategory;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}
}
