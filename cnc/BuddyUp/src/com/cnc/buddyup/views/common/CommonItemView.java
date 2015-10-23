package com.cnc.buddyup.views.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class CommonItemView extends LinearLayout implements OnClickListener {
	protected Handler handler;
	private CommonItemResquestParcelable parcelable;

	public CommonItemView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.message_item);
	}

	public void setParcelable(CommonItemResquestParcelable parcelable) {
		this.parcelable = parcelable;

		setView();
	}

	private void setView() {
		if (parcelable != null) {
			getTextView(R.id.message_item_tvUser).setText(
					parcelable.getTxtView());
		}
	}

	public CommonItemView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.message_item);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		findViewById(R.id.message_item_rlContent).setOnClickListener(this);
	}

	public void hiddenFooter() {
		getLinearLayout(R.id.message_tiem_llfooter).setVisibility(View.GONE);
	}
	
	public void setBack(int res) {
		findViewById(R.id.message_item_rlContent).setBackgroundResource(res);
	}

	public void onClick(View arg0) {
		handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_COMMON,
				parcelable));
	}
}