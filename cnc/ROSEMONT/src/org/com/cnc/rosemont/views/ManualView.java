package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;

import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ManualView extends LinearLayout implements IView{
	//private AnimationSlide animationSlide=new AnimationSlide();
	public ManualView(Context context) {
		super(context);
		config(R.layout.manual);
	}

	public ManualView(Context context, AttributeSet attrs) {
		super(context, attrs);//setAnimation(animationSlide.inFromRightAnimation());
		config(R.layout.manual);
	}

	private void config(int resource_layouy) {
		
		String sv = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(sv);
		
		li.inflate(resource_layouy, this);
		//setAnimation(animationSlide.inFromRightAnimation());
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setText(string.key13);

	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}