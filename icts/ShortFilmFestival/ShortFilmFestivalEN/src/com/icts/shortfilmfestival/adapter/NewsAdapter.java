package com.icts.shortfilmfestival.adapter;

import java.util.List;

import com.icts.shortfilmfestival.entity.NewsEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.vnp.shortfilmfestival.R;

public class NewsAdapter extends ArrayAdapter<NewsEntity> {
	private LayoutInflater mInflater;
	private Context mContext;
	private Activity mActivity;
	private int mPosition;
	private static final String TAG = "LOG_NEWS_ADAPTER";

	public int getmPosition() {
		return mPosition;
	}

	public void setmPosition(int mPosition) {
		this.mPosition = mPosition;
	}

	public NewsAdapter(Context context, List<NewsEntity> objects, Activity pActivity) {
		super(context, R.layout.list_news, objects);
		mInflater = LayoutInflater.from(context.getApplicationContext());
		mContext = context;
		mActivity = pActivity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("YoutubeListFragment", position + "-------------");
		this.mPosition = position;
		View view = convertView;
		Wrapper wrapper = null;
		view = null;
		if (view == null) {
			if (position == 0 && getItem(position).getId() == 0) {
				view = mInflater.inflate(R.layout.list_news_fragment_all_begin,
						null);
				final ImageView mImageView = (ImageView) view.findViewById(R.id.imgBanner);
				ResizeView mResizeView = new ResizeView();
				final RelativeLayout.LayoutParams viewLayoutParams = new RelativeLayout.LayoutParams((int)(480 * mResizeView.
		        		ratioResizeWidth),(int)(0 * mResizeView.ratioResizeHeight));
				mImageView.setLayoutParams(viewLayoutParams);
				return view;
			} else {
				view = mInflater.inflate(R.layout.list_news, null);
				wrapper = new Wrapper(view);
				view.setTag(wrapper);
			}
		} else {
			wrapper = (Wrapper) view.getTag();
		}
		// Check if Id = 0 then set Background For View
		
			wrapper.getDateTimeTextView().setText(
					getItem(position).getDatetime());
			if (getItem(position).getTitle() != null
					&& getItem(position).getTitle().equals("")) {
				wrapper.getTiletTextView().setText("");
			} else {
				wrapper.getTiletTextView().setText(
						Html.fromHtml(getItem(position).getTitle().trim()));
			}
			if (getItem(position).getDesc() != null
					&& (getItem(position).getDesc().equals("") || getItem(
							position).getDesc().equals("null"))) {
				wrapper.getContentTextView().setText("");
			} else {
				wrapper.getContentTextView().setText(
						Html.fromHtml(getItem(position).getDesc().trim()));
			}
			if (!getItem(position).getThumbnail().equals("")) {
				wrapper.getThumbnailSmartImageView().setImageUrl(
						getItem(position).getThumbnail());
			} else {
				wrapper.getLinearLayoutImageView().setVisibility(View.GONE);
			}

			boolean isJp = false;
			if (Resource.localization.equals(ISettings.LANG_JP_FONT)) {
				isJp = true;
			}
			FontUtils.setCustomFont(wrapper.getTiletTextView(), true, isJp,
					mContext.getAssets());
			FontUtils.setCustomFont(wrapper.getDateTimeTextView(), false,
					false, mContext.getAssets());
			FontUtils.setCustomFont(wrapper.getContentTextView(), false, isJp,
					mContext.getAssets());
		
		// if (getItem(position).isHasPhoto() == 0)
		// {
		// wrapper.getPhotoImageView().setVisibility(View.INVISIBLE);
		// }
		//
		// if (getItem(position).isHasMovie() == 0)
		// {
		// wrapper.getMovieImageView().setVisibility(View.INVISIBLE);
		// }
		return view;
	}

	// use an wrapper (or view holder) object to limit calling the
	// findViewById() method, which parses the entire structure of your
	// XML in search for the ID of your view
	private class Wrapper {
		private final View mRoot;
		private TextView mDateTimeText;
		private TextView mTitleText;
		private TextView mContentText;
		private LinearLayout mLayoutImage;
		private SmartImageView mThumbnailSmartImageView;

		public Wrapper(View root) {
			mRoot = root;
		}

		public TextView getDateTimeTextView() {
			if (mDateTimeText == null) {
				mDateTimeText = (TextView) mRoot
						.findViewById(R.id.datetimetext_id);
			}

			return mDateTimeText;
		}

		public TextView getTiletTextView() {
			if (mTitleText == null) {
				mTitleText = (TextView) mRoot.findViewById(R.id.titletext_id);
			}

			return mTitleText;
		}

		public TextView getContentTextView() {
			if (mContentText == null) {
				mContentText = (TextView) mRoot
						.findViewById(R.id.contenttext_id);
			}

			return mContentText;
		}

		public SmartImageView getThumbnailSmartImageView() {
			if (mThumbnailSmartImageView == null) {
				mThumbnailSmartImageView = (SmartImageView) mRoot
						.findViewById(R.id.thumbnail_id);
			}
			return mThumbnailSmartImageView;
		}

		public LinearLayout getLinearLayoutImageView() {
			if (mLayoutImage == null) {
				mLayoutImage = (LinearLayout) mRoot
						.findViewById(R.id.layout_image_id);
			}
			return mLayoutImage;
		}

		// public ImageView getPhotoImageView() {
		// if (mPhotoImageView == null) {
		// mPhotoImageView = (ImageView)
		// mRoot.findViewById(R.id.image_photo_icon_id);
		// }
		// return mPhotoImageView;
		// }
		//
		// public ImageView getMovieImageView() {
		// if (mMovieImageView == null) {
		// mMovieImageView = (ImageView)
		// mRoot.findViewById(R.id.movie_movie_icon_id);
		// }
		// return mMovieImageView;
		// }
	}
	class ResizeView {
		private static final String LOG_RESIZEVIEW = "LOG_RESIZEVIEW_HAPPY";
		public DisplayMetrics metrics;
		public float ratioResizeWidth;
		public float ratioResizeHeight;
		public int hemBlackWidth = 0, hemBlackHeight = 0;
		private int maxWidth = 480, maxHeight = 800;
		public int statusHem = 0;

		ResizeView() {
			metrics = new DisplayMetrics();
			
			mActivity.getWindowManager().getDefaultDisplay()
					.getMetrics(metrics);
			Log.d(LOG_RESIZEVIEW, "CHANGE..." + metrics.toString());
//			if (metrics.widthPixels < metrics.heightPixels) {
//				int temp = metrics.widthPixels;
//				metrics.widthPixels = metrics.heightPixels;
//				metrics.heightPixels = temp;
//			}

			// Height bar for 3.0
			int heightBar = 0;
			metrics.heightPixels = metrics.heightPixels
					- Math.round(heightBar / metrics.density);

			ratioResizeWidth = (float) (metrics.widthPixels) / maxWidth;
			ratioResizeHeight = (float) (metrics.heightPixels) / maxHeight;

			if (ratioResizeHeight < ratioResizeWidth) {
				statusHem = 1;

				hemBlackWidth = Math.round((metrics.widthPixels - maxWidth
						* ratioResizeHeight) / 2);

				ratioResizeWidth = (float) ((metrics.widthPixels) - 2 * hemBlackWidth)
						/ maxWidth;
			} else {
				if (ratioResizeHeight > ratioResizeWidth) {
					statusHem = 2;

					hemBlackHeight = Math
							.round((metrics.heightPixels - maxHeight
									* ratioResizeWidth) / 2);

					ratioResizeHeight = (float) ((metrics.heightPixels) - 2 * hemBlackHeight)
							/ maxHeight;
				}
			}
			Log.d(TAG, "ratioResizeWidth" + ratioResizeWidth
					+ "ratioResizeHeight" + ratioResizeHeight + "hemBlackWidth"
					+ hemBlackWidth + "hemBlackHeight" + hemBlackHeight + "metric with" + metrics.widthPixels + "metric height" + metrics.heightPixels);
		}

		protected void resizeViewFix(View view, int left, int top, int width,
				int height) {
			RelativeLayout.LayoutParams prView = new RelativeLayout.LayoutParams(
					Math.round(width * this.ratioResizeWidth),
					Math.round(height * this.ratioResizeHeight));
			prView.setMargins(Math.round(left * this.ratioResizeWidth),
					Math.round(top * this.ratioResizeHeight), 0, 0);
			view.setLayoutParams(prView);
		}
	}
}
