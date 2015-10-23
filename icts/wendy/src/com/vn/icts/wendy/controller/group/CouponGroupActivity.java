package com.vn.icts.wendy.controller.group;

import android.os.Bundle;

import com.ict.library.activity.BaseGroupActivity;
import com.vn.icts.wendy.controller.group.coupon.CouponListActivity;

public class CouponGroupActivity extends BaseGroupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addView("CouponListActivity", CouponListActivity.class, null);
		setCanFinish(false);
	}
}
