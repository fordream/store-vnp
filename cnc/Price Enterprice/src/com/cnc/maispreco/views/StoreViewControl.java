package com.cnc.maispreco.views;

import org.com.cnc.common.maispreco.response.StoreResponse;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cnc.maispreco.common.StringCommon;
import com.cnc.maispreco.listenner.OnCLickLinerStore;
import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Store;

public class StoreViewControl extends LinearLayout {
	Offers offers;
	Store store;
	private Product product;
	private Context context;
	private ImageView imgProduct;
	private TextView tVProductName;
	private TextView tVLaboratoryName;
	private TextView tVPrice;
	private ImageView imgStore;
	private ImageButton imgBtnWebsite;
	private ImageButton imgBtnAddress;
	private TextView tVCommen;
	private TextView etPhoneNumber;
	private ImageButton imgBtnCall;
	private TextView tVStoreName;
	private LinearLayout llPhones;
	private LinearLayout LinearLayout1;
	private Drawable bitmapProduct, bitmapOffer;
	private String LOCALID = Common.LOCALID;

	public String getLOCALID() {
		return LOCALID;
	}

	public void setLOCALID(String lOCALID) {
		LOCALID = lOCALID;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public StoreViewControl(Context context) {
		super(context);
		this.context = context;
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.storesview, this);

		get();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product, Offers offers) {
		this.offers = offers;
		this.product = product;
		try {
			tVProductName.setText(StringCommon.cut(product.get(Product.NAME),
					40));

			tVLaboratoryName.setText(product.get(Product.LAB));
			tVPrice.setText(offers.get(Offers.PRICE));
			if ("true".equals(product.get(Product.GROUP))) {
				tVProductName.setText(offers.get(Offers.OFFERNAME));
			}
		} catch (Exception e) {
		}
	}

	public void setProduct(Product product, Store store, Offers offers) {
		this.store = store;
		this.product = product;
		this.offers = offers;
	}

	private void get() {
		llPhones = (LinearLayout) findViewById(R.id.linearLayout4);
		addLphones();
		LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
		imgProduct = (ImageView) findViewById(R.id.imgProduct);
		tVProductName = (TextView) findViewById(R.id.tVProductName);
		tVLaboratoryName = (TextView) findViewById(R.id.tVLaboratoryName);
		tVPrice = (TextView) findViewById(R.id.tVPrice);

		imgStore = (ImageView) findViewById(R.id.imgStore);

		imgBtnWebsite = (ImageButton) findViewById(R.id.imgBtnWebsite);
		imgBtnAddress = (ImageButton) findViewById(R.id.imgBtnAddress);
		imgBtnAddress.setOnClickListener(new OnCLickLinerStore(this));

		tVCommen = (TextView) findViewById(R.id.tVCommen);

		etPhoneNumber = (TextView) findViewById(R.id.etPhoneNumber);
		etPhoneNumber.setEnabled(false);
		imgBtnCall = (ImageButton) findViewById(R.id.imgBtnCall);
		imgBtnCall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:"
						+ etPhoneNumber.getText().toString()));
				getContext().startActivity(callIntent);
			}
		});

		tVStoreName = (TextView) findViewById(R.id.tVStoreName);
	}

	private void addLphones() {

	}

	public void addLPhones(StoreResponse storeResponse) {
		if (storeResponse != null) {
			for (int i = 0; i < storeResponse.getLPhones().size(); i++) {
				if (storeResponse.getLPhones().get(i) != null) {
					if (!storeResponse.getLPhones().get(i).trim().equals("")) {
						ItemPhoneStoreView view = new ItemPhoneStoreView(
								context);
						view.addData(storeResponse.getLPhones().get(i));
						llPhones.addView(view);
					}
				}
			}
		}
	}

	public void reloadView() {
		try {
			tVProductName.setText(product.get(Product.NAME));
			tVLaboratoryName.setText(product.get(Product.LAB));
			tVPrice.setText(offers.get(Offers.PRICE));

			store.setDrawable(offers.getDrawable());

			Runnable runnable = new Runnable() {
				public void run() {

					try {
						Bitmap bitmap = ((BitmapDrawable) offers.getDrawable())
								.getBitmap();
						bitmap = ((BitmapDrawable) bitmapOffer).getBitmap();
						imgStore.setImageBitmap(bitmap);
						imgStore.invalidate();
					} catch (Exception e) {
					}

					try {
						Bitmap bitmap = ((BitmapDrawable) bitmapProduct)
								.getBitmap();
						imgProduct.setImageBitmap(bitmap);
						imgProduct.invalidate();
					} catch (Exception e) {
					}
					tVCommen.setText(store.get(Store.INFOSITE));
					tVCommen.setText(offers.get(Offers.OFFERINFORMATION)
							.replace("<br>", "\n"));
					etPhoneNumber.setText(store.get(Store.PHONESITE));

					tVStoreName.setText(store.get(Store.NAMESITE));

					if ("true".equals(product.get(Product.GROUP))) {
						tVProductName.setText(offers.get(Offers.OFFERNAME));
					}
				}
			};

			LinearLayout1.post(runnable);
		} catch (Exception e) {
		}
	}

	public ImageView getImgProduct() {
		return imgProduct;
	}

	public void setImgProduct(ImageView imgProduct) {
		this.imgProduct = imgProduct;
	}

	public TextView gettVProductName() {
		return tVProductName;
	}

	public void settVProductName(TextView tVProductName) {
		this.tVProductName = tVProductName;
	}

	public TextView gettVLaboratoryName() {
		return tVLaboratoryName;
	}

	public void settVLaboratoryName(TextView tVLaboratoryName) {
		this.tVLaboratoryName = tVLaboratoryName;
	}

	public TextView gettVPrice() {
		return tVPrice;
	}

	public void settVPrice(TextView tVPrice) {
		this.tVPrice = tVPrice;
	}

	public ImageView getImgStore() {
		return imgStore;
	}

	public void setImgStore(ImageView imgStore) {
		this.imgStore = imgStore;
	}

	public ImageButton getImgBtnWebsite() {
		return imgBtnWebsite;
	}

	public void setImgBtnWebsite(ImageButton imgBtnWebsite) {
		this.imgBtnWebsite = imgBtnWebsite;
	}

	public TextView gettVCommen() {
		return tVCommen;
	}

	public void settVCommen(TextView tVCommen) {
		this.tVCommen = tVCommen;
	}

	public TextView getEtPhoneNumber() {
		return etPhoneNumber;
	}

	public void setEtPhoneNumber(TextView etPhoneNumber) {
		this.etPhoneNumber = etPhoneNumber;
	}

	public ImageButton getImgBtnCall() {
		return imgBtnCall;
	}

	public void setImgBtnCall(ImageButton imgBtnCall) {
		this.imgBtnCall = imgBtnCall;
	}

	public Offers getOffers() {
		return offers;
	}

	public void addImage(Drawable bitmapProduct, Drawable bitmapOffer) {
		if (bitmapOffer != null) {
			this.bitmapOffer = bitmapOffer;
		} else if (store.getDrawable() != null) {
			this.bitmapOffer = store.getDrawable();
		}

		if (bitmapProduct != null) {
			this.bitmapProduct = bitmapProduct;
		} else if (product != null && product.getDrawable() != null) {
			this.bitmapProduct = product.getDrawable();
		}
	}
}