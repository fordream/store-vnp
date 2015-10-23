package com.cnc.maispreco.asyn;

import java.util.List;

import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.common.maispreco.response.CategoryResponse;
import org.com.cnc.common.maispreco.response.LoginResponse;
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
import com.cnc.maispreco.soap.data.Category;
import com.cnc.maispreco.views.CategoryViewControl;

public class CategoryAsyn extends AsyncTask<String, String, String> {
	private Handler handler = new Handler();
	private ProgressDialog dialog;
	private MaisprecoScreen maisprecoScreen;
	private Resources resources;
	private CategoryViewControl categoryViewControl;
	private int page = 0;
	private boolean isRelogin = false;

	public CategoryAsyn(MaisprecoScreen maisprecoScreen, Resources resources,
			CategoryViewControl categoryViewControl, int page, boolean isRelogin) {
		super();
		this.maisprecoScreen = maisprecoScreen;
		this.resources = resources;
		this.categoryViewControl = categoryViewControl;
		this.page = page;
		this.isRelogin = isRelogin;
	}

	protected String doInBackground(String... params) {
		Runnable runnable = new Runnable() {
			public void run() {
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		};
		handler.post(runnable);
		TrackerGoogle.homeTracker(maisprecoScreen,
				"/categorias/REPLACE_BY_THE_STORE_NAME_THAT_BROUGHT_HERE");
		TrackerGoogle.homeTracker(maisprecoScreen,
				"/categoria/REPLACE_BY_THE_CATEGORY_NAME_THAT_BROUGHT_HERE");
		boolean check = true;
		// int page = 1;

		if (isRelogin) {
			relogin();
		} else {
		}

		while (check) {
			CategoryResponse categoryResponse = Category.getLCategory(
					Common.tooken, page);
			List<Category> lCategory = categoryResponse.getlProducts();
			categoryViewControl.addCatagory(lCategory);
			if (page == 1) {
				categoryViewControl.post(new Runnable() {
					public void run() {
						dialog.dismiss();
						categoryViewControl.notifyDataSetChanged();
					}
				});
			}
			if (lCategory.size() < 10) {
				check = false;
			}
			page++;

			if ("-1".equals(categoryResponse.getStatusCode())) {
				check = false;
				page--;
				return categoryResponse.getStatusMessage() + "";

			}
		}

		return null;
	}

	protected void onPostExecute(String result) {
		dialog.dismiss();
		if (result != null) {
			Builder builder = new Builder(maisprecoScreen);
			builder.setMessage(result);
			builder.setTitle("Error");
			builder.setCancelable(false);
			builder.setPositiveButton("YES", new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					new CategoryAsyn(maisprecoScreen, maisprecoScreen
							.getResources(), categoryViewControl, page, true)
							.execute("");
				}
			});
			builder.show();
		}
	}

	public void relogin() {
		LoginRequest loginRequest = new LoginRequest("" + Common.latitude, ""
				+ Common.longitude);
		LoginResponse loginResponse = LoginResponse
				.getLoginResponse(loginRequest);
		if (loginResponse != null) {
			Common.tooken = loginResponse.getToken();
		}
	}
}
