package org.com.vnp.storeapp;

import org.com.cnc.common.android._interface.IHeaderView;
import org.com.cnc.common.android.database.DataStore;
import org.com.cnc.common.android.views1.HeaderView;
import org.com.vnp.storeapp.common.Conts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuScreen extends Activity implements IHeaderView,
		OnItemClickListener {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);

		HeaderView headerView = new HeaderView(this);
		headerView.hiddenButtonback();
		headerView.setTile("Menu");

		// getListView().addHeaderView(headerView);
		String[] array = getResources().getStringArray(R.array.menu);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array);
		// setListAdapter(adapter);
		ListView listView = ((ListView) findViewById(R.id.listView1));

		listView.addHeaderView(headerView);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	public void onClickBackHeader(Object sender) {
		finish();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 > 0) {
			DataStore.getInstance().setConfig(Conts.DIADIEM, (arg2 - 1) + "");
			Intent intent = new Intent(this, MyListData.class);
			startActivity(intent);
		}
	}
}