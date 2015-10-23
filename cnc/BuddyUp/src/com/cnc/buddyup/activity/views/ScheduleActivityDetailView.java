package com.cnc.buddyup.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class ScheduleActivityDetailView extends LinearLayout {

	public class Filed{
		public Button fReschedule;
		public Button fsendMessage;
	}
	 Filed filed = new Filed();
	 
	


	public Filed getFiled() {
		return filed;
	}


	public void setFiled(Filed filed) {
		this.filed = filed;
	}


	public ScheduleActivityDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.scheduleactivitydetail);
	}

	
	public ScheduleActivityDetailView(Context context) {
		super(context);
		config(R.layout.scheduleactivitydetail);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		
		filed.fReschedule = getButton(R.id.button1);
		filed.fsendMessage = getButton(R.id.button2);
		

	}
	
	
}