package com.cnc.buddyup.views.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class CommonCountView extends LinearLayout {
	protected Handler handler;
	private CommonListCountView commonList;
	List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();

	public CommonCountView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.commoncountview);
	}

	public void setListParcelable(List<CommonItemResquestParcelable> list) {
		commonList.setListParcelable(list);
	}

	public CommonCountView(Context context, Handler handler, boolean hiddenDate) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.commoncountview, hiddenDate);
	}
	
	public CommonCountView(Context context, Handler handler, boolean hiddenDate,boolean hiddenImage) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.commoncountview, hiddenDate,hiddenImage);
	}
	public void config(int resLayout, boolean hidddenDate,boolean hiddenimage) {
		super.config(resLayout);
		commonList = new CommonListCountView(getContext(), handler, hidddenDate,hiddenimage);
		list.add(createCommonItemResquestParcelable("id0", "Administrator"));
		list.add(createCommonItemResquestParcelable("id1", "Krared"));

		list.add(createCommonItemResquestParcelable("id2", "Coundbound"));
		list.add(createCommonItemResquestParcelable("id1", "Jon"));
		list.add(createCommonItemResquestParcelable("id1", "Ken"));
		list.add(createCommonItemResquestParcelable("id1", "Punch"));
		commonList.setListParcelable(list);
		getLinearLayout(R.id.activity_main_llContent).addView(commonList);
	}
	public void config(int resLayout, boolean hidddenDate) {
		super.config(resLayout);
		commonList = new CommonListCountView(getContext(), handler, hidddenDate);
		list.add(createCommonItemResquestParcelable("id0", "Administrator"));
		list.add(createCommonItemResquestParcelable("id1", "Krared"));

		list.add(createCommonItemResquestParcelable("id2", "Coundbound"));
		list.add(createCommonItemResquestParcelable("id1", "Jon"));
		list.add(createCommonItemResquestParcelable("id1", "Ken"));
		list.add(createCommonItemResquestParcelable("id1", "Punch"));
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