package com.cnc.android.timtalk.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.cnc.android.timtalk.ui.adapter.PagerAdapter;
import com.cnc.android.timtalk.ui.fragment.ChatsFragment;
import com.cnc.android.timtalk.ui.fragment.FriendsFragment;
import com.cnc.android.timtalk.ui.fragment.Tab3Fragment;

/**
 * The <code>TimTalkProScreen</code> class implements the Fragment activity that
 * maintains a TabHost using a ViewPager.
 * 
 * @author namnd
 * 
 */
public class TimTalkProScreen extends FragmentActivity implements
		TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

	private TabHost mTabHost;
	private ViewPager mViewPager;

	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TimTalkProScreen.TabInfo>();
	private PagerAdapter mAdapter;

	/**
	 * Maintains extrinsic info of a tab's construct
	 * 
	 * @author namnd
	 * 
	 */
	private class TabInfo {
		private String tag;
		private int drawableId;
		@SuppressWarnings("unused")
		private Class<?> clzz;
		@SuppressWarnings("unused")
		private Bundle bundle;

		TabInfo(String tag, int drawableId, Class<?> clzz, Bundle bundle) {
			// TODO Auto-generated constructor stub
			this.tag = tag;
			this.drawableId = drawableId;
			this.clzz = clzz;
			this.bundle = bundle;
		}
	}

	/**
	 * A simple factory that returns dummy views to the Tabhost
	 * 
	 * @author namnd
	 * 
	 */
	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */

		public TabFactory(Context context) {
			this.mContext = context;
		}

		@Override
		public View createTabContent(String tag) {
			// TODO Auto-generated method stub
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_viewpager_layout);
		this.initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		this.intialiseViewPager();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	/**
	 * Initialise ViewPager
	 */

	private void intialiseViewPager() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				FriendsFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, Tab3Fragment.class.getName()));
		fragments
				.add(Fragment.instantiate(this, ChatsFragment.class.getName()));
		fragments.add(Fragment.instantiate(this, Tab3Fragment.class.getName()));
		this.mAdapter = new PagerAdapter(super.getSupportFragmentManager(),
				fragments);
		this.mViewPager = (ViewPager) findViewById(R.id.viewpager);
		this.mViewPager.setAdapter(mAdapter);
		this.mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * Initialise the Tab Host
	 */

	private void initialiseTabHost(Bundle bundle) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		TimTalkProScreen.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Friends").setIndicator("Friends"),
				(tabInfo = new TabInfo("Friends", R.drawable.tab_friends,
						FriendsFragment.class, bundle)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		TimTalkProScreen.AddTab(
				this,
				this.mTabHost,
				this.mTabHost.newTabSpec("Add Friend").setIndicator(
						"Add Friend"), (tabInfo = new TabInfo("Add Friend",
						R.drawable.ic_ex, Tab3Fragment.class, bundle)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		TimTalkProScreen.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Chats").setIndicator("Chats"),
				(tabInfo = new TabInfo("Chats", R.drawable.tab_chats,
						ChatsFragment.class, bundle)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		TimTalkProScreen.AddTab(this, this.mTabHost,
				this.mTabHost.newTabSpec("Setting").setIndicator("Setting"),
				(tabInfo = new TabInfo("Setting", R.drawable.ic_ex,
						Tab3Fragment.class, bundle)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		mTabHost.setOnTabChangedListener(this);

	}

	/**
	 * Add Tab content to the Tabhost
	 * 
	 * @param activity
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */

	private static void AddTab(TimTalkProScreen activity, TabHost tabHost,
			TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		View tabIndicator = LayoutInflater.from(activity).inflate(
				R.layout.tab_indicator, tabHost.getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(tabInfo.tag);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(tabInfo.drawableId);
		tabSpec.setIndicator(tabIndicator);
		tabSpec.setContent(activity.new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */

	@Override
	public void onTabChanged(String tag) {
		// TODO Auto-generated method stub
		int pos = this.mTabHost.getCurrentTab();
		this.mViewPager.setCurrentItem(pos);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */

	@Override
	public void onPageScrollStateChanged(int state) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		this.mTabHost.setCurrentTab(position);
	}
}