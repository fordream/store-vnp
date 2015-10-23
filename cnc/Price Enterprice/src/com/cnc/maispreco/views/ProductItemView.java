package com.cnc.maispreco.views;

import org.com.cnc.maispreco.R;

import com.cnc.maispreco.common.CommonView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductItemView extends LinearLayout {
	private ImageView imgProduct;
	private ImageView imgView;

	private TextView tVProductName;
	private TextView tVLaboratoryName;
	private TextView tVPriceFromTo;
	private TextView textView1;
	private LinearLayout linearLayout2;

	public ProductItemView(Context context) {
		super(context);
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.item_product, this, true);
		configure();
	}

	private void configure() {
		imgProduct = (ImageView) findViewById(R.id.imgProduct);
		imgView = (ImageView) findViewById(R.id.imageView1);
		tVProductName = (TextView) findViewById(R.id.tVProductName);
		tVLaboratoryName = (TextView) findViewById(R.id.tVLaboratoryName);
		tVPriceFromTo = (TextView) findViewById(R.id.tVPriceFromTo);

		textView1 = (TextView) findViewById(R.id.textView1);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
	}

	public TextView getTextView1() {
		return textView1;
	}

	public void setTextView1(TextView textView1) {
		this.textView1 = textView1;
	}

	public ImageView getImgProduct() {
		return imgProduct;
	}

	public void setImgProduct(ImageView imgProduct) {
		this.imgProduct = imgProduct;
	}

	public ImageView getImgView() {
		return imgView;
	}

	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
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

//	public void setDrawable(Drawable drawable) {
//		// imgProduct.post(new Runnable() {
//		// public void run() {
//		// int width = linearLayout2.getWidth();
//		// int height = linearLayout2.getHeight();
//		// Drawable drawable2 = CommonView.scaleDraable(drawable, width,
//		// height);
//		// imgProduct.setImageDrawable(drawable2);
//		// }
//		// });
//		//linearLayout2.post(new RunableImage(drawable));
//
//	}

	@SuppressWarnings("unused")
	private class RunableImage implements Runnable {
		private Drawable drawable;

		public RunableImage(Drawable drawable) {
			this.drawable = drawable;
		}

		public void run() {
			if (drawable != null) {
				int width = linearLayout2.getWidth();
				int height = linearLayout2.getHeight();
				Drawable drawable2 = CommonView.scaleDraable(drawable, width,
						height);
				imgProduct.setImageDrawable(drawable2);
			}
		}

	}
}