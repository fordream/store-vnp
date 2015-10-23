package org.com.cnc.maispreco;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cnc.maispreco.adpters.AddressChoiseApater;
import com.cnc.maispreco.soap.data.Address;
import com.cnc.maispreco.views.AddressViewControl;

public class AddressChoiseScreen extends Activity implements
		OnItemClickListener {

	public static final String ARG0 = "arg0";
	public static final String ARG1 = "arg1";
	public static final String ARG2 = "arg2";
	public static final String ARG3 = "arg3";
	public static final String ARG4 = "arg4";

	public static List<Address> lAddresses = new ArrayList<Address>();
	private ListView lV;
	int type = AddressViewControl.STATE;
	ArrayAdapter<String> adapter;

	private List<String> list = new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addess_choose);
		lV = (ListView) findViewById(R.id.listView1);
		lV.setOnItemClickListener(this);

		type = getIntent().getExtras().getInt(ARG0);

		adapter = new AddressChoiseApater(this, list);

		lV.setAdapter(adapter);
		config();
	}

	void config() {
		if (lAddresses != null) {
			for (int i = 0; i < lAddresses.size(); i++) {
				if (type == AddressViewControl.STATE) {
					String input = lAddresses.get(i).get(Address.STATE);
					if (!exists(list, input)) {
						if (input != null) {
							list.add(input);
						}
					}
				} else if (type == AddressViewControl.CITY) {
					String input = lAddresses.get(i).get(Address.CITY);
					if (!exists(list, input)) {
						if (input != null) {
							list.add(input);
						}
					}
				} else if (type == AddressViewControl.NEIGHBORHOOD) {
					String input = lAddresses.get(i).get(Address.NEIGHBORHOOD);
					if (!exists(list, input)) {
						if (input != null) {
							list.add(input);
						}
					}
				}

			}
		}

		adapter.notifyDataSetChanged();
	}

	private boolean exists(List<String> list2, String input) {
		for (int i = 0; i < list2.size(); i++) {
			if (list2.get(i).equals(input)) {
				return true;
			}
		}
		return false;
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		String search = list.get(arg2);

		Bundle data = new Bundle();
		data.putInt(ARG0, type);
		data.putString(ARG1, search);
		Intent intent = new Intent();
		intent.putExtras(data);
		setResult(RESULT_OK, intent);
		finish();
	}
}
