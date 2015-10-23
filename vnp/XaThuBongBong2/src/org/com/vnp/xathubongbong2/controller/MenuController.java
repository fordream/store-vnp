package org.com.vnp.xathubongbong2.controller;

import org.com.vnp.xathubongbong2.R;
import org.com.vnp.xathubongbong2.model.CommonView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuController extends Activity {
	// private Holder holder;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CommonView.hiddenTitleBarAndFullScreen(this);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.memu);
		new Holder();
	}

	private class Holder implements View.OnClickListener {
		Button btnPlay;
		Button btnScore;
		Button btnSetting;
		Button btnAbout;
		Button btnMore;

		public Holder() {
			btnAbout = (Button) findViewById(R.id.btnAbout);
			btnScore = (Button) findViewById(R.id.btnHightScore);
			btnSetting = (Button) findViewById(R.id.btnSetting);
			btnPlay = (Button) findViewById(R.id.btnPlay);
			btnMore = (Button) findViewById(R.id.btnMarket);

			// add Action
			btnMore.setOnClickListener(this);
			btnAbout.setOnClickListener(this);
			btnScore.setOnClickListener(this);
			btnSetting.setOnClickListener(this);
			btnPlay.setOnClickListener(this);
		}

		public void onClick(View v) {
			if (btnMore == v) {
				CommonView.showMarketPublish(MenuController.this,
						"Truong Vuong Van");
			}
		}
	}
}