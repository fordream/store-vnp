package com.icts.shortfilmfestival.fragment;

import com.icts.shortfilmfestival.detailfragment.MovieListFragment;
import com.icts.shortfilmfestivalJa.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MoviesFragment extends Fragment implements OnTabChangeListener{
	private static final String TAG = "MoviesTabs";
	public static final String TAB_YOUTUBE = "Youtube";
	public static final String TAB_USTREAM = "UStream";

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
		Log.d(TAG, "---------Activity Created-----------");
		mRoot = inflater.inflate(R.layout.moviestabs, null);
		mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
		mTabWidget = (TabWidget) mRoot.findViewById(android.R.id.tabs);
		mBottomLineImageView = (ImageView) mRoot.findViewById(R.id.bottom_line);
		setupTabs();
		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//setRetainInstance(true);
		
		mTabHost.setOnTabChangedListener(this);
		mTabHost.setCurrentTab(mCurrentTab);
		// manually start loading stuff in the first tab
		updateTab(TAB_YOUTUBE, R.id.tab_youtube);
		mBottomLineImageView.setBackgroundResource(R.drawable.youtube_line_active);
	}

	private void setupTabs() {
		mTabHost.setup(); // important!utube_logo_active
		mTabHost.addTab(newTab(TAB_YOUTUBE, R.drawable.youtube, R.id.tab_youtube));
		mTabHost.addTab(newTab(TAB_USTREAM, R.drawable.ustream, R.id.tab_ustream));
		
	}

	private TabSpec newTab(String tag, int drawableId, int tabContentId) {

		View indicator = LayoutInflater.from(getActivity()).inflate(
				R.layout.tab_movie_indicator,
				(ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
		((ImageView) indicator.findViewById(R.id.icon)).setBackgroundResource(drawableId);
		TabSpec tabSpec = mTabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		tabSpec.setContent(tabContentId);
		return tabSpec;
	}

	@Override
	public void onTabChanged(String tabId) {
		Log.d(TAG, "onTabChanged(): tabId=" + tabId);
		if (TAB_YOUTUBE.equals(tabId)) {
			updateTab(tabId, R.id.tab_youtube);
			mCurrentTab = 0;
			mBottomLineImageView.setBackgroundResource(R.drawable.youtube_line_active);
			return;
		}
		if (TAB_USTREAM.equals(tabId)) {
			updateTab(TAB_USTREAM, R.id.tab_ustream);
			mCurrentTab = 1;
			mBottomLineImageView.setBackgroundResource(R.drawable.ustream_line_active);
			return;
		}
		
	}

	private void updateTab(String tabId, int placeholder) {
		FragmentManager fm = getFragmentManager();
		if (fm.findFragmentByTag(tabId) == null) {
			fm.beginTransaction()
					.replace(placeholder, new MovieListFragment(tabId, this), tabId)
					.commit();
		}
	}
}
