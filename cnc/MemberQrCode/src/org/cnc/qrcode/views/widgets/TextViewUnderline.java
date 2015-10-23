package org.cnc.qrcode.views.widgets;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;

public class TextViewUnderline extends android.widget.TextView {
	public TextViewUnderline(Context context) {
		super(context);
	}

	public TextViewUnderline(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TextViewUnderline(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setText(CharSequence url, boolean isUnderline) {
		String text = "URL : " + url;
		SpannableString content1 = new SpannableString(text);
		content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
		super.setText(isUnderline ? content1 : text);
	}
}
