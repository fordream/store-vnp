package org.com.vnp.storeapp;

import java.util.ArrayList;

import org.com.cnc.common.android.Common;
import org.com.cnc.common.android._interface.IHeaderView;
import org.com.cnc.common.android._interface.ISearchBar;
import org.com.cnc.common.android.views1.HeaderView;
import org.com.vnp.storeapp.adapter.Adapter;
import org.com.vnp.storeapp.adapter.items.Item;
import org.com.vnp.storeapp.views.HeaderList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class MyListData extends Activity implements OnClickListener,
		ISearchBar, IHeaderView {
	//private boolean isDiadiem = true;
	private Adapter adapter;
	private HeaderView headerView;
	private String search = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mlist);

		headerView = (HeaderView) findViewById(R.id.headerView1);
		headerView.findViewById(R.id.buttonSearch1).setOnClickListener(this);
		headerView.setTile(getIntent().getIntExtra(Common.KEY01,
				R.string.diadiem));

		//isDiadiem = DataStore.getInstance().getConfig(Conts.DIADIEM, false);

		//headerView.setTile(isDiadiem ? R.string.diadiem : R.string.quanan);

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.addHeaderView(new HeaderList(this));
		adapter = new Adapter(this, new ArrayList<Item>());
		listView.setAdapter(adapter);

		onSearchBarBeforeTextChanged(search);
	}

	public void onClick(View view) {
		if (view == headerView.findViewById(R.id.buttonSearch1)) {
			findViewById(R.id.searchWiget1).setVisibility(View.VISIBLE);
		}
	}

	public void onReload() {
		onSearchBarBeforeTextChanged(search);
	}

	public void onClickButtonSearchBar(String arg0) {

	}

	public void onLickSearchBarBacground(String arg0) {

	}

	public void onSearchBarBeforeTextChanged(String arg0) {
		search = arg0;
//		if (isDiadiem) {
//			Plan table = new Plan();
//			DBAdapter.getInstance().select(table, arg0);
//			adapter.addData(table.create());
//		} else {
//			QuanAn table = new QuanAn();
//			DBAdapter.getInstance().select(table, arg0);
//			adapter.addData(table.create());
//		}
	}

	public void onSearchBarKeySearch(String arg0) {

	}

	public void onClickBackHeader(Object sender) {
		finish();
	}
}