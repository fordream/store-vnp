package com.cnc.buddyup.views.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class SearchResultItemView extends LinearLayout implements
		OnClickListener {
	protected Handler handler;

	public SearchResultItemView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.search_reult_item);
	}

	public SearchResultItemView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.search_reult_item);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		getButton(R.id.search_reult_item_btnAddBuddy).setOnClickListener(this);
		getImageView(R.id.ImageView01).setOnClickListener(this);
		getImageView(R.id.ImageView02).setOnClickListener(this);
		getImageView(R.id.imageView1).setOnClickListener(this);
	}

	public void hiddenAllKey() {
	}

	public void onClick(View arg0) {
		if (R.id.search_reult_item_btnAddBuddy == arg0.getId()) {
			handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_4));
		} else {
			handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_5));
		}
	}
}