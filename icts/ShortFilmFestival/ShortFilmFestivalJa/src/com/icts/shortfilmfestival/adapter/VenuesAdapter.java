package com.icts.shortfilmfestival.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.social.FaceBookShortFilmFestival;
import com.icts.shortfilmfestival.social.TwitterShortFilmFestival;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.icts.shortfilmfestivalJa.R;

public class VenuesAdapter extends ArrayAdapter<String[]> {

	private LayoutInflater mInflater;
	private Context mContext;
	private Activity mActivity;
	private int[] map;
	private int[] logo;
	private int positionSelect;
	private ArrayList<ViewHolder> mArrayListViewHolder;

	public VenuesAdapter(Context context, List<String[]> objects, int[] pmap,
			int[] plogo, Activity pActivity) {
		super(context, R.layout.list_venus, objects);
		mInflater = LayoutInflater.from(context.getApplicationContext());
		mContext = context;
		map = pmap;
		logo = plogo;
		this.mActivity = pActivity;
		mArrayListViewHolder = new ArrayList<ViewHolder>();
		positionSelect = -1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder mViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_venus, parent, false);
			mViewHolder = new ViewHolder();

			mViewHolder.thumbnail = (ImageView) convertView
					.findViewById(R.id.thumbnail);
			mViewHolder.title = (TextView) convertView.findViewById(R.id.title);
			mViewHolder.address = (TextView) convertView
					.findViewById(R.id.address);
//			mViewHolder.btn_viewmap = (ImageView) convertView
//					.findViewById(R.id.imgviewmap);
			mViewHolder.btn_tweet = (ImageView) convertView
					.findViewById(R.id.imgtweet);
			mViewHolder.btn_facebook = (ImageView) convertView
					.findViewById(R.id.imgfacebook);
			mViewHolder.btn_close = (ImageView) convertView
					.findViewById(R.id.btn_close);
			mViewHolder.imgMap = (ImageView) convertView
					.findViewById(R.id.imgMapBackGround);
			mViewHolder.layoutMap = (RelativeLayout) convertView
					.findViewById(R.id.layout_map);

			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		mViewHolder.thumbnail.setBackgroundResource(logo[position]);
		mViewHolder.title.setText(getItem(position)[0]);
		FontUtils.setCustomFont(mViewHolder.title, true, false, mContext
				.getAssets());
		mViewHolder.address.setText(getItem(position)[1]);
		FontUtils.setCustomFont(mViewHolder.address, false, false, mContext
				.getAssets());

		// Set Click
		mViewHolder.btn_viewmap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				if (mViewHolder.layoutMap.getVisibility() == View.GONE) {
					if (mArrayListViewHolder != null && mArrayListViewHolder.size() > 0)
					{
						for (int i = 0; i < mArrayListViewHolder.size(); i++)
						{
							mArrayListViewHolder.get(i).layoutMap.setVisibility(View.GONE);
							mArrayListViewHolder.get(i).btn_viewmap
							.setBackgroundResource(R.drawable.view_map_buttn);
						}
					}
					
					mViewHolder.layoutMap.setVisibility(View.VISIBLE);
					mViewHolder.btn_viewmap
							.setBackgroundResource(R.drawable.view_larger_buttn);
				} else {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(getItem(position)[3]));
					mContext.startActivity(browserIntent);
				}
				positionSelect = position;

			}
		});

		mViewHolder.btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				mViewHolder.layoutMap.setVisibility(View.GONE);
				mViewHolder.btn_viewmap
						.setBackgroundResource(R.drawable.view_map_buttn);
			}
		});

		mViewHolder.btn_tweet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				final TwitterShortFilmFestival mTwitterShortFilmFestival = new TwitterShortFilmFestival(
						mActivity, getItem(position)[0].toString() + "  "
								+ getItem(position)[3].toString());

				mTwitterShortFilmFestival.onTwitterClick();
			}
		});
		mViewHolder.btn_facebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View paramView) {
				final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
						mContext, mActivity, getItem(position)[0].toString(),
						getItem(position)[3].toString(), getItem(position)[1],
						"");
				mFaceBookShortFilmFestival.restoreSestion();
				mFaceBookShortFilmFestival.onFacebookClick();
			}
		});
		
		mViewHolder.imgMap.setBackgroundResource(map[position]);
		mArrayListViewHolder.add(mViewHolder);
		if (position == positionSelect)
		{
			mViewHolder.layoutMap.setVisibility(View.VISIBLE);
			mViewHolder.btn_viewmap
					.setBackgroundResource(R.drawable.view_larger_buttn);
		}
		return convertView;
	}

	static class ViewHolder {
		private ImageView thumbnail;
		private TextView title;
		private TextView address;
		private ImageView btn_viewmap;
		private ImageView btn_tweet;
		private ImageView btn_facebook;
		private ImageView btn_close;
		private ImageView imgMap;
		private RelativeLayout layoutMap;
	}

}
