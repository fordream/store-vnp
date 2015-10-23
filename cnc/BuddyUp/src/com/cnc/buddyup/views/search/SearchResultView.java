package com.cnc.buddyup.views.search;

import java.util.List;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;
import com.cnc.buddyup.views.common.CommonListView;

public class SearchResultView extends LinearLayout {
	protected Handler handler;
	private CommonListView commonListAdvertiser;
	private CommonListView commonListUser;
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == Common.MESSAGE_WHAT_COMMON) {
				CommonItemResquestParcelable parcelable = getCommonItemResquestParcelable(msg);
				handler.sendMessage(Common.createMessage(Common.MESSAGE_WHAT_3,
						parcelable));
			}
		}
	};

	public void setListParcelable(List<CommonItemResquestParcelable> list1,
			List<CommonItemResquestParcelable> list2) {
		commonListAdvertiser.setListParcelable(list1);
		commonListUser.setListParcelable(list2);
	}

	public SearchResultView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.search_result);
	}

	public SearchResultView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.search_result);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		commonListAdvertiser = new CommonListView(getContext(), handler);
		commonListUser = new CommonListView(getContext(), handler2);
		getLinearLayout(R.id.search_result_llAdvertiser).addView(
				commonListAdvertiser);
		getLinearLayout(R.id.search_result_llUser).addView(commonListUser);
	}

	public void hiddenAllKey() {
	}

}