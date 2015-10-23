package com.cnc.buddyup.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class ReScheduleView extends LinearLayout {

	public class Filed{
		public TextView fTVFlexxibility; 
	}
	 Filed filed = new Filed();
	 
	


	public Filed getFiled() {
		return filed;
	}


	public void setFiled(Filed filed) {
		this.filed = filed;
	}


	public ReScheduleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.reschedule);
	}

	
	public ReScheduleView(Context context) {
		super(context);
		config(R.layout.reschedule);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		filed.fTVFlexxibility = getTextView(R.id.TextView1);
		

	}
	
	
}