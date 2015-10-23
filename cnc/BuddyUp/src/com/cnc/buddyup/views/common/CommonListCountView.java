package com.cnc.buddyup.views.common;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;

import com.cnc.buddyup.R;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class CommonListCountView extends LinearLayout {
	protected Handler handler;

	public CommonListCountView(Context context, AttributeSet attrs, Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.common_list);
	}
	boolean hiddenDate = false;
	boolean hiddenImage = false;
	public CommonListCountView(Context context, Handler handler, boolean hiddenDate) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.common_list);
		this.hiddenDate = hiddenDate;
	}
	
	public CommonListCountView(Context context, Handler handler, boolean hiddenDate, boolean hiddenImage) {
		super(context, handler);
		this.handler = handler;
		config(R.layout.common_list);
		this.hiddenDate = hiddenDate;
		this.hiddenImage = hiddenImage;
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
				CommonItemCountView view = new CommonItemCountView(getContext(), handler, hiddenDate,hiddenImage);
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
			//findViewById(R.id.include1).setVisibility(GONE);
			//findViewById(R.id.include2).setVisibility(GONE);
		}
	}
}