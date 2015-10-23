package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemCategoriesView extends LinearLayout implements IView{
	private AnimationSlide animationSlide=new AnimationSlide();
	public ItemCategoriesView(Context context) {
		super(context);
		config();
	}

	public ItemCategoriesView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config();
	}

	public void config() {
		//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_categories, this);
	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.ll).setLayoutParams(params);
	}

	public void updateData(String text) {
		((TextView) findViewById(R.id.textView1)).setText(text);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}