package com.icts.shortfilmfestival.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival_en.FestivalTabActivity;
import com.vnp.shortfilmfestival.R;

public class WebViewFragment extends FragmentActivity {

	private static WebView mWebview;
	private ProgressDialog mProgressDialog;
	private static final FrameLayout.LayoutParams ZOOM_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
	
	private static String url = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.webview);
		// Get data
		
		url = getIntent().getExtras().getString("url");
		// Get WebView
		mWebview = (WebView) findViewById(R.id.webView1);
		if (url.equals(ISettings.FESTIVAL_REPORT_EN) || url.equals(ISettings.FESTIVAL_REPORT_JA))
		{
			mWebview.getSettings().setUserAgentString("Chrome");
		}
		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.setInitialScale(25);
		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				if(failingUrl.equals(url)){
					Toast.makeText(WebViewFragment.this, description, Toast.LENGTH_SHORT).show();
				}
					
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		FrameLayout mContentView = (FrameLayout) getWindow().getDecorView()
				.findViewById(android.R.id.content);
		final View zoom = mWebview.getZoomControls();
		mContentView.addView(zoom, ZOOM_PARAMS);
		zoom.setVisibility(View.GONE);
		mWebview.getSettings().setUseWideViewPort(true);
		mWebview.getSettings().setSupportZoom(true);
		mWebview.getSettings().setBuiltInZoomControls(true);
		mWebview.setWebViewClient(new WebViewClient() {
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {              
	                view.loadUrl(url);
	                return true;
	            }
	            public void onLoadResource (WebView view, String url) {
	            	  if (mProgressDialog == null) {
		                	mProgressDialog = new ProgressDialog(WebViewFragment.this);
		                	mProgressDialog.setMessage("Loading...");
		                	mProgressDialog.show();
		                }
	            }
	            public void onPageFinished(WebView view, String url) {
	            	
	                if (mProgressDialog.isShowing()) {
	                	mProgressDialog.dismiss();
	                }
	            }
	        }); 
		 mWebview.loadUrl(url);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
			mWebview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		FestivalTabActivity.onKeyBack();
		super.onBackPressed();
	}

}
