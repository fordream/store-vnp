package org.com.vnp.shortheart;

import org.com.vnp.caothubongbong.views.PlayView;
import org.com.vnp.shortheart.database.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class CaoThuBongBongScreen extends MBaseActivity {
	private TextView tvScore;
	private int score = 0;
	private TextView tvTime;
	private int time = 60;
	private RunCheck runCheck;
	private PlayView playView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvScore = (TextView) findViewById(R.id.textView2);
		tvTime = (TextView) findViewById(R.id.textView3);
		playView = (PlayView) findViewById(R.id.playView1);
		runCheck = new RunCheck();
		runCheck.execute("");
	}

	protected void onResume() {
		super.onResume();
		playView.setIspause(false);
	}

	protected void onStop() {
		playView.setIspause(true);
		super.onStop();
	}

	protected void onDestroy() {
		super.onDestroy();
		runCheck.close();
	}

	private class RunCheck extends AsyncTask<String, String, String> {
		private boolean isNotExit = true;
		private boolean isEnd = false;

		protected String doInBackground(String... params) {
			while (isNotExit) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}

				updateTime(-1);
				if (time <= 0) {
					isNotExit = false;
					isEnd = true;
					playView.setIspause(true);
				}
			}
			return null;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (isEnd) {
				updateData();
			}
		}

		public void close() {
			isNotExit = false;
		}
	}

	public void updateScore(int score) {
		if (time > 0) {
			this.score += score;
		}
		Runnable action = new Runnable() {
			public void run() {
				tvScore.setText(CaoThuBongBongScreen.this.score + "");
			}
		};
		tvScore.post(action);
	}

	public void updateData() {
		Builder builder = new Builder(this);
		builder.setTitle("Message");
		builder.setCancelable(false);
		builder.setMessage("Score : " + score);
		OnClickListener listener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new DBAdapter(CaoThuBongBongScreen.this).insertScore(score);
				finish();
			}
		};
		builder.setPositiveButton("OK", listener);
		builder.show();
	}

	public void updateTime(int time) {
		if (this.time > 0) {
			if (!playView.isPause()) {
				this.time += time;
			}
		} else {
			this.time = 0;
			playView.setIspause(true);
		}
		Runnable action = new Runnable() {
			public void run() {
				int time1 = CaoThuBongBongScreen.this.time;
				String text = "Time : " + (time1 < 10 ? "0" : "") + time1;
				tvTime.setText(text + "");
			}
		};
		tvTime.post(action);
	}

}