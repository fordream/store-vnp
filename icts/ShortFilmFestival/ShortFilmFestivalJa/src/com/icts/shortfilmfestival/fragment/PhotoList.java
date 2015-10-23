package com.icts.shortfilmfestival.fragment;

import java.util.ArrayList;

import com.icts.shortfilmfestival.adapter.PhotosAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.PhotosEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.myjson.JSPhoto;
import com.icts.shortfilmfestivalJa.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.GridView;

public class PhotoList extends Fragment implements LoaderCallbacks<Void> , OnScrollListener, OnItemClickListener{
	private View mRoot;
	private ArrayList<PhotosEntity> mItems;
	private ArrayList<PhotosEntity> mItemsTemp;
	private boolean loadingMore = true;
	private GridView gridview;
	private String mType;
	private PhotosAdapter mPhotosAdapter;
	private static final String TAG = "LOG_PHOTOLIST";
	private ProgressDialog mProgressDialog;
	private int totalRow;
	public PhotoList(String tabId)
	{
		if (tabId.equals(PhotoFragment.TAB_FESTIVAL))
		{
			this.mType = "ssff";
		}
		else if (tabId.equals(PhotoFragment.TAB_BIZ))
		{
			this.mType = "biz";
		}
		else if (tabId.equals(PhotoFragment.TAB_LOUNGE))
		{
			this.mType = "lounge";
		}
		else if (tabId.equals(PhotoFragment.TAB_THEATER))
		{
			this.mType = "theater";
		}
		else if (tabId.equals(PhotoFragment.TAB_ALL))
		{
			this.mType = "all";
		}
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mProgressDialog = new ProgressDialog(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadingMore = true;
		mRoot = inflater.inflate(R.layout.list_photo, null);
		gridview = (GridView) mRoot.findViewById(R.id.gridview);
		gridview.setOnScrollListener(this);
		gridview.setOnItemClickListener(this);
		//gridview.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));
		mItems = new ArrayList<PhotosEntity>();
		mItemsTemp = new ArrayList<PhotosEntity>();
		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		getLoaderManager().initLoader(0, null, this);
		totalRow = 0;
		return mRoot;
	}
	@Override
	public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.show();
		AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(getActivity()) {

			@Override
			public Void loadInBackground() {
				Log.d(TAG, "-----------loading-------------");
				
				loadingMore = false;
				JSPhoto mJSPhoto = new JSPhoto();
				mItemsTemp = mJSPhoto.getmArrayListPhotoEntity(
						SSSFApi.getAllPhoto(ISettings.PHOTOS_URL + "?type=" + mType + "&limit=20&offset=" + (mItems.size())));
				totalRow = mJSPhoto.getTotalRow();
				
				return null;
			}
		};
		
		loader.forceLoad();
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Void> arg0, Void arg1) {
		mProgressDialog.dismiss();
		 if (mPhotosAdapter == null) {
			 	mItems.addAll(mItemsTemp);
			 	mPhotosAdapter = new PhotosAdapter(getActivity(), mItems);
			}
		 else
		 {
			 mItems.addAll(mItemsTemp);
		 }
		 gridview.setAdapter(mPhotosAdapter);
		 mPhotosAdapter.notifyDataSetChanged();
		 loadingMore = true;
	}

	@Override
	public void onLoaderReset(Loader<Void> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		//Log.d(TAG, visibleItemCount + "," + firstVisibleItem + "," + totalItemCount);
		if (mItems != null && mItems.size() < totalRow)
		{
			if (visibleItemCount + firstVisibleItem >= totalItemCount && totalItemCount > 0)
			{
				if (loadingMore)
				{
					Log.d(TAG, "Total Row:" + totalRow + "-- Size Items:" + mItems.size());
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
		Intent mIntent = new Intent();
		mIntent.putExtra("URL_IAMGE", mItems.get(postition).getImgBig());
		mIntent.putExtra("CAPTION", mItems.get(postition).getCaption());
		mIntent.putExtra("PICTURE", mItems.get(postition).getImgSmall());
		mIntent.setClass(getActivity(), PhotoDetail.class);
		startActivity(mIntent);
		
	}
	
}
