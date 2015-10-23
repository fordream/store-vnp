package org.com.vnp.springgarden;

import org.com.vnp.springgarden.views.PlayView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PlayActivity extends Activity {
	private PlayView playView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		playView = new PlayView(this);
		setContentView(R.layout.play);
		playView = (PlayView) findViewById(R.id.playView1);

		playView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				if (arg1.getAction() == MotionEvent.ACTION_UP) {
					playView.clear();
				} else {
					playView.setNewPoint(arg1);
				}
				return true;
			}
		});
	}
}
