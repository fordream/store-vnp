package com.vnp.shortfirmfestival_rework.shortfirmfestival_rework;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.vnp.shortfirmfestival_rework.R;
import com.vnp.shortfirmfestival_rework.base.ShortBaseFragment;
import com.vnp.shortfirmfestival_rework.base.ShortFirmBaseActivity;
import com.vnp.shortfirmfestival_rework.view.ShareView;

public class NewsDetailActivity extends ShortFirmBaseActivity implements Runnable {
	private ContentValues contentValues;
	private ShareView shareView;

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.abc_slide_in_left, R.anim.abc_slide_out_right);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetail);
		overridePendingTransition(R.anim.abc_slide_in_right, R.anim.abc_slide_out_left);
		contentValues = getIntent().getParcelableExtra("data");

		shareView = (ShareView) findViewById(R.id.shareView1);
		shareView.setContentValues(contentValues);

		final View new_detail_progressbar = findViewById(R.id.new_detail_progressbar);
		TextView new_detail_name = (TextView) findViewById(R.id.new_detail_name);
		TextView new_detail_date = (TextView) findViewById(R.id.new_detail_date);
		TextView new_detail_description = (TextView) findViewById(R.id.new_detail_description);

		new_detail_name.setText(Html.fromHtml(contentValues.getAsString("title")));
		new_detail_date.setText(Html.fromHtml(contentValues.getAsString("date")));
		new_detail_description.setText(Html.fromHtml(contentValues.getAsString("shortdesc")));

		final WebView new_webview = (WebView) findViewById(R.id.new_webview);

		String _type = contentValues.getAsString("_type");
		if ("theater".equals(_type)) {
			new_detail_progressbar.setVisibility(View.VISIBLE);
			new_webview.setVisibility(View.VISIBLE);
			new_webview.getSettings().setJavaScriptEnabled(true);
			new_webview.setEnabled(false);
			new_webview.setWebChromeClient(new WebChromeClient() {
			});
			new_webview.setWebViewClient(new WebViewClient() {
				@Override
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
					super.onReceivedError(view, errorCode, description, failingUrl);
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					new_webview.setEnabled(true);
					new_detail_progressbar.setVisibility(View.GONE);
				}
			});

			new_webview.loadUrl(contentValues.getAsString("url"));

		} else {
			new_detail_progressbar.setVisibility(View.GONE);
			findViewById(R.id.new_scrollView1).setVisibility(View.VISIBLE);
		}

		new Handler().postDelayed(this, getTimeStartAnimation());
	}

	private void changeFragemtn(ShortBaseFragment allFragement) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame_main, allFragement).commit();
	}

	@Override
	public void run() {

	}
}