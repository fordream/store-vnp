package com.icts.shortfilmfestival_en;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import com.icts.shortfilmfestival.fragment.WebViewFragment;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.tabgroup.ScheduleTab;
import com.icts.shortfilmfestival.tabgroup.VenuesTab;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.icts.shortfilmfestival.utils.ImageFlash;
import com.vnp.shortfilmfestival.R;

public class FestivalTabActivity extends TabActivity {
	private static final String TAG = "LOG_SCHEDULETABACTIVITY";
	private RelativeLayout logoButton;
	private ImageFlash mImageFlash;
	private static TabHost tabHost;
	private static ImageView imgLineActivate;
	private ImageView mBannerImageView;
	private static final int HEIGH = 52;

	private static final String TAG_SCHEDULE = "Schedule";
	private static final String TAG_VENUES = "Top";
	private static final String TAG_TICKET = "Ticket";
	private static final String TAG_REPORT = "Report";

	private static ImageView imgNew;
	private static ImageView imgPhoto;
	private static ImageView imgMovie;
	private static ImageView imgApp;

	
	protected void onCreate(Bundle savedInstanceState) {
		this.setTheme(android.R.style.Theme_Black_NoTitleBar);
		super.onCreate(savedInstanceState);
		// Set view to display.
		setContentView(R.layout.festival);
		mBannerImageView = (ImageView) findViewById(R.id.banner_id);
		int pixel = 57;
		final float density = getBaseContext().getResources()
				.getDisplayMetrics().density;
		int dp = (int) (pixel * density);
		mBannerImageView.getLayoutParams().height = dp;
		imgLineActivate = (ImageView) findViewById(R.id.line_active);
		// Add tabs to tab host.
		setTabs();

		logoButton = (RelativeLayout) findViewById(R.id.logo_button);
		logoButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Log.i(TAG, "Call to Schedule");

			}
		});
		mImageFlash = new ImageFlash(this, R.drawable.festival_info_buttn,
				R.drawable.glow1);
		logoButton.addView(mImageFlash,
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);

		getTabHost().setOnTabChangedListener(new OnTabChangeListener() {

			
			public void onTabChanged(String tabId) {
				if (tabId.equals(TAG_SCHEDULE)) {
					imgLineActivate
							.setBackgroundResource(R.drawable.schedule_line_active);
				}
				if (tabId.equals(TAG_VENUES)) {
					imgLineActivate
							.setBackgroundResource(R.drawable.all_active_line);
				}
				if (tabId.equals(TAG_TICKET)) {
					imgLineActivate
							.setBackgroundResource(R.drawable.ticket_line_active);
				}
				if (tabId.equals(TAG_REPORT)) {
					imgLineActivate
							.setBackgroundResource(R.drawable.report_line_active);
				}

			}
		});

		// Tab Direc
		imgNew = (ImageView) findViewById(R.id.img_news);
		imgPhoto = (ImageView) findViewById(R.id.img_photo);
		imgMovie = (ImageView) findViewById(R.id.img_movie);
		imgApp = (ImageView) findViewById(R.id.img_app);

		imgNew.setOnClickListener(click);
		imgPhoto.setOnClickListener(click);
		imgMovie.setOnClickListener(click);
		imgApp.setOnClickListener(click);

	}

	private void Direc(int id) {
		Intent in = new Intent(this, MainTabActivity.class);
		in.putExtra("id", id);

		startActivity(in);
		finish();
	}

	// ================================================
	View.OnClickListener click = new OnClickListener() {

		
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_news:
				Direc(0);
				Log.i(TAG, "CLick img_news");
				break;
			case R.id.img_photo:
				Direc(1);
				Log.i(TAG, "CLick img_photo");
				break;
			case R.id.img_movie:
				Direc(2);
				Log.i(TAG, "CLick img_movie");
				break;
			case R.id.img_app:
				Direc(3);
				Log.i(TAG, "CLick img_app");
				break;
			}
		}
	};

	// ================================================

	
	protected void onResume() {
		super.onResume();
		tabHost = getTabHost();
	}

	private void setTabs() {
		addTab(TAG_VENUES, VenuesTab.class, R.drawable.color_festival_venues,
		"");
		addTab(TAG_SCHEDULE, ScheduleTab.class,
				R.drawable.color_festival_schedule, "");
		addTab(TAG_TICKET, WebViewFragment.class,
				R.drawable.color_festival_ticket,
				ISettings.FESTIVAL_TICKET_URL);
		if (ISettings.LANGUAGE.equals("en"))
		{
			addTab(TAG_REPORT, WebViewFragment.class,
					R.drawable.color_festival_report,
					ISettings.FESTIVAL_REPORT_EN);
		}
		else
		{
			addTab(TAG_REPORT, WebViewFragment.class,
					R.drawable.color_festival_report,
					ISettings.FESTIVAL_REPORT_JA);
		}

		// Set height of TabHost.
//		int pixel = HEIGH;
//		final float density = getBaseContext().getResources()
//				.getDisplayMetrics().density;
//		int dp = (int) (pixel * density);
//		getTabHost().getTabWidget().getLayoutParams().height = dp;
	}

	private void addTab(final String str, Class<?> c, int pBg, String url) {
		TabHost tabhost = getTabHost();
		Intent intent = new Intent(this, c);
		intent.putExtra("url", url);

		TabHost.TabSpec spec = tabhost.newTabSpec(str);
		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tab_festival_indicator, getTabWidget(), false);
		tabIndicator.setBackgroundResource(pBg);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(str);
		FontUtils.setCustomFont(title, false, false, getAssets());
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

	public static void onKeyBack() {
		Log.i(TAG, "AlO..................!");
		final Context con = getTabHostFromChildActivity().getContext();
		Intent in = new Intent(con, MainTabActivity.class);
		in.putExtra("id", 0);
		con.startActivity(in);

	}

}
