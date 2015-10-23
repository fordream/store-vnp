package com.cnc.buddyup.message.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class ReplyMessageView extends LinearLayout {

	public class Field {
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public ReplyMessageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.replymessageview);

	}

	public ReplyMessageView(Context context) {
		super(context);
		config(R.layout.replymessageview);

		findViewById(R.id.commonbtnBack).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						if (getContext() instanceof BuddiesScreen)
							((BuddiesScreen) getContext()).onBack();
					}
				});
	}

	public void config(int resLayout) {
		super.config(resLayout);
	}

	public void hiddenAllKey() {
	}
}