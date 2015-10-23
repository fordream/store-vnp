package com.icts.shortfilmfestival.adapter;

import java.util.List;

import com.icts.shortfilmfestival.entity.MovieEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.vnp.shortfilmfestival.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends ArrayAdapter<MovieEntity> {
	private LayoutInflater mInflater;
	private Context mContext;
	private String mType;
	private int position;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public MovieAdapter(Context context, List<MovieEntity> objects, String type) {
		super(context, R.layout.list_movie, objects);
		mInflater = LayoutInflater.from(context.getApplicationContext());
		this.mContext = context;
		this.mType = type;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("YoutubeListFragment", position + "-------------");
		View view = convertView;
		Wrapper wrapper;
		this.position = position;
		if (view == null) {
			view = mInflater.inflate(R.layout.list_movie, null);
			wrapper = new Wrapper(view);
			view.setTag(wrapper);
		} else {
			wrapper = (Wrapper) view.getTag();
		}
		boolean isJp = false;
		if (Resource.localization.equals(ISettings.LANG_JP_FONT)) {
			isJp = true;
		}
		wrapper.getThumbnailImageView().setImageUrl(
				(getItem(position).getThumbnail()), 0, R.drawable.loading);
		wrapper.getTiletTextView().setText(getItem(position).getTitle());
		FontUtils.setCustomFont(wrapper.getTiletTextView(), true, isJp,
				mContext.getAssets());
		wrapper.getRatingTextView().setText(getItem(position).getRating());
		FontUtils.setCustomFont(wrapper.getRatingTextView(), false, false,
				mContext.getAssets());
		wrapper.getCountRateTextView().setText(
				getItem(position).getCountrator());
		FontUtils.setCustomFont(wrapper.getCountRateTextView(), false, false,
				mContext.getAssets());
		wrapper.getDurationTextView().setText(getItem(position).getDuration());
		FontUtils.setCustomFont(wrapper.getDurationTextView(), false, false,
				mContext.getAssets());
		wrapper.getAuthorTextView().setText(getItem(position).getAuthor());
		FontUtils.setCustomFont(wrapper.getAuthorTextView(), false, isJp,
				mContext.getAssets());
		if (this.mType.equals("youtube")) {
			wrapper.getArrowImageView().setBackgroundResource(
					R.drawable.white_arrow_button);
		} else if (this.mType.equals("ustream")) {
			wrapper.getArrowImageView().setBackgroundResource(
					R.drawable.blue_arrow);
		}
		return view;
	}

	// use an wrapper (or view holder) object to limit calling the
	// findViewById() method, which parses the entire structure of your
	// XML in search for the ID of your view
	private class Wrapper {
		private final View mRoot;
		private SmartImageView mThumbnailImageView;
		private TextView mTitleTextView;
		private TextView mRatingTextView;
		private TextView mCountRateTextView;
		private TextView mDurationTextView;
		private TextView mAuthorTextView;
		private ImageView mArrowImageView;

		public Wrapper(View root) {
			mRoot = root;
		}

		public SmartImageView getThumbnailImageView() {
			if (mThumbnailImageView == null) {
				mThumbnailImageView = (SmartImageView) mRoot
						.findViewById(R.id.thumbnail);
			}
			return mThumbnailImageView;
		}

		public TextView getTiletTextView() {
			if (mTitleTextView == null) {
				mTitleTextView = (TextView) mRoot.findViewById(R.id.title);
			}
			return mTitleTextView;
		}

		public TextView getRatingTextView() {
			if (mRatingTextView == null) {
				mRatingTextView = (TextView) mRoot.findViewById(R.id.rating);
			}
			return mRatingTextView;
		}

		public TextView getCountRateTextView() {
			if (mCountRateTextView == null) {
				mCountRateTextView = (TextView) mRoot
						.findViewById(R.id.count_rate);
			}
			return mCountRateTextView;
		}

		public TextView getDurationTextView() {
			if (mDurationTextView == null) {
				mDurationTextView = (TextView) mRoot
						.findViewById(R.id.duration_view);
			}
			return mDurationTextView;
		}

		public TextView getAuthorTextView() {
			if (mAuthorTextView == null) {
				mAuthorTextView = (TextView) mRoot.findViewById(R.id.author);
			}
			return mAuthorTextView;
		}

		public ImageView getArrowImageView() {
			if (mArrowImageView == null) {
				mArrowImageView = (ImageView) mRoot.findViewById(R.id.arrow);
			}
			return mArrowImageView;
		}

	}

}
