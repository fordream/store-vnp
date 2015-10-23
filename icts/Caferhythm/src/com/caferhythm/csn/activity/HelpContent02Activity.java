package com.caferhythm.csn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

import com.caferhythm.csn.R;

public class HelpContent02Activity extends Activity {
	// view on screen
	private WebView webView;
	private String URL = "http://josei0001.murashiki.com/help/faq";
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
		titleTV.setText(R.string.help_text_02);
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
