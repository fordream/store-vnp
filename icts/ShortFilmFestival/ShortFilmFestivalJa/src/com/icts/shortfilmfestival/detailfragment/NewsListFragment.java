package com.icts.shortfilmfestival.detailfragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.icts.shortfilmfestival.adapter.NewsAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.MovieEntity;
import com.icts.shortfilmfestival.entity.NewsEntity;
import com.icts.shortfilmfestival.fragment.NewsFragment;
import com.icts.shortfilmfestival.fragment.WebViewFragmentTheater;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.myjson.JSNews;
import com.icts.shortfilmfestival.myjson.JSNewsTop;
import com.icts.shortfilmfestival.zzz.t.api.BannerView;
import com.icts.shortfilmfestival.zzz.t.api.MyGallery;
import com.icts.shortfilmfestival.zzz.t.api.Rest;
import com.icts.shortfilmfestivalJa.MainTabActivity;
import com.icts.shortfilmfestivalJa.R;

public class NewsListFragment extends Fragment implements
		LoaderCallbacks<Void>, OnScrollListener, OnItemClickListener {
	private static final String TAG = "NewsListFragment";
	private View mRoot;
	private Fragment mFragmentTarget;
	private String mTypeNews;
	private int indexLoad;
	private int indexLoadDetail;
	private NewsAdapter mAdapter;
	private ArrayList<NewsEntity> mItems, mItemsTemp;
	private int mTotal;
	private boolean loadingMore = true;
	private LayoutInflater mInflater;
	private View footer;
	private ProgressDialog mProgressDialog;
	private Fragment mNewsDetailFragment;
	private ListView mListView;
	private TextView mReloadTextView;
	private String mTag;
	private MovieEntity mMovieEntity;

	public NewsListFragment() {
	}

	public NewsListFragment(String tag, Fragment pFragmentTarget) {
		mTag = tag;
		if (tag.equals(NewsFragment.TAB_FESTIVAL)) {
			mTypeNews = "ssff";
		} else if (tag.equals(NewsFragment.TAB_BIZ)) {
			mTypeNews = "biz";
		} else if (tag.equals(NewsFragment.TAB_LOUNGE)) {
			mTypeNews = "lounge";
		} else if (tag.equals(NewsFragment.TAB_THEATER)) {
			mTypeNews = "theater";
		} else if (tag.equals(NewsFragment.TAB_ALL)) {
			mTypeNews = "all";
		}
		this.mFragmentTarget = pFragmentTarget;
		// mAdapter = null;
		mItems = new ArrayList<NewsEntity>();
		Log.d(TAG, "Constructor: tag=" + tag);
		mItems = new ArrayList<NewsEntity>();
		mItemsTemp = new ArrayList<NewsEntity>();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mProgressDialog = new ProgressDialog(activity);
		mProgressDialog.setCancelable(false);
	}

	private BannerView gallery;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadingMore = true;
		mRoot = inflater.inflate(R.layout.list_news_fragment, null);
		mListView = (ListView) mRoot.findViewById(R.id.listview);
		mReloadTextView = (TextView) mRoot.findViewById(R.id.try_again);
		// mReloadTextView.setOnTouchListener(this);
		mListView.setOnScrollListener(this);
		mListView.setOnItemClickListener(this);

		gallery = new BannerView(getActivity());
		mListView.addHeaderView(gallery);
		mItems = new ArrayList<NewsEntity>();
		mItemsTemp = new ArrayList<NewsEntity>();
		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		getLoaderManager().initLoader(0, null, this);

		mTotal = 0;

		return mRoot;
	}

	Rest rest;

	@Override
	public void onResume() {
		super.onResume();
		if (NewsFragment.TAB_ALL.equals(mTag)) {
			LinearLayout layout = (LinearLayout) mRoot
					.findViewById(R.id.llSwich);
			if (rest != null) {
				rest.close();
			}
			rest = new Rest();
			rest.setSlide(gallery);
			Intent intent = new Intent("reset");
			getActivity().sendBroadcast(intent);
		}
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setRetainInstance(true);
		if (mTag == null) {
			mTag = NewsFragment.TAB_ALL;
		}
		indexLoad = 0;
		loadingMore = true;
		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		getLoaderManager().initLoader(indexLoad++, null, this);
		mListView.setOnScrollListener(this);
		mInflater = LayoutInflater.from(getActivity().getApplicationContext());
		if (mInflater != null && footer == null) {
			footer = mInflater.inflate(R.layout.row_loading_more, null);
			mListView.addFooterView(footer);
			footer.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public Loader<Void> onCreateLoader(int id, Bundle args) {
		Log.d(TAG, MainTabActivity.isFromPhotos + "--------------");
		AsyncTaskLoader<Void> loader = null;
		if (MainTabActivity.isFromPhotos == false
				&& MainTabActivity.isFromMovie == false
				&& MainTabActivity.isFromApps == false) {
			if (!MainTabActivity.isFromNotification) {
				mProgressDialog.setCancelable(false);
				mProgressDialog.setMessage("Loading...");
				mProgressDialog.show();
			}
			Resource.isLoading = true;
			if (mTypeNews == null) {
				mTypeNews = "all";
			}
			loader = new AsyncTaskLoader<Void>(getActivity()) {

				@Override
				public Void loadInBackground() {
					Log.d(TAG, "-----------loading-------------");
					loadingMore = false;
					JSNews mJSNews = new JSNews();
					mItemsTemp = mJSNews.getmArrayListNewsEntity(SSSFApi
							.getAllNews(ISettings.NEWS_URL_PREFIX + "?type="
									+ mTypeNews + "&lang=" + ISettings.LANGUAGE
									+ "&limit=5&offset=" + (mItems.size()),
									mTypeNews, ISettings.LANG_ENGLISH));
					mTotal = mJSNews.getTotal();

					JSNewsTop mJSNewsTop = new JSNewsTop();
					if (ISettings.LANGUAGE.equals("en")) {
						mMovieEntity = mJSNewsTop
								.getmMovieEntity(SSSFApi
										.getYoutubeTopNews(ISettings.YOUTUBE_LINK_TOP_NEWS_EN));
					} else {
						mMovieEntity = mJSNewsTop
								.getmMovieEntity(SSSFApi
										.getYoutubeTopNews(ISettings.YOUTUBE_LINK_TOP_NEWS_JA));
					}

					return null;
				}
			};

			loader.forceLoad();
		}
		return loader;

	}

	@Override
	public void onLoadFinished(Loader<Void> loader, Void result) {
		Log.d(TAG, "________________ GONE ________________");
		mProgressDialog.setCancelable(true);
		mProgressDialog.dismiss();
		Resource.isLoading = false;
		mListView.setVisibility(View.VISIBLE);
		if (mItemsTemp == null || mItemsTemp.size() == 0) {
			if (mItems == null || mItems.size() == 0) {
				mProgressDialog.dismiss();

				mListView.setVisibility(View.GONE);
				return;
			}
		}

		if (mAdapter == null) {
			if (mItemsTemp != null && mItems != null) {
				if (mTag.equals(NewsFragment.TAB_ALL)) {
					Log.d(TAG, "------------------Add NewsEntity");
					mItemsTemp.add(0, new NewsEntity());
				}
				mItems.addAll(mItemsTemp);
				mAdapter = new NewsAdapter(getActivity(), mItems, getActivity());
			}
		} else {
			mItems.addAll(mItemsTemp);
		}
		if (footer != null) {
			footer.setVisibility(View.INVISIBLE);
		}
		if (mAdapter != null) {
			mListView.setAdapter(mAdapter);
			mAdapter.notifyDataSetChanged();
			loadingMore = true;
			mListView.setSelection(mAdapter.getmPosition());
		}
	}

	@Override
	public void onLoaderReset(Loader<Void> loader) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		if (mAdapter != null) {
			// Log.d(TAG, mAdapter.getmPosition() + "");
		}
		if (mItems != null && mItems.size() < mTotal) {
			if ((visibleItemCount + firstVisibleItem >= totalItemCount)
					&& totalItemCount > 0) {
				if (loadingMore) {
					Log.d(TAG, "RestartLoad");
					// addFooter();
					getLoaderManager().restartLoader(0, null, this);
				}
			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
			long arg3) {
		Log.d(TAG, "onItemClick");

		if (position == 0 && mTag.equals(NewsFragment.TAB_ALL)) {
			if (mMovieEntity != null && !mMovieEntity.getId().equals("")) {
				String videoId = mMovieEntity.getId();
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("vnd.youtube:" + videoId));
				intent.putExtra("VIDEO_ID", videoId);
				startActivity(intent);
			}
			return;
		}
		final FragmentTransaction ft = getActivity()
				.getSupportFragmentManager().beginTransaction();

		indexLoadDetail += 1;
		getLoaderManager().initLoader(indexLoadDetail, null,
				new LoaderCallbacks<Void>() {

					@Override
					public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
						mProgressDialog.setCancelable(false);
						mProgressDialog.setMessage("Loading...");
						mProgressDialog.show();

						AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(
								getActivity()) {

							@Override
							public Void loadInBackground() {
								if (mTypeNews.equals("theater")) {
									WebViewFragmentTheater mWebViewFragmentTheater = new WebViewFragmentTheater(
											mFragmentTarget, mItems.get(
													position).getType(), mItems
													.get(position).getId(),
											mItems.get(position).getUrl(),
											mItems.get(position).getTitle());
									ft.setCustomAnimations(
											android.R.anim.slide_in_left,
											android.R.anim.slide_out_right);
									ft.hide(mFragmentTarget);
									ft.replace(R.id.detail_fragment,
											mWebViewFragmentTheater);
									ft.commit();

								} else {
									mNewsDetailFragment = new NewsDetail(
											mFragmentTarget, mItems.get(
													position).getType(), mItems
													.get(position).getId(),
											mItems.get(position).getUrl());
									Log.d(TAG, "________COME HERE________");
									ft.setCustomAnimations(
											android.R.anim.slide_in_left,
											android.R.anim.slide_out_right);
									ft.hide(mFragmentTarget);

									ft.replace(R.id.detail_fragment,
											mNewsDetailFragment);
									ft.commit();
								}
								return null;
							}
						};

						loader.forceLoad();

						return loader;
					}

					@Override
					public void onLoadFinished(Loader<Void> arg0, Void arg1) {
						mProgressDialog.setCancelable(true);
						mProgressDialog.dismiss();
					}

					@Override
					public void onLoaderReset(Loader<Void> arg0) {

					}

				});
	}

	// @Override
	// public boolean onTouch(View v, MotionEvent c) {
	// if (c.getAction() == MotionEvent.ACTION_DOWN) {
	// if (v.equals(mReloadTextView)) {
	// refreshLoad();
	// }
	// return true;
	// }
	// return false;
	// }
	//
	// private void refreshLoad() {
	// getLoaderManager().restartLoader(0, null, this);
	// }
}
