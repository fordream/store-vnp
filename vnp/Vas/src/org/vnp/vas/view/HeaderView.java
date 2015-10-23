package org.vnp.vas.view;

import org.vnp.vas.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class HeaderView extends LinearLayout {

	public HeaderView(Context context) {
		super(context);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.headerview, this);
	}

	public void setTitle(String txtheader) {
		TextView textView = (TextView)findViewById(R.id.headerview_tv_title);
		textView.setText(txtheader);
	}
}