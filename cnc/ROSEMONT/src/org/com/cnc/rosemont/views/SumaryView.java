package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class SumaryView extends LinearLayout implements IView {
	//private AnimationSlide animationSlide=new AnimationSlide();
	public SumaryView(Context context) {
		super(context);
		config(R.layout.sumary);
	}

	public SumaryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.sumary);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setText(string.key7);
		setUrl();

	}

	public void setUrl() {
		WebViewController controller = (WebViewController) findViewById(R.id.webViewController1);
		controller.loadUrl("http://www.yellowcard.gov.uk");
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
