package org.com.vnp.caothubongbong;

import java.util.List;

import org.com.cnc.common.adnroid16.CommonView;
import org.com.cnc.common.adnroid16.activity.CommonActivity;
import org.com.vnp.caothubongbong.config.ConfigMediaplayer;
import org.com.vnp.caothubongbong.db.DBAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuActivity extends CommonActivity implements OnClickListener {
	private boolean isStartPlay = false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
	}

	protected void onResume() {
		super.onResume();
		ConfigMediaplayer.start();
	}

	protected void onPause() {
		if (!isStartPlay) {
			ConfigMediaplayer.pause();
		}
		super.onPause();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ConfigMediaplayer.stop();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onClick(View v) {
		if (R.id.button1 == v.getId()) {
			isStartPlay = true;
			Intent intent = new Intent(this, CaothubongbongActivity.class);
			startActivityForResult(intent, 0);
			finish();
		} else if (R.id.button3 == v.getId()) {
			CommonView.showMarketPublish(this, "Truong Vuong Van");
		} else if (R.id.button2 == v.getId()) {
			List<Integer> lIntegers = new DBAdapter(MenuActivity.this)
					.getLScore();
			String txt = "";
			for (int i = 0; i < lIntegers.size(); i++) {
				txt += (i + 1) + " : " + lIntegers.get(i);
				txt += "\n";
			}
			CommonView.viewDialog(MenuActivity.this, "Message", txt);
		}
	}
}