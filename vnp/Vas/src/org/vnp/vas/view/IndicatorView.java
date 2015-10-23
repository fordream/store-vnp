package org.vnp.vas.view;

import org.vnp.vas.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * TODO: document your custom view class.
 */
public class IndicatorView extends LinearLayout {

	public IndicatorView(Context context) {
		super(context);
		init();
	}

	public IndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.sample_indicator_view, this);
	}

}
