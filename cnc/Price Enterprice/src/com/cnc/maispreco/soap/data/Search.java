package com.cnc.maispreco.soap.data;

public class Search {
	public static final String CODE = "beb9ffca5176417df26b4c3e93327598";
	public static final String APPVERSION = "1.1";
	// search login
	private String code = CODE;
	private String appVersion = APPVERSION;
	private String latitude = null;
	private String longitude = null;

	// search
	private String localeId = null;
	private String nearBy = null;
	private String page = null;
	private String search = null;
	private String searchType = null;
	private String token = null;

	public Search() {

	}

	public Search(String search, String searchType, String token) {
		this.search = search;
		this.searchType = searchType;
		this.token = token;
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

	public String getLocaleId() {
		return localeId;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public String getNearBy() {
		return nearBy;
	}

	public void setNearBy(String nearBy) {
		this.nearBy = nearBy;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return token + " =  " + search + " = " + searchType;

	}
}