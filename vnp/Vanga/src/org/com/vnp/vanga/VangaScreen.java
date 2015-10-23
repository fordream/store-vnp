package org.com.vnp.vanga;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.com.vnp.vanga.view.ItemViewList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ict.library.adapter.CommonBaseAdapter;
import com.ict.library.adapter.CommonGenderView;
import com.ict.library.common.CommonResize;
import com.ict.library.view.CustomLinearLayoutView;

public class VangaScreen extends BaseActivity {
	private ProgressBar progressBar;
	private ListView webView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();

	}

	private void init() {
		// progress Bar
		progressBar = getView(R.id.progressBar1);
		CommonResize._20130408_resizeW960(progressBar, 50, 50);
		CommonResize._20130408_sendViewToPositionW960(progressBar,
				960 - 50 - 50, 200 / 2 - 50 / 2);

		// header
		View Header = getView(R.id.header);
		CommonResize._20130408_resizeW960(Header, 960, 185);

		// header background
		View header_bacground = getView(R.id.header_background);
		CommonResize._20130408_resizeW960(header_bacground, 960, 250);

		// listview
		webView = getView(R.id.listView1);
		// webView.getSettings().setJavaScriptEnabled(true);
		updateScreen.onReceive(null, null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		((VangaApplication) getApplication()).download();

	}

	private BroadcastReceiver updateScreen = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String url = "file:///android_asset/webview/index.html";
			String pathFile = getApplicationContext().getFilesDir()
					.getAbsolutePath() + "/webview/index.html";
			if (new File(pathFile).exists()) {
				// webView.loadUrl("file://" + pathFile);
			} else {
				// webView.loadUrl(url);
			}

			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < 100; i++)
				list.add(new Object());

			webView.setAdapter(new CommonBaseAdapter(VangaScreen.this, list,
					new CommonGenderView() {

						public CustomLinearLayoutView getView(Context arg0,
								Object arg1) {
							return new ItemViewList(arg0);
						}
					}) {
			});
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		registerReceiver(updateScreen, new IntentFilter("screen.update"));
	}

	@Override
	protected void onStop() {
		unregisterReceiver(updateScreen);
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		// if (webView.canGoBack()) {
		// webView.goBack();
		// return;
		super.onBackPressed();
	}
}