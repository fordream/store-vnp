package org.com.vnp.line98.activity;

import org.com.vnp.line98.database.DBAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoseActivity extends Activity {
	private EditText etName;
	private EditText etScore;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lose);
		etName = (EditText) findViewById(R.id.EditText01);
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(10);
		etName.setFilters(FilterArray);

		etScore = (EditText) findViewById(R.id.editText1);
		etScore.setText(getIntent().getStringExtra("ARG0"));
		// Save
		OnClickListener l = new OnClickListener() {
			public void onClick(View v) {
				if (v.getId() == R.id.Button01) {
					String name = etName.getText().toString();
					if (name == null || "".equals(name)) {
						name = "NONAME";
					}

					String score = etScore.getText().toString();
					int score1 = 0;
					try {
						score1 = Integer.parseInt(score);
					} catch (Exception e) {
					}
					new DBAdapter(LoseActivity.this).insert(name, score1);
				}
				finish();
			}
		};
		findViewById(R.id.Button01).setOnClickListener(l);
		// Close
		findViewById(R.id.Button1).setOnClickListener(l);
	}
}
