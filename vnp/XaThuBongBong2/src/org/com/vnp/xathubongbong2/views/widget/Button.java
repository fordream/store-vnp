package org.com.vnp.xathubongbong2.views.widget;

import org.com.vnp.xathubongbong2.model.Font;

import android.content.Context;
import android.util.AttributeSet;

public class Button extends android.widget.Button {
	public Button(Context context) {
		super(context);
		config();
	}

	public Button(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public Button(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		config();
	}

	private void config() {
		Font.setTypefaceFromAsset(getContext(), this, Font.ALGER);
	}
}