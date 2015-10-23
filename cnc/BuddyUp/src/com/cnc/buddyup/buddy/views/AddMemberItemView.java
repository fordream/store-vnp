package com.cnc.buddyup.buddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.cnc.buddyup.R;
import com.cnc.buddyup.views.LinearLayout;

public class AddMemberItemView extends LinearLayout {
	private CheckBox checkBox;

	public AddMemberItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.addmemberitem);
	}

	public AddMemberItemView(Context context) {
		super(context);
		config(R.layout.addmemberitem);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		checkBox = getCheckBox(R.id.checkBox1);
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(CheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public void setData(String item) {
		checkBox.setText(item);
	}
	
	public void configBack(int resid){
		findViewById(R.id.linearLayout1).setBackgroundResource(resid);
	}
	
	public void showFooter(boolean show){
		findViewById(R.id.linearLayout2).setVisibility(show?VISIBLE:GONE);
	}
}