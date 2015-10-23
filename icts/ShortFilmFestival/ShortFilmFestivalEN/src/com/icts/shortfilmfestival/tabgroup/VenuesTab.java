package com.icts.shortfilmfestival.tabgroup;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.icts.shortfilmfestival.adapter.VenuesAdapter;
import com.icts.shortfilmfestival.inf.VenuesData;
import com.icts.shortfilmfestival_en.FestivalTabActivity;
import com.vnp.shortfilmfestival.R;

public class VenuesTab extends FragmentActivity {
	private ImageView mSlideImageView;
	private RelativeLayout mBackgroundSlide;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.festival_venues);
		mSlideImageView = (ImageView) findViewById(R.id.slide_venues);
		mSlideImageView.setBackgroundResource(R.drawable.slide_animation);
		
		mBackgroundSlide = (RelativeLayout) findViewById(R.id.slide_bg);
		ResizeView mResizeView = new ResizeView();
		
		final RelativeLayout.LayoutParams viewLayoutParamsBg = (RelativeLayout.LayoutParams)
			mBackgroundSlide.getLayoutParams();
		viewLayoutParamsBg.width = (int)(480 * mResizeView.
		ratioResizeWidth);
		viewLayoutParamsBg.height = (int)(158 * mResizeView.ratioResizeHeight);
		mBackgroundSlide.setLayoutParams(viewLayoutParamsBg);
		
		final RelativeLayout.LayoutParams viewLayoutParams = (RelativeLayout.LayoutParams)mSlideImageView.getLayoutParams();
		viewLayoutParams.width = (int)(450 * mResizeView.
        		ratioResizeWidth);
		viewLayoutParams.height = (int)(129 * mResizeView.ratioResizeHeight);
		
		mSlideImageView.setLayoutParams(viewLayoutParams);
		
	}
	
	@Override
	protected void onResume() {
		 // Get the background, which has been compiled to an AnimationDrawable object.
		final AnimationDrawable frameAnimation = (AnimationDrawable) mSlideImageView.getBackground();
		
		 // Start the animation (looped playback by default).
		 if (frameAnimation != null)
		 {
			if (frameAnimation.isRunning())
			{
				frameAnimation.stop();
			}
			mSlideImageView.post(new Runnable() {
				
				public void run() {
					 frameAnimation.start();
					
				}
			});
		 }
		super.onResume();
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	public void onBackPressed() {
		FestivalTabActivity.onKeyBack();
		super.onBackPressed();
	}
	
 @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	 	System.gc();
		super.onDestroy();
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
				
				VenuesTab.this.getWindowManager().getDefaultDisplay()
						.getMetrics(metrics);
				Log.d(LOG_RESIZEVIEW, "CHANGE..." + metrics.toString());
//				if (metrics.widthPixels < metrics.heightPixels) {
//					int temp = metrics.widthPixels;
//					metrics.widthPixels = metrics.heightPixels;
//					metrics.heightPixels = temp;
//				}

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
				Log.d("LOG_NEWS_ADAPTER", "ratioResizeWidth" + ratioResizeWidth
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
