package com.icts.shortfilmfestival.detailfragment;

import java.util.ArrayList;

import javax.crypto.spec.PSource;

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
import android.widget.ListView;
import android.widget.TextView;

import com.icts.shortfilmfestival.adapter.ScheduleAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.ScheduleEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.myjson.JSSchedule;
import com.icts.shortfilmfestival_en.MainTabActivity;
import com.vnp.shortfilmfestival.R;

public class ScheduleListFragment extends Fragment implements
		LoaderCallbacks<Void>, OnScrollListener, OnItemClickListener {
	private static final String TAG = "NewsListFragment";
	private View mRoot;
	private Fragment mFragmentTarget;
	private String mTag;
	private int indexLoadDetail;
	private ScheduleAdapter mAdapter;
	private ProgressDialog mProgressDialog;
	private Fragment mScheduleDetailFragment;
	private ListView mListView;
	private String typeSort = "";
	private ArrayList<ScheduleEntity> mItemsTemp;
	private String typeSortEvent = "eventAsc";
	private String typeSortDate = "eventAsc";
	private String typeSortTime = "eventAsc";

	public ScheduleListFragment() {
	}

	public ScheduleListFragment(String tag, Fragment pFragmentTarget) {
		this.mFragmentTarget = pFragmentTarget;
		mTag = tag;
	}

	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mProgressDialog = new ProgressDialog(activity);
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRoot = inflater.inflate(R.layout.list_event_fragment, null);
		mListView = (ListView) mRoot.findViewById(R.id.list_schedule);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		getLoaderManager().initLoader(0, null, this);
		// SOrt
		typeSort = "eventAsc";
		typeSortEvent = "eventDesc";
		return mRoot;
	}

	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			final int position, long paramLong) {
		Log.d(TAG, mItemsTemp.get(position).getLink());
		if (mItemsTemp.get(position).isClick() == false)
		{
			return;
		}
		final FragmentTransaction ft = getActivity()
				.getSupportFragmentManager().beginTransaction();
		indexLoadDetail += 1;
		getLoaderManager().initLoader(indexLoadDetail, null,
				new LoaderCallbacks<Void>() {

					
					public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
						mProgressDialog.setCancelable(false);
						mProgressDialog.setMessage("Loading...");
						mProgressDialog.show();
						AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(
								getActivity()) {

							
							public Void loadInBackground() {
								Log.d(TAG, "________COME HERE________");
								ft.setCustomAnimations(
										android.R.anim.slide_in_left,
										android.R.anim.slide_out_right);
								ft.hide(mFragmentTarget);
								mScheduleDetailFragment = new ScheduleDetail(
										mTag, mItemsTemp.get(position)
												.getLink(), mFragmentTarget);
								ft.replace(R.id.detail_fragment,
										mScheduleDetailFragment);

								ft.commit();
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
				});
		
	}

	
	public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
		// TODO Auto-generated method stub

	}

	
	public void onScroll(AbsListView paramAbsListView, int paramInt1,
			int paramInt2, int paramInt3) {
		// TODO Auto-generated method stub

	}

	
	public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		Log.d(TAG, MainTabActivity.isFromPhotos + "--------------");
		AsyncTaskLoader<Void> loader = null;

		mProgressDialog.setMessage("Loading...");
		mProgressDialog.show();

		loader = new AsyncTaskLoader<Void>(getActivity()) {

			
			public Void loadInBackground() {
				Log.d(TAG, "-----------loading-------------");
				JSSchedule mJSSchedule = new JSSchedule();

				// Check if Locate # Japanese
				String url = ISettings.SCHEDULE_URL + "?type=" + mTag
				+ "&lang=" + ISettings.LANGUAGE;
			    url += ("&sort=dateAsc");
				mItemsTemp = mJSSchedule.getmArrayListPhotoEntity(

			    SSSFApi.getAllSchedule(url, mTag, ISettings.LANG_ENGLISH));
				
//				if (!Resource.localization.equals("ko_KR")) {
//					
//				} else {
//					String url = ISettings.SCHEDULE_URL + "?type=" + mTag
//							+ "&lang=" + ISettings.LANG_JP;
//					if (!typeSort.equals("")) {
//						url += ("&sort=" + typeSort);
//					}
//					mItemsTemp = mJSSchedule.getmArrayListPhotoEntity(SSSFApi
//							.getAllSchedule(url, mTag, ISettings.LANG_JP));
//				}

				return null;
			}
		};

		loader.forceLoad();
		return loader;
	}

	
	public void onLoadFinished(Loader<Void> arg0, Void arg1) {
		mProgressDialog.dismiss();
		if (mItemsTemp == null || mItemsTemp.size() == 0) {
			return;
		}

		if (mItemsTemp != null) {
			mAdapter = new ScheduleAdapter(getActivity(), mItemsTemp);
			mListView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
		}
	}

	
	public void onLoaderReset(Loader<Void> arg0) {
		// TODO Auto-generated method stub

	}

}
