package com.cnc.maispreco.asyn;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.common.BitmapCommon;
import android.graphics.drawable.Drawable;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.views.ProductViewControl;

public class AsynProductImage extends AsynTask {
	private ProductViewControl productViewControl;
	private Drawable drawable;

	public AsynProductImage(MaisprecoScreen maisprecoScreen,
			ProductViewControl productViewControl) {
		super(maisprecoScreen);
		this.productViewControl = productViewControl;
	}

	protected String doInBackground(String... params) {
		showDialog();
		try {
			drawable = BitmapCommon
					.LoadImageFromWebOperations(productViewControl.getProduct()
							.get(Product.URLIMAGE));
		} catch (Exception e) {
		}
		dimiss();
		return super.doInBackground(params);
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		productViewControl.setBitmap(drawable);
	}
}