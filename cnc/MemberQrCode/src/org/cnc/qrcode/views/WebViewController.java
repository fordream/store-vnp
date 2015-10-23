package org.cnc.qrcode.views;

import org.cnc.qrcode.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewController extends WebView {

	public WebViewController(Context context) {
		super(context);

		config();
	}

	public WebViewController(Context context, AttributeSet attrs) {
		super(context, attrs);

		config();
	}

	public WebViewController(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		config();
	}

	private void config() {
		getSettings().setJavaScriptEnabled(true);
		getSettings().setBuiltInZoomControls(false);
		getSettings().setSupportZoom(true);
		setInitialScale(1);
		setWebViewClient(new HelloWebViewClient());
		setWebChromeClient(webChromeClient);
		getSettings().setJavaScriptEnabled(true);

	}

	private ProgressDialog dialog;

	private void _showDialog(String strView) {
		Resources resources = getResources();
		strView = resources.getString(R.string.loading_trans);

		dialog = ProgressDialog.show(getContext(), null, strView, true);
	}

	private void hiddenDialog() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	private class HelloWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			hiddenDialog();
		}
	}

	private WebChromeClient webChromeClient = new WebChromeClient() {

		public void onProgressChanged(WebView view, int progress) {
			if (dialog == null) {
				Resources resources = getResources();
				String strView = resources.getString(R.string.loading_trans);
				_showDialog(strView);
			}
			if (progress == 100) {
				hiddenDialog();
			}
		}
	};
}