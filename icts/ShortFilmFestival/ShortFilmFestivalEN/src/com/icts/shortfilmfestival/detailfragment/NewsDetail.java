package com.icts.shortfilmfestival.detailfragment;

import java.net.URLEncoder;

import com.icts.shortfilmfestival.api.SSSFApi;
import com.icts.shortfilmfestival.entity.DetailNews;
import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsDetail extends Fragment implements OnTouchListener {
	private View mRoot;
	private ImageView mBackImageView;
	private TextView mTitleTextView;
	private TextView mDateTextView;
	private WebView mContentTextView;
	
	private TextView mNumLikeFacebook;
	private TextView mNumLikeTwitter;
	private LinearLayout mImagesLinearLayout;
	private LinearLayout mBackLinearLayout;
	private Fragment mFromFragment;
	private int mNewsId;
	private String mParam;
	private boolean isNew;
	private String mLink;
	private Activity mActivity;
	private boolean isJp = false;
	static FaceBookShortFilmFestival mFaceBookShortFilmFestival;
	static TwitterShortFilmFestival mTwitterShortFilmFestival;
	private DetailNews mNewsDetailEntity;
	private static final String TAG = "LOG_NewsDetail";
	private ImageView mFacebookImageView, mTweeterImageView, mGoogleImageView;

	public NewsDetail() {

	}

	public NewsDetail(Fragment pFromFragment, String pParam, int pNewsId,
			String pLink) {
		Log.d(TAG, "____________COME HERE_____________0");
		NewsTab.isDeailShow = true;
		isNew = true;
		this.mLink = pLink;
		this.mNewsId = pNewsId;
		this.mParam = pParam;
		mFromFragment = pFromFragment;
		isJp = false;
		JSNews mJSNews = new JSNews();
		mNewsDetailEntity = new DetailNews();
		mNewsDetailEntity = mJSNews.getDetailNews(SSSFApi.getDetailNews(
				ISettings.NEWS_DETAIL_URL_PREFIX, this.mParam, this.mNewsId,
				ISettings.LANGUAGE));

		if (mNewsDetailEntity != null) {

			mNewsDetailEntity
					.setNumLikeFacebook(mJSNews.getNumberLikeFaceBook(SSSFApi
							.getNumLikeFaceBook(ISettings.GET_NUMBER_LIKE_FACEBOOK_URL
									+ URLEncoder.encode(this.mLink))));

			mNewsDetailEntity
					.setNumLikeTwitter(mJSNews.getNumberLikeTwitter(SSSFApi
							.getNumLikeFaceBook(ISettings.GET_NUMBER_LIKE_TWITER_URL
									+ URLEncoder.encode(this.mLink))));
		}
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

		mRoot = inflater.inflate(R.layout.newsdetail, container, false);
		mBackImageView = (ImageView) mRoot.findViewById(R.id.back_button_id);
		mTitleTextView = (TextView) mRoot
				.findViewById(R.id.newsdetail_title_id);
		mDateTextView = (TextView) mRoot.findViewById(R.id.newsdetail_date_id);
		mContentTextView = (WebView) mRoot.findViewById(R.id.newsdetail_id);
		mContentTextView.setBackgroundColor(0x00000000);
		mImagesLinearLayout = (LinearLayout) mRoot
				.findViewById(R.id.layout_image_id);
		mGoogleImageView = (ImageView) mRoot.findViewById(R.id.google_button_id);
		mGoogleImageView.setOnTouchListener(this);
		
		mFacebookImageView = (ImageView) mRoot
				.findViewById(R.id.facebook_button_id);
		
		mNumLikeFacebook = (TextView) mRoot.findViewById(R.id.numLikeFaceBook);
		mNumLikeFacebook.setVisibility(View.INVISIBLE);
		mFacebookImageView.setOnTouchListener(this);
		mTweeterImageView = (ImageView) mRoot
				.findViewById(R.id.tweet_button_id);
		mNumLikeTwitter = (TextView) mRoot.findViewById(R.id.numLikeTwitter);
		mNumLikeTwitter.setVisibility(View.INVISIBLE);
		mTweeterImageView.setOnTouchListener(this);
		
		if (ISettings.LANGUAGE.equals("en"))
		{
			mBackImageView.setBackgroundResource(R.drawable.back_buttn);
			mFacebookImageView.setBackgroundResource(R.drawable.face_buttn);
			mTweeterImageView.setBackgroundResource(R.drawable.tweet_buttn);
		}
		else
		{
			mBackImageView.setBackgroundResource(R.drawable.back_buttn_ja);
			mFacebookImageView.setBackgroundResource(R.drawable.face_buttn_ja);
			mTweeterImageView.setBackgroundResource(R.drawable.tweet_buttn_ja);
		}
		
		mBackImageView.setOnTouchListener(this);

		mBackLinearLayout = (LinearLayout) mRoot
				.findViewById(R.id.layout_back_button);
		mBackLinearLayout.setOnClickListener(new OnClickListener() {

			
			public void onClick(View arg0) {
				backPressed();

			}
		});
		return mRoot;
	}

	
	public void onResume() {
		Log.d(TAG, "____________COME HERE_____________3");
//		if (mNewsDetailEntity != null && mNewsDetailEntity.isError()) {
//			NewsTab.isDeailShow = false;
//			backPressed();
//			final AlertDialog.Builder builder = new AlertDialog.Builder(
//					mActivity);
//
//			builder.setMessage("Sorry, Data Error!")
//					.setCancelable(false)
//					.setPositiveButton("Ok",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int id) {
//									dialog.cancel();
//								}
//							});
//
//			final AlertDialog alert = builder.create();
//
//			alert.show();
//			mNewsDetailEntity.setError(false);
//		}
		
		super.onResume();

	}

	
	public void onPause() {
		Log.d(TAG, "____________COME HERE_____________4");
		isNew = false;
		super.onPause();
	}

	
	public void onDestroy() {

		super.onDestroy();
	}

	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// setRetainInstance(true);
		Log.d(TAG, "____________COME HERE_____________2");

		if (mNewsDetailEntity != null && isNew) {
			// mNewsDetailEntity.setContent("&amp;nbsp;Finally, today is the last day of the Market at Clermont-Ferrand. However the most of the participants already left for their country so on Friday, it starts very quiet.&amp;nbsp;&lt;br /&gt;&lt;br /&gt;We went to the video library which is located in the second floor of the market. Those who registered in CF market could use the video library where they can be accesible to about 6000 submitted shorts. There is some who never go to the screening but is in the library all days. If we use the video library once, we could access the online library 9 month after the last day of the market from anywhere.&lt;br /&gt;&lt;br /&gt;After demantling the booth, we try to cacth some screenings. This year&#039;s special program is focusing on.... &amp;quot;Insect&amp;quot;. They screen more than 20 shorts which are related variety of Insects.&amp;nbsp;&lt;br /&gt;&lt;br /&gt;Tomorrow is the last day of the festival, with the award ceremony. Who win the Grand Prix this year?&lt;br type=&quot;_moz&quot; /&gt;");
			if (mNewsDetailEntity.getTitle() != null) {
				mTitleTextView.setText(Html.fromHtml(mNewsDetailEntity
						.getTitle()));

				FontUtils.setCustomFont(mTitleTextView, true, isJp,
						getActivity().getAssets());
			}
			if (mNewsDetailEntity.getDate() != null) {
				mDateTextView.setText(mNewsDetailEntity.getDate());
				FontUtils.setCustomFont(mDateTextView, false, false,
						getActivity().getAssets());
			}
			if (mNewsDetailEntity.getContent() != null
					&& !mNewsDetailEntity.getContent().equals("")) {
				try{
					mContentTextView.loadDataWithBaseURL(null, mNewsDetailEntity
							.getContent(), "text/html", "utf-8", null);
					
				}
				catch (Exception e) {
				}
				FontUtils.setCustomFont(mContentTextView, false, isJp,
						getActivity().getAssets());
			}
			mNumLikeFacebook.setText(mNewsDetailEntity.getNumLikeFacebook()
					+ "");
			FontUtils.setCustomFont(mNumLikeFacebook, false, false,
					getActivity().getAssets());
			mNumLikeTwitter.setText(mNewsDetailEntity.getNumLikeTwitter() + "");
			FontUtils.setCustomFont(mNumLikeTwitter, false, false,
					getActivity().getAssets());
			//

			if (mNewsDetailEntity.getArrayImage() != null
					&& mNewsDetailEntity.getArrayImage().length > 0) {
				mImagesLinearLayout.setVisibility(View.VISIBLE);
				for (int i = 0; i < mNewsDetailEntity.getArrayImage().length; i++) {
					final int index = i;
					SmartImageView imgViewTemp = new SmartImageView(mActivity);

					imgViewTemp.setImageUrl(
							mNewsDetailEntity.getArrayImage()[i], 0,
							R.drawable.loading);
					mImagesLinearLayout.addView(imgViewTemp);

					imgViewTemp.setOnTouchListener(new OnTouchListener() {

						
						public boolean onTouch(View v, MotionEvent event) {
							if (event.getAction() == MotionEvent.ACTION_DOWN) {
								Intent mIntent = new Intent();
								mIntent.putExtra(
										"URL_IAMGE",
										mNewsDetailEntity.getArrayImage()[index]);
								mIntent.setClass(getActivity(),
										PhotoNewsDetail.class);
								startActivity(mIntent);
								return true;
							}
							return false;
						}
					});
				}
			} else {
				mImagesLinearLayout.setVisibility(View.GONE);
			}
		}

	}

	
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// if (mBackLinearLayout.equals(v))
			// {
			// backPressed();
			// }
			//
			//
			
			if (mFacebookImageView.getId() == v.getId()) {
				mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(
						getActivity().getApplicationContext(), mActivity,mNewsDetailEntity.getTitle()
								,this.mLink,
						"", "");
				mFaceBookShortFilmFestival.restoreSestion();
				mFaceBookShortFilmFestival.onFacebookClick();
			} else if (mTweeterImageView.getId() == v.getId()) {

				mTwitterShortFilmFestival = new TwitterShortFilmFestival(
						mActivity, mNewsDetailEntity.getTitle() + " "
								+ this.mLink);

				mTwitterShortFilmFestival.onTwitterClick();
			}
			else if (mGoogleImageView.getId() == v.getId())
			{
				Intent mGooglePlus = new Intent();
				mGooglePlus.setClass(getActivity(),
						gplusActivity.class);
				mGooglePlus.putExtra("URL", this.mLink);
				startActivity(mGooglePlus);
			}
			return true;
		}
		return false;
	}

	private void backPressed() {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
		if (mFromFragment != null) {
			NewsTab.isDeailShow = false;
			MainTabActivity.isFromNotification = false;
			ft.show(mFromFragment);
			// ft.replace(R.id.tabs_fragment, new NewsFragment());
			ft.commit();
		}
	}

}
