package org.com.cnc.common.android.views1;

import org.com.cnc.common.android._interface.IHeaderView;
import org.com.vnp.storeapp.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HeaderView extends RelativeLayout implements OnClickListener {

	private TextView title;

	private ButtonBack buttonBack;

	public HeaderView(Context context) {
		super(context);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li;
		li = (LayoutInflater) getContext().getSystemService(service);
		li.inflate(R.layout.header, this);

		buttonBack = (ButtonBack) findViewById(R.id.buttonBack1);

		title = (TextView) findViewById(R.id.textView1);

		buttonBack.setOnClickListener(this);
	}

	public void setTile(String txt_title) {
		title.setText(txt_title);
	}

	public void setTile(int res) {
		title.setText(res);
	}

	public void setOnClickBackListener(OnClickListener l) {
		buttonBack.setOnClickListener(l);
	}

	public int getIdBack() {
		return buttonBack.getId();
	}

	public void onClick(View v) {
		if (getContext() instanceof IHeaderView) {
			((IHeaderView) getContext()).onClickBackHeader(null);
		}
	}

	public void hiddenButtonback() {
		buttonBack.setVisibility(GONE);
	}
}