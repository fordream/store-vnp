package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IActivity;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class AboutView extends LinearLayout implements OnClickListener,IView {
//AnimationSlide animationSlide=new AnimationSlide();
	public AboutView(Context context) {
		super(context);
		config(R.layout.about1);
	}

	public AboutView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.about1);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		
		String sv = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(sv);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		String str="About this app";
		
		headerView.setText(string.key12);
		
		findViewById(R.id.ll123).setOnClickListener(this);

	}

	public void onClick(View v) {
		//setAnimation(animationSlide.inFromRightAnimation());
		((IActivity)getContext()).gotoManual();
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
}