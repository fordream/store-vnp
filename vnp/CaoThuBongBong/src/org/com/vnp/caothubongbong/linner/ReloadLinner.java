package org.com.vnp.caothubongbong.linner;

import org.com.vnp.caothubongbong.views.PlayView;

import android.view.View;
import android.view.View.OnClickListener;

public class ReloadLinner implements OnClickListener {
	private PlayView playView;

	public ReloadLinner(PlayView playView) {
		this.playView = playView;
	}

	public void onClick(View v) {
		playView.setNumber_bullet(7);
		playView.showBtnReload(false);
		playView.invalidate();
	}
}