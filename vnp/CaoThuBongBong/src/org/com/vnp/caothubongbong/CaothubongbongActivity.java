package org.com.vnp.caothubongbong;

import org.com.cnc.common.adnroid16.CommonView;
import org.com.cnc.common.adnroid16.activity.CommonActivity;
import org.com.vnp.caothubongbong.asyn.AsynTime;
import org.com.vnp.caothubongbong.config.ConfigMediaplayer;
import org.com.vnp.caothubongbong.db.DBAdapter;
import org.com.vnp.caothubongbong.views.PlayView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class CaothubongbongActivity extends CommonActivity {
	private boolean isStartPlay = false;
	private PlayView playView;
	private AsynTime asynTime;
	private LinearLayout linearLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);

		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		linearLayout.setOnClickListener(null);
		// linearLayout.setVisibility(View.GONE);
		playView = (PlayView) findViewById(R.id.playView1);
		asynTime = new AsynTime(this);
		asynTime.execute("");
		findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				linearLayout.setVisibility(View.GONE);
				asynTime.setPause(false);
			}
		});

	}

	protected void onResume() {
		super.onResume();
		ConfigMediaplayer.start();
	}

	protected void onPause() {
		if (!isStartPlay) {
			ConfigMediaplayer.pause();
		}
		asynTime.setPause(true);
		linearLayout.setVisibility(View.VISIBLE);
		super.onPause();
	}
//	protected void onStop() {
//		if (!isStartPlay) {
//			ConfigMediaplayer.pause();
//		}
//		asynTime.setPause(true);
//		linearLayout.setVisibility(View.VISIBLE);
//		super.onStop();
//	}

	protected void onDestroy() {
		asynTime.setClose(true);
		super.onDestroy();
	}

	public void updateTime() {
		playView.updateTime();
	}

	public boolean isClose() {
		return playView.isClose();
	}

	public void updateScore() {
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				isStartPlay = true;
				startActivity(new Intent(CaothubongbongActivity.this,
						MenuActivity.class));
				finish();
			}
		};
		new DBAdapter(this).insertScore(playView.getScore());
		CommonView.viewDialog(this, "Message",
				"Score : " + playView.getScore(), onClickListener);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isStartPlay = true;
			startActivity(new Intent(this, MenuActivity.class));
		}
		return super.onKeyDown(keyCode, event);
	}
}