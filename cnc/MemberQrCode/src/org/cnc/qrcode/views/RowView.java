package org.cnc.qrcode.views;


import org.cnc.qrcode.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class RowView extends LinearLayout {
	private RadioButton radioButton;
	
	public RowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public RowView(Context context) {
		super(context);

		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.row, this);
		radioButton = (RadioButton)findViewById(R.id.radioButton1);
		//radioButton.setFocusable(false);

	}
	
	public void setText(String text){
		radioButton.setText(text);
	}
	public void setChecked(boolean isCheck){
		radioButton.setChecked(isCheck);
	}

	
}