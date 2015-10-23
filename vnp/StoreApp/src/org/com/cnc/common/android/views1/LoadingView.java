package org.com.cnc.common.android.views1;

import org.com.vnp.storeapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoadingView extends LinearLayout {

	private TextView tvLoadingMessage;

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public LoadingView(Context context) {
		super(context);
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.loadding, this);
		tvLoadingMessage = (TextView) findViewById(R.id.textView1);
		findViewById(R.id.llContent).setOnClickListener(null);
	}

	public void setLoadingMessage(String message) {
		tvLoadingMessage.setText(message);
	}

}
