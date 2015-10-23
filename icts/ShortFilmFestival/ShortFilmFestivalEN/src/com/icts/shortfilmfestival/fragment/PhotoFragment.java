package com.icts.shortfilmfestival.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.icts.shortfilmfestival.adapter.PhotosAdapter;
import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.PhotosEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.myjson.JSPhoto;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.icts.shortfilmfestival_en.MainTabActivity;
import com.vnp.core.service.RestClient;
import com.vnp.core.service.RestClient.RequestMethod;
import com.vnp.shortfilmfestival.R;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhotoFragment extends Fragment implements LoaderCallbacks<Void>, OnClickListener {
	private static final String TAG = "FragmentTabs";
	public static final String TAB_FESTIVAL = "Festival";
	public static final String TAB_BIZ = "Biz";
	public static final String TAB_LOUNGE = "Lounge";
	public static final String TAB_THEATER = "Theater";
	public static final String TAB_ALL = "Top";

	private GridView mAllGridView;
	private GridView mFestivalGridView;
	private GridView mBizGridView;
	private GridView mLoungeGridView;
	private GridView mTheaterGridView;

	private LinearLayout mAllGridLinearLayout;
	private LinearLayout mFestivalGridLinearLayout;
	private LinearLayout mBizGridLinearLayout;
	private LinearLayout mLougneGridLinearLayout;
	private LinearLayout mTheaterGridLinearLayout;

	private LinearLayout mAllTitleLinearLayout;
	private LinearLayout mFestivalTitleLinearLayout;
	private LinearLayout mBizTitleLinearLayout;
	private LinearLayout mLougneTitleLinearLayout;
	private LinearLayout mTheaterTitleLinearLayout;

	private TextView mAllTextView;
	private TextView mFestivalTextView;
	private TextView mBizTextView;
	private TextView mLougneTextView;
	private TextView mTheaterTextView;

	private ArrayList<PhotosEntity> mItemsAll;
	private ArrayList<PhotosEntity> mItemsAllTemp;
	private ArrayList<PhotosEntity> mItemsFestival;
	private ArrayList<PhotosEntity> mItemsBiz;
	private ArrayList<PhotosEntity> mItemsLougne;
	private ArrayList<PhotosEntity> mItemsTheater;

	private String mArrayImageUrlAll[];
	private String mArrayImageUrlFestival[];
	private String mArrayImageUrlBiz[];
	private String mArrayImageUrlLougne[];
	private String mArrayImageUrlTheater[];

	private String mArrayImageCategoryAll[];
	private String mArrayImageCategoryFestival[];
	private String mArrayImageCategoryBiz[];
	private String mArrayImageCategoryLougne[];
	private String mArrayImageCategoryTheater[];

	private String mArrayImageCaptionAll[];
	private String mArrayImageCaptionFestival[];
	private String mArrayImageCaptionBiz[];
	private String mArrayImageCaptionLougne[];
	private String mArrayImageCaptionTheater[];

	private ImageView mUpdateImageView;
	private TextView mDateUpdateTextView;

	private PhotosAdapter mAllPhotosAdapter;
	private PhotosAdapter mFestivalPhotosAdapter;
	private PhotosAdapter mBizPhotosAdapter;
	private PhotosAdapter mLougnePhotosAdapter;
	private PhotosAdapter mTheaterPhotosAdapter;

	private ProgressDialog mProgressDialog;

	private View mRoot;
	private Activity mActivity;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRoot = inflater.inflate(R.layout.list_photo, null);

		mAllGridView = (GridView) mRoot.findViewById(R.id.gridview_all_id);
		mAllGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
				Intent mIntent = new Intent();
				mIntent.putExtra("URL_IAMGE", mItemsAll.get(postition).getImgBig());
				mIntent.putExtra("CAPTION", mArrayImageCaptionAll);
				mIntent.putExtra("CATEGORY", mArrayImageCategoryAll);
				mIntent.putExtra("LIST", mArrayImageUrlAll);
				mIntent.putExtra("INDEX", postition);
				mIntent.putExtra("PICTURE", mItemsAll.get(postition).getImgSmall());
				mIntent.setClass(getActivity(), PhotoDetail.class);
				startActivity(mIntent);

			}
		});
		mFestivalGridView = (GridView) mRoot.findViewById(R.id.gridview_festival_id);
		mFestivalGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
				Intent mIntent = new Intent();
				mIntent.putExtra("URL_IAMGE", mItemsFestival.get(postition).getImgBig());
				mIntent.putExtra("PICTURE", mItemsFestival.get(postition).getImgSmall());
				mIntent.putExtra("CAPTION", mArrayImageCaptionFestival);
				mIntent.putExtra("CATEGORY", mArrayImageCategoryFestival);
				mIntent.putExtra("LIST", mArrayImageUrlFestival);
				mIntent.putExtra("INDEX", postition);
				mIntent.setClass(getActivity(), PhotoDetail.class);
				startActivity(mIntent);

			}
		});
		mBizGridView = (GridView) mRoot.findViewById(R.id.gridview_biz_id);
		mBizGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
				Intent mIntent = new Intent();
				mIntent.putExtra("URL_IAMGE", mItemsBiz.get(postition).getImgBig());
				mIntent.putExtra("PICTURE", mItemsBiz.get(postition).getImgSmall());
				mIntent.putExtra("CAPTION", mArrayImageCaptionBiz);
				mIntent.putExtra("CATEGORY", mArrayImageCategoryBiz);
				mIntent.putExtra("LIST", mArrayImageUrlBiz);
				mIntent.putExtra("INDEX", postition);
				mIntent.setClass(getActivity(), PhotoDetail.class);
				startActivity(mIntent);

			}
		});
		mLoungeGridView = (GridView) mRoot.findViewById(R.id.gridview_lounge_id);
		mLoungeGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
				Intent mIntent = new Intent();
				mIntent.putExtra("URL_IAMGE", mItemsLougne.get(postition).getImgBig());
				mIntent.putExtra("PICTURE", mItemsLougne.get(postition).getImgSmall());
				mIntent.putExtra("CAPTION", mArrayImageCaptionLougne);
				mIntent.putExtra("CATEGORY", mArrayImageCategoryLougne);
				mIntent.putExtra("LIST", mArrayImageUrlLougne);
				mIntent.putExtra("INDEX", postition);
				mIntent.setClass(getActivity(), PhotoDetail.class);
				startActivity(mIntent);

			}
		});
		mTheaterGridView = (GridView) mRoot.findViewById(R.id.gridview_theater_id);
		mTheaterGridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int postition, long arg3) {
				Intent mIntent = new Intent();
				mIntent.putExtra("URL_IAMGE", mItemsTheater.get(postition).getImgBig());
				mIntent.putExtra("PICTURE", mItemsTheater.get(postition).getImgSmall());
				mIntent.putExtra("CAPTION", mArrayImageCaptionTheater);
				mIntent.putExtra("CATEGORY", mArrayImageCategoryTheater);
				mIntent.putExtra("LIST", mArrayImageUrlTheater);
				mIntent.putExtra("INDEX", postition);
				mIntent.setClass(getActivity(), PhotoDetail.class);
				startActivity(mIntent);

			}
		});

		mAllGridLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_gridview_all_id);
		mFestivalGridLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_gridview_festival_id);
		mBizGridLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_gridview_biz_id);
		mLougneGridLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_gridview_lounge_id);
		mTheaterGridLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_gridview_theater_id);

		mAllTitleLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_all_id);
		mAllTitleLinearLayout.setOnClickListener(this);
		mFestivalTitleLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_festival_id);
		mFestivalTitleLinearLayout.setOnClickListener(this);
		mBizTitleLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_biz_id);
		mBizTitleLinearLayout.setOnClickListener(this);
		mLougneTitleLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_lounge_id);
		mLougneTitleLinearLayout.setOnClickListener(this);
		mTheaterTitleLinearLayout = (LinearLayout) mRoot.findViewById(R.id.layout_theater_id);
		mTheaterTitleLinearLayout.setOnClickListener(this);
		boolean isJp = false;
		if (Resource.localization.equals(ISettings.LANG_JP_FONT)) {
			isJp = true;
		}
		mAllTextView = (TextView) mRoot.findViewById(R.id.all_title_id);
		FontUtils.setCustomFont(mAllTextView, true, isJp, getActivity().getAssets());
		mFestivalTextView = (TextView) mRoot.findViewById(R.id.festival_title_id);
		FontUtils.setCustomFont(mFestivalTextView, true, isJp, getActivity().getAssets());
		mBizTextView = (TextView) mRoot.findViewById(R.id.biz_title_id);
		FontUtils.setCustomFont(mBizTextView, true, isJp, getActivity().getAssets());
		mLougneTextView = (TextView) mRoot.findViewById(R.id.lounge_title_id);
		FontUtils.setCustomFont(mLougneTextView, true, isJp, getActivity().getAssets());
		mTheaterTextView = (TextView) mRoot.findViewById(R.id.theater_title_id);
		FontUtils.setCustomFont(mTheaterTextView, true, isJp, getActivity().getAssets());

		mUpdateImageView = (ImageView) mRoot.findViewById(R.id.update_list_id);
		mUpdateImageView.setOnClickListener(this);
		mDateUpdateTextView = (TextView) mRoot.findViewById(R.id.date_update_id);
		mProgressDialog = new ProgressDialog(this.mActivity);
		mProgressDialog.setCancelable(false);
		mItemsAll = new ArrayList<PhotosEntity>();
		mItemsFestival = new ArrayList<PhotosEntity>();
		mItemsBiz = new ArrayList<PhotosEntity>();
		mItemsLougne = new ArrayList<PhotosEntity>();
		mItemsTheater = new ArrayList<PhotosEntity>();
		mItemsAllTemp = new ArrayList<PhotosEntity>();

		SSSFApi.mConnectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		getLoaderManager().initLoader(0, null, this);
		mAllGridLinearLayout.setVisibility(View.VISIBLE);
		mFestivalGridLinearLayout.setVisibility(View.GONE);
		mBizGridLinearLayout.setVisibility(View.GONE);
		mLougneGridLinearLayout.setVisibility(View.GONE);
		mTheaterGridLinearLayout.setVisibility(View.GONE);
		return mRoot;
	}

	public void onResume() {

		super.onResume();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	public Loader<Void> onCreateLoader(int arg0, Bundle arg1) {
		Log.d("NewsListFragment", "Loading.......");
		mProgressDialog.setCancelable(false);
		mProgressDialog.setMessage("Loading...");
		mProgressDialog.show();
		AsyncTaskLoader<Void> loader = new AsyncTaskLoader<Void>(getActivity()) {

			public Void loadInBackground() {
				Log.d(TAG, "-----------loading-------------");
				JSPhoto mJSPhoto = new JSPhoto();
				mItemsAll = mJSPhoto.getmArrayListPhotoEntity(SSSFApi.getAllPhoto(ISettings.PHOTOS_URL));

				//?type=all&lang=en&limit=10&offset=0
				String url = "http://www.shortshorts.org/api/photos.php";
				RestClient client = new RestClient(url);
				try {
					client.execute(RequestMethod.POST);
					mItemsAll = mJSPhoto.getmArrayListPhotoEntity(client.getResponse());
				} catch (Exception e) {
				}

				return null;
			}
		};

		loader.forceLoad();
		return loader;
	}

	public void onLoadFinished(Loader<Void> arg0, Void arg1) {
		mProgressDialog.setCancelable(true);
		mProgressDialog.dismiss();
		MainTabActivity.isFromPhotos = false;
		mAllGridLinearLayout.setVisibility(View.VISIBLE);
		mFestivalGridLinearLayout.setVisibility(View.GONE);
		mBizGridLinearLayout.setVisibility(View.GONE);
		mLougneGridLinearLayout.setVisibility(View.GONE);
		mTheaterGridLinearLayout.setVisibility(View.GONE);
		mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
		mBizTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
		mLougneTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
		mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
		if (mItemsAll != null) {
			getAllItemsSub();
			mAllPhotosAdapter = new PhotosAdapter(mActivity, mItemsAllTemp);
			mAllTitleLinearLayout.setBackgroundResource(R.drawable.all_bg_active);
			mAllGridView.setAdapter(mAllPhotosAdapter);
			mAllTextView.setText("All (" + mItemsAllTemp.size() + ")");

			LinearLayout.LayoutParams mAllGridViewLinearLayout = (LinearLayout.LayoutParams) mAllGridView.getLayoutParams();
			mAllGridViewLinearLayout.height = measureRealHeight(mAllGridView, mItemsAllTemp.size());
			mAllGridView.setLayoutParams(mAllGridViewLinearLayout);
			mAllGridView.setVisibility(View.VISIBLE);

			mFestivalPhotosAdapter = new PhotosAdapter(mActivity, mItemsFestival);
			mFestivalGridView.setAdapter(mFestivalPhotosAdapter);
			mFestivalTextView.setText("Festival (" + mItemsFestival.size() + ")");

			LinearLayout.LayoutParams mFestivalGridViewLinearLayout = (LinearLayout.LayoutParams) mFestivalGridView.getLayoutParams();
			mFestivalGridViewLinearLayout.height = measureRealHeight(mFestivalGridView, mItemsFestival.size());
			mFestivalGridView.setLayoutParams(mFestivalGridViewLinearLayout);

			mBizPhotosAdapter = new PhotosAdapter(mActivity, mItemsBiz);
			mBizGridView.setAdapter(mBizPhotosAdapter);
			mBizTextView.setText("Biz (" + mItemsBiz.size() + ")");

			LinearLayout.LayoutParams mBizGridViewLinearLayout = (LinearLayout.LayoutParams) mBizGridView.getLayoutParams();
			mBizGridViewLinearLayout.height = measureRealHeight(mBizGridView, mItemsBiz.size());
			mBizGridView.setLayoutParams(mBizGridViewLinearLayout);

			mLougnePhotosAdapter = new PhotosAdapter(mActivity, mItemsLougne);
			mLoungeGridView.setAdapter(mLougnePhotosAdapter);
			mLougneTextView.setText("Lounge (" + mItemsLougne.size() + ")");

			LinearLayout.LayoutParams mLougneGridViewLinearLayout = (LinearLayout.LayoutParams) mLoungeGridView.getLayoutParams();
			mLougneGridViewLinearLayout.height = measureRealHeight(mLoungeGridView, mItemsLougne.size());
			mLoungeGridView.setLayoutParams(mLougneGridViewLinearLayout);

			mTheaterPhotosAdapter = new PhotosAdapter(mActivity, mItemsTheater);
			mTheaterGridView.setAdapter(mTheaterPhotosAdapter);
			mTheaterTextView.setText("Theater (" + mItemsTheater.size() + ")");

			LinearLayout.LayoutParams mTheaterGridViewLinearLayout = (LinearLayout.LayoutParams) mTheaterGridView.getLayoutParams();
			mTheaterGridViewLinearLayout.height = measureRealHeight(mTheaterGridView, mItemsTheater.size());
			mTheaterGridView.setLayoutParams(mTheaterGridViewLinearLayout);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm");

			mDateUpdateTextView.setText("Updated: " + sdf.format(cal.getTime()));
			FontUtils.setCustomFont(mDateUpdateTextView, false, false, getActivity().getAssets());
		}
	}

	public void onLoaderReset(Loader<Void> arg0) {

	}

	private void getAllItemsSub() {
		for (int i = 0; i < mItemsAll.size(); i++) {
			// if (mItemsAllTemp.size() < 25)
			// {
			// mItemsAllTemp.add(mItemsAll.get(i));
			// }
			if (mItemsAll.get(i).getType().equals("all") && mItemsAllTemp.size() < 100) {
				mItemsAllTemp.add(mItemsAll.get(i));
			} else if (mItemsAll.get(i).getType().equals("ssff") && mItemsFestival.size() < 100) {
				mItemsFestival.add(mItemsAll.get(i));
			} else if (mItemsAll.get(i).getType().equals("biz") && mItemsBiz.size() < 100) {
				mItemsBiz.add(mItemsAll.get(i));
			} else if (mItemsAll.get(i).getType().equals("lounge") && mItemsLougne.size() < 100) {
				mItemsLougne.add(mItemsAll.get(i));
			} else if (mItemsAll.get(i).getType().equals("theater") && mItemsTheater.size() < 100) {
				mItemsTheater.add(mItemsAll.get(i));
			}
		}
		if (mItemsAllTemp != null && mItemsAllTemp.size() > 0) {
			mArrayImageUrlAll = new String[mItemsAllTemp.size()];
			mArrayImageCategoryAll = new String[mItemsAllTemp.size()];
			mArrayImageCaptionAll = new String[mItemsAllTemp.size()];
			for (int i = 0; i < mItemsAllTemp.size(); i++) {
				mArrayImageUrlAll[i] = mItemsAllTemp.get(i).getImgBig();
				mArrayImageCategoryAll[i] = mItemsAllTemp.get(i).getCategory();
				mArrayImageCaptionAll[i] = mItemsAllTemp.get(i).getCaption();
			}
		}
		if (mItemsFestival != null && mItemsFestival.size() > 0) {
			mArrayImageUrlFestival = new String[mItemsFestival.size()];
			mArrayImageCategoryFestival = new String[mItemsFestival.size()];
			mArrayImageCaptionFestival = new String[mItemsFestival.size()];
			for (int i = 0; i < mItemsFestival.size(); i++) {
				mArrayImageUrlFestival[i] = mItemsFestival.get(i).getImgBig();
				mArrayImageCategoryFestival[i] = mItemsFestival.get(i).getCategory();
				mArrayImageCaptionFestival[i] = mItemsFestival.get(i).getCaption();
			}
		}

		if (mItemsBiz != null && mItemsBiz.size() > 0) {
			mArrayImageUrlBiz = new String[mItemsBiz.size()];
			mArrayImageCategoryBiz = new String[mItemsBiz.size()];
			mArrayImageCaptionBiz = new String[mItemsBiz.size()];
			for (int i = 0; i < mItemsBiz.size(); i++) {
				mArrayImageUrlBiz[i] = mItemsBiz.get(i).getImgBig();
				mArrayImageCategoryBiz[i] = mItemsBiz.get(i).getCategory();
				mArrayImageCaptionBiz[i] = mItemsBiz.get(i).getCaption();
			}
		}
		if (mItemsLougne != null && mItemsLougne.size() > 0) {
			mArrayImageUrlLougne = new String[mItemsLougne.size()];
			mArrayImageCategoryLougne = new String[mItemsLougne.size()];
			mArrayImageCaptionLougne = new String[mItemsLougne.size()];
			for (int i = 0; i < mItemsLougne.size(); i++) {
				mArrayImageUrlLougne[i] = mItemsLougne.get(i).getImgBig();
				mArrayImageCategoryLougne[i] = mItemsLougne.get(i).getCategory();
				mArrayImageCaptionLougne[i] = mItemsLougne.get(i).getCaption();
			}
		}

		if (mItemsTheater != null && mItemsTheater.size() > 0) {
			mArrayImageUrlTheater = new String[mItemsTheater.size()];
			mArrayImageCategoryTheater = new String[mItemsTheater.size()];
			mArrayImageCaptionTheater = new String[mItemsTheater.size()];
			for (int i = 0; i < mItemsTheater.size(); i++) {
				mArrayImageUrlTheater[i] = mItemsTheater.get(i).getImgBig();
				mArrayImageCategoryTheater[i] = mItemsTheater.get(i).getCategory();
				mArrayImageCaptionTheater[i] = mItemsTheater.get(i).getCaption();
			}
		}

	}

	private int measureRealHeight(GridView pGridView, int picsCount) {

		final double screenDensity = getResources().getDisplayMetrics().density;
		// final int paddingLeft = (int) (X * screenDensity + 0.5f); // where X
		// is your desired padding
		// final int paddingRight = ...;

		final int verticalSpacing = (int) (10 * screenDensity + 0.5f); // the
																		// spacing
																		// between
																		// your
																		// rows
		final int columnWidth = (int) (60 * screenDensity + 0.5f);
		// final int columnsCount = (screenWidth - paddingLeft - paddingRight +
		// horizontalSpacing - myGridView.getVerticalScrollbarWidth()) /
		// (columnWidth + horizontalSpacing);
		final int columnsCount = 5;
		final int rowsCount = picsCount / columnsCount + (picsCount % columnsCount == 0 ? 0 : 1);
		return columnWidth * rowsCount + verticalSpacing * (rowsCount - 1) + verticalSpacing;
	}

	private void resfreshList() {
		mItemsAll = new ArrayList<PhotosEntity>();
		mItemsFestival = new ArrayList<PhotosEntity>();
		mItemsBiz = new ArrayList<PhotosEntity>();
		mItemsLougne = new ArrayList<PhotosEntity>();
		mItemsTheater = new ArrayList<PhotosEntity>();
		getLoaderManager().restartLoader(0, null, this);
	}

	public void onClick(View v) {
		if (v.equals(mAllTitleLinearLayout)) {
			if (mAllGridLinearLayout.getVisibility() == View.GONE) {
				mAllGridLinearLayout.setVisibility(View.VISIBLE);
				mAllTitleLinearLayout.setBackgroundResource(R.drawable.all_bg_active);
				mFestivalGridLinearLayout.setVisibility(View.GONE);
				mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mBizGridLinearLayout.setVisibility(View.GONE);
				mBizTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mLougneGridLinearLayout.setVisibility(View.GONE);
				mLougneTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mTheaterGridLinearLayout.setVisibility(View.GONE);
				mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			} else {
				mAllGridLinearLayout.setVisibility(View.GONE);
				mAllTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			}
		} else if (v.equals(mFestivalTitleLinearLayout)) {
			if (mFestivalGridLinearLayout.getVisibility() == View.GONE) {
				mFestivalGridLinearLayout.setVisibility(View.VISIBLE);
				mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.festival_bg_active);
				mAllGridLinearLayout.setVisibility(View.GONE);
				mAllTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mBizGridLinearLayout.setVisibility(View.GONE);
				mBizTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mLougneGridLinearLayout.setVisibility(View.GONE);
				mLougneTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mTheaterGridLinearLayout.setVisibility(View.GONE);
				mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			} else {
				mFestivalGridLinearLayout.setVisibility(View.GONE);
				mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			}
		}

		else if (v.equals(mBizTitleLinearLayout)) {
			if (mBizGridLinearLayout.getVisibility() == View.GONE) {
				mBizGridLinearLayout.setVisibility(View.VISIBLE);
				mBizTitleLinearLayout.setBackgroundResource(R.drawable.biz_bg_active);
				mAllGridLinearLayout.setVisibility(View.GONE);
				mAllTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mFestivalGridLinearLayout.setVisibility(View.GONE);
				mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mLougneGridLinearLayout.setVisibility(View.GONE);
				mLougneTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mTheaterGridLinearLayout.setVisibility(View.GONE);
				mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			} else {
				mBizGridLinearLayout.setVisibility(View.GONE);
				mBizTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			}
		}

		else if (v.equals(mLougneTitleLinearLayout)) {
			if (mLougneGridLinearLayout.getVisibility() == View.GONE) {
				mLougneGridLinearLayout.setVisibility(View.VISIBLE);
				mLougneTitleLinearLayout.setBackgroundResource(R.drawable.lounge_bg_active);
				mAllGridLinearLayout.setVisibility(View.GONE);
				mAllTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mFestivalGridLinearLayout.setVisibility(View.GONE);
				mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mBizGridLinearLayout.setVisibility(View.GONE);
				mBizTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mTheaterGridLinearLayout.setVisibility(View.GONE);
				mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			} else {
				mLougneGridLinearLayout.setVisibility(View.GONE);
				mLougneTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			}
		}

		else if (v.equals(mTheaterTitleLinearLayout)) {
			if (mTheaterGridLinearLayout.getVisibility() == View.GONE) {
				mTheaterGridLinearLayout.setVisibility(View.VISIBLE);
				mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.theater_bg_active);
				mAllGridLinearLayout.setVisibility(View.GONE);
				mFestivalGridLinearLayout.setVisibility(View.GONE);
				mBizGridLinearLayout.setVisibility(View.GONE);
				mLougneGridLinearLayout.setVisibility(View.GONE);
				mAllTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mFestivalTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mBizTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
				mLougneTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			} else {
				mTheaterGridLinearLayout.setVisibility(View.GONE);
				mTheaterTitleLinearLayout.setBackgroundResource(R.drawable.tab_bg_inactive);
			}
		} else if (v.equals(mUpdateImageView)) {
			resfreshList();
		}
	}

}
