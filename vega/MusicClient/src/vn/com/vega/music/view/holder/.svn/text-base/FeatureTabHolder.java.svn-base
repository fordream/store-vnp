package vn.com.vega.music.view.holder;

import java.util.Stack;

import vn.com.vega.common.Session;
import vn.com.vega.music.device.DeviceIntegrator;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.FeatureHomeActivity;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

public class FeatureTabHolder extends ActivityGroup implements Const {
	
	private static final String LOG_TAG = Const.LOG_PREF + FeatureTabHolder.class.getSimpleName();
	
	private Stack<String> stackActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (stackActivity == null)
			stackActivity = new Stack<String>();
		push(FeatureHomeActivity.class.getName(), new Intent(this, FeatureHomeActivity.class));
		Log.e(LOG_TAG, "Starting... " + FeatureTabHolder.class.getName());
	}

	@Override
	public void finishFromChild(Activity child) {
		LocalActivityManager manager = getLocalActivityManager();
		int index = stackActivity.size() - 1;
		if (index < 1) {
			finish();
		}

		Session.clearSharedObjectsOf(manager.getActivity(stackActivity.get(index)));
		manager.destroyActivity(stackActivity.get(index), true);
		stackActivity.remove(index);
		index--;

		String lastId = stackActivity.get(index);
		Intent lastIntent = manager.getActivity(lastId).getIntent();
		Window newWindow = manager.startActivity(lastId, lastIntent);
		setContentView(newWindow.getDecorView());
	}

	@Override
	public void onBackPressed() {
		int numb = stackActivity.size();
		if (numb > 1) {
			Activity current = getLocalActivityManager().getActivity(stackActivity.get(numb - 1));
			current.finish();
		}
	}

	public void push(String id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(id, intent);
		if (window != null) {
			stackActivity.push(id);
			setContentView(window.getDecorView());
		}
	}

	public void pop() {
		if (stackActivity.size() == 1)
			finish();

		LocalActivityManager manager = getLocalActivityManager();
		Session.clearSharedObjectsOf(manager.getActivity(stackActivity.peek()));
		manager.destroyActivity(stackActivity.pop(), true);
		if (stackActivity.size() > 0) {
			Intent lastIntent = manager.getActivity(stackActivity.peek()).getIntent();
			Window newWindow = manager.startActivity(stackActivity.peek(), lastIntent);
			setContentView(newWindow.getDecorView());
		}
	}

	/**
	 * Start child activity
	 * 
	 * @param id
	 * @param intent
	 */
	public void startChildActivity(String id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(id, intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			stackActivity.add(id);
			setContentView(window.getDecorView());
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
		}
		return true;
	}

}
