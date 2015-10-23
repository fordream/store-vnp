package com.icts.shortfilmfestival.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;

import com.icts.shortfilmfestival.adapter.VenuesAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.detailfragment.VenuesDetail;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.myjson.JSVenuesLink;
import com.vnp.shortfilmfestival.R;

public class VenuesFragment extends Fragment implements OnTabChangeListener, LoaderCallbacks<Void>{
	
	private ListView mListView;
	private VenuesAdapter mVenuesAdapter;
	
	private static final String TAG = "Venues Tab";
	public static final String TAB_LAFORET = "laforet";
	public static final String TAB_SPACEO = "spaceo";
	public static final String TAB_TOHO = "toho";
	public static final String TAB_YOKOHAMA = "yokohama";

	private View mRoot;
	private TabHost mTabHost;
	private int mCurrentTab;
	private TabWidget mTabWidget;
	public static boolean isDetail;
	private static String ISTABNAME = "";
	private static int ISTABID = 0;
	public static ArrayList<String> mArrayListVenuesLink = new ArrayList<String>();
	private ProgressDialog mProgressDialog;
public void onAttach(Activity activity) {
	super.onAttach(activity);
	mProgressDialog = new ProgressDialog(activity);
	mProgressDialog.setCancelable(false);
	
}


public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	mRoot = inflater.inflate(R.layout.venues_fragment, null);
	mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
	mTabWidget = (TabWidget) mRoot.findViewById(android.R.id.tabs);
	setupTabs();
	
	return mRoot;
}

public void onActivityCreated(Bundle savedInstanceState) {
	super.onActivityCreated(savedInstanceState);
	//setRetainInstance(true);

	mTabHost.setOnTabChangedListener(this);
	mTabHost.setCurrentTab(mCurrentTab);
	// manually start loading stuff in the first tab
	//mTabWidget.setBackgroundResource(R.drawable.all_select_bg);
	isDetail = false;
	updateTab(TAB_LAFORET, R.id.tab_laforet);
	SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity()
	.getApplicationContext().getSystemService(
			Context.CONNECTIVITY_SERVICE);

	getLoaderManager().initLoader(0, null, this);
}


public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
	mProgressDialog.setMessage("Loading...");
	mProgressDialog.show();
	AsyncTaskLoader<Void> 
		loader = new AsyncTaskLoader<Void>(getActivity()) {
		
			
			public Void loadInBackground() {
				Log.d(TAG, "-----------loading-------------");
				JSVenuesLink mJSVenuesLink = new JSVenuesLink();
				mArrayListVenuesLink = mJSVenuesLink.getmArrayListVenuesLink(SSSFApi
						.getAllVenuesLink(
								ISettings.BIT_FLY_URL));
				return null;
			}
		};

		loader.forceLoad();
	return loader;
}



public void onLoadFinished(Loader<Void> arg0, Void arg1) {
	mProgressDialog.setCancelable(true);
	mProgressDialog.dismiss();
	
}


public void onLoaderReset(Loader<Void> arg0) {
	
}

private void setupTabs() {
	mTabHost.setup(); // important!
	mTabHost.addTab(newTab(TAB_LAFORET, R.drawable.ic_laforest, R.id.tab_laforet));
	mTabHost.addTab(newTab(TAB_SPACEO, R.drawable.ic_space, R.id.tab_spaceo));
	mTabHost.addTab(newTab(TAB_TOHO, R.drawable.ic_toho, R.id.tab_toho));
	mTabHost.addTab(newTab(TAB_YOKOHAMA, R.drawable.ic_yokohama, R.id.tab_yokohama));	
}

private TabSpec newTab(String tag, int drawableId, int tabContentId) {
	Log.d(TAG, "buildTab(): tag=" + tag);

	View indicator = LayoutInflater.from(getActivity()).inflate(
			R.layout.tab_schedule_indicator,
			(ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
	ImageView mIcon = ((ImageView) indicator.findViewById(R.id.imgIcon));
	mIcon.setBackgroundResource(drawableId);;
	TabSpec tabSpec = mTabHost.newTabSpec(tag);
	tabSpec.setIndicator(indicator);
	tabSpec.setContent(tabContentId);
	return tabSpec;
}


public void onTabChanged(String tabId) {
	Log.d(TAG, "onTabChanged(): tabId=" + tabId);
	ISTABNAME = tabId;
	if (TAB_LAFORET.equals(tabId)) {
		ISTABID = R.id.tab_laforet;
		updateTab(tabId, R.id.tab_laforet);
		mCurrentTab = 0;
		return;
	}
	if (TAB_SPACEO.equals(tabId)) {
		ISTABID = R.id.tab_spaceo;
		updateTab(tabId, R.id.tab_spaceo);
		mCurrentTab = 1;
		return;
	}
	if (TAB_TOHO.equals(tabId)) {
		ISTABID = R.id.tab_toho;
		updateTab(tabId, R.id.tab_toho);
		mCurrentTab = 2;
		return;
	}
	if (TAB_YOKOHAMA.equals(tabId)) {
		ISTABID = R.id.tab_yokohama;
		updateTab(tabId, R.id.tab_yokohama);
		mCurrentTab = 3;
		return;
	}
	
}



private void updateTab(String tabId, int placeholder) {
	FragmentManager fm = getFragmentManager();
	FragmentTransaction ft = getActivity().getSupportFragmentManager()
	.beginTransaction();
	ft.setCustomAnimations(android.R.anim.slide_in_left,
	android.R.anim.slide_out_right);
		ft.replace(placeholder,
				new VenuesDetail(tabId), tabId).commit();
}

public static void backIsTAB(){
	//FragmentManager fm = 
	/*if (fm.findFragmentByTag(tabId) == null) {
		fm.beginTransaction()
				.replace(placeholder, new ScheduleListFragment(tabId, this))
				.commit();
	}*/
}
}
