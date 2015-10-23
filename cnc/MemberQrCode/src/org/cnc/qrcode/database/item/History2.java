package org.cnc.qrcode.database.item;

public class History2 {
	public static final String TABLE_NAME = "history";
	public static final String[] COLUMNS = new String[] { "key", "messge",
			"address", "lat", "long", "url" };
	private String address;
	private String lat;
	private String _long;
	private String messge;
	private String key;
	private String url;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String get_long() {
		return _long;
	}

	public void set_long(String _long) {
		this._long = _long;
	}

	public String getMessge() {
		return messge;
	}

	public void setMessge(String messge) {
		this.messge = messge;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
