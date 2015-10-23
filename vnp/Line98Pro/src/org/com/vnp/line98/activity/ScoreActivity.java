package org.com.vnp.line98.activity;

import java.util.List;

import org.com.vnp.line98.database.DBAdapter;
import org.com.vnp.line98.database.item.Score;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends Activity {
	private TextView tvHeader, tV1, tV2, tV3, tV4, tV5;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		tvHeader = (TextView) findViewById(R.id.textView1);
		tV1 = (TextView) findViewById(R.id.textView2);
		tV2 = (TextView) findViewById(R.id.TextView04);
		tV3 = (TextView) findViewById(R.id.TextView03);
		tV4 = (TextView) findViewById(R.id.TextView02);
		tV5 = (TextView) findViewById(R.id.TextView01);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"font/Chantelli_Antiqua.ttf");
		font = Typeface.createFromAsset(getAssets(),
				"font/BRUSHSCI.TTF");
		
		tvHeader.setTypeface(font);
		tV1.setTypeface(font);
		tV2.setTypeface(font);
		tV3.setTypeface(font);
		tV4.setTypeface(font);
		tV5.setTypeface(font);

		addDData();
	}

	private void addDData() {
		List<Score> lData = new DBAdapter(this).getLScore();
		addValue(tV1, lData, lData.size() - 1, 1);
		addValue(tV2, lData, lData.size() - 2, 2);
		addValue(tV3, lData, lData.size() - 3, 3);
		addValue(tV4, lData, lData.size() - 4, 4);
		addValue(tV5, lData, lData.size() - 5, 5);
		// Log.i("Count", lData.size() + "");
	}

	private void addValue(TextView tv, List<Score> lData, int index,
			int indexText) {
		try {
			Score score = lData.get(index);
			String name = score.getName();
			if (name == null) {
				name = "          ";
			}

			while (name.length() < 10) {
				name += " ";
			}

			name += " ";
			String score1 = score.getScore() + "";
			String textView = indexText + ". " + name + "." + score1;
			tv.setText(textView);
		} catch (Exception e) {
		}

	}
}
