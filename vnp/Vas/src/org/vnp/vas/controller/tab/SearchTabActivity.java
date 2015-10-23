package org.vnp.vas.controller.tab;

import org.vnp.vas.controller.BaseGridActivity;

import android.os.Bundle;

public class SearchTabActivity extends BaseGridActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.showSearch();
		super.showHeaderVeiew("Search");
	}
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.search_tab);
	// }

}
