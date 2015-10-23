package com.icts.shortfilmfestival.fragment;

import com.icts.shortfilmfestival.inf.ISettings;
import com.icts.shortfilmfestival.inf.Resource;
import com.icts.shortfilmfestival.social.FaceBookShortFilmFestival;
import com.icts.shortfilmfestival.social.TwitterShortFilmFestival;
import com.icts.shortfilmfestival.social.gplusActivity;
import com.icts.shortfilmfestival.utils.FontUtils;
import com.icts.shortfilmfestivalJa.R;
import com.loopj.android.image.SmartImageViewPhoto;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhotoDetail extends Activity implements OnTouchListener {
	private static SmartImageViewPhoto mSmartImageView;
	private ImageView mImageViewGoogle;
	private ImageView mImageViewTwitter;
	private ImageView mImageViewFacebook;
	private ImageView mImageViewTop;
	private ImageView mImageViewTemp;
	private static TextView mCategoryTextView;
	private RelativeLayout mLinearLayout;
	private RelativeLayout mBottomBarLayout;
	private static TextView mCaptionTextView;
	private FaceBookShortFilmFestival mFaceBookShortFilmFestival;
	private TwitterShortFilmFestival mTwitterShortFilmFestival;
	private String urlLink;
	private static String[] caption;
	private String picture;
	private static String[] categoty;
	private static String[] list;
	private static int index;
	private float density;
	private boolean isJp;
	private android.widget.RelativeLayout.LayoutParams mTopBarLayoutParam;
	private final String TAG = "LOG_PHOTO_DETAIL";
	private static float pX;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_photo);
		if (!Resource.localization.equals(ISettings.LANG_JP_FONT)) {
			isJp = false;
		} else {
			isJp = true;
		}
		mSmartImageView = (SmartImageViewPhoto) findViewById(R.id.photo_image_id);
		mSmartImageView.sharedConstructing(mSmartImageView.getContext());
		mImageViewTemp = (ImageView) findViewById(R.id.img_temp);
		mCategoryTextView = (TextView) findViewById(R.id.categoty_id);
		urlLink = getIntent().getStringExtra("URL_IAMGE");
		caption = getIntent().getStringArrayExtra("CAPTION");
		picture = getIntent().getStringExtra("PICTURE");
		categoty = getIntent().getStringArrayExtra("CATEGORY");
		list = getIntent().getStringArrayExtra("LIST");
		index = getIntent().getIntExtra("INDEX", -1);
		mSmartImageView.setImageUrl(urlLink, 0, R.drawable.loading);
		mCategoryTextView.setText(categoty[index]);
		FontUtils.setCustomFont(mCategoryTextView, true, isJp, getAssets());
		mLinearLayout = (RelativeLayout) findViewById(R.id.top_bar_id);
		mImageViewGoogle = (ImageView) findViewById(R.id.google_id);
		mImageViewTwitter = (ImageView) findViewById(R.id.twitter_id);
		mImageViewFacebook = (ImageView) findViewById(R.id.facebook_id);
		mImageViewTop = (ImageView) findViewById(R.id.top_id);

		mBottomBarLayout = (RelativeLayout) findViewById(R.id.bottom_bar_id);
		mCaptionTextView = (TextView) findViewById(R.id.caption_id);
		mCaptionTextView.setText(caption[index]);

		FontUtils.setCustomFont(mCaptionTextView, false, isJp, getAssets());
		int pixel = 68;
		density = getBaseContext().getResources().getDisplayMetrics().density;
		int dp = (int) (pixel * density);
		mLinearLayout.getLayoutParams().height = dp;

		int withImage = 800;
		int heightImage = 480;

		int dpWithImage = (int) (withImage * density);
		int dpHeightImage = (int) (heightImage * density);
		mSmartImageView.getLayoutParams().height = dpHeightImage;
		mSmartImageView.getLayoutParams().width = dpWithImage;
		mLinearLayout.getLayoutParams().height = dp;
		mImageViewGoogle.setOnTouchListener(this);
		mImageViewTwitter.setOnTouchListener(this);
		mImageViewFacebook.setOnTouchListener(this);
		mImageViewTop.setOnTouchListener(this);
		mLinearLayout.setOnTouchListener(this);
		mImageViewTemp.setOnTouchListener(this);

		mTopBarLayoutParam = (RelativeLayout.LayoutParams) mLinearLayout
				.getLayoutParams();
		Log.d("LOG_MAIN", "COME__HERE");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			mCategoryTextView.setVisibility(View.INVISIBLE);
			mBottomBarLayout.setBackgroundResource(R.drawable.bar_vertical);
		} else {
			mCategoryTextView.setVisibility(View.VISIBLE);
			mBottomBarLayout.setBackgroundResource(R.drawable.bar_hozinal);
		}
		mTopBarLayoutParam.setMargins(0, -200, 0, 0);
		mLinearLayout.setLayoutParams(mTopBarLayoutParam);
		mBottomBarLayout.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		Log.d(TAG, "--------onConfigurationChanged--------");
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			mCategoryTextView.setVisibility(View.INVISIBLE);
			mBottomBarLayout.setBackgroundResource(R.drawable.bar_vertical);
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mCategoryTextView.setVisibility(View.VISIBLE);
			mBottomBarLayout.setBackgroundResource(R.drawable.bar_hozinal);
		}

		super.onConfigurationChanged(newConfig);

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getPointerCount() > 1) {
			Log.d(TAG, "ACTION_MOVE 2 " + event.getPointerCount());
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:

			Log.d(TAG, "ACTION_DOWN");
			pX = event.getX();

			if (v.equals(mImageViewTop)) {
				// Top Touch
				back();
			} 
			else if (v.equals(mImageViewGoogle)) {
				// Share via twitter
				shareViaGooglePlus();
			}
			else if (v.equals(mImageViewTwitter)) {
				// Share via twitter
				shareViaTwitter();
			} else if (v.equals(mImageViewFacebook)) {
				// Share via facebook
				shareViaFacebook();
			} else if (v.equals(mLinearLayout)) {
				// Log.d(TAG, "TOUCH TO BAR");
				// mLinearLayout.startAnimation(mMenubarDownAnimaion);
				mTopBarLayoutParam.setMargins(0, 0, 0, 0);
				mLinearLayout.setLayoutParams(mTopBarLayoutParam);
			} else if (v.equals(mImageViewTemp)) {
				if (mSmartImageView.getScale() > 1) {
					// return false;
				} else {

					if (mTopBarLayoutParam.topMargin == 0) {
						mTopBarLayoutParam.setMargins(0, -200, 0, 0);
						mLinearLayout.setLayoutParams(mTopBarLayoutParam);
					} else {
						if (mSmartImageView.getScale() == 1) {
							mTopBarLayoutParam.setMargins(0, 0, 0, 0);
							mLinearLayout.setLayoutParams(mTopBarLayoutParam);
						} else {
							mTopBarLayoutParam.setMargins(0, -200, 0, 0);
							mLinearLayout.setLayoutParams(mTopBarLayoutParam);
						}
					}

					if (mBottomBarLayout.getVisibility() == View.VISIBLE) {
						mBottomBarLayout.setVisibility(View.INVISIBLE);
					} else {
						if (mSmartImageView.getScale() == 1) {
							mBottomBarLayout.setVisibility(View.VISIBLE);
						} else {
							mBottomBarLayout.setVisibility(View.INVISIBLE);
						}
					}
				}
			}
			break;
		// case MotionEvent.ACTION_MOVE:
		//			
		// if (event.getPointerCount() > 1)
		// {
		// Log.d(TAG, "ACTION_MOVE " + event.getPointerCount());
		// return false;
		// }
		// /**
		// mTopBarLayoutParam.setMargins(0, -200, 0, 0);
		// mLinearLayout.setLayoutParams(mTopBarLayoutParam);
		// */
		// if (mSmartImageView.getScale() == 1 && event.getPointerCount() == 1)
		// {
		// if (event.getX() - pX <= -150 && pX > 0)
		// {
		// if (index > 0)
		// {
		// index -=1;
		// mSmartImageView.setImageUrl(list[index]);
		// pX = -1;
		// }
		//					
		// }
		// else
		// if (event.getX() - pX >= 150 && pX > 0)
		// {
		// if (index < list.length)
		// {
		// index +=1;
		// mSmartImageView.setImageUrl(list[index]);
		// pX = -1;
		// }
		//						
		// }
		// }
		// break;
		}

		return false;
	}

	public static void transformImage(int type) {
		if (type == 0) {
			if (index > 0) {
				index -= 1;
				mSmartImageView.setImageBitmap(null);
				mSmartImageView.setImageUrl(list[index]);
				mCategoryTextView.setText(categoty[index]);
				mCaptionTextView.setText(caption[index]);
				pX = -1;
			}
		} else

		{
			if (index < list.length - 1) {
				index += 1;
				mSmartImageView.setImageBitmap(null);
				mSmartImageView.setImageUrl(list[index]);
				mCategoryTextView.setText(categoty[index]);
				mCaptionTextView.setText(caption[index]);
				pX = -1;
			}

		}
	}

	private void shareViaFacebook() {
		Log.d(TAG, picture);
		mFaceBookShortFilmFestival = new FaceBookShortFilmFestival(this, this,
				"Short Short Film Festival", urlLink, "", picture);
		mFaceBookShortFilmFestival.restoreSestion();
		mFaceBookShortFilmFestival.onFacebookClick();
	}

	private void shareViaTwitter() {
		mTwitterShortFilmFestival = new TwitterShortFilmFestival(this, urlLink);
		mTwitterShortFilmFestival.onTwitterClick();
	}
	
	private void shareViaGooglePlus()
	{
		Intent mGooglePlus = new Intent();
		mGooglePlus.setClass(this,
				gplusActivity.class);
		mGooglePlus.putExtra("URL", this.urlLink);
		startActivity(mGooglePlus);
	}

	private void back() {
		super.onBackPressed();
	}

}
