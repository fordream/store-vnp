package com.vn.icts.wendy.controller.group;

import android.os.Bundle;

import com.ict.library.activity.BaseGroupActivity;
import com.vn.icts.wendy.controller.group.news.NewsListActivity;

public class NewsGroupActivity extends BaseGroupActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addView("NewsListActivity", NewsListActivity.class, null);
		setCanFinish(false);
	}
}
