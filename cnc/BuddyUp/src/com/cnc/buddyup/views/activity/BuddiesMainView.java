package com.cnc.buddyup.views.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.common.CommonListView;

public class BuddiesMainView extends LinearLayout {
	private CommonListView commonList;
	private List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			CommonItemResquestParcelable parcelable = msg.getData().getParcelable(Common.ARG0);
			if(parcelable.getId().equals("id0")){
				((BuddiesScreen)getContext()).addBuddiesList();
			}else{
				((BuddiesScreen)getContext()).addGroupList();
			}
		}
	};
	public BuddiesMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.activity_main);
	}

	public void setListParcelable(List<CommonItemResquestParcelable> list) {
		commonList.setListParcelable(list);
	}

	public BuddiesMainView(Context context) {
		super(context);
		config(R.layout.activity_main);
	}

	public void config(int resLayout) {
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.buddiesmain, this);
		commonList = new CommonListView(getContext(), handler);
		list.add(createCommonItemResquestParcelable("id0",
				Common.BUDDIES_LIST));
		list.add(createCommonItemResquestParcelable("id1",
				Common.GROUP_OF_BUDDIES));

		commonList.setListParcelable(list);
		((LinearLayout)findViewById(R.id.activity_main_llContent)).addView(commonList);
	}

	private CommonItemResquestParcelable createCommonItemResquestParcelable(
			String id, String text) {
		CommonItemResquestParcelable parcelable = new CommonItemResquestParcelable();
		parcelable.setId(id);
		parcelable.setTxtView(text);
		return parcelable;
	}
}