package org.com.vnp.storeapp;

import org.com.cnc.common.android._interface.IHeaderView;
import org.com.cnc.common.android._interface.ISearchBar;
import org.com.cnc.common.android.views1.SearchWiget;

import android.app.Activity;
import android.os.Bundle;

public class SearchActivity extends Activity implements ISearchBar, IHeaderView {

	private SearchWiget searchWiget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView((searchWiget = new SearchWiget(this)));
	}

	@Override
	protected void onResume() {
		super.onResume();
		searchWiget.showKeyBoard();
	}

	public void onClickButtonSearchBar(String arg0) {

	}

	public void onLickSearchBarBacground(String arg0) {

	}

	public void onSearchBarBeforeTextChanged(String arg0) {

	}

	public void onSearchBarKeySearch(String arg0) {

	}

	public void onClickBackHeader(Object sender) {

	}

}
