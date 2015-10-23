package com.cnc.buddyup.activity.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class FollowUpDetailView extends LinearLayout {

	public class Filed{
		public TextView fDepend; 
		public TextView fSkillLevel;
		public TextView fOverRating;
	}
	 Filed filed = new Filed();
	 
	


	public Filed getFiled() {
		return filed;
	}


	public void setFiled(Filed filed) {
		this.filed = filed;
	}


	public FollowUpDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.followupdetail);
	}

	
	public FollowUpDetailView(Context context) {
		super(context);
		config(R.layout.followupdetail);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		filed.fDepend = getTextView(R.id.TextView06);
		filed.fSkillLevel = getTextView(R.id.TextView03);
		filed.fOverRating = getTextView(R.id.TextView02);
		

	}
	
	
}