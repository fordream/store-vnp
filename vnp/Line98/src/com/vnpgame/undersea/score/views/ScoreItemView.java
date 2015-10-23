package com.vnpgame.undersea.score.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vnpgame.line98.R;
import com.vnpgame.line98.Score;

public class ScoreItemView extends LinearLayout {
	public ScoreItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public ScoreItemView(Context context) {
		super(context);
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.scoreitem, this);

	}

	public void setData(Score item) {
		((TextView) findViewById(R.id.textView1)).setText(item.getName());
		((TextView) findViewById(R.id.textView2)).setText("Score : "
				+ item.getScore());
		((TextView) findViewById(R.id.textView3)).setText("Level : "
				+ item.getLevel());
	}
}
