package com.caferhythm.csn.activity;

import com.caferhythm.csn.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

public class PrivacyPolicyActivity extends Activity{
	private WebView webView;
	private String URL = "http://josei0001.murashiki.com/help/privacy";
	private TextView titleTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_content_01_screen);
		//contentTV = (TextView) findViewById(R.id.tv_help_content);
		titleTV = (TextView) findViewById(R.id.tv_help_content_title);
		webView = (WebView) findViewById(R.id.help_001);
		webView.loadUrl(URL);
		titleTV.setText(R.string.privacy_policy);
		findViewById(R.id.bt_help_content01_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}
}