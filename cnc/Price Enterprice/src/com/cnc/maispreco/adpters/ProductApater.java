package com.cnc.maispreco.adpters;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.views.ProductItemView;

public class ProductApater extends ArrayAdapter<Product> {
	List<Product> productList;
	int resource;
	Context context;

	public ProductApater(Context context, int textViewResourceId,
			ArrayList<Product> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		resource = textViewResourceId;
		productList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View workView = convertView;
		// boolean isNewRow = false;
		if (workView == null) {
			// isNewRow = true;
			workView = new ProductItemView(getContext());
		}

		final Product product = productList.get(position);

		if (product != null) {
			TextView txtProductName = ((ProductItemView) workView)
					.gettVProductName();
			TextView tVLaboratoryName = ((ProductItemView) workView)
					.gettVLaboratoryName();
			TextView tVPriceFromTo = ((ProductItemView) workView)
					.gettVPriceFromTo();

			TextView textView = ((ProductItemView) workView).getTextView1();
			final ImageView imgProduct = ((ProductItemView) workView)
					.getImgProduct();
			ImageView imgView = ((ProductItemView) workView).getImgView();

			String name = product.get(Product.NAME);

			txtProductName.setText(name);

			tVLaboratoryName.setText(product.get(Product.LAB));

			String maxprice = product.get(Product.MAXPRICE);
			String minprice = product.get(Product.MINPRICE);

			if (Common.havePrice(minprice) || Common.havePrice(maxprice)) {
				//maxprice = maxprice == null ? "?" : maxprice;
				//minprice = minprice == null ? "?" : minprice;
//				String text = minprice + " ~ " + maxprice;
//				
//				if(!Common.havePrice(minprice)){
//					text = maxprice;
//				}else if(!Common.havePrice(maxprice)){
//					text = minprice;
//				}
				String text = Common.convertPrice(minprice, maxprice);
				tVPriceFromTo.setText(text);
				imgView.setVisibility(View.VISIBLE);
				tVPriceFromTo.setVisibility(View.VISIBLE);
				textView.setVisibility(View.GONE);
			} else {
				tVPriceFromTo.setVisibility(View.GONE);
				textView.setVisibility(View.VISIBLE);
				imgView.setVisibility(View.GONE);
			}


			if (product.getDrawable() != null) {
				imgProduct.setImageDrawable(product.getDrawable());
				imgView.setBackgroundResource(R.drawable.next);
				if (Common.havePrice(minprice) || Common.havePrice(maxprice))
					tVPriceFromTo.setVisibility(View.VISIBLE);
				else
					tVPriceFromTo.setVisibility(View.GONE);
			}

		}

		return workView;
	}
}