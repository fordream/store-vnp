package org.com.cnc.rosemont.database.table;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.activity.commom.CommonApp;

public class Config {
	public static final String TABLE_NAME = "config";
	public static final String REMOVETHESTARTUP = "removethestartup";
	public static final String WIFIUPDATE = "wifiupdate";
	public static final String APPVERTION = "appversion";
	public static final String LASTUPDATE = "lastupdate";

	private String removethestartup;
	private String wifiupdate;
	private String appversion;
	private String lastupdate;

	public String toString() {
		String text = "";
		text += removethestartup + "\n";
		text += wifiupdate + "\n";
		text += appversion + "\n";
		text += lastupdate + "\n";
		return text;
	}

	public String getRemovethestartup() {
		return removethestartup;
	}

	public void setRemovethestartup(String removethestartup) {
		this.removethestartup = removethestartup;
	}

	public String getWifiupdate() {
		return wifiupdate;
	}

	public void setWifiupdate(String wifiupdate) {
		this.wifiupdate = wifiupdate;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getLastupdate() {
		if (lastupdate == null || "".equals(lastupdate)) {
			return Common.getTimeMMDDYYY();
		}
		return lastupdate;
	}

	public String getLastupdateSetting() {
		if (lastupdate == null || "".equals(lastupdate)) {
			return Common.getTimeDDMMYYY();
		} else {
			return CommonApp.convertDate(lastupdate);
		}
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

}
