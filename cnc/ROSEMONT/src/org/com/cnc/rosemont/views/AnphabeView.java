package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;
import org.com.cnc.rosemont.items.ItemSearch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnphabeView extends LinearLayout implements IView {
	private TextView tvHeader;

	public AnphabeView(Context context) {
		super(context);
		config(R.layout.anphabe);
	}

	public AnphabeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.anphabe);
	}

	private void config(int resource_layouy) {
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		tvHeader = (TextView) findViewById(R.id.textView1);
	}

	public void updateData(String txtHeader, String txtContent) {
		tvHeader.setText(txtHeader);
	}

	public void updateData(ItemSearch item) {
		tvHeader.setText(item.getTxtHeader());
	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.llItemsearchContent).setLayoutParams(params);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}