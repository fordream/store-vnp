package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutViewControl extends LinearLayout {
	private TextView tVConentAboutHelp;
	Context context;

	public AboutViewControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config();
	}

	public AboutViewControl(Context context) {
		super(context);
		this.context = context;
		config();
	}

	private void config() {
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.about, this);
		tVConentAboutHelp = (TextView) findViewById(R.id.tVConentAboutHelp);
		new AsyncTask<String, String, String>() {
			protected String doInBackground(String... params) {
				TrackerGoogle.homeTracker(context, "/sobrec");
				return null;
			}
		}.execute("");
	}

	public TextView gettVConentAboutHelp() {
		return tVConentAboutHelp;
	}

	public void settVConentAboutHelp(TextView tVConentAboutHelp) {
		this.tVConentAboutHelp = tVConentAboutHelp;
	}

}