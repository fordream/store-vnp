package com.cnc.buddyup.buddy.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.cnc.buddyup.BuddiesScreen;
import com.cnc.buddyup.R;
import com.cnc.buddyup.buddy.adapters.AddMemberAdapter;
import com.cnc.buddyup.views.LinearLayout;

public class AddMemberView extends LinearLayout {
	private ListView listView;
	private AddMemberAdapter adapter;
	private List<String> lData = new ArrayList<String>();
	private Context context;

	public AddMemberView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		config(R.layout.addmember);
	}

	public AddMemberView(Context context) {
		super(context);
		this.context = context;
		config(R.layout.addmember);
	}

	public void config(int resLayout) {
		super.config(resLayout);
		listView = getListView(R.id.listView1);
		HeaderView headerView = new HeaderView(context);
		listView.addHeaderView(headerView);
		//
		dataExample();
		adapter = new AddMemberAdapter(context, lData);
		listView.setAdapter(adapter);
		findViewById(R.id.commonbtnBack).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				((BuddiesScreen)getContext()).onBack();
			}
		});
	}

	private void dataExample() {
		lData.add("Kraked");
		lData.add("Kraked2");
		lData.add("Kraked4");
		lData.add("Kraked7");
		lData.add("Kraked10");
		lData.add("JKraked");
		lData.add("MKraked");
		lData.add("UKraked");
		lData.add("YKraked");
		lData.add("KKraked");
		lData.add("ZKraked");
		lData.add("MOKraked");
		lData.add("MNKraked");
		lData.add("SEKraked");
		
		
	}
}