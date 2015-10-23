package com.cnc.buddyup.views.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.common.Common;
import com.cnc.buddyup.data.parcelable.common.CommonItemResquestParcelable;
import com.cnc.buddyup.handler.Handler;
import com.cnc.buddyup.views.LinearLayout;

public class CommonItemCountView extends LinearLayout implements
		OnClickListener {
	protected Handler handler;
	private CommonItemResquestParcelable parcelable;
	
	public class Filed{
		public TextView fTVCount;
		public TextView fTVDate;
		public TextView fTVMain;
	}
	
	private Filed filed = new Filed();
	
	public Filed getFiled() {
		return filed;
	}

	public void setFiled(Filed filed) {
		this.filed = filed;
	}

	public CommonItemCountView(Context context, AttributeSet attrs,
			Handler handler) {
		super(context, attrs, handler);
		this.handler = handler;
		config(R.layout.commonitemcountview);
	}
	private boolean hiddenDate = false;
	public CommonItemCountView(Context context, Handler handler, boolean hiddenDate) {
		super(context, handler);
		this.handler = handler;
		this.hiddenDate = hiddenDate;
		config(R.layout.commonitemcountview);
	}
	private boolean hiddenImage = false;
	public CommonItemCountView(Context context, Handler handler, boolean hiddenDate,boolean hiddenImage) {
		super(context, handler);
		this.handler = handler;
		this.hiddenDate = hiddenDate;
		this.hiddenImage = hiddenImage;
		config(R.layout.commonitemcountview);
	}

	public void setParcelable(CommonItemResquestParcelable parcelable) {
		this.parcelable = parcelable;

		setView();
	}

	private void setView() {
		if (parcelable != null) {
			filed.fTVMain.setText(parcelable.getTxtView());
			
			if(hiddenDate){
				filed.fTVDate.setText("");
			}
			
			if(hiddenImage){
				findViewById(R.id.LinearLayout1).setVisibility(GONE);
			}
			//getTextView(R.id.message_item_tvUser).setText(
			//		parcelable.getTxtView());
		}
	}

	public void config(int resLayout) {
		super.config(resLayout);
		// getTextView(R.id.message_item_tvUser).setText(user);
		findViewById(R.id.message_item_rlContent).setOnClickListener(this);
		filed.fTVCount = getTextView(R.id.TextView02);
		filed.fTVDate = getTextView(R.id.TextView01);
		filed.fTVMain = getTextView(R.id.message_item_tvUser);
		if(hiddenDate){
			filed.fTVDate.setText("");
		}
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