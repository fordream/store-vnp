package com.vnp.camerakorea.view;

import com.vnp.camerakorea.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeaderView extends RelativeLayout {
	private TextView header_text;
	private ImageButton header_btn_left, header_btn_right;

	public HeaderView(Context context) {
		super(context);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.header, this);

		header_text = (TextView) findViewById(R.id.header_text);
		header_btn_left = (ImageButton) findViewById(R.id.header_btn_left);
		header_btn_right = (ImageButton) findViewById(R.id.header_btn_right);
	}

}
