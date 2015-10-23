package com.cnc.buddyup.views.common;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class CommonListView extends LinearLayout {
	protected Handler handler;

	public CommonListView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.common_list);
	}

	public CommonListView(Context context, Handler handler) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.common_list);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		setListParcelable(null);

	}

	public void setListParcelable(List<CommonItemResquestParcelable> list) {
		addListMessage(list);
	}

	private void addListMessage(List<CommonItemResquestParcelable> list) {
		findViewById(R.id.include1).setVisibility(GONE);
		findViewById(R.id.include2).setVisibility(GONE);
		
		getLinearLayout(R.id.meassage_llContent).removeAllViews();
		if (list != null && list.size() > 0) {
			
			for (int i = 0; i < list.size(); i++) {
				CommonItemView view = new CommonItemView(getContext(), handler);
				view.setParcelable(list.get(i));
				if(i== 0){
					view.setBack(R.drawable.l1);
				}else{
					view.setBack(R.drawable.l2);
				}
				if (i == list.size() - 1) {
					view.hiddenFooter();
					view.setBack(R.drawable.l3);
				}
				if(list.size() == 1){
					view.setBack(R.drawable.l4);
				}
				
				getLinearLayout(R.id.meassage_llContent).addView(view);
			}
		} else if (list != null && list.size() == 0 || list == null) {
		}
	}
}