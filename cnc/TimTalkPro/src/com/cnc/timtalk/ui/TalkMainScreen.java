package com.cnc.timtalk.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class TalkMainScreen extends TabActivity {

	private static final int MENU_SETTING = 0;
	private static final int MENU_ABOUTS = 1;
	TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_talk_tab);
		tabHost = getTabHost();
		setTabs();
	}

	private void setTabs() {
		addTab("Friends", R.drawable.tab_friends, FriendsActivity.class);
		addTab("Chats", R.drawable.tab_chats, ChatsActivity.class);
		// addTab("	", R.drawable.tab_search, CameraActivity.class);
		// addRaisedCenterTab(CameraActivity.class);
		// addTab("Home", R.drawable.tab_home, HomeActivity.class);
		// addTab("Search", R.drawable.tab_search, SearchActivity.class);
	}

	private void addTab(String labelId, int drawableId, Class<?> c) {
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, MENU_SETTING, 0, "Settings");
		menu.add(0, MENU_ABOUTS, 1, "About Us");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

}
