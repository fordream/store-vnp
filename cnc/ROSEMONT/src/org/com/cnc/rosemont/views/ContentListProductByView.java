package org.com.cnc.rosemont.views;

import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont._interface.IView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ContentListProductByView extends LinearLayout implements IView {
	public AnimationSlide animationSlide=new AnimationSlide();
	public ContentListProductByView(Context context) {
		super(context);
		config(R.layout.contentlistproductby);
	}

	public ContentListProductByView(Context context, AttributeSet attrs) {
		super(context, attrs);
		config(R.layout.contentlistproductby);
	}

	private void config(int resource_layouy) {
//setAnimation(animationSlide.inFromRightAnimation());
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(resource_layouy, this);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
