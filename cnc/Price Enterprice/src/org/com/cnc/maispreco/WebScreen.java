package org.com.cnc.maispreco;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebScreen extends Activity {
	
	public static final String URL ="url";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webscreen);
		WebView myWebView = (WebView) findViewById(R.id.webview);
		
		Bundle bundle = getIntent().getExtras();
		
		myWebView.loadUrl(bundle.getString(URL));
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
	}
}
