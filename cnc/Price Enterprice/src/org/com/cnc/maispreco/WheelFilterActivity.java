package org.com.cnc.maispreco;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cnc.maispreco.soap.data.Address;
import com.cnc.maispreco.views.AddressViewControl;

public class WheelFilterActivity extends Activity implements OnClickListener {
	public static final String ARG0 = "arg0";
	public static final String ARG1 = "arg1";
	public static final String ARG2 = "arg2";
	public static final String ARG3 = "arg3";
	private String text;
	public List<Address> lAddresses = null;
	private List<String> list = new ArrayList<String>();
	private WheelView city;
	private TextView tvTextView;
	private int type = AddressViewControl.STATE;

	String cities1[] = new String[] { "New York", "Washington", "Chicago",
			"Atlanta", "Orlando" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cities_layout);
		tvTextView = (TextView) findViewById(R.id.tvWheel);
		type = getIntent().getExtras().getInt(AddressChoiseScreen.ARG0);
		text = getIntent().getExtras().getString(AddressChoiseScreen.ARG1);
		Resources resources = getResources();
		String text = "";
		if (type == AddressViewControl.STATE) {
			text = resources.getString(R.string.estado);
		} else if (type == AddressViewControl.CITY) {
			text = resources.getString(R.string.cidade);
		} else if (type == AddressViewControl.NEIGHBORHOOD) {
			text = resources.getString(R.string.bairro);
		}
		String textCity = getIntent().getExtras().getString(
				AddressChoiseScreen.ARG2);
		String textState = getIntent().getExtras().getString(
				AddressChoiseScreen.ARG3);
		String textNeighborhood = getIntent().getExtras().getString(
				AddressChoiseScreen.ARG4);
		tvTextView.setText(text);
		city = (WheelView) findViewById(R.id.city);
		city.setVisibleItems(2);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.Button01).setOnClickListener(this);
		config(textCity, textState, textNeighborhood);
		updateCities1();
	}

	void config(String textCity, String textState, String textNeighborhood) {
		lAddresses = AddressChoiseScreen.lAddresses;
		for (int i = 0; i < lAddresses.size(); i++) {
			Address address = lAddresses.get(i);
			boolean check = true;
			if(equalsCheck(textCity, address.get(Address.CITY))){
				check = false;
			}
			
			if(equalsCheck(textState, address.get(Address.STATE))){
				check = false;
			}
			
			if(equalsCheck(textNeighborhood, address.get(Address.NEIGHBORHOOD))){
				check = false;
			}
			


			if (check) {
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

	}

	private void updateCities1() {
		cities1 = new String[list.size()];
		int index = 0;
		if (text.equals("")) {

		}
		for (int i = 0; i < list.size(); i++) {
			cities1[i] = list.get(i);
			if (text.endsWith(cities1[i])) {
				index = i;
			}
		}
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities1);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(index);

	}

	public void onClick(View v) {
		if (v.getId() == R.id.button1) {
			finish();
			return;
		}

		try {
			String message = cities1[city.getCurrentItem()];
			Bundle data = new Bundle();
			data.putInt(ARG0, type);
			data.putString(ARG1, message);
			Intent intent = new Intent();
			intent.putExtras(data);
			setResult(RESULT_OK, intent);
			finish();
		} catch (Exception e) {
			finish();
		}
	}

	private boolean exists(List<String> list2, String input) {
		for (int i = 0; i < list2.size(); i++) {
			if (list2.get(i).equals(input)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean equalsCheck(String s1, String s2){
		if(s1.equals("") || s2.equals("")){
			return false;
		}
		
		return !s1.equals(s2);
	}
}
