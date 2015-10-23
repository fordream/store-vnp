package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class CrushingTheEqualityView extends LinearLayout implements IView {
	// private HeaderView headerView;
	public AnimationSlide animationSlide=new AnimationSlide();
	public CrushingTheEqualityView(Context context) {
		super(context);
		config(R.layout.crushing_the_equality);
	}

	public CrushingTheEqualityView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.crushing_the_equality);
	}

	private void config(int resource_layouy) {
		//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
		HeaderView headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.showButton(true, false);
		headerView.setText(string.key16);
		setUrl();

	}

	public void setUrl() {
		WebViewController controller = (WebViewController) findViewById(R.id.webViewController1);
		controller.loadUrl("http://www.legislation.gov.uk/ukpga/2010/15/contents");

	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
