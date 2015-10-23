package vn.com.vega.music.view.holder;

import java.util.Stack;

import vn.com.vega.chacha.R;
import vn.com.vega.common.Session;
import vn.com.vega.music.utils.Const;
import vn.com.vega.music.view.MyPlaylistActivity;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MyPlaylistStack extends ActivityGroup implements Const {
	private Stack<String> stack;
	private final String ACTIVITY_NAME = MyPlaylistStack.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (stack == null)
			stack = new Stack<String>();
		// start default activity
		push(ACTIVITY_NAME, new Intent(this, MyPlaylistActivity.class));
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

	// override these to show menu option in sub-activity of group activity.
	// maybe use later

//	@Override
//	public boolean onPrepareOptionsMenu(Menu menu){
//		return this.getCurrentActivity().onPrepareOptionsMenu(menu);
//	}
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		return this.getCurrentActivity().onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		return this.getCurrentActivity().onOptionsItemSelected(item);
//	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch (id) {
		case DIALOG_LOGOUT:
			return new AlertDialog.Builder(getParent()).setIcon(android.R.drawable.ic_dialog_alert).setTitle(R.string.app_name).setMessage(R.string.logout_msg)
					.setPositiveButton(R.string.confirm_yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							getParent().finish();
						}
					}).setNegativeButton(R.string.confirm_no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							// do nothing
						}
					}).create();
		}
		return null;
	}
}
