package org.vnp.vas.controller.tab;

import org.vnp.vas.R;
import org.vnp.vas.controller.tab.history.DownloadActivity;
import org.vnp.vas.controller.tab.history.FavoritesActivity;
import org.vnp.vas.controller.tab.history.ListenedActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ict.library.activity.CommonBaseTabActivity;

public class HistoryTabActivity extends CommonBaseTabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historytab);
		addTab(FavoritesActivity.class, "FavoritesActivity",
				createIndivicator("Favorites"));
		addTab(DownloadActivity.class, "DownloadActivity",
				createIndivicator("Download"));
		addTab(ListenedActivity.class, "ListenedActivity",
				createIndivicator("History"));
	}

	private View createIndivicator(String string) {
		TextView textView = new TextView(this);
		textView.setText(string);
		textView.setBackgroundResource(R.drawable.indicator);
		textView.setGravity(Gravity.CENTER);
		return textView;
	}

}