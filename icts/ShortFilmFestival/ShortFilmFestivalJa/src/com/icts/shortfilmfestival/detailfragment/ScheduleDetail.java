package com.icts.shortfilmfestival.detailfragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.icts.shortfilmfestival.adapter.ScheduleDetailAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.ScheduleDetailEntity;
import com.icts.shortfilmfestival.fragment.ScheduleFragment;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.myjson.JSScheduleDetail;
import com.icts.shortfilmfestival.tabgroup.NewsTab;
import com.icts.shortfilmfestivalJa.MainTabActivity;
import com.icts.shortfilmfestivalJa.R;
public class ScheduleDetail extends Fragment implements
LoaderCallbacks<Void>, OnScrollListener, OnItemClickListener,
OnClickListener {
	private static final String TAG = "NewsListFragment";
	private View mRoot;
	private Fragment mFragmentTarget;
	private ScheduleDetailAdapter mAdapter;
	private ProgressDialog mProgressDialog;
	private ListView mListView;
	private ArrayList<ScheduleDetailEntity> mItems;
	private String mLink;
	public static final String TAB_LAFORET = "laforet";
	public static final String TAB_SPACEO = "spaceo";
	public static final String TAB_TOHO = "toho";
	public static final String TAB_YOKOHAMA = "yokohama";
	private ImageView imgBack;
	
	public ScheduleDetail() {
	}

	public ScheduleDetail(String tag, String link, Fragment pFragmentTarget) {
		this.mFragmentTarget = pFragmentTarget;
		mLink = link;
		ScheduleFragment.isDetail = true;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mProgressDialog = new ProgressDialog(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRoot = inflater.inflate(R.layout.festival_schedule_detail, null);
		mListView = (ListView) mRoot.findViewById(R.id.list_schedule_detail);		
		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		getLoaderManager().initLoader(0, null, this);
		
		imgBack = (ImageView) mRoot.findViewById(R.id.imgBack);		
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				back();
			}
		});
		
		return mRoot;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView paramAbsListView, int paramInt1,
			int paramInt2, int paramInt3) {
		// TODO Auto-generated method stub

	}

	@Override
	public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		Log.d(TAG, MainTabActivity.isFromPhotos + "--------------");
		AsyncTaskLoader<Void> loader = null;
		
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.show();
		
			loader = new AsyncTaskLoader<Void>(getActivity()) {
	
				@Override
				public Void loadInBackground() {
					Log.d(TAG, "-----------loading-------------");
					JSScheduleDetail mJSScheduleDetail = new JSScheduleDetail();
					// Check if Locate # Japanese
				
					String url = mLink + "&lang=" + ISettings.LANGUAGE ;
					mItems = mJSScheduleDetail.getmArrayListScheduleDetailEntity(
							SSSFApi.getAllScheduleDetail(url));
					return null;
				}
			};
			
			loader.forceLoad();
			return loader;
	}

	@Override
	public void onLoadFinished(Loader<Void> arg0, Void arg1) {
		mProgressDialog.dismiss();
		if (mItems == null || mItems.size() == 0)
		{
			
			return;
		}
		
			 if (mItems != null)
			 {
				 Log.d(TAG, "-----" + mItems.size()  +"------");
				 mAdapter = new ScheduleDetailAdapter(getActivity(), mItems, getActivity());
				 mListView.setAdapter(mAdapter);
				 mAdapter.notifyDataSetChanged();
			 }
			
		}

	@Override
	public void onLoaderReset(Loader<Void> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View paramView) {
		
	}
	private void back()
	{
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
		if (mFragmentTarget != null)
		{
			NewsTab.isDeailShow = false;
			ft.hide(this);
			ft.show(mFragmentTarget);
			//ft.replace(R.id.tabs_fragment, new NewsFragment());
			ft.commit();
		}
	}
	
}
