package com.cnc.maispreco.asyn;

import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.common.maispreco.response.LoginResponse;
import org.com.cnc.common.maispreco.response.OffersResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Handler;

import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.views.OffersViewControl;

public class OfferFirstBestpriceAsyn extends AsyncTask<String, String, String> {
	private MaisprecoScreen maisprecoScreen;
	private Product product;
	private Handler handler = new Handler();
	private ProgressDialog dialog;
	private Resources resources;
	private int page = 0;
	private boolean isRelogin = false;
	private OffersViewControl offersViewControl;

	public OfferFirstBestpriceAsyn(MaisprecoScreen maisprecoScreen,
			Product product, int page, boolean isRelogin,
			OffersViewControl offersViewControl) {
		super();
		this.maisprecoScreen = maisprecoScreen;
		this.product = product;
		this.resources = maisprecoScreen.getResources();
		this.page = page;
		this.isRelogin = isRelogin;
		this.offersViewControl = offersViewControl;
	}

	boolean isFirst = true;

	protected String doInBackground(String... params) {
		Runnable runnable = new Runnable() {
			public void run() {
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		};

		handler.post(runnable);
		TrackerGoogle.homeTracker(maisprecoScreen,
				"/ofertas/REPLACE_BY_THE_PRODUCT_NAME_THAT_BROUGHT_HERE");
		TrackerGoogle
				.homeTracker(maisprecoScreen,
						"/ofertas_proximas/REPLACE_BY_THE_PRODUCT_NAME_THAT_BROUGHT_HERE");

		if (isRelogin) {
			relogin();
		}
		boolean check = true;
		while (check) {
			Search search = new Search();
			search.setToken(Common.tooken);
			search.setSearch(product.get(Product.ID));
			search.setSearchType("4");
			search.setNearBy("?");
			OffersResponse offersResponse2 = Offers.getLOffer(search,
					maisprecoScreen, page);
			offersViewControl.addOffers(offersResponse2.getLoOffersResponses());
			if (page == 1) {
				offersViewControl.post(new Runnable() {
					public void run() {
						dialog.dismiss();
						offersViewControl.notifyDataSetChangedBesprice();
					}
				});
			}
			page++;

			if ("-1".equals(offersResponse2.getStatusCode())) {
				check = false;
				page--;
				return offersResponse2.getStatusMessage() + "";
			}

			if (offersResponse2.getLoOffersResponses().size() < 10) {
				check = false;
			}
		}
		return null;
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

	protected void onPostExecute(String result) {
		dialog.dismiss();
		if (result != null) {
			Builder builder = new Builder(maisprecoScreen);
			builder.setMessage(result);
			builder.setTitle("Error");
			builder.setCancelable(false);
			builder.setPositiveButton("YES", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					new OfferFirstBestpriceAsyn(maisprecoScreen, product, page,
							false, offersViewControl).execute("");
				}
			});
			builder.show();
		}
	}
}
