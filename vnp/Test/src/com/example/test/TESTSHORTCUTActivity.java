package com.example.test;

import com.ict.library.common.CommonAndroid;
import com.ict.library.common.CommonShortCutUtils;
import com.vnp.core.util.v2.AndroidChecker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TESTSHORTCUTActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testshortcut);
		final CommonShortCutUtils commonShortCutUtils = new CommonShortCutUtils(
				this);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AndroidChecker.instance.init(v.getContext());
				boolean value = AndroidChecker.instance.checkPermission("com.android.launcher.permission.UNINSTALL_SHORTCUT");
				//value = AndroidChecker.instance.isExitService();
				CommonAndroid.toast(v.getContext(), value + "");
				//commonShortCutUtils.autoCreateShortCut(MainActivity.class,
				//		R.string.app_name, R.drawable.ic_launcher);
			}
		});

		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				commonShortCutUtils.deleteShortCut(MainActivity.class,
						R.string.app_name, R.drawable.ic_launcher);
			}
		});
	}

}
