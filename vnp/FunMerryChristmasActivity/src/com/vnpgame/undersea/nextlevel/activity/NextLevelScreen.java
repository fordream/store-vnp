package com.vnpgame.undersea.nextlevel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.vnpgame.undersea.nextlevel.views.NextLevelView;

public class NextLevelScreen extends Activity {
	private NextLevelView nextLevelView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		nextLevelView = new NextLevelView(this);
		setContentView(nextLevelView);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
