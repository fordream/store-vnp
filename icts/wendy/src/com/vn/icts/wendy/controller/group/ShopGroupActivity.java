package com.vn.icts.wendy.controller.group;

import android.os.Bundle;

import com.ict.library.activity.BaseGroupActivity;
import com.vn.icts.wendy.controller.group.shop.ShopListActivity;

public class ShopGroupActivity extends BaseGroupActivity {
	public static final String LOCATION = "LOCATION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addView("ShopListActivity", ShopListActivity.class, getIntent()
				.getExtras());
		if (getIntent().getExtras() != null &&getIntent().getExtras().getBoolean(LOCATION, true) ) {
			setCanFinish(true);
		} else {
			setCanFinish(false);
		}
	}
}
