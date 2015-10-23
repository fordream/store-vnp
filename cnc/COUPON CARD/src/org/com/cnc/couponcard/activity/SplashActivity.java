package org.com.cnc.couponcard.activity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonView;
import org.com.cnc.couponcard.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.qrcode.encoder.QRCode;

public class SplashActivity extends Activity implements OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Common.REQUESTCODE_01) {
			if (resultCode == RESULT_OK) {
				String key = data.getStringExtra("key");
				CommonView.makeText(this, key + "");
				ImageView imageView = (ImageView) findViewById(R.id.imageView1);
				imageView.setImageBitmap(encodeString(key));
				// setContentView(imageView);
			}
		}
	}

	private Bitmap encodeString(String input) {
		try {
			URL aURL = new URL(
					"http://chart.apis.google.com/chart?chs=300x300&cht=qr&choe=UTF-8&chl="
							+ URLEncoder.encode(input, "UTF-8"));
			URLConnection conn = aURL.openConnection();

			conn.connect();

			InputStream is = conn.getInputStream();

			BufferedInputStream bis = new BufferedInputStream(is);

			Bitmap bm = BitmapFactory.decodeStream(bis);

			bis.close();

			is.close();

			return bm;

		} catch (MalformedURLException e) {

		} catch (IOException e) {

		}
		return null;
	}

	public void onClick(View v) {
		if (R.id.button1 == v.getId()) {
			Intent intent = new Intent(this, CaptureActivity.class);
			startActivityForResult(intent, Common.REQUESTCODE_01);
		} else if (R.id.button2 == v.getId()) {

		}
	}
}
