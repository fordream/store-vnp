package com.example.phomemanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.vnp.core.activity.BaseActivity;
import com.vnp.core.common.CommonAndroid;
import com.vnp.core.common.VNPResize.ICompleteInit;
import com.vnp.core.contact.Contact;
import com.vnp.core.contact.ContactAdapter;
import com.vnp.core.contact.ContactAdapter.ContactCommon;
import com.vnp.core.v2.BaseAdapter;
import com.vnp.core.view.CustomLinearLayoutView;

public class MainActivity extends BaseActivity implements ICompleteInit {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.main_listview);
		listView.setAdapter(baseAdapter = new BaseAdapter(this,
				new ArrayList<Object>()) {

			@Override
			public boolean isShowHeader(int arg0) {
				return false;
			}

			@Override
			public CustomLinearLayoutView getView(Context arg0, Object arg1) {
				return new ItemView(arg0);
			}
		});

		findViewById(R.id.main_content).setVisibility(View.GONE);
		initVNPResize(this, 320, 0, this,
				(TextView) findViewById(R.id.main_txt));

		for (int i : inres) {
			findViewById(i).setOnClickListener(clickListener);
		}

		findViewById(R.id.call_keyborad).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						View v1 = findViewById(R.id.call_form);
						v1.setVisibility(v1.getVisibility() == View.GONE ? View.VISIBLE
								: View.GONE);
					}
				});
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			String value = ((TextView) findViewById(id)).getText().toString();
			String text = ((TextView) findViewById(R.id.call_number)).getText()
					.toString();
			if (value.equals("x")) {
				if (text.length() >= 1)
					((TextView) findViewById(R.id.call_number)).setText(text
							.substring(0, text.length() - 1));
			} else if (value.equals("Call")) {
				CommonAndroid.callPhone(getContext(), text);
			} else if (value.equals("clear")) {
				((TextView) findViewById(R.id.call_number)).setText("");
			} else {
				((TextView) findViewById(R.id.call_number)).setText(text
						+ value);
			}
		}
	};

	private int[] inres = new int[] { R.id.call_0, R.id.call_1, R.id.call_2,
			R.id.call_3, R.id.call_4, R.id.call_5, R.id.call_6, R.id.call_7,
			R.id.call_8, R.id.call_9, R.id.call_call, R.id.call_clear,
			R.id.call_start, R.id.call_shap, R.id.call_clearall };
	private BaseAdapter baseAdapter;

	@Override
	public void complete() {
		resize(findViewById(R.id.main_content), LayoutParams.MATCH_PARENT, 240,
				0);

		for (int res : inres) {
			resize(findViewById(res), 60, 40, 20);
		}
		resize(findViewById(R.id.call_number), LayoutParams.MATCH_PARENT, 50,
				30);
		resize(findViewById(R.id.call_keyborad), 60, 40, 20);
		findViewById(R.id.main_content).setVisibility(View.VISIBLE);

	}

	private class ItemView extends CustomLinearLayoutView {

		public ItemView(Context context) {
			super(context);
			init(R.layout.item);
			resize(findViewById(R.id.item_name), LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 25);
			resize(findViewById(R.id.item_phone), LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 20);
		}

		@Override
		public void setGender() {
			Contact contact = (Contact) getData();
			setTextStr(R.id.item_name, contact.getName());
		}

		@Override
		public void showHeader(boolean arg0) {

		}
	}

	public void setTextStr(int itemName, String name) {
		((TextView) findViewById(itemName)).setText(name);
	}
}