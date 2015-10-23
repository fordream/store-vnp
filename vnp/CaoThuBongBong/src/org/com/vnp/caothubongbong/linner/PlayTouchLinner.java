package org.com.vnp.caothubongbong.linner;

import org.com.vnp.caothubongbong.views.PlayView;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PlayTouchLinner implements OnTouchListener {
	private PlayView playView;

	public PlayTouchLinner(PlayView playView) {
		this.playView = playView;
	}

	public boolean onTouch(View v, MotionEvent event) {
		int newNumbllet = playView.getNumber_bullet();
		if (newNumbllet > 0) {
			newNumbllet--;
			playView.update((int) event.getX(), (int) event.getY());
		}
		
		playView.setNumber_bullet(newNumbllet);
		if (newNumbllet == 0) {
			playView.showBtnReload(true);
		}
		
		playView.invalidate();
		return false;
	}
}
