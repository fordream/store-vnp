package com.vn.icts.wendy.controller;

import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.ict.library.activity.BaseActivity;
import com.urbanairship.iap.marketinterface.Consts;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.controller.group.ShopGroupActivity;
import com.vn.icts.wendy.controller.scanner.ScannerActivity;
import com.vn.icts.wendy.util.Const;

public class HomeActivity extends BaseActivity implements OnClickListener {
	private ImageView home_imageView1;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			if (msg.what == Const.MORNING)
				home_imageView1.setBackgroundResource(R.drawable.home_morning);
			else {
				home_imageView1.setBackgroundResource(R.drawable.home_night);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		getView(R.id.imgScanToWinCoupon).setOnClickListener(this);
		getView(R.id.imgNearestShop).setOnClickListener(this);
		home_imageView1 = getView(R.id.home_imageView1);

		new Thread() {
			public void run() {
				while (!isFinishing()) {
					Calendar calendar = Calendar.getInstance(Locale.US);
					int hour = calendar.get(Calendar.HOUR);
					boolean isAM = calendar.get(Calendar.AM_PM) == Calendar.AM;
					Message msg = new Message();
					// night
					if ((isAM && hour < 6) || (!isAM && hour > 6)) {
						msg.what = Const.NIGHT;
					} else {
						msg.what = Const.MORNING;
					}
					handler.sendMessage(msg);
					try {
						Thread.sleep(1000 * 10);
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.imgScanToWinCoupon:
			intent = new Intent(this, ScannerActivity.class);
			break;
		case R.id.imgNearestShop:
			intent = new Intent(this, ShopGroupActivity.class);
			Bundle extras = new Bundle();
			extras.putBoolean(ShopGroupActivity.LOCATION, true);
			intent.putExtras(extras);
		default:
			break;
		}

		if (intent != null) {
			startActivity(intent);
		}
	}
}
