package com.cnc.maispreco.asyn;

import org.com.cnc.common.maispreco.response.ProductResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;

import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.views.CommonHomeViewControl;

public class SearchAsyn extends AsyncTask<String, String, String> {
	private MaisprecoScreen maisprecoScreen;
	private ProgressDialog dialog;
	private Handler handler = new Handler();
	private Resources resources;
	private CommonHomeViewControl searchViewControl;
	private int page = 0;
	private boolean isRelogin = false;
	private String message = null;

	public SearchAsyn(MaisprecoScreen maisprecoScreen, Resources resources,
			Handler handler, CommonHomeViewControl searchViewControl, int page,
			boolean isRelogin) {
		super();
		this.maisprecoScreen = maisprecoScreen;
		this.resources = resources;
		this.searchViewControl = searchViewControl;
		this.page = page;
		this.isRelogin = isRelogin;
	}

	protected String doInBackground(String... params) {
		handler.post(new Runnable() {
			public void run() {
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		});
		TrackerGoogle.homeTracker(maisprecoScreen,
				"/busca/REPLACE_BY_THE_SEARCH");
		if (isRelogin) {
			Common.relogin();
		} else {
		}
		boolean check = true;
		while (check) {
			Search search = new Search();
			search.setToken(Common.tooken);
			search.setSearchType("3");
			search.setSearch(params[0]);
			search.setPage(page + "");
			ProductResponse productResponse = Product.getLProduct(search,
					maisprecoScreen);
			searchViewControl.addProduct(productResponse.getlProducts());
			if (page == 1) {
				handler.post(new Runnable() {
					public void run() {
						dialog.dismiss();
						searchViewControl.notifyDataSetChanged();

					}
				});
			}

			page++;
			if ("-1".equals(productResponse.getStatusCode())) {
				message = productResponse.getStatusMessage() + "";
				page--;
				return params[0];
			}
			if (productResponse.getlProducts().size() < 10) {
				return params[0];
			}
		}
		return params[0];
	}

	protected void onPostExecute(String result1) {
		dialog.dismiss();
		if (message != null) {
			Builder builder = new Builder(maisprecoScreen);
			builder.setMessage(message);
			builder.setTitle("Error");
			builder.setCancelable(false);
			builder.setPositiveButton("YES", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					new SearchAsyn(maisprecoScreen, maisprecoScreen
							.getResources(), null, searchViewControl, page,
							true).execute("");
				}
			});
			builder.show();
		}
	}
}
