package org.com.cnc.rosemont.activity;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android.CommonDeviceId;
import org.com.cnc.common.android.CommonNetwork;
import org.com.cnc.common.android.activity.CommonTabActivity;
import org.com.cnc.rosemont.R;
import org.com.cnc.rosemont.R.drawable;
import org.com.cnc.rosemont.R.string;
import org.com.cnc.rosemont.activity.commom.CommonApp;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.handler.HandlerTab;
import org.com.cnc.rosemont.views.TabViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.ViewFlipper;

public class CustomTabsActivity extends CommonTabActivity implements
		OnTabChangeListener {
	private TabViews[] views = new TabViews[4];
	public TabHost tabHost;
	private HandlerTab handlerTab;
	boolean isSecond = false;
	boolean isNetwork = false;
	ViewFlipper flipper;

	private class MHandler extends Handler implements Parcelable {
		public boolean isbackFormCalculator = false;

		public MHandler() {
			isbackFormCalculator = false;
		}

		public void dispatchMessage(Message msg) {
			if (msg.what == 1) {
				Bundle data = msg.getData();
				CommonApp.DATA.id_row = data.getString(Common.KEY03);
				CommonApp.DATA.id = data.getString(Common.KEY04);
				tabHost.setCurrentTab(2);
				return;
			} else {
				isbackFormCalculator = true;
				tabCurent = DataStore.getInstance().getConfig(Conts.TAB_ID, 0);
				tabHost.setCurrentTab(tabCurent);

			}
		}

		public int describeContents() {
			return 0;
		}

		public void writeToParcel(Parcel dest, int flags) {

		}
	};

	MHandler handler = new MHandler();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		handlerTab = new HandlerTab(this);
		isSecond = true;
		handler.isbackFormCalculator = false;
		setTabs();

		new AsyncTask<String, String, String>() {
			private ProgressDialog dialog;

			protected String doInBackground(String... params) {
				handler.post(new Runnable() {
					public void run() {
						dialog = ProgressDialog.show(CustomTabsActivity.this,
								"", getResources().getString(string.loading));
					}
				});

				CommonApp.getDataROSEMONT(CustomTabsActivity.this);
				return null;
			}

			protected void onPostExecute(String result) {
				CommonDeviceId.rescanSdcard(CustomTabsActivity.this);
				if (dialog != null)
					dialog.dismiss();
			};
		}.execute("");
	}

	private void setTabs() {
		tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);

		Bundle data = new Bundle();
		data.putParcelable(Common.KEY01, handlerTab);
		data.putParcelable(Common.KEY02, handler);
		// home
		Intent intent = new Intent(this, HomeActivity.class);
		intent.putExtras(data);
		addTab(intent, 0, drawable.tabhome, drawable.tabhome);

		// search
		intent = new Intent(this, SearchActivity.class);
		intent.putExtras(data);
		addTab(intent, 1, drawable.tabsearch, drawable.tabsearch);

		// calculator
		intent = new Intent(this, CalculatorActivity.class);
		intent.putExtras(data);
		addTab(intent, 2, drawable.tabcalculator, drawable.tabcalculator);

		// setting
		intent = new Intent(this, SettingsActivity.class);
		intent.putExtras(data);
		addTab(intent, 3, drawable.tabsettings, drawable.tabsettings);

		int tabCurent = 0;
		config(tabCurent == 0, 0);
		config(tabCurent == 1, 1);
		config(tabCurent == 2, 2);
		config(tabCurent == 3, 3);
	}

	private void addTab(Intent intent, int index, int resource, int resource1) {
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + index);
		TabViews tabIndicator = new TabViews(this);
		tabIndicator.setImageResource(resource, resource1);
		views[index] = tabIndicator;
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	private int tabCurent = 0;

	public void onTabChanged(String tabId) {

		int tabCurent = 0;
		if (!handler.isbackFormCalculator) {
			if (tabId.equals("tab0")) {
				tabCurent = 0;

				this.tabCurent = 0;
				if (CommonApp.hlHome != null) {
					CommonApp.hlHome.sendEmptyMessage(0);
				}
			}

			if (tabId.equals("tab1")) {
				tabCurent = 1;
				this.tabCurent = 1;
				if (CommonApp.hlSearch != null) {
					CommonApp.hlSearch.sendEmptyMessage(0);
				}
			}

			if (tabId.equals("tab2")) {
				tabCurent = 2;
				if (CommonApp.hlCalculator != null) {
					CommonApp.hlCalculator.sendEmptyMessage(0);
				} 
			}

			if (tabId.equals("tab3")) {
				tabCurent = 3;
				this.tabCurent = 3;
			}
		} else {
			tabCurent = this.tabCurent;
		}

		handler.isbackFormCalculator = false;

		config(tabCurent == 0, 0);
		config(tabCurent == 1, 1);
		config(tabCurent == 2, 2);
		config(tabCurent == 3, 3);
	}

	private void config(boolean b, int index) {
		if (views[index] != null) {
			views[index].setImageResource(b);
		}
	}

	protected void onStop() {
		super.onStop();
		DataStore.getInstance().setConfig(Conts.RE_SEARCH, true);
	}

	@Override
	protected void onPause() {
		isSecond = false;
		super.onPause();

	}

	@Override
	protected void onStart() {
		/*
		 * if ((!isSecond)&&(!CommonNetwork.haveConnectTed(this))) { Intent
		 * intent = new Intent(this, DialogWarnningActivity.class);
		 * startActivity(intent); }
		 */
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

}