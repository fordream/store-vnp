package org.com.vnp.defenserun;

import org.com.vnp.defenserun.data.BitmapCommon;
import org.com.vnp.defenserun.views.PlayView;
import org.com.vnp.shortheart.database.DBAdapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class DefenseRunActivity extends Activity {
	private TextView tvScore;
	private TextView tvTime;
	private int socre = 0;
	private int time = 60;
	private Run run;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		BitmapCommon.create(this);
		tvScore = (TextView) findViewById(R.id.textView1);
		tvTime = (TextView) findViewById(R.id.textView2);
		updateScore(0);
		updateTime(0);
		final PlayView playView = (PlayView) findViewById(R.id.playView1);
		playView.post(new Runnable() {
			public void run() {
				int x = playView.getWidth() / 2;
				int y = playView.getHeight();
				BitmapCommon.POINT.x = x;
				BitmapCommon.POINT.y = y;
			}
		});

		run = new Run();
		run.execute("");
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (run != null) {
				run.setPause(true);
			}
			Builder builder = new Builder(DefenseRunActivity.this);
			builder.setCancelable(false);
			builder.setTitle("Message");
			builder.setMessage("Do you want to back menu?");
			builder.setNegativeButton("NO", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (run != null) {
						run.setPause(false);
					}
				}
			});
			builder.setPositiveButton("Yes", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					if (run != null) {
						run.setStop(true);
					}
					finish();
				}
			});
			builder.show();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void updateScore(int score1) {
		socre += score1;
		tvScore.post(new Runnable() {
			public void run() {
				tvScore.setText("Score : " + socre);
			}
		});
	}

	public void updateTime(int time1) {
		time += time1;
		tvTime.post(new Runnable() {
			public void run() {
				String text = "Time : ";
				text += (time < 10 ? "0" : "") + time;
				tvTime.setText(text);
			}
		});
	}

	private class Run extends AsyncTask<String, String, String> {
		private boolean isStop = false;
		private boolean isPause = false;

		protected String doInBackground(String... params) {
			while (time > 0 && !isStop) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				while (isPause) {
					if (isStop) {
						break;
					}
				}
				updateTime(-1);
			}
			return null;
		}

		public void setPause(boolean b) {
			isPause = b;
		}

		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (!isStop) {
				Builder builder = new Builder(DefenseRunActivity.this);
				builder.setCancelable(false);
				builder.setTitle("Do you save score?");
				builder.setMessage("Score of you : " + socre);
				builder.setNegativeButton("NO", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
				builder.setPositiveButton("Yes", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						new DBAdapter(DefenseRunActivity.this)
								.insertScore(socre);
						finish();
					}
				});
				builder.show();
			}
		}

		public void setStop(boolean isStop) {
			this.isStop = isStop;
		}
	}

	protected void onPause() {
		if (run != null) {
			run.setPause(true);
		}
		super.onPause();
	}

	protected void onStop() {
		if (run != null) {
			run.setPause(true);
		}
		super.onStop();
	}

	protected void onResume() {
		super.onResume();
		if (run != null) {
			run.setPause(false);
		}
	}

	protected void onDestroy() {
		if (run != null) {
			run.setStop(true);
		}
		super.onDestroy();
	}
}