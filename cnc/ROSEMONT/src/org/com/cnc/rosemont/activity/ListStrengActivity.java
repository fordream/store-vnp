package org.com.cnc.rosemont.activity;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.android.Common;
import org.com.cnc.rosemont.R.id;
import org.com.cnc.rosemont.R.layout;
import org.com.cnc.rosemont.activity.commom.Conts;
import org.com.cnc.rosemont.adapter.ListStrengthAdapter;
import org.com.cnc.rosemont.database.DataStore;
import org.com.cnc.rosemont.views.HeaderView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListStrengActivity extends Activity implements OnItemClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(layout.liststreng);
		HeaderView headerView = (HeaderView) findViewById(id.headerView1);
		headerView.showButton(true, false);
		headerView.setOnClick(onClickListener, null);
		headerView.setType(HeaderView.TYPE_LIST_STRENGTH);
		
		DataStore.getInstance().setConfig(Conts.MG_STRENG, true);
		
		addDataExample((ListView) findViewById(id.listView1));
		
	}

	private void addDataExample(ListView listView) {
		List<String> lDatas = new ArrayList<String>();
		int count = getIntent().getIntExtra(Common.KEY01, 0);
		for (int index = 0; index < count; index++) {
			lDatas.add(getIntent().getStringExtra(Common.KEY01 + index));
		}
		ListStrengthAdapter adapter = new ListStrengthAdapter(this, listView,
				lDatas);

		new ListStrengthAdapter(this, listView, lDatas);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent();
		intent.putExtra(Common.KEY01, position);
		setResult(RESULT_OK, intent);
		finish();
	}
}