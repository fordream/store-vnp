package minh.app.mbook;

import java.util.ArrayList;
import java.util.List;

import minh.app.mbook.utils.MbookManager;
import minh.app.mbook.views.GroupItemView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.vnp.core.activity.BaseActivity;

public class MainListAtivity extends BaseActivity {
	private LinearLayout layout;
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// update UI
		}
	};
	private List<GroupItemView> groupItemViews = new ArrayList<GroupItemView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		layout = getView(R.id.content);
		groupItemViews.add(new GroupItemView(getContext()));
		groupItemViews.add(new GroupItemView(getContext()));
		groupItemViews.add(new GroupItemView(getContext()));
		groupItemViews.add(new GroupItemView(getContext()));
		groupItemViews.add(new GroupItemView(getContext()));
		groupItemViews.add(new GroupItemView(getContext()));
		
		for (GroupItemView groupItemView : groupItemViews) {
			layout.addView(groupItemView);
		}

		registerReceiver(broadcastReceiver, new IntentFilter(
				MbookManager.BROASTCAST_UPDATE_UI));

		startService(new Intent(MbookManager.ACTIONG_SERVICE));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// update UI
		broadcastReceiver.onReceive(this, new Intent());
	}

	@Override
	protected void onStart() {
		super.onStart();
		// bindService(new Intent(MbookManager.ACTIONG_SERVICE), mConnection,
		// Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// if (mService != null) {
		// unbindService(mConnection);
		// }
	}

	// private ServiceConnection mConnection = new ServiceConnection() {
	//
	// @Override
	// public void onServiceConnected(ComponentName className, IBinder service)
	// {
	// LocalBinder binder = (LocalBinder) service;
	// mService = binder.getService();
	// }
	//
	// @Override
	// public void onServiceDisconnected(ComponentName arg0) {
	// }
	// };
}