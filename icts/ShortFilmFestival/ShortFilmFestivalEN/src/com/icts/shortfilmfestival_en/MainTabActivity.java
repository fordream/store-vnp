package com.icts.shortfilmfestival_en;

import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.tabgroup.AppsTab;
import com.icts.shortfilmfestival.tabgroup.MoviesTab;
import com.icts.shortfilmfestival.tabgroup.NewsTab;
import com.icts.shortfilmfestival.tabgroup.PhotosTab;
import com.icts.shortfilmfestival.utils.ImageFlash;
//import com.icts.shortfilmfestival.zzz.t.api.Rest;
import com.vnp.shortfilmfestival.R;

public class MainTabActivity extends TabActivity {
//	Rest rest = new Rest();
	private static final String TAG = "LOG_MAINTAB_ACTIVITY";
	private static TabHost tabHost;
	public static boolean isFromPhotos;
	public static boolean isFromNews;
	public static boolean isFromMovie;
	public static boolean isFromApps;
	private RelativeLayout logoButton;
	private ImageFlash mImageFlash;
	private ImageView mBannerImageView;
	private int idselected = 0;
	public static boolean isFromNotification;
	public static String typeNews;
	public static String urlNews;
	public static int idNews;

	/** Height of tab widget. */
	public void onCreate(Bundle savedInstanceState) {
		this.setTheme(android.R.style.Theme_Black_NoTitleBar);
		super.onCreate(savedInstanceState);
		// Set view to display.
		setContentView(R.layout.main);
		register();
		// Add tabs to tab host.
		setTabs();
		isFromNotification = false;

		// Add touch event for tab.
		for (int i = 1; i < getTabHost().getTabWidget().getChildCount(); i++) {
			getTabHost().getTabWidget().getChildAt(i)
					.setOnTouchListener(new OnTouchListener() {

						public boolean onTouch(View v, MotionEvent event) {
							if (event.getAction() == MotionEvent.ACTION_DOWN) {

							}
							return false;
						}
					});
		}

		// Get Logo Button

		isFromPhotos = getIntent().getBooleanExtra("FROM_PHOTOS", false);
		// Auto Select tab
		Bundle data = getIntent().getExtras();
		if (data != null) {
			isFromNotification = data.getBoolean("notification", false);
		}
		if (isFromNotification == true) {
			// isFromNotification = false;
			idNews = Integer.parseInt(data.getString("newsID"));
			typeNews = data.getString("type");
			urlNews = data.getString("url");
		}

		if (data != null) {
			idselected = data.getInt("id", 0);
		}

		switch (idselected) {
		case 1:
			isFromPhotos = true;
			break;
		case 2:
			isFromMovie = true;
			break;
		case 3:
			isFromApps = true;
			break;
		default:
			break;
		}

		Resource.localization = getApplicationContext().getResources()
				.getConfiguration().locale.toString();

		mBannerImageView = (ImageView) findViewById(R.id.banner_id);
		int pixel = 57;
		final float density = getBaseContext().getResources()
				.getDisplayMetrics().density;
		int dp = (int) (pixel * density);
		mBannerImageView.getLayoutParams().height = dp;
		logoButton = (RelativeLayout) findViewById(R.id.logo_button);
		registerReceiver(broadcastReceiver, new IntentFilter("reset"));
	}
	
	protected void onDestroy() {
		System.gc();
		unregisterReceiver(broadcastReceiver);
		super.onDestroy();

	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (!isFinishing()) {
//				rest.setLogo(logoButton);
			}
		}
	};

	private void selectTab(int id) {
		MainTabActivity.getTabHostFromChildActivity().setCurrentTab(id);

	}

	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		// Get Locate For Application
		tabHost = getTabHost();
		Log.d(TAG, "Localization" + Resource.localization);
		if (isFromMovie) {
			selectTab(2);
		} else if (isFromApps) {
			selectTab(3);
		}

		if (isFromPhotos) {
			getTabWidget().getChildAt(0).clearFocus();
			getTabWidget().clearChildFocus(getTabWidget().getChildTabViewAt(0));
			MainTabActivity.getTabHostFromChildActivity().setCurrentTab(1);
			getTabWidget().setFocusable(true);
			MainTabActivity.getTabHostFromChildActivity().requestFocus(1);
		}
	}

	/**
	 * This function used for setup tab host.
	 * */
	private void setTabs() {
		addTab(R.drawable.news, NewsTab.class);
		addTab(R.drawable.photos, PhotosTab.class);
		addTab(R.drawable.movies, MoviesTab.class);
		addTab(R.drawable.apps, AppsTab.class);

		// Set height of TabHost.
		// int pixel = HEIGH;
		// final float density = getBaseContext().getResources()
		// .getDisplayMetrics().density;
		// int dp = (int) (pixel * density);
		// getTabHost().getTabWidget().getLayoutParams().height = dp;
	}

	/**
	 * This function used to add tabs to tab host.
	 * 
	 * @param labelId
	 *            id of label to add.
	 * @param drawableId
	 *            id of image icon.
	 * @param c
	 *            Class of activity.
	 * */
	private void addTab(final int drawableId, Class<?> c) {
		TabHost tabhost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabhost.newTabSpec("");
		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, getTabWidget(), false);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabhost.addTab(spec);
	}

	/**
	 * This function used for access reference to tab widget from child
	 * activity.
	 * 
	 * @return {@link TabHost} Keep reference to tab host instance.
	 * */
	public static TabHost getTabHostFromChildActivity() {
		return tabHost;
	}

	/**
	 * This function for Show all tab again.
	 * */
	public static void showAll() {

		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i).setVisibility(View.VISIBLE); // Unselected
																				// Tabs
		}
	}

	/**
	 * This function used for hide tab with corresponding id.
	 * 
	 * @param id
	 *            Index of tab to hide.
	 * */
	public final void setHide(final int id) {
		tabHost.getTabWidget().getChildAt(id).setVisibility(View.GONE);
	}

	

	public void register() {
		Log.e("C2DM", "start registration process");
		Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
		intent.putExtra("app",
				PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		// Sender currently not used
		intent.putExtra("sender", "shortfilmfestivaldeveloper@gmail.com");
		startService(intent);
	}

}
