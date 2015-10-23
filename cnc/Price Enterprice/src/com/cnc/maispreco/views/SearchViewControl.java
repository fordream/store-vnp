package com.cnc.maispreco.views;

import com.cnc.maispreco.soap.data.Product;

import android.content.Context;

public class SearchViewControl extends CommonHomeViewControl {
	public SearchViewControl(Context context, Product product) {
		super(context, product);
	}

	private String _strSearch;

	public String get_strSearch() {
		return " \"" + _strSearch + "\"";
	}

	public String getStrSearch() {
		return _strSearch;
	}

	public void set_strSearch(String _strSearch) {
		this._strSearch = _strSearch;
	}
}