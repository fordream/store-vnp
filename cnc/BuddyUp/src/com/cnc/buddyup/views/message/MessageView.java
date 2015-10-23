package com.cnc.buddyup.views.message;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;
import com.cnc.buddyup.views.common.CommonListView;

public class MessageView extends LinearLayout {
	protected Handler handler;
	private CommonListView commonList;
	public MessageView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.message);
	}
	public void setListParcelable(List<CommonItemResquestParcelable> list){
		commonList.setListParcelable(list);
	}
	public MessageView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.message);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		commonList = new CommonListView(getContext(), handler);
		getLinearLayout(R.id.messge_llContent).addView(commonList);
	}
}