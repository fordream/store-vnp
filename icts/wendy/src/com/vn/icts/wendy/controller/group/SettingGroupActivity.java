package com.vn.icts.wendy.controller.group;

import android.os.Bundle;

import com.ict.library.activity.BaseGroupActivity;
import com.vn.icts.wendy.controller.group.setting.SettingActivity;

public class SettingGroupActivity extends BaseGroupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addView("SettingActivity", SettingActivity.class, null);
		setCanFinish(false);
	}
}
