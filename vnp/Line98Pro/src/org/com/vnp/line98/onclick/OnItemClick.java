package org.com.vnp.line98.onclick;

import org.com.vnp.line98.activity.Line98MenuActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class OnItemClick implements OnClickListener {
	private int x;
	private int y;
	private Line98MenuActivity playView;

	public OnItemClick(int x, int y, Line98MenuActivity playView) {
		this.x = x;
		this.y = y;
		this.playView = playView;
	}

	public void onClick(View v) {
		playView.onClick(x,y);
	}
}
