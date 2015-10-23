package com.vn.icts.wendy.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import com.ict.library.activity.BaseTabActivity;
import com.vn.icts.wendy.R;
import com.vn.icts.wendy.controller.group.CouponGroupActivity;
import com.vn.icts.wendy.controller.group.NewsGroupActivity;
import com.vn.icts.wendy.controller.group.SettingGroupActivity;
import com.vn.icts.wendy.controller.group.ShopGroupActivity;
import com.vn.icts.wendy.controller.scanner.ScannerActivity;
import com.vn.icts.wendy.view.TabBarView;
import com.vn.icts.wendy.view.TabBarView.OnCallBackTab;

public class HomeTabActivity extends BaseTabActivity implements OnCallBackTab {
	private TabHost tabHost;
	private TabBarView tabBarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_tab);

		this.tabHost = getTabHost();

		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// add home tab
		intent = new Intent().setClass(this, HomeActivity.class);
		spec = tabHost.newTabSpec("HomeActivity").setContent(intent);
		spec.setIndicator("HomeActivity",
				getResources().getDrawable(R.drawable.ic_action_search));
		tabHost.addTab(spec);

		// add tab Shop
		intent = new Intent().setClass(this, ShopGroupActivity.class);
		spec = tabHost.newTabSpec("ShopGroupActivity").setContent(intent);
		spec.setIndicator("ShopGroupActivity",
				getResources().getDrawable(R.drawable.ic_action_search));
		tabHost.addTab(spec);

		// add Tab Coupon
		intent = new Intent().setClass(this, CouponGroupActivity.class);
		spec = tabHost.newTabSpec("CouponGroupActivity").setContent(intent);
		spec.setIndicator("CouponGroupActivity",
				getResources().getDrawable(R.drawable.ic_action_search));
		tabHost.addTab(spec);

		// add Tab News
		intent = new Intent().setClass(this, NewsGroupActivity.class);
		spec = tabHost.newTabSpec("NewsGroupActivity").setContent(intent);
		spec.setIndicator("NewsGroupActivity",
				getResources().getDrawable(R.drawable.ic_action_search));
		tabHost.addTab(spec);
		// Add Tab Search
		intent = new Intent().setClass(this, SettingGroupActivity.class);
		spec = tabHost.newTabSpec("SettingGroupActivity").setContent(intent);
		spec.setIndicator("SettingGroupActivity",
				getResources().getDrawable(R.drawable.ic_action_search));
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		tabBarView = getView(R.id.tabBarView1);
		tabBarView.setOnCallBackTab(this);
	}

	@Override
	public void onCallBackTab(int position) {
		if (position == 0 || position == 1) {
			tabHost.setCurrentTab(position + 1);
		} else if (position == 3 || position == 4) {
			tabHost.setCurrentTab(position);
		} else if (position ==2) {
			// show scan
			Intent intent = new Intent(this,ScannerActivity.class);
			startActivity(intent);
		}

		
	}

	@Override
	public void onBackPressed() {
		Log.w("Curent Tab", tabHost.getCurrentTab() + "");
		if (tabHost.getCurrentTab() != 0) {
			tabHost.setCurrentTab(0);
			tabBarView.setCurentTab(-1);
			return;
		}
		super.onBackPressed();
	}
}
