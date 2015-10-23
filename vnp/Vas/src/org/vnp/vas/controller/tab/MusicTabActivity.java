package org.vnp.vas.controller.tab;

import org.vnp.vas.R;
import org.vnp.vas.controller.tab.music.AllMusicActivity;
import org.vnp.vas.controller.tab.music.GenesActivity;
import org.vnp.vas.controller.tab.music.PopularActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.ict.library.activity.CommonBaseTabActivity;

public class MusicTabActivity extends CommonBaseTabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musictab);

		addTab(AllMusicActivity.class, "AllMusicActivity",
				createIndivicator("All"));
		addTab(GenesActivity.class, "GenesActivity", createIndivicator("Genes"));
		addTab(PopularActivity.class, "PopularActivity",
				createIndivicator("Popular"));
	}

	private View createIndivicator(String string) {
		TextView textView = new TextView(this);
		textView.setText(string);
		textView.setBackgroundResource(R.drawable.indicator);
		textView.setGravity(Gravity.CENTER);
		return textView;
	}
}
