package com.icts.shortfilmfestival.zzz.t.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fedorvlasov.lazylist.ImageLoader;
import com.icts.shortfilmfestivalJa.R;

public class BannerView extends LinearLayout implements Runnable,
		OnClickListener {
	private ImageView imageView1, imageView2;

	public BannerView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.headerlist, this);
		imageView1 = (ImageView) findViewById(R.id.headerlist_img1);
		imageView2 = (ImageView) findViewById(R.id.headerlist_img2);
		imageView1.setAdjustViewBounds(true);
		imageView2.setAdjustViewBounds(true);

		this.setOnClickListener(this);
	}

	public String web = "";
	private String imgUrl;
	private String oldUrl;

	public void setUrl(final String imgUrl, String urlWeb) {
		this.web = urlWeb;
		oldUrl = this.imgUrl;
		this.imgUrl = imgUrl;
		post(this);
	}

	ImageView swap;

	public void run() {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView1
				.getLayoutParams();
		layoutParams.height = getWidth() * 164 / 640;
		imageView1.setLayoutParams(layoutParams);
		imageView2.setLayoutParams(layoutParams);

		ImageView imgHidden;
		if (swap == null || swap == imageView1) {
			swap = imageView2;
			imgHidden = imageView1;
		} else {
			swap = imageView1;
			imgHidden = imageView2;
		}

		// swap = imageView2;

		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setFillAfter(true);
		alphaAnimation.setDuration(900);

		AlphaAnimation alphaAnimationSwap = new AlphaAnimation(0, 1);
		alphaAnimationSwap.setFillAfter(true);
		alphaAnimationSwap.setDuration(900);

		if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {

			if (oldUrl == null) {
				oldUrl = imgUrl;
			}

			imgHidden.startAnimation(alphaAnimation);
			swap.startAnimation(alphaAnimationSwap);

			ImageLoader imageLoader = new ImageLoader(getContext(),
					R.drawable.transfer);
			imageLoader.DisplayImage(imgUrl, (Activity) getContext(), swap);

			//imageLoader
			//		.DisplayImage(oldUrl, (Activity) getContext(), imgHidden);

		}
	}

	public void onClick(View v) {
		if (web != null) {
			if (!web.equals("")) {
				if (!web.startsWith("http:")) {
					web = "http://" + web;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
				getContext().startActivity(intent);
			}
		}
	}
}