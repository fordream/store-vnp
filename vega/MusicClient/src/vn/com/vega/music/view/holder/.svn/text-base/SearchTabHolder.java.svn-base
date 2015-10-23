package vn.com.vega.music.view.holder;

import java.util.Stack;

import vn.com.vega.common.Session;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.SearchActivity;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class SearchTabHolder extends ActivityGroup implements Const {
	private Stack<String> stack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (stack == null)
			stack = new Stack<String>();
		push(SearchActivity.class.getName(), new Intent(this, SearchActivity.class));
	}

	@Override
	public void finishFromChild(Activity child) {
		pop();
	}

	public void pop() {
		if (stack.size() == 1)
			finish();
		LocalActivityManager manager = getLocalActivityManager();
		Session.clearSharedObjectsOf(manager.getActivity(stack.peek()));
		manager.destroyActivity(stack.pop(), true);
		if (stack.size() > 0) {
			Intent lastIntent = manager.getActivity(stack.peek()).getIntent();
			Window newWindow = manager.startActivity(stack.peek(), lastIntent);
			setContentView(newWindow.getDecorView());
		}
	}

	public void push(String id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(id, intent);
		if (window != null) {
			stack.push(id);
			setContentView(window.getDecorView());
		}
	}

	/**
	 * Starts an Activity as a child Activity to this.
	 * 
	 * @param Id
	 *            Unique identifier of the activity to be started.
	 * @param intent
	 *            The Intent describing the activity to be started.
	 * @throws android.content.ActivityNotFoundException.
	 */
	public void startChildActivity(String Id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(Id, intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			stack.push(Id);
			setContentView(window.getDecorView());
		}
	}

	/**
	 * If a Child Activity handles KeyEvent.KEYCODE_BACK. Simply override and
	 * add this method.
	 */
	@Override
	public void onBackPressed() {
		pop();
	}

}
