package com.cnc.buddyup.views.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;
import com.cnc.buddyup.views.common.CommonListView;

public class ActivityMainView extends LinearLayout {
	protected Handler handler;
	private CommonListView commonList;
	List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();

	public ActivityMainView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.activity_main);
	}

	public void setListParcelable(List<CommonItemResquestParcelable> list) {
		commonList.setListParcelable(list);
	}

	public ActivityMainView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.activity_main);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		commonList = new CommonListView(getContext(), handler);
		list.add(createCommonItemResquestParcelable("id0",
				Common.PENDING_ACTIVITY));
		list.add(createCommonItemResquestParcelable("id1",
				Common.SHEDULE_ACTIVITY));
		list.add(createCommonItemResquestParcelable("id2",
				Common.FOLLOW_ACTIVITY));
		commonList.setListParcelable(list);
		getLinearLayout(R.id.activity_main_llContent).addView(commonList);
	}

	private CommonItemResquestParcelable createCommonItemResquestParcelable(
			String id, String text) {
		CommonItemResquestParcelable parcelable = new CommonItemResquestParcelable();
		parcelable.setId(id);
		parcelable.setTxtView(text);
		return parcelable;
	}
}