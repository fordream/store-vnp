package com.cnc.buddyup.views.buddy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class BuddyListAdministratorView extends LinearLayout implements
		OnClickListener {
	protected Handler handler;

	public class Field {
		public Button btnSendMessage;
	}

	private Field field = new Field();

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public BuddyListAdministratorView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.buddy_list_adminstrator);
	}

	public BuddyListAdministratorView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.buddy_list_adminstrator);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		getImageView(R.id.ImageView02).setOnClickListener(this);
		getImageView(R.id.ImageView01).setOnClickListener(this);
		getImageView(R.id.imageView1).setOnClickListener(this);

		field.btnSendMessage = getButton(R.id.Button01);
		field.btnSendMessage.setOnClickListener(this);
		findViewById(R.id.commonbtnBack).setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						((BuddiesScreen) getContext()).onBack();
					}
				});
	}

	public void onClick(View v) {
		// handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_0));
		if (R.id.ImageView02 == v.getId()) {
			((BuddiesScreen) getContext()).addBuddyListInlineView();
		} else if (R.id.ImageView01 == v.getId()) {
			((BuddiesScreen) getContext()).addBuddyListInlineView();
		} else if (R.id.imageView1 == v.getId()) {
			((BuddiesScreen) getContext()).addBuddyListInlineView();
		}else if (field.btnSendMessage.getId() == v.getId()) {
			((BuddiesScreen) getContext()).addRepLyMessage();
		}
	}
}