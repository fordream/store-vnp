package com.icts.shortfilmfestival.detailfragment;

import java.util.ArrayList;

import com.icts.shortfilmfestival.adapter.MovieAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.MovieEntity;
import com.icts.shortfilmfestival.fragment.MoviesFragment;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.myjson.JSYoutube;
import com.icts.shortfilmfestival_en.MainTabActivity;
import com.vnp.core.service.RestClient;
import com.vnp.core.service.RestClient.RequestMethod;
import com.vnp.shortfilmfestival.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MovieListFragment extends Fragment implements LoaderCallbacks<Void>, OnScrollListener, OnItemClickListener, OnTouchListener {
	private static final String TAG = "YoutubeListFragment";
	private Fragment mFragmentTarget;
	private String mTag;
	private String mTypeMovies;
	private MovieAdapter mAdapter;
	private ArrayList<MovieEntity> mItems, mItemsTemp;
	private int mTotalYoutube;
	private int mTotalUstream;
	private TextView mReloadTextView;

	public int getmTotalYoutube() {
		return mTotalYoutube;
	}

	public void setmTotalYoutube(int mTotalYoutube) {
		this.mTotalYoutube = mTotalYoutube;
	}

	public int getmTotalUstream() {
		return mTotalUstream;
	}

	public void setmTotalUstream(int mTotalUstream) {
		this.mTotalUstream = mTotalUstream;
	}

	private int mPosition;
	private ListView mListView;
	private boolean loadingMore = true;
	private ProgressDialog mProgressDialog;
	private View mRoot;
	private int totalRow;

	public MovieListFragment() {
	}

	public MovieListFragment(String tag, Fragment pFragmentTarget) {
		if (tag == MoviesFragment.TAB_YOUTUBE) {
			mTypeMovies = "youtube";
		} else if (tag == MoviesFragment.TAB_USTREAM) {
			mTypeMovies = "ustream";
		}

		this.mFragmentTarget = pFragmentTarget;
		mTag = tag;
		mPosition = 0;
		// mAdapter = null;
		mItems = new ArrayList<MovieEntity>();
		mItemsTemp = new ArrayList<MovieEntity>();
		loadingMore = true;
		Log.d(TAG, "Constructor: tag=" + tag);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mProgressDialog = new ProgressDialog(activity);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadingMore = true;
		mRoot = inflater.inflate(R.layout.list_movie_fragment, null);
		mListView = (ListView) mRoot.findViewById(R.id.list_movie);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);
		mReloadTextView = (TextView) mRoot.findViewById(R.id.try_again);
		mReloadTextView.setOnTouchListener(this);
		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		getLoaderManager().initLoader(0, null, this);
		totalRow = 0;
		return mRoot;
	}

	public Loader<Void> onCreateLoader(int id, Bundle args) {
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.show();
		AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(getActivity()) {

			public Void loadInBackground() {
				loadingMore = false;
				if (mTypeMovies.equals("youtube")) {
					JSYoutube mJSYoutube = new JSYoutube(ISettings.TYPE_YOUTUBE);
					mItemsTemp = mJSYoutube.getmArrayListMovieEntity(SSSFApi.getAllYoutube(ISettings.YOUTUBE_URL + "?limit=10&offset=" + mItems.size()));
					// http://www.shortshorts.org/api/list.php
					//apissff
//					String url = "http://www.shortshorts.org/api/ytvod.php?lang=en&limit=10&offset=0";
//
//					RestClient client = new RestClient(url);
//					try {
//						client.execute(RequestMethod.POST);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					Log.e("ABCD", client.getResponse() + "x");

					mTotalYoutube = mJSYoutube.getTotal();
					mTotalUstream = 0;
				} else if (mTypeMovies.equals("ustream")) {
					Log.d(TAG, "________COME HERE________");
					JSYoutube mJSYoutube = new JSYoutube(ISettings.TYPE_USTREAM);
					mItemsTemp = mJSYoutube.getmArrayListMovieEntity(SSSFApi.getAllYoutube(ISettings.USTREAM_URL + "?limit=10&offset=" + mItems.size()));
					mTotalUstream = mJSYoutube.getTotal();
					mTotalYoutube = 0;
				}
				return null;
			}
		};
		loader.forceLoad();
		return loader;
	}

	public void onLoadFinished(Loader<Void> loader, Void result) {
		Log.d(TAG, mItems.size() + "----------------------");
		mProgressDialog.setCancelable(true);
		mProgressDialog.dismiss();
		mListView.setVisibility(View.VISIBLE);
		MainTabActivity.isFromMovie = false;
		if (mItemsTemp == null || mItemsTemp.size() == 0) {
			if (mItemsTemp == null || mItemsTemp.size() == 0) {
				mProgressDialog.dismiss();
				mListView.setVisibility(View.GONE);
				return;
			}
		}

		if (mItemsTemp != null && mItems != null) {
			mItems.addAll(mItemsTemp);
			if (mAdapter == null) {

				mAdapter = new MovieAdapter(getActivity(), mItems, mTypeMovies);
			}
			mListView.setAdapter(mAdapter);
			mListView.setSelection(mPosition);
			mAdapter.notifyDataSetChanged();
		}
		loadingMore = true;
	}

	public void onLoaderReset(Loader<Void> loader) {

	}

	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem + visibleItemCount >= totalItemCount) {
			if (loadingMore) {
				if (mAdapter != null) {
					this.mPosition = mAdapter.getPosition();
				}
				if (mTypeMovies.equals("youtube")) {
					if (!(this.mItems.size() >= mTotalYoutube)) {
						getLoaderManager().restartLoader(0, null, this);
					}
				} else if (mTypeMovies.equals("ustream")) {
					if (!(this.mItems.size() >= mTotalUstream)) {
						getLoaderManager().restartLoader(0, null, this);
					}
				}
			}
		}
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Log.d(TAG, "--------Come here---------");
		if (mTypeMovies.equals("youtube")) {
			String videoId = mItems.get(position).getId();
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
			intent.putExtra("VIDEO_ID", videoId);
			startActivity(intent);
		} else if (mTypeMovies.equals("ustream")) {
			final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(mItems.get(position).getLink()));
			startActivity(intent);
		}

	}

	public boolean onTouch(View v, MotionEvent c) {
		if (c.getAction() == MotionEvent.ACTION_DOWN) {
			if (v.equals(mReloadTextView)) {
				refreshLoad();
			}
			return true;
		}
		return false;
	}

	private void refreshLoad() {
		getLoaderManager().restartLoader(0, null, this);
	}

}
