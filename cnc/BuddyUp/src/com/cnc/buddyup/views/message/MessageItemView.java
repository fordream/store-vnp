package com.cnc.buddyup.views.message;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class MessageItemView extends LinearLayout implements OnClickListener {
	protected Handler handler;

	public MessageItemView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.message_item, "");
	}

	public MessageItemView(Context context, Handler handler, String user) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.message_item, user);
	}

	public void config(int resLayout, String user) {
		super.config(resLayout);
		getTextView(R.id.message_item_tvUser).setText(user);
		findViewById(R.id.message_item_rlContent).setOnClickListener(this);
	}

	public void hiddenFooter() {
		getLinearLayout(R.id.message_tiem_llfooter).setVisibility(View.GONE);
	}

	public void onClick(View arg0) {
		handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_0));
	}
}