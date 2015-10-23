package com.icts.shortfilmfestival.detailfragment;

import com.icts.shortfilmfestivalJa.MainTabActivity;
import com.icts.shortfilmfestivalJa.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PhotoNewsDetail extends Activity implements OnTouchListener {
	private SmartImageView mSmartImageView;
	private ImageView mImageViewTop;
	private ImageView mImageViewPhotos;
	private ImageView mImageViewTemp;
	private RelativeLayout mLinearLayout;
	private String urlLink;
	private float density;
	private android.widget.RelativeLayout.LayoutParams mTopBarLayoutParam;
	private final String TAG = "LOG_PHOTO_DETAIL";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_news_photo);
		mSmartImageView = (SmartImageView) findViewById(R.id.photo_image_id);
		mSmartImageView.sharedConstructing(mSmartImageView.getContext());
		mImageViewTemp = (ImageView) findViewById(R.id.img_temp);
		urlLink = getIntent().getStringExtra("URL_IAMGE");
		// caption = getIntent().getStringExtra("CAPTION");
		// picture = getIntent().getStringExtra("PICTURE");
		mSmartImageView.setImageUrl(urlLink, 0, R.drawable.loading);
		// mTextViewCaption.setText("Short Film Festival");
		mLinearLayout = (RelativeLayout) findViewById(R.id.top_bar_id);
		mImageViewPhotos = (ImageView) findViewById(R.id.photos_id);
		mImageViewTop = (ImageView) findViewById(R.id.top_id);
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
		mImageViewPhotos.setOnTouchListener(this);
		mImageViewTop.setOnTouchListener(this);
		mLinearLayout.setOnTouchListener(this);
		mImageViewTemp.setOnTouchListener(this);

		mTopBarLayoutParam = (RelativeLayout.LayoutParams) mLinearLayout
				.getLayoutParams();

		Log.d("LOG_MAIN", "COME__HERE");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "ACTION_MOVE");
			if (v.equals(mImageViewTop)) {
				// Top Touch
				back();
			}

			else if (v.equals(mImageViewPhotos)) {
				Log.d(TAG, "COME______________HERE");
				Intent mIntent = new Intent(this, MainTabActivity.class);
				mIntent.putExtra("FROM_PHOTOS", true);
				startActivity(mIntent);

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
					// return false;
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "ACTION_MOVE");
			mTopBarLayoutParam.setMargins(0, -200, 0, 0);
			mLinearLayout.setLayoutParams(mTopBarLayoutParam);
			break;

		default:
			break;
		}

		return false;
	}

	private void back() {
		super.onBackPressed();
	}

}
