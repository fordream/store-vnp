package com.cnc.buddyup;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.message.views.NewMessageView;
import com.cnc.buddyup.message.views.ReplyMessageView;
import com.cnc.buddyup.request.RequestView;
import com.cnc.buddyup.views.buddy.BuddyListInlineView;
import com.cnc.buddyup.views.common.CommonCountView;
import com.cnc.buddyup.views.message.MessageDetailView;

public class MessageScreen extends NActivity2 implements OnClickListener {
	private CommonCountView messageView;
	private MessageDetailView detailView;
	private NewMessageView newMessageView;
	private ReplyMessageView replyMessageView;
	private RequestView requestView;
	private BuddyListInlineView buddyListInlineView;
	private Handler handler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_COMMON) {
				CommonItemResquestParcelable parcelable = getCommonItemResquestParcelable(msg);
				addView(detailView);
			} else if (msg.what == Common.MESSAGE_WHAT_1) {
			} else if (msg.what == Common.MESSAGE_WHAT_2) {
			} else if (msg.what == Common.MESSAGE_WHAT_3) {
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		messageView = new CommonCountView(this, handler, false);

		detailView = new MessageDetailView(this, handler);
		detailView.getFiled().fTVUser.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addView(requestView);
			}
		});

		detailView.getFiled().fReply.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addView(replyMessageView);
			}
		});

		newMessageView = new NewMessageView(this);
		replyMessageView = new ReplyMessageView(this);

		configRequestView();
		buddyListInlineView = new BuddyListInlineView(this, new Handler());
		addView(messageView);
		tempData();
	}

	private void configRequestView() {
		requestView = new RequestView(this);
		OnClickListener onClick = new OnClickListener() {
			public void onClick(View v) {
				addView(buddyListInlineView);
			}
		};
		requestView.getField().fImgView1.setOnClickListener(onClick);
		requestView.getField().fImgView2.setOnClickListener(onClick);
		requestView.getField().fImgView3.setOnClickListener(onClick);
	}

	private void tempData() {
		messageView.setListParcelable(Common.createListparcel(10));
	}

	protected void addView(View view) {
		super.addView(view);
		config();
	}

	private void config() {
		View view = llContent.getChildAt(0);
		getButton(R.id.commonBtnAdd).setVisibility(View.GONE);
		commonBtnAdd.setBackgroundResource(R.drawable.btnnewmessage);
		commonmessageList.setVisibility(View.GONE);
		commonimageView.setVisibility(View.GONE);

		if (view == requestView) {
			getButton(R.id.commonBtnAdd).setVisibility(View.GONE);
			getButton(R.id.commonbtnBack).setVisibility(View.VISIBLE);
			commonmessageList.setVisibility(View.GONE);
			getTextView(R.id.commonETHearder).setText("Addministrator");
			return;
		}

		if (view == buddyListInlineView) {
			getButton(R.id.commonBtnAdd).setVisibility(View.GONE);
			getButton(R.id.commonbtnBack).setVisibility(View.VISIBLE);
			commonmessageList.setVisibility(View.GONE);
			getTextView(R.id.commonETHearder).setText("Cound");
			commonimageView.setVisibility(View.VISIBLE);
			commonimageView.setBackgroundResource(R.drawable.icon1);
			return;
		}

		if (view == messageView) {
			getButton(R.id.commonBtnAdd).setVisibility(View.VISIBLE);
			getButton(R.id.commonbtnBack).setVisibility(View.GONE);
			commonmessageList.setVisibility(View.VISIBLE);
			getTextView(R.id.commonETHearder).setText("");
		} else if (view == detailView) {
			getButton(R.id.commonBtnAdd).setVisibility(View.GONE);
			getButton(R.id.commonbtnBack).setVisibility(View.VISIBLE);
			getTextView(R.id.commonETHearder).setText("Message Detail");
		} else if (view == newMessageView) {
			commonbtnBack.setVisibility(View.VISIBLE);
			commonETHearder.setText("New Message");
		} else if (view == replyMessageView) {
			commonbtnBack.setVisibility(View.VISIBLE);
			commonETHearder.setText("Reply Message");
		}

		getButton(R.id.commonbtnBack).setOnClickListener(this);
		commonBtnAdd.setOnClickListener(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			configBack();
		}
		return true;
	}

	public void onClick(View v) {

		if (v.getId() == commonBtnAdd.getId()) {
			if (llContent.getChildAt(0) == messageView) {
				addView(newMessageView);
			}
			return;
		}
		configBack();
	}

	private void configBack() {
		View view = llContent.getChildAt(0);
		if (addView(view, detailView, messageView)) {
			config();
			// finish();
			return;
		}

		if (addView(view, requestView, detailView)) {
			config();
			return;
		}

		if (addView(view, buddyListInlineView, requestView)) {
			config();
			return;
		}
		if (addView(view, newMessageView, messageView)) {
			config();
			return;
		}

		if (addView(view, replyMessageView, detailView)) {
			config();
			return;
		}

		finish();

	}
}
