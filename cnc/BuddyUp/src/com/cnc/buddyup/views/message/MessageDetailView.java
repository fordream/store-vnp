package com.cnc.buddyup.views.message;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class MessageDetailView extends LinearLayout implements OnClickListener {
	public class Filed{
		public Button fReply;
		public TextView fTVUser;
	}
	private Filed filed = new Filed();
	
	public Filed getFiled() {
		return filed;
	}

	public void setFiled(Filed filed) {
		this.filed = filed;
	}


	protected Handler handler;
	public MessageDetailView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.message_detail);
	}

	public MessageDetailView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.message_detail);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		
		filed.fReply = getButton(R.id.message_detail_btnReply);
		filed.fTVUser = getTextView(R.id.message_detail_tVUserName);
		getButton(R.id.message_detail_btnAddBuddy).setOnClickListener(this);
		getButton(R.id.message_detail_btnDelete).setOnClickListener(this);
		getButton(R.id.message_detail_btnReply).setOnClickListener(this);

	}

	
	public void onClick(View v) {
		if (v.getId() == R.id.message_detail_btnAddBuddy) {
			handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_1));
		} else if (v.getId() == R.id.message_detail_btnDelete) {
			handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_2));
		} else if (v.getId() == R.id.message_detail_btnReply) {
			handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_3));
		}
	}
}