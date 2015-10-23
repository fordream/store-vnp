package org.cnc.qrcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.cnc.qrcode.common.Common;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowWebActivity extends Activity {
	final Activity activity = this;
	private String url;
	private static int index = 0;
	private String extStorageDirectory = Environment
			.getExternalStorageDirectory().toString();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.show_web_layout);
		WebView webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		Bundle extras = getIntent().getExtras();
		url = extras.getString("msgAns");
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				String title = Common.getText(ShowWebActivity.this,
						R.string.loading_trans);
				activity.setTitle(title);
				activity.setProgress(progress * 100);
				if (progress == 100)
					activity.setTitle(R.string.app_name_trans);
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		webView.loadUrl(url);
		new Download().execute("");

	}

	private Handler handler = new Handler();

	private class Download extends AsyncTask<String, String, String> {
		boolean check = false;

		protected String doInBackground(String... params) {
			handler.post(new Runnable() {

				public void run() {
					Date todayD = new Date(System.currentTimeMillis());
					SimpleDateFormat dayFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					String todayS = dayFormat.format(todayD.getTime());
					String arrDate[] = todayS.split("/");
					String d = arrDate[0];
					String m = arrDate[1];
					String n = arrDate[2];
					index = index + 1;
					String nameOfImage = "DQR_" + n + "_" + m + "_" + d + "_"
							+ index + ".png";
					if (loadImageURL(url, nameOfImage)) {
						String message = Common
								.getText(
										ShowWebActivity.this,
										R.string.Image_XXX_has_been_download_to_sdcard_trans);
						message = message.replace("XXX", nameOfImage);
						Toast toast = Toast.makeText(ShowWebActivity.this,
								message, Toast.LENGTH_LONG);
						toast.show();
					} else {
						check = true;
					}
					try {
						rescanSdcard();
					} catch (Exception e) {
					}
				}
			});
			return null;
		}

		protected void onPostExecute(String result) {
			if (check) {
				setResult(RESULT_OK);
			}
		}
	};

	private void rescanSdcard() throws Exception {
		// Intent scanIntent = new Intent(
		// Intent.ACTION_MEDIA_MOUNTED,
		// Uri.parse("file://" + Environment.getExternalStorageDirectory()));
		new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"
				+ Environment.getExternalStorageDirectory()));
		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addDataScheme("file");
		sendBroadcast(new Intent(
				Intent.ACTION_MEDIA_MOUNTED,
				Uri.parse("file://" + Environment.getExternalStorageDirectory())));
	}

	public Boolean loadImageURL(String URL, String title) {
		Bitmap bitmap = null;
		InputStream in = null;
		bitmap = BitmapFactory.decodeFile("/mnt/sdcard/" + title);
		if (bitmap != null) {
			return false;
		} else {
			try {
				in = openHttpConnection(URL);
				bitmap = BitmapFactory.decodeStream(in);
				saveImageCache(title, bitmap);
				in.close();
			} catch (Exception e1) {
				return false;
			}
		}
		return true;
	}

	public void saveImageCache(String titleImage, Bitmap bm) throws Exception {
		OutputStream outStream = null;
		File file = new File(extStorageDirectory + "/download/", titleImage);
		outStream = new FileOutputStream(file);
		bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		outStream.flush();
		outStream.close();
	}

	private InputStream openHttpConnection(String strURL) throws IOException {
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpConn.getInputStream();
			}
		} catch (Exception ex) {
		}
		return inputStream;
	}

}