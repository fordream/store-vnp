package com.icts.shortfilmfestival.fragment;

import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.detailfragment.NewsDetail;
import com.icts.shortfilmfestival.detailfragment.NewsListFragment;
import com.icts.shortfilmfestival.tabgroup.NewsTab;
import com.icts.shortfilmfestivalJa.MainTabActivity;
import com.icts.shortfilmfestivalJa.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class NewsFragment extends Fragment implements OnTabChangeListener {
	private static final String TAG = "FragmentTabs";
	public static final String TAB_FESTIVAL = "Festival";
	public static final String TAB_BIZ = "Biz";
	public static final String TAB_LOUNGE = "Lounge";
	public static final String TAB_THEATER = "Theater";
	public static final String TAB_ALL = "Top";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;
	private TabWidget mTabWidget;
	private ImageView mBottomLineImageView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.maintabs, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) mRoot.findViewById(android.R.id.tabs);
		mBottomLineImageView = (ImageView) mRoot.findViewById(R.id.bottom_line);
		setupTabs();

		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		NewsTab.isDeailShow = false;

	}

	@Override
	public void onResume() {
		Log.d(TAG, "-----------------onResume-----------------");
		// setRetainInstance(true);
		mTabHost.setOnTabChangedListener(this);
		
		// manually start loading stuff in the first tab
		
		if (MainTabActivity.isFromNotification)
		{
			Log.d(TAG, " isFromNotification----------------Come Here----------------");
			SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity()
			.getApplicationContext().getSystemService(
					Context.CONNECTIVITY_SERVICE);
			if (MainTabActivity.typeNews.equals("ssff"))
			{
				
				mTabHost.getChildAt(0).clearFocus();
				mTabHost.clearChildFocus(mTabWidget.getChildTabViewAt(0));
				mTabHost.setCurrentTab(1);
				mTabHost.setFocusable(true);
				mTabHost.requestFocus(1);
				onTabChanged(TAB_FESTIVAL);
				//updateTab(TAB_FESTIVAL, R.id.tab_festival);
			}
			else if (MainTabActivity.typeNews.equals("biz"))
			{
				
				mTabHost.getChildAt(0).clearFocus();
				mTabHost.clearChildFocus(mTabWidget.getChildTabViewAt(0));
				mTabHost.setCurrentTab(2);
				mTabHost.setFocusable(true);
				mTabHost.requestFocus(2);
				onTabChanged(TAB_BIZ);
				//updateTab(TAB_BIZ, R.id.tab_biz);
			}
			else if (MainTabActivity.typeNews.equals("lounge"))
			{
				
				mTabHost.setCurrentTab(3);
				mTabHost.getChildAt(0).clearFocus();
				mTabHost.clearChildFocus(mTabWidget.getChildTabViewAt(0));
				mTabHost.setFocusable(true);
				mTabHost.requestFocus(3);
				onTabChanged(TAB_LOUNGE);
				//updateTab(TAB_LOUNGE, R.id.tab_lounge);
			}
			else if (MainTabActivity.typeNews.equals("theater"))
			{
				
				mTabHost.getChildAt(0).clearFocus();
				mTabHost.clearChildFocus(mTabWidget.getChildTabViewAt(0));
				mTabHost.setCurrentTab(4);
				mTabHost.setFocusable(true);
				mTabHost.requestFocus(4);
				onTabChanged(TAB_THEATER);
				//updateTab(TAB_THEATER, R.id.tab_theater);
			}
			
			final FragmentTransaction ft = getActivity()
			.getSupportFragmentManager().beginTransaction();
			NewsDetail mNewsDetailFragment = new NewsDetail(
					this,MainTabActivity.typeNews,MainTabActivity.idNews, MainTabActivity.urlNews);
			
			Log.d(TAG, "________COME HERE________");
			ft.setCustomAnimations(
					android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
			ft.hide(this);

			ft.replace(R.id.detail_fragment,
					mNewsDetailFragment);
			ft.commit();
		}
		else
		{
			mTabHost.setCurrentTab(mCurrentTab);
			updateTab(TAB_ALL, R.id.tab_all);
			mTabWidget.setBackgroundResource(R.drawable.all_select_bg);
			mBottomLineImageView.setBackgroundResource(R.drawable.all_active_line);
		}
		super.onResume();
	}

	private void setupTabs() {
		mTabHost.setup(); // important!
		mTabHost.addTab(newTab(TAB_ALL, R.drawable.all, R.id.tab_all));
		mTabHost.addTab(newTab(TAB_FESTIVAL, R.drawable.festival,
				R.id.tab_festival));
		mTabHost.addTab(newTab(TAB_BIZ, R.drawable.biz, R.id.tab_biz));
		mTabHost.addTab(newTab(TAB_LOUNGE, R.drawable.lounge, R.id.tab_lounge));
		mTabHost.addTab(newTab(TAB_THEATER, R.drawable.theater,
				R.id.tab_theater));

	}

	private TabSpec newTab(String tag, int drawableId, int tabContentId) {
		Log.d(TAG, "buildTab(): tag=" + tag);

		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab_news_indicator,
				(ViewGroup) mRoot.findViewById(android.R.id.tabs), false);

		((ImageView) indicator.findViewById(R.id.icon))
				.setBackgroundResource(drawableId);
		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_FESTIVAL.equals(tabId)) {
			updateTab(tabId, R.id.tab_festival);
			mCurrentTab = 1;
			mTabWidget.setBackgroundResource(R.drawable.festival_select_bg);
			mBottomLineImageView
					.setBackgroundResource(R.drawable.festival_active_line);
			return;
		}
		if (TAB_BIZ.equals(tabId)) {
			updateTab(tabId, R.id.tab_biz);
			mCurrentTab = 2;
			mTabWidget.setBackgroundResource(R.drawable.biz_select_bg);
			mBottomLineImageView
					.setBackgroundResource(R.drawable.biz_active_line);
			return;
		}
		if (TAB_LOUNGE.equals(tabId)) {
			updateTab(tabId, R.id.tab_lounge);
			mCurrentTab = 3;
			mTabWidget.setBackgroundResource(R.drawable.lounge_select_bg);
			mBottomLineImageView
					.setBackgroundResource(R.drawable.lounge_active_line);
			return;
		}
		if (TAB_THEATER.equals(tabId)) {
			updateTab(tabId, R.id.tab_theater);
			mCurrentTab = 4;
			mTabWidget.setBackgroundResource(R.drawable.theater_select_bg);
			mBottomLineImageView
					.setBackgroundResource(R.drawable.theater_line_active);
			return;
		}
		if (TAB_ALL.equals(tabId)) {
			updateTab(tabId, R.id.tab_all);
			mCurrentTab = 0;
			mTabWidget.setBackgroundResource(R.drawable.all_select_bg);
			mBottomLineImageView
					.setBackgroundResource(R.drawable.all_active_line);
			return;
		}
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentByTag(tabId) == null) {
			fm.beginTransaction().replace(placeholder,
					new NewsListFragment(tabId, this), tabId).commit();
		}
	}
}
