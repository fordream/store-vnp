package com.ict.library.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BaseGroupActivity extends ActivityGroup {

	// Keep this in a static variable to make it accessible for all the nested
	// activities, lets them manipulate the view
	// public static FirstGroup group;

	// Need to keep track of the history if you want the back-button to work
	// properly, don't use this if your activities requires a lot of memory.
	private List<View> history = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void addView(String name, Class<?> activity, Bundle extras) {
		replaceView(createView(name, activity, extras));
	}

	private View createView(String name, Class<?> activity, Bundle extras) {
		Intent intent = new Intent(this, activity);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		if (extras != null) {
			intent.putExtras(extras);
		}

		View view = getLocalActivityManager().startActivity(name, intent)
				.getDecorView();
		return view;
	}

	private void replaceView(View v) {
		// Adds the old one to history
		history.add(v);
		// Changes this Groups View to the new View.
		setContentView(v);
	}

	public void onBackPressed() {
		if (history.size() > 1) {
			history.remove(history.size() - 1);
			setContentView(history.get(history.size() - 1));
		} else {
			finish();
		}
	}
}