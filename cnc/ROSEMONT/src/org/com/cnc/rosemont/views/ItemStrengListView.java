package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemStrengListView extends LinearLayout implements IView{
	private AnimationSlide animationSlide=new AnimationSlide();
	public ItemStrengListView(Context context) {
		super(context);
		config(0);
	}

	public ItemStrengListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(0);
	}

	private void config(int index) {
	//	setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(org.com.cnc.rosemont.R.layout.itemstrength, this);
	}

	public void setData(String string) {
		((TextView) findViewById(R.id.textView1)).setText(string);
	}

	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		findViewById(R.id.ll).setLayoutParams(params);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
