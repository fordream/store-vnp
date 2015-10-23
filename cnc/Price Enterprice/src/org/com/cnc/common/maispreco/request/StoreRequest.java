package org.com.cnc.common.maispreco.request;

public class StoreRequest {
	private String itemsPerPage;
	private String localeIdString;
	private String nearBy;
	private String page;
	private String search;
	private String searchType;
	private String token;

	public String getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(String itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public String getLocaleIdString() {
		return localeIdString;
	}

	public void setLocaleIdString(String localeIdString) {
		this.localeIdString = localeIdString;
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
}
