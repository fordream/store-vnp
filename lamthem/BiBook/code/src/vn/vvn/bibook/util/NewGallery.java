/**
 * 
 */
package vn.vvn.bibook.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * @author haipn
 * 
 */
public class NewGallery extends Gallery {

	private static final float PARAM = 2f;
	int scroll = 0;

	/**
	 * @param context
	 */
	public NewGallery(Context context) {
		super(context);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(displaymetrics);
		scroll = displaymetrics.widthPixels;
	}

	private boolean isScrollingLeft(float velocityX) {
		return velocityX > 0;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		boolean leftScroll = isScrollingLeft(velocityX);

		float velX;
		if (e1 == null || e2 == null) {
			if (leftScroll)
				velX = PARAM * scroll;
			else
				velX = -PARAM * scroll;
		} else if (leftScroll) {
			velX = scroll;
		} else {
			velX = -scroll;
		}
		// this.requestDisallowInterceptTouchEvent(true);
		return super.onFling(e1, e2, velX, velocityY);
	}
}
