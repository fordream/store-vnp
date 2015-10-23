package com.cnc.buddyup.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.common.CommonListView;

public class BuddyGroupListView extends LinearLayout {
	protected Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			((BuddiesScreen)getContext()).addGroupDetail();
		};
	};
	private CommonListView commonList;
	List<CommonItemResquestParcelable> list = new ArrayList<CommonItemResquestParcelable>();
	
	public TextView textView1;
	public BuddyGroupListView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		config(R.layout.buddygrouplistview);
	}

	public void setListParcelable(List<CommonItemResquestParcelable> list) {
		commonList.setListParcelable(list);
	}

	public BuddyGroupListView(Context context, Handler handler) {
		super(context, handler);
		config(R.layout.buddygrouplistview);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		
		textView1 = getTextView(R.id.textView1);
		
		commonList = new CommonListView(getContext(), handler);
		
		
		dataExample();
		
		commonList.setListParcelable(list);
		getLinearLayout(R.id.activity_main_llContent).addView(commonList);
		findViewById(R.id.commonbtnBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((BuddiesScreen)getContext()).onBack();
			}
		});
		findViewById(R.id.commonBtnAdd).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((BuddiesScreen)getContext()).addAddGroup();
			}
		});
	}
	
	private void dataExample() {
		list.add(createCommonItemResquestParcelable("id0", "Kraked"));
		list.add(createCommonItemResquestParcelable("id1", "cobund"));
		list.add(createCommonItemResquestParcelable("id1", "hotblue"));
		list.add(createCommonItemResquestParcelable("id1", "Rod"));
		list.add(createCommonItemResquestParcelable("id1", "spinartist"));
		list.add(createCommonItemResquestParcelable("id1", "briankd1"));
		list.add(createCommonItemResquestParcelable("id1", "Administrator"));
		list.add(createCommonItemResquestParcelable("id1", "Jon"));
		list.add(createCommonItemResquestParcelable("id1", "Kan"));
		list.add(createCommonItemResquestParcelable("id1", "Maria"));
		list.add(createCommonItemResquestParcelable("id1", "Moon"));
	}
	
	public void dataExample1() {
		list.clear();
		list.add(createCommonItemResquestParcelable("id0", "Inline group"));
		list.add(createCommonItemResquestParcelable("id1", "Swimming"));
		list.add(createCommonItemResquestParcelable("id1", "Skating"));
		list.add(createCommonItemResquestParcelable("id1", "Golf"));
		commonList.setListParcelable(list);
	}

	private CommonItemResquestParcelable createCommonItemResquestParcelable(
			String id, String text) {
		CommonItemResquestParcelable parcelable = new CommonItemResquestParcelable();
		parcelable.setId(id);
		parcelable.setTxtView(text);
		return parcelable;
	}
}