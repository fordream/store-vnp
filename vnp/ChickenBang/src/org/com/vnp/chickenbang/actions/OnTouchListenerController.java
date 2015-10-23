package org.com.vnp.chickenbang.actions;

import org.com.vnp.chickenbang.views.DrawView;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnTouchListenerController implements OnTouchListener {
	private DrawView view;

	public OnTouchListenerController(DrawView view) {
		this.view = view;
	}

	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int dx = v.getWidth() / 2 - x;
		int dy = v.getHeight() / 2 - y;
		if (dx * dx + dy * dy <= v.getWidth() / 2 * v.getWidth() / 2) {
			view.invalidate(dx, dy);
		}
		return true;
	}
}