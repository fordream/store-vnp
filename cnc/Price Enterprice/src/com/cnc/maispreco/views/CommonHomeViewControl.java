package com.cnc.maispreco.views;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cnc.maispreco.adpters.ProductApater;
import com.cnc.maispreco.soap.data.Product;

public class CommonHomeViewControl extends LinearLayout {
	private ListView lVProduct;
	private ArrayList<Product> alProduct = new ArrayList<Product>();
	private ArrayList<Product> alProductStore = new ArrayList<Product>();
	private ArrayAdapter<Product> adapter;
	private Context context;
	private LoadMoreView loadMoreView;
	private int cutentPage = 1;
	private String LOCALID = Common.LOCALID;
	private Product product;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getLOCALID() {
		return LOCALID;
	}

	public void setLOCALID(String lOCALID) {
		LOCALID = lOCALID;
	}

	public CommonHomeViewControl(Context context,Product product) {
		super(context);
		this.product = product;
		this.context = context;
		LayoutInflater li = (LayoutInflater) this.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		li.inflate(R.layout.homeview, this);
		lVProduct = (ListView) findViewById(R.id.lVProducts);
		loadMoreView = new LoadMoreView(context);
		loadMoreView.setType(LoadMoreView.TYPE_PRODUCT);
		lVProduct.addFooterView(loadMoreView);
		loadMoreView.setVisibility(View.GONE);
		adapter = new ProductApater(context, R.layout.item_product, alProduct);

		lVProduct.setAdapter(adapter);
		lVProduct.setOnItemClickListener(new OnItemClickListenerHome());

		addReload();
	}

	private void addReload() {
		MyCount myCount = new MyCount(15000, 1000);
		myCount.start();
	}

	private class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		public void onFinish() {
			adapter.notifyDataSetChanged();
		}

		public void onTick(long millisUntilFinished) {
			adapter.notifyDataSetChanged();
		}
	}

	private class OnItemClickListenerHome implements OnItemClickListener {
		public void onItemClick(AdapterView<?> adapter, View view,
				int position, long arg3) {
			Product product = null;
			if (view instanceof LoadMoreView) {
				if (alProduct.size() < alProductStore.size()) {
					cutentPage++;
				}
				notifyDataSetChanged();
				return;
			}

			product = alProduct.get(position);

			if (product != null) {
				// Common.builder(getContext(), product.get(Product.GROUP));
				String maxprice = product.get(Product.MAXPRICE);
				String minprice = product.get(Product.MINPRICE);
				if (!Common.havePrice(minprice)) {
					if (!Common.havePrice(maxprice)) {
						return;
					}
				}

				if (!Common.havePrice(maxprice)) {
					if (!Common.havePrice(minprice)) {
						return;
					}
				}

				if (Common.moreThanOne(product.get(Product.GENERICSTARTINGAT))
						|| Common.moreThanOne(product
								.get(Product.SIMILARSTARTINGAT))) {
					((MaisprecoScreen) context).addProductView(product);
				} else {
					((MaisprecoScreen) context).addOfferView(product);
				}
			}
		}
	}

	public void reload() {
		adapter.notifyDataSetChanged();
	}

	public void addProduct(List<Product> lProducts) {
		alProductStore.addAll(lProducts);
		loadMoreView.post(new Runnable() {
			public void run() {
				loadMoreView.setData(alProduct.size() + "",
						alProductStore.size() + "");
				if (alProduct.size() < alProductStore.size()) {
					loadMoreView.setVisibility(VISIBLE);
				}

				if (cutentPage * ConfigurationView.page > alProduct.size()) {
					notifyDataSetChanged();
				}
			}
		});
	}

	public void notifyDataSetChanged() {
		alProduct.clear();
		for (int i = 0; i < cutentPage * ConfigurationView.page; i++) {
			if (i < alProductStore.size()) {
				alProduct.add(alProductStore.get(i));
			}
		}

		if (alProduct.size() < alProductStore.size()) {
			loadMoreView.setData(alProduct.size() + "", alProductStore.size()
					+ "");
			loadMoreView.setVisibility(VISIBLE);
		} else {
			loadMoreView.setVisibility(GONE);
		}

		adapter.notifyDataSetChanged();
	}
}