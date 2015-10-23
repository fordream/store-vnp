package org.com.cnc.rosemont.handler;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.activity.CustomTabsActivity;
import org.com.cnc.rosemont.activity.commom.CommonApp;

import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;

public class HandlerTab extends Handler implements Parcelable {
	private CustomTabsActivity tabsActivity;

	public HandlerTab(CustomTabsActivity tabsActivity) {
		this.tabsActivity = tabsActivity;

	}

	public void dispatchMessage(Message message) {
		if (message.what == Common.REQUESTCODE_01) {
			CommonApp.isReload = true;
			tabsActivity.tabHost.setCurrentTab(1);
		} else if (message.what == Common.REQUESTCODE_02) {
			CommonApp.isMoveProductList = true;
			tabsActivity.tabHost.setCurrentTab(0);
		} else if (message.what == Common.REQUESTCODE_03) {
			tabsActivity.tabHost.setCurrentTab(2);
		} else if (message.what == Common.REQUESTCODE_04) {
			CommonApp.isGotoProductDetail = true;
			tabsActivity.tabHost.setCurrentTab(0);
		}
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {

	}

}
