package com.icts.shortfilmfestival.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icts.shortfilmfestival.entity.ScheduleDetailEntity;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.social.FaceBookShortFilmFestival;
import com.icts.shortfilmfestival.social.TwitterShortFilmFestival;
import com.icts.shortfilmfestival.social.gplusActivity;
import com.icts.shortfilmfestival.social.gplusOpenActivity;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.vnp.shortfilmfestival.R;
import com.loopj.android.image.SmartImageView;

public class ScheduleDetailAdapter extends ArrayAdapter<ScheduleDetailEntity> {

	private LayoutInflater mInflater;
	private Context mContext;
	private Activity mActivity;

	public ScheduleDetailAdapter(Context context,
			List<ScheduleDetailEntity> objects, Activity pActivity) {
		super(context, 0, objects);
		mInflater = LayoutInflater.from(context.getApplicationContext());
		mContext = context;
		mActivity = pActivity;
	}

	
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.d("NewsListFragment", position + "---"
				+ getItem(position).getLinkShareGoogle());
		final String googleShare = getItem(position).getLinkShareGoogle();
		convertView = null;
		final ViewHolder mViewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_schedule_detail,
					parent, false);
			mViewHolder = new ViewHolder();

			mViewHolder.name = (TextView) convertView.findViewById(R.id.name);
			mViewHolder.titleMain = (TextView) convertView
					.findViewById(R.id.title_main);
			mViewHolder.titleSub = (TextView) convertView
					.findViewById(R.id.title_sub);
			mViewHolder.fdata = (TextView) convertView.findViewById(R.id.fdata);
			mViewHolder.titleSchedule = (TextView) convertView
					.findViewById(R.id.schedule);
			mViewHolder.titleDescrition = (TextView) convertView
					.findViewById(R.id.description);
			mViewHolder.img = (SmartImageView) convertView
					.findViewById(R.id.thumbnail);
			mViewHolder.imgRibbonGoogle = (ImageView) convertView
					.findViewById(R.id.google_ribbon);

			mViewHolder.top = (RelativeLayout) convertView
					.findViewById(R.id.top);
			mViewHolder.content = (RelativeLayout) convertView
					.findViewById(R.id.layout_content);
			mViewHolder.btn_tweet = (ImageView) convertView
					.findViewById(R.id.imgtweet);
			mViewHolder.btn_facebook = (ImageView) convertView
					.findViewById(R.id.imgfacebook);
			mViewHolder.btn_google = (ImageView) convertView
					.findViewById(R.id.imggoogle);
			convertView.setTag(mViewHolder);

			mViewHolder.name.setText(getItem(position).getProgramName());
			mViewHolder.titleMain.setText(getItem(position).getTitleMain()
					+ " - ");
			mViewHolder.titleSub.setText(getItem(position).getTitleSub());
			mViewHolder.fdata.setText(getItem(position).getfData());
			mViewHolder.titleSchedule.setText(getItem(position)
					.getShedulteList());
			mViewHolder.titleDescrition.setText(getItem(position)
					.getDescription());
			mViewHolder.img.setScale(0.6f);
			mViewHolder.img.setImageUrl(getItem(position).getImage());

			if (getItem(position).getLinkShareGoogle() != null
					&& !getItem(position).getLinkShareGoogle().equals("null")
					&& !getItem(position).getLinkShareGoogle().equals("")) {
				mViewHolder.imgRibbonGoogle.setVisibility(View.VISIBLE);
			}
			boolean isJp = false;
			if (Resource.localization.equals(ISettings.LANG_JP_FONT)) {
				isJp = true;
			}
			FontUtils.setCustomFont(mViewHolder.name, false, isJp,
					mContext.getAssets());
			FontUtils.setCustomFont(mViewHolder.titleMain, false, isJp,
					mContext.getAssets());
			FontUtils.setCustomFont(mViewHolder.titleSub, false, false,
					mContext.getAssets());
			FontUtils.setCustomFont(mViewHolder.titleSchedule, false, false,
					mContext.getAssets());
			FontUtils.setCustomFont(mViewHolder.titleDescrition, false, false,
					mContext.getAssets());

			if (getItem(position).getImage().equals("")) {
				mViewHolder.img.setVisibility(View.INVISIBLE);
			} else {
				mViewHolder.img.setVisibility(View.VISIBLE);
			}

			mViewHolder.top.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					if (mViewHolder.content.getVisibility() == View.GONE) {
						mViewHolder.content.setVisibility(View.VISIBLE);
					} else {
						mViewHolder.content.setVisibility(View.GONE);
					}
				}
			});

			mViewHolder.btn_tweet.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					final TwitterShortFilmFestival mTwitterShortFilmFestival = new TwitterShortFilmFestival(
							mActivity, getItem(position).getTitleMain()
									+ getItem(position).getTitleSub() + "  "
									+ getItem(position).getLinkShare());

					mTwitterShortFilmFestival.onTwitterClick();
				}
			});
			mViewHolder.btn_facebook.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
							mContext, mActivity, getItem(position)
									.getTitleMain()
									+ getItem(position).getTitleSub(), getItem(
									position).getLinkShare(), getItem(position)
									.getDescription(), "");
					mFaceBookShortFilmFestival.restoreSestion();
					mFaceBookShortFilmFestival.onFacebookClick();
				}
			});
			mViewHolder.btn_google.setOnClickListener(new OnClickListener() {

				
				public void onClick(View arg0) {
					Intent mGooglePlus = new Intent();
					mGooglePlus.setClass(mContext, gplusActivity.class);
					mGooglePlus.putExtra("URL", getItem(position)
							.getLinkShare());
					mContext.startActivity(mGooglePlus);
				}
			});

			mViewHolder.img.setOnClickListener(new OnClickListener() {

				
				public void onClick(View arg0) {
					Log.d("NewsListFragment",
							"-------------Come Here------------" + position
									+ "---"
									+ getItem(position).getLinkShareGoogle());
					if (getItem(position).getLinkShareGoogle() != null
							&& !getItem(position).getLinkShareGoogle().equals(
									"null")
							&& !(getItem(position).getLinkShareGoogle()
									.equals(""))) {
						// final Intent intent = new
						// Intent(Intent.ACTION_VIEW).setData(Uri
						// .parse(googleShare));
						// mContext.startActivity(intent);

						Intent mGooglePlus = new Intent();
						mGooglePlus.setClass(mContext, gplusOpenActivity.class);
						mGooglePlus.putExtra("URL", getItem(position)
								.getLinkShareGoogle());
						mContext.startActivity(mGooglePlus);
					}
				}
			});

		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}

	private class ViewHolder {
		private RelativeLayout top;
		private RelativeLayout content;
		private TextView name;
		private TextView titleMain;
		private TextView titleSub;
		private TextView fdata;
		private TextView titleSchedule;
		private TextView titleDescrition;
		private SmartImageView img;
		private ImageView imgRibbonGoogle;
		private ImageView btn_tweet;
		private ImageView btn_facebook;
		private ImageView btn_google;

	}

}
