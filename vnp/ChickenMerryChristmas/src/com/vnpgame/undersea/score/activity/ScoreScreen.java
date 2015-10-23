package com.vnpgame.undersea.score.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.vnpgame.chickenmerrychristmas.R;
import com.vnpgame.chickenmerrychristmas.Score;
import com.vnpgame.undersea.database.DBAdapter;
import com.vnpgame.undersea.music.Music;
import com.vnpgame.undersea.score.adapters.ScoreAdapter;

public class ScoreScreen extends Activity implements OnClickListener {
	boolean isBack = false;
	private ListView listView;
	private ScoreAdapter adapter;
	private List<Score> lScores = new ArrayList<Score>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		findViewById(R.id.button1).setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listView1);
		adapter = new ScoreAdapter(this, lScores);
		listView.setAdapter(adapter);
		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				List<Score> list = new DBAdapter(ScoreScreen.this)
						.getListScore();
				lScores.clear();
				lScores.addAll(list);
				return null;
			}

			protected void onPostExecute(String result) {
				adapter.notifyDataSetChanged();
			}
		}.execute("");
	}

	protected void onRestart() {
		super.onRestart();
		if (Music.runSoundBack) {
			Music.playerBackground.start();
		}
	}

	protected void onPause() {
		super.onPause();
		if (!isBack) {
			Music.playerBackground.pause();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			isBack = true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onClick(View arg0) {
		finish();
	}
}
