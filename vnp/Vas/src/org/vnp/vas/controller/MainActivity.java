package org.vnp.vas.controller;

import org.vnp.vas.R;
import org.vnp.vas.controller.tab.HistoryTabActivity;
import org.vnp.vas.controller.tab.MusicTabActivity;
import org.vnp.vas.controller.tab.SearchTabActivity;
import org.vnp.vas.controller.tab.SettingActivity;
import org.vnp.vas.view.IndicatorView;

import android.os.Bundle;

import com.ict.library.activity.CommonBaseTabActivity;

public class MainActivity extends CommonBaseTabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		addTab(MusicTabActivity.class, "MusicTabActivity", new IndicatorView(
				this));
		addTab(SearchTabActivity.class, "SearchTabActivity", new IndicatorView(
				this));
		addTab(HistoryTabActivity.class, "HistoryTabActivity",
				new IndicatorView(this));

		addTab(SettingActivity.class, "SettingActivity",
				new IndicatorView(this));
	}

}
