package org.com.vnp.defenserun;

import java.util.List;

import org.com.vnp.shortheart.database.DBAdapter;

import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

public class MenuScreen extends MBaseActivity implements Runnable {
	Handler handler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse("market://search?q=pub:Truong Vuong Van"));
				startActivity(intent);
			}
		});

		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MenuScreen.this,
						DefenseRunActivity.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<Integer> lScore = new DBAdapter(MenuScreen.this)
						.getlScore();

				String message = "";
				for (int i = 0; i < lScore.size(); i++) {
					message += (i + 1) + " : " + lScore.get(i);
					message += "\n";

					if (i == 4) {
						break;
					}
				}

				for (int i = lScore.size(); i < 5; i++) {
					message += (i + 1) + " : 0";
					message += "\n";
				}

				Builder builder = new Builder(MenuScreen.this);
				builder.setCancelable(false);
				builder.setTitle("List Score");
				builder.setMessage(message);
				builder.setNegativeButton("Close", null);
				builder.show();
			}
		});

		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				new DBAdapter(MenuScreen.this).createDatabase();
				return null;
			}
		}.execute("");

		handler.post(this);
	}

	@Override
	public void run() {
		resize(R.id.button1, 100, 25);
		resize(R.id.button2, 100, 25);
		resize(R.id.button3, 100, 25);
		resize(R.id.linearLayout1, 100, LayoutParams.WRAP_CONTENT);
		setTextSize(R.id.button1, 12);
		setTextSize(R.id.button2, 12);
		setTextSize(R.id.button3, 12);
	}
}
