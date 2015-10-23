package org.com.vnp.xathubongbong2.views.widget;

import org.com.vnp.xathubongbong2.model.Font;

import android.content.Context;
import android.util.AttributeSet;

public class EditText extends android.widget.EditText {
	public static final String FONT_AGENCYB = "AGENCYB.TTF";

	public EditText(Context context) {
		super(context);
		config();
	}

	public EditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public EditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		config();
	}

	private void config() {
		setTypeface();
	}

	private void setTypeface() {
		Font.setTypefaceFromAsset(getContext(), this, Font.ALGER);
	}
}
