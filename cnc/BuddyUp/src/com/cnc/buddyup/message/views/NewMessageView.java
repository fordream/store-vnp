package com.cnc.buddyup.message.views;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class NewMessageView extends LinearLayout {

	public class Field {
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public NewMessageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.newmessageview);
	}

	public NewMessageView(Context context) {
		super(context);
		config(R.layout.newmessageview);
	}

	public void config(int resLayout) {
		super.config(resLayout);
	}

	public void hiddenAllKey() {
	}
}