package com.icts.shortfilmfestival.detailfragment;

import java.net.URLEncoder;

import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.DetailNews;
import com.icts.shortfilmfestival.fragment.VenuesFragment;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.inf.VenuesData;
import com.icts.shortfilmfestival.myjson.JSNews;
import com.icts.shortfilmfestival.social.FaceBookShortFilmFestival;
import com.icts.shortfilmfestival.social.TwitterShortFilmFestival;
import com.icts.shortfilmfestival.social.gplusActivity;
import com.icts.shortfilmfestival.tabgroup.NewsTab;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.icts.shortfilmfestival_en.MainTabActivity;
import com.vnp.shortfilmfestival.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VenuesDetail extends Fragment implements OnTouchListener {
	private View mRoot;

	private Activity mActivity;
	private boolean isJp = false;
	static FaceBookShortFilmFestival mFaceBookShortFilmFestival;
	static TwitterShortFilmFestival mTwitterShortFilmFestival;
	private static final String TAG = "LOG_VenuesDetail";
	private ImageView thumbnail;
	private TextView title;
	private TextView address;
	private ImageView btn_google;
	private ImageView btn_tweet;
	private ImageView btn_facebook;
	private ImageView btn_close;
	private ImageView imgMap;
	private RelativeLayout layoutMap;
	private String mTag = "";

	public VenuesDetail(String pTag) {
		Log.d(TAG, "____________COME HERE_____________0");
		mTag = pTag;
		if (ISettings.LANGUAGE.equals("en")) {
			isJp = false;
		} else {
			isJp = true;
		}
	}

	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mActivity = activity;
	}

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "____________COME HERE_____________1");

		mRoot = inflater.inflate(R.layout.list_venus, container, false);
		thumbnail = (ImageView) mRoot
		.findViewById(R.id.thumbnail);
		title = (TextView) mRoot.findViewById(R.id.title);
		address = (TextView) mRoot
				.findViewById(R.id.address);
		btn_google = (ImageView) mRoot
		.findViewById(R.id.google);
		
		btn_tweet = (ImageView) mRoot
				.findViewById(R.id.imgtweet);
		btn_facebook = (ImageView) mRoot
				.findViewById(R.id.imgfacebook);
		btn_close = (ImageView) mRoot
				.findViewById(R.id.btn_close);
		imgMap = (ImageView) mRoot
				.findViewById(R.id.imgMapBackGround);
		layoutMap = (RelativeLayout) mRoot
				.findViewById(R.id.layout_map);
		return mRoot;
	}

	
	public void onResume() {
		Log.d(TAG, "____________COME HERE_____________3");
		if (mTag.equals(""))
		{
			mTag = VenuesFragment.TAB_LAFORET;
		}
		if (mTag.equals(VenuesFragment.TAB_LAFORET))
		{
			thumbnail.setBackgroundResource(R.drawable.venues_icon);
			title.setText(VenuesData.DATA[0][0]);
			if (ISettings.LANGUAGE.equals("en"))
			{
				address.setText(VenuesData.DATA[0][1]);
			}
			else if (ISettings.LANGUAGE.equals("ja"))
			{
				address.setText(VenuesData.DATA[0][2]);
			}
			layoutMap.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(VenuesData.DATA[0][3]));
						mActivity.startActivity(browserIntent);
					}
			});
			btn_google.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					if (VenuesFragment.mArrayListVenuesLink != null && !VenuesFragment.mArrayListVenuesLink.get(0).equals(""))
					{
						shareViaGooglePlus(VenuesFragment.mArrayListVenuesLink.get(0));
					}
					
				}
			});
			
			btn_tweet.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					final TwitterShortFilmFestival mTwitterShortFilmFestival = new TwitterShortFilmFestival(
							mActivity, VenuesData.DATA[0][0].toString() + "  "
									+ VenuesData.DATA[0][3].toString());

					mTwitterShortFilmFestival.onTwitterClick();
				}
			});
			btn_facebook.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					if (ISettings.LANGUAGE.equals("en"))
					{
						final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
								mActivity.getApplicationContext(), mActivity, VenuesData.DATA[0][0].toString(),
								VenuesData.DATA[0][3].toString(), VenuesData.DATA[0][1],
								"");
						mFaceBookShortFilmFestival.restoreSestion();
						mFaceBookShortFilmFestival.onFacebookClick();
					}
					else
					{
						final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
								mActivity.getApplicationContext(), mActivity, VenuesData.DATA[0][0].toString(),
								VenuesData.DATA[0][3].toString(), VenuesData.DATA[0][2],
								"");
						mFaceBookShortFilmFestival.restoreSestion();
						mFaceBookShortFilmFestival.onFacebookClick();
					}
				}
			});
		}
		else if (mTag.equals(VenuesFragment.TAB_SPACEO))
		{
			thumbnail.setBackgroundResource(R.drawable.venues_icon1);
			title.setText(VenuesData.DATA[1][0]);
			if (ISettings.LANGUAGE.equals("en"))
			{
				address.setText(VenuesData.DATA[1][1]);
			}
			else if (ISettings.LANGUAGE.equals("ja"))
			{
				address.setText(VenuesData.DATA[1][2]);
			}
			layoutMap.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(VenuesData.DATA[1][3]));
						mActivity.startActivity(browserIntent);
					}
			});
			
			btn_google.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					if (VenuesFragment.mArrayListVenuesLink != null && !VenuesFragment.mArrayListVenuesLink.get(0).equals(""))
					{
						shareViaGooglePlus(VenuesFragment.mArrayListVenuesLink.get(1));
					}
					
				}
			});
			btn_tweet.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					final TwitterShortFilmFestival mTwitterShortFilmFestival = new TwitterShortFilmFestival(
							mActivity, VenuesData.DATA[1][0].toString() + "  "
									+ VenuesData.DATA[1][3].toString());

					mTwitterShortFilmFestival.onTwitterClick();
				}
			});
			btn_facebook.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					if (ISettings.LANGUAGE.equals("en"))
					{
						final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
								mActivity.getApplicationContext(), mActivity, VenuesData.DATA[1][0].toString(),
								VenuesData.DATA[1][3].toString(), VenuesData.DATA[1][1],
								"");
						mFaceBookShortFilmFestival.restoreSestion();
						mFaceBookShortFilmFestival.onFacebookClick();
					}
					else
					{
						final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
								mActivity.getApplicationContext(), mActivity, VenuesData.DATA[1][0].toString(),
								VenuesData.DATA[1][3].toString(), VenuesData.DATA[1][2],
								"");
						mFaceBookShortFilmFestival.restoreSestion();
						mFaceBookShortFilmFestival.onFacebookClick();
					}
				}
			});
		}
		else if (mTag.equals(VenuesFragment.TAB_TOHO))
		{
			thumbnail.setBackgroundResource(R.drawable.venues_icon2);
			title.setText(VenuesData.DATA[2][0]);
			if (ISettings.LANGUAGE.equals("en"))
			{
				address.setText(VenuesData.DATA[2][1]);
			}
			else if (ISettings.LANGUAGE.equals("ja"))
			{
				address.setText(VenuesData.DATA[2][2]);
			}
			layoutMap.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(VenuesData.DATA[2][3]));
						mActivity.startActivity(browserIntent);
					}
			});
			
			btn_google.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					if (VenuesFragment.mArrayListVenuesLink != null && !VenuesFragment.mArrayListVenuesLink.get(0).equals(""))
					{
						shareViaGooglePlus(VenuesFragment.mArrayListVenuesLink.get(2));
					}
					
				}
			});
			
			btn_tweet.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					final TwitterShortFilmFestival mTwitterShortFilmFestival = new TwitterShortFilmFestival(
							mActivity, VenuesData.DATA[2][0].toString() + "  "
									+ VenuesData.DATA[2][3].toString());

					mTwitterShortFilmFestival.onTwitterClick();
				}
			});
			
				btn_facebook.setOnClickListener(new OnClickListener() {
	
					
					public void onClick(View paramView) {
						if (ISettings.LANGUAGE.equals("en"))
						{
							final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
									mActivity.getApplicationContext(), mActivity, VenuesData.DATA[2][0].toString(),
									VenuesData.DATA[2][3].toString(), VenuesData.DATA[2][1],
									"");
							mFaceBookShortFilmFestival.restoreSestion();
							mFaceBookShortFilmFestival.onFacebookClick();
						}
						else
						{
							final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
									mActivity.getApplicationContext(), mActivity, VenuesData.DATA[2][0].toString(),
									VenuesData.DATA[2][3].toString(), VenuesData.DATA[2][2],
									"");
							mFaceBookShortFilmFestival.restoreSestion();
							mFaceBookShortFilmFestival.onFacebookClick();
						}
					}
				});
			
		}
		else if (mTag.equals(VenuesFragment.TAB_YOKOHAMA))
		{
			thumbnail.setBackgroundResource(R.drawable.venues_icon3);
			title.setText(VenuesData.DATA[3][0]);
			if (ISettings.LANGUAGE.equals("en"))
			{
				address.setText(VenuesData.DATA[3][1]);
			}
			else if (ISettings.LANGUAGE.equals("ja"))
			{
				address.setText(VenuesData.DATA[3][2]);
			}
			layoutMap.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse(VenuesData.DATA[3][3]));
						mActivity.startActivity(browserIntent);
					}
			});
			btn_google.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					if (VenuesFragment.mArrayListVenuesLink != null && !VenuesFragment.mArrayListVenuesLink.get(0).equals(""))
					{
						shareViaGooglePlus(VenuesFragment.mArrayListVenuesLink.get(3));
					}
					
				}
			});
			btn_tweet.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					final TwitterShortFilmFestival mTwitterShortFilmFestival = new TwitterShortFilmFestival(
							mActivity, VenuesData.DATA[3][0].toString() + "  "
									+ VenuesData.DATA[3][3].toString());

					mTwitterShortFilmFestival.onTwitterClick();
				}
			});
			btn_facebook.setOnClickListener(new OnClickListener() {

				
				public void onClick(View paramView) {
					if (ISettings.LANGUAGE.equals("en"))
					{
						final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
								mActivity.getApplicationContext(), mActivity, VenuesData.DATA[3][0].toString(),
								VenuesData.DATA[3][3].toString(), VenuesData.DATA[3][1],
								"");
						mFaceBookShortFilmFestival.restoreSestion();
						mFaceBookShortFilmFestival.onFacebookClick();
					}
					else
					{
						final FaceBookShortFilmFestival mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
								mActivity.getApplicationContext(), mActivity, VenuesData.DATA[3][0].toString(),
								VenuesData.DATA[3][3].toString(), VenuesData.DATA[3][2],
								"");
						mFaceBookShortFilmFestival.restoreSestion();
						mFaceBookShortFilmFestival.onFacebookClick();
					}
				}
			});
		}
		
		FontUtils.setCustomFont(title, true, false, mActivity
				.getAssets());
		FontUtils.setCustomFont(address, false, false, mActivity
				.getAssets());
		super.onResume();

	}

	
	public void onPause() {
		Log.d(TAG, "____________COME HERE_____________4");
		super.onPause();
	}

	
	public void onDestroy() {

		super.onDestroy();
	}

	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setRetainInstance(true);
		Log.d(TAG, "____________COME HERE_____________2");
		
	}

	
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
	
			return true;
		}
		return false;
	}
	
	private void shareViaGooglePlus(String urlLink)
	{
		Intent mGooglePlus = new Intent();
		mGooglePlus.setClass(getActivity(),
				gplusActivity.class);
		mGooglePlus.putExtra("URL", urlLink);
		startActivity(mGooglePlus);
	}
}
