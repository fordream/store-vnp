package vn.com.vega.music.view.custom;

import vn.com.vega.chacha.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoaddingMore extends LinearLayout {
	private TextView progressTextView;

	public LoaddingMore(Context context) {
		super(context);
		init();
	}

	public LoaddingMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.view_loadding, this);

		progressTextView = (TextView) findViewById(R.id.textView1);
	}

	public void setText(int resource) {
		progressTextView.setText(resource);
	}
}
