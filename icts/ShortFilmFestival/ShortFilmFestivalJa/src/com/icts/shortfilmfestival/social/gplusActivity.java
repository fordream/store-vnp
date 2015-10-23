package com.icts.shortfilmfestival.social;

import com.icts.shortfilmfestivalJa.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class gplusActivity extends Activity{
	private String mUrlShare;
	private WebView mWebview;
	private ImageView mBackImageView;
	private int mStatus = 0;
	private static final String TAG = "gplusActivity";
	private static final String URL_FINISH_SHARE = "https://plus.google.com/app/plus/";
	private ProgressDialog mProgressDialog;
	private static final FrameLayout.LayoutParams ZOOM_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.FILL_PARENT,
			ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewgplus);
		mStatus = 0;
		mWebview = (WebView) findViewById(R.id.webView1);
		mUrlShare ="https://plus.google.com/share?url=" +  getIntent().getStringExtra("URL");
		Log.d(TAG, mUrlShare);
		mBackImageView = (ImageView) findViewById(R.id.back_button_id);
		mBackImageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				onBackPressed();
			}
		});
		mWebview.getSettings().setJavaScriptEnabled(true);
		mWebview.setInitialScale(25);
		mWebview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				if(failingUrl.equals(mUrlShare)){
					Toast.makeText(gplusActivity.this, description, Toast.LENGTH_SHORT).show();
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
	                Log.d(TAG,"1: " + url);
	                return true;
	            }
	            public void onLoadResource (WebView view, String url) {
	            	  if (mProgressDialog == null) {
		                	mProgressDialog = new ProgressDialog(gplusActivity.this);
		                	mProgressDialog.setMessage("Loading...");
		                	mProgressDialog.show();
		                }
	            }
	            public void onPageFinished(WebView view, String url) {
	            	Log.d(TAG,"2: " +url);
	            	if (url.equals(URL_FINISH_SHARE))
	            	{
	            		mStatus = 1;
	            		onBackPressed();
	            	}
	                if (mProgressDialog.isShowing()) {
	                	mProgressDialog.dismiss();
	                }
	            }
	        }); 
		 mWebview.loadUrl(mUrlShare);
	}
	
	@Override
	public void onBackPressed() {
//		if (mStatus == 0 && mProgressDialog != null && !mProgressDialog.isShowing())
//		{
//			mProgressDialog = null;
//			back();
//		}
//		else {
		super.onBackPressed();
//		}
	}
	
	private void back(){
		mWebview.stopLoading();
		mStatus = 1;
		mWebview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {              
                view.loadUrl(url);
                Log.d(TAG,"1: " + url);
                return true;
            }
            public void onLoadResource (WebView view, String url) {
            	  if (mProgressDialog == null) {
	                	mProgressDialog = new ProgressDialog(gplusActivity.this);
	                	mProgressDialog.setMessage("Loading...");
	                	mProgressDialog.show();
	                }
            }
            public void onPageFinished(WebView view, String url) {
            	Log.d(TAG,"2: " +url);
                if (mProgressDialog.isShowing()) {
                	mProgressDialog.dismiss();
                }
            }
        }); 
		mWebview.loadUrl("https://plus.google.com");
	}
}
