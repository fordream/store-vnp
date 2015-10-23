package org.com.cnc.common.maispreco.request;

import org.com.cnc.maispreco.common.Common;

public class LoginRequest {
	private String code = Common.convertToMD5("cprs");//"beb9ffca5176417df26b4c3e93327598";
	// private String code = "cprs733cbc5e964ba163830701b241c1d77a";
	private String appVersion = "1.1";
	private String latitude;
	private String longitude;

	public LoginRequest(String latitude, String longitude) {
		super();
		
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}