package com.cnc.maispreco.views;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.BitmapCommon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;

public class OfferView extends LinearLayout {
	private Drawable bitmap;
	private ImageView imgOffers;
	private ImageView imgOffferNext;
	private TextView tVOfferInformation;
	private TextView tVOfferPrice;
	private LinearLayout linearLayout;
	private TextView tvNewName;

	public OfferView(Context context) {
		super(context);
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_offers, this, true);
		imgOffers = (ImageView) findViewById(R.id.imgOffers);
		imgOffferNext = (ImageView) findViewById(R.id.imgOffferNext);
		imgOffferNext = (ImageView) findViewById(R.id.imgOffferNext);

		tVOfferInformation = (TextView) findViewById(R.id.tVOfferInformation);
		tVOfferPrice = (TextView) findViewById(R.id.tVOfferPrice);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
		tvNewName = (TextView) findViewById(R.id.textView1);
		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MaisprecoScreen maisprecoScreen = (MaisprecoScreen) getContext();
				maisprecoScreen.addStoreView(offers, product);
			}
		});
	}

	public void setNewName(String text) {
		tvNewName.setText(text);
	}

	public ImageView getImgOffers() {
		return imgOffers;
	}

	public void setImgOffers(ImageView imgOffers) {
		this.imgOffers = imgOffers;
	}

	public LinearLayout getLinearLayout() {
		return linearLayout;
	}

	public void setLinearLayout(LinearLayout linearLayout) {
		this.linearLayout = linearLayout;
	}

	public ImageView getImgOffferNext() {
		return imgOffferNext;
	}

	public void setImgOffferNext(ImageView imgOffferNext) {
		this.imgOffferNext = imgOffferNext;
	}

	public TextView gettVOfferInformation() {
		return tVOfferInformation;
	}

	public void settVOfferInformation(TextView tVOfferInformation) {
		this.tVOfferInformation = tVOfferInformation;
	}

	public TextView gettVOfferPrice() {
		return tVOfferPrice;
	}

	public void settVOfferPrice(TextView tVOfferPrice) {
		this.tVOfferPrice = tVOfferPrice;
	}

	public void setImage(Drawable loadImageProduct) {
		// imgOffers.setImageDrawable(loadImageProduct);
	}

	public void setBitmap(Drawable loadImageProduct) {
		imgOffers.setImageDrawable(loadImageProduct);
	}

	public void setUrl(final String string) {
		if (string != null && bitmap == null) {
			new AsyncTask<String, String, String>() {
				protected String doInBackground(String... params) {
					bitmap = BitmapCommon.LoadImageFromWebOperations(string);
					if (bitmap == null) {
						bitmap = BitmapCommon
								.LoadImageFromWebOperations(string);
					}

					if (bitmap == null) {
						bitmap = BitmapCommon
								.LoadImageFromWebOperations(string);
					}
					return null;
				}

				protected void onPostExecute(String result) {
					setBitmap(bitmap);
					findViewById(R.id.linearLayout100).setVisibility(GONE);
				}
			}.execute("");
		} else if (bitmap != null) {
			setBitmap(bitmap);
		}
	}

	public void setPrice(String string) {
		gettVOfferPrice().setText(string);
	}

	public void setOfferInformation(String string) {
		gettVOfferInformation().setText(string);
	}

	private Offers offers;
	private Product product;

	public void setOffers(Offers offers, Product product) {
		this.offers = offers;
		this.product = product;

		setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				MaisprecoScreen maisprecoScreen = (MaisprecoScreen) getContext();
				maisprecoScreen.addStoreView(OfferView.this.offers,
						OfferView.this.product);
			}
		});
	}
}