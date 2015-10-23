package com.cnc.maispreco.asyn;

import java.util.List;

import org.com.cnc.common.maispreco.response.CategoryResponse;
import org.com.cnc.common.maispreco.response.ProductResponse;
import org.com.cnc.common.maispreco.response.Response;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

import com.cnc.maispreco.soap.data.Category;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.views.CategoryViewControl;
import com.cnc.maispreco.views.HomeViewControl;

public class Category1Asyn extends AsyncTask<String, String, String> {
	private MaisprecoScreen maisprecoScreen;
	private Handler handler = new Handler();
	private ProgressDialog dialog;
	private Resources resources;
	private int page = 0;
	private boolean isRelogin = false;
	private View view;
	private String message = null;
	private CategoryViewControl categoryViewControl;
	private HomeViewControl homeViewControl;

	public Category1Asyn(MaisprecoScreen maisprecoScreen,
			OnItemClickListener clickCate, OnItemClickListener clickProduct,
			Resources resources, Handler handler2, int page, boolean isRelogin,
			View view, CategoryViewControl categoryViewControl,
			HomeViewControl homeViewControl) {
		super();
		this.maisprecoScreen = maisprecoScreen;
		this.resources = resources;
		this.page = page;
		this.isRelogin = isRelogin;
		this.view = view;
		this.categoryViewControl = categoryViewControl;
		this.homeViewControl = homeViewControl;
	}

	protected String doInBackground(String... params) {
		Runnable runnable = new Runnable() {
			public void run() {
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		};

		handler.post(runnable);
		if (isRelogin) {
			Common.relogin();
		} else {
		}

		boolean check = true;
		while (check) {
			boolean test = true;
			Search search = new Search();
			search.setToken(Common.tooken);
			search.setSearchType("2");
			search.setSearch(params[0]);
			Response response = Category.getLCategory1(search, maisprecoScreen,
					page);
			if (response instanceof CategoryResponse) {
				List<Category> lCategory = ((CategoryResponse) response)
						.getlProducts();
				if (lCategory.size() < 10) {
					test = false;
				}
				if (view == null) {
					view = categoryViewControl;
				}
				if (view instanceof CategoryViewControl)
					((CategoryViewControl) view).addCatagory(lCategory);
			} else if (response instanceof ProductResponse) {
				List<Product> lProduct = ((ProductResponse) response)
						.getlProducts();
				if (lProduct.size() < 10) {
					test = false;
				}
				if (view == null) {
					view = homeViewControl;
				}
				if (view instanceof HomeViewControl)
					((HomeViewControl) view).addProduct(lProduct);
			}

			if (page == 1) {
				handler.post(new Runnable() {
					public void run() {
						dialog.dismiss();
						maisprecoScreen.addlView(view);
						if (view instanceof CategoryViewControl) {
							((CategoryViewControl) view).notifyDataSetChanged();
						} else if (view instanceof HomeViewControl) {
							((HomeViewControl) view).notifyDataSetChanged();
						}
					}
				});
			}

			page++;

			if ("-1".equals(response.getStatusCode())) {
				message = response.getStatusMessage() + "";
				page--;
				return params[0];
			}

			if (!test) {
				return params[0];
			}
		}
		return params[0];
	}

	String result;

	protected void onPostExecute(String result1) {
		this.result = result1;
		dialog.dismiss();
		if (message != null) {
			Builder builder = new Builder(maisprecoScreen);
			builder.setMessage(message);
			builder.setTitle("Error");
			builder.setCancelable(false);
			builder.setPositiveButton("YES", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					new Category1Asyn(maisprecoScreen, null, null,
							maisprecoScreen.getResources(), null, page, true,
							view, categoryViewControl, homeViewControl)
							.execute(result);
				}
			});
			builder.show();
		}
	}
}