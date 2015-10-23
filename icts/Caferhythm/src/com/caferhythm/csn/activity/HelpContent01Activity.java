package com.caferhythm.csn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

import com.caferhythm.csn.R;

public class HelpContent01Activity extends Activity {
	private WebView webView;
	private String URL = "http://josei0001.murashiki.com/help/about";
	//view on screen
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_content_01_screen);
		webView = (WebView) findViewById(R.id.help_001);
		webView.loadUrl(URL);
		findViewById(R.id.bt_help_content01_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
