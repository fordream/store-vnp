package com.cnc.maispreco.asyn;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.request.LoginRequest;
import org.com.cnc.common.maispreco.response.LoginResponse;
import org.com.cnc.common.maispreco.response.OffersResponse;
import org.com.cnc.common.maispreco.response.ProductResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.BitmapCommon;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;

import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.views.OffersViewControl;

public class OfferBarcodeAsyn extends AsyncTask<String, String, String> {
	private List<Product> lProducts = new ArrayList<Product>();
	private MaisprecoScreen maisprecoScreen;
	private ProgressDialog dialog;
	private Handler handler = new Handler();
	private Resources resources;
	private Drawable drawable;
	private Product product = null;
	ProductResponse productResponse = new ProductResponse();
	OffersResponse offersResponse = new OffersResponse(),
			offersResponse2 = new OffersResponse();
	private OffersViewControl view;
	private int page = 0;

	public OfferBarcodeAsyn(MaisprecoScreen maisprecoScreen,
			Resources resources, Handler handler, OffersViewControl view,
			int page, boolean isRelogin, Product product) {
		super();
		this.maisprecoScreen = maisprecoScreen;
		this.resources = resources;
		this.view = view;
		this.page = page;
		this.product = product;
	}

	protected String doInBackground(String... params) {

		handler.post(new Runnable() {
			public void run() {
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		});

		TrackerGoogle.homeTracker(maisprecoScreen,
				"/codigobarra/REPLACE_BY_THE_BARCODE");
		TrackerGoogle.homeTracker(maisprecoScreen,
				"/ofertas/REPLACE_BY_THE_PRODUCT_NAME_THAT_BROUGHT_HERE");
		TrackerGoogle
				.homeTracker(maisprecoScreen,
						"/ofertas_proximas/REPLACE_BY_THE_PRODUCT_NAME_THAT_BROUGHT_HERE");
		Search search1 = new Search();
		search1.setToken(Common.tooken);
		search1.setSearchType("3");
		search1.setSearch(params[0]);
		search1.setPage("" + 1);
		productResponse = Product.getLProduct(search1, maisprecoScreen);
		lProducts = productResponse.getlProducts();
		if ("-1".equals(productResponse.getStatusCode())) {
			relogin();
			lProducts = productResponse.getlProducts();
		}

		if (!"-1".equals(productResponse.getStatusCode())) {
			if (lProducts.size() > 0) {
				product = lProducts.get(0);
				handler.post(new Runnable() {

					public void run() {
						view.setProduct(product);

					}
				});
				try {
					String url = product.get(Product.URLIMAGE);
					drawable = BitmapCommon.LoadImageFromWebOperations(url);
					handler.post(new Runnable() {
						public void run() {
							view.setImageProduct(drawable);
						}
					});
				} catch (Exception e) {
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
					view.addOffers(offersResponse2.getLoOffersResponses());
					if (page == 1) {
						view.post(new Runnable() {
							public void run() {
								dialog.dismiss();
								view.notifyDataSetChangedBesprice();
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

			}
		}

		handler.post(new Runnable() {
			public void run() {
				dialog.dismiss();
			}
		});
		return params[0];
	}

	protected void onPostExecute(String result) {
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
