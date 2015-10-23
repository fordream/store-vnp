package com.cnc.maispreco.views;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cnc.maispreco.asyn.AsynProductImage;
import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.common.StringCommon;
import com.cnc.maispreco.soap.data.Product;

public class ProductViewControl extends LinearLayout {
	private ImageView imgProduct;
	private TextView tVProductName;
	private TextView tVLaboratoryName;
	private TextView tVPriceFromTo;
	private TextView tVCountOffersProducts;
	private TextView tVCountSilarsProducts;
	private TextView tVCountGenericproducts;
	private RelativeLayout rlOffersProduct;
	private RelativeLayout rlSimilarsProduct;
	private RelativeLayout rlGenericProducts;
	private Product product = null;
	private Bitmap bitmap;
	private String LOCALID = Common.LOCALID;

	public String getLOCALID() {
		return LOCALID;
	}

	public void setLOCALID(String lOCALID) {
		LOCALID = lOCALID;
	}

	public ProductViewControl(MaisprecoScreen context, Product id) {
		super(context);
		this.product = id;
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.productview, this);
		get();
		new AsynProductImage((MaisprecoScreen) context, this).execute("");
	}

	public void reload() {
		try {
			imgProduct.setImageBitmap(bitmap);
			imgProduct.invalidate();

			tVLaboratoryName.setText(product.get(Product.LAB));
			tVProductName.setText(product.get(Product.NAME));

			String minPrice = product.get(Product.MINPRICE);
			String maxPrice = product.get(Product.MAXPRICE);
			String text = Common.convertPrice(minPrice, maxPrice);
			tVPriceFromTo.setText(text);

			if (StringCommon.isNumber(product.get(Product.QTOFFERS))) {
				tVCountOffersProducts.setText(product.get(Product.QTOFFERS));
			}
		} catch (Exception e) {
		}
	}

	private void get() {
		imgProduct = (ImageView) findViewById(R.id.imgProduct);
		tVProductName = (TextView) findViewById(R.id.tVProductName);
		tVLaboratoryName = (TextView) findViewById(R.id.tVLaboratoryName);
		tVPriceFromTo = (TextView) findViewById(R.id.tVPriceFromTo);
		tVCountOffersProducts = (TextView) findViewById(R.id.tVCountOffersProducts);
		tVCountSilarsProducts = (TextView) findViewById(R.id.tVCountSilarsProducts);
		tVCountGenericproducts = (TextView) findViewById(R.id.tVCountGenericproducts);
		rlOffersProduct = (RelativeLayout) findViewById(R.id.rlOffersProduct);
		rlSimilarsProduct = (RelativeLayout) findViewById(R.id.rlSimilarsProduct);
		rlGenericProducts = (RelativeLayout) findViewById(R.id.rlGenericProducts);

		rlOffersProduct.setOnClickListener(onClickListener);
		rlSimilarsProduct.setOnClickListener(onClickListener);
		rlGenericProducts.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {
		public void onClick(View v) {
			MaisprecoScreen maisprecoScreen = ((MaisprecoScreen) getContext());
			if (v == rlGenericProducts) {
				maisprecoScreen.addGenericProductsViewControl(product);
			} else if (v == rlSimilarsProduct) {
				maisprecoScreen.addSimilarsProductView(product);
			} else if (v == rlOffersProduct) {
				maisprecoScreen.addOfferView(product);
			}
		}
	};

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

	public TextView gettVPriceFromTo() {
		return tVPriceFromTo;
	}

	public void settVPriceFromTo(TextView tVPriceFromTo) {
		this.tVPriceFromTo = tVPriceFromTo;
	}

	public TextView gettVCountOffersProducts() {
		return tVCountOffersProducts;
	}

	public void settVCountOffersProducts(TextView tVCountOffersProducts) {
		this.tVCountOffersProducts = tVCountOffersProducts;
	}

	public TextView gettVCountSilarsProducts() {
		return tVCountSilarsProducts;
	}

	public void settVCountSilarsProducts(TextView tVCountSilarsProducts) {
		this.tVCountSilarsProducts = tVCountSilarsProducts;
	}

	public TextView gettVCountGenericproducts() {
		return tVCountGenericproducts;
	}

	public void settVCountGenericproducts(TextView tVCountGenericproducts) {
		this.tVCountGenericproducts = tVCountGenericproducts;
	}

	public void addProduct(Product search) {
		try {
			search = product;
			tVLaboratoryName.setText(search.get(Product.LAB));
			tVProductName.setText(search.get(Product.NAME));
			String minPrice = product.get(Product.MINPRICE);
			String maxPrice = product.get(Product.MAXPRICE);
			String text = Common.convertPrice(minPrice, maxPrice);
			tVPriceFromTo.setText(text);

			if (StringCommon.isNumber(search.get(Product.QTOFFERS))) {
				tVCountOffersProducts.setText(search.get(Product.QTOFFERS));
			}

			String gener = search.get(Product.GENERICSTARTINGAT);
			String simmi = search.get(Product.SIMILARSTARTINGAT);
			tVCountGenericproducts.setText(gener);
			tVCountSilarsProducts.setText(simmi);

			if (!Common.moreThanOne(gener)) {
				rlGenericProducts.setVisibility(GONE);
				rlSimilarsProduct
						.setBackgroundResource(R.drawable.bgconfiguration3indicator);
			}

			if (!Common.moreThanOne(simmi)) {
				rlSimilarsProduct.setVisibility(GONE);
			}
		} catch (Exception e) {
		}
	}

	public boolean isNumberAndBesterZero(String input) {
		return StringCommon.isNumber(input) && !input.equals("0");
	}

	public Product getProduct() {
		return product;
	}

	public void setBitmap(Drawable loadImageFromWebOperations) {
		if (loadImageFromWebOperations != null) {
			bitmap = ((BitmapDrawable) loadImageFromWebOperations).getBitmap();
		} else {
			try {
				bitmap = CommonView.convertBitmap(((BitmapDrawable) product
						.getDrawable()).getBitmap());
			} catch (Exception e) {
			}
		}

		reload();
	}
}