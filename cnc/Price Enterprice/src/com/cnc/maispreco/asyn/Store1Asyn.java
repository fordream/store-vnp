package com.cnc.maispreco.asyn;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.request.StoreRequest;
import org.com.cnc.common.maispreco.response.StoreResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.BitmapCommon;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.soap.data.Offers;
import com.cnc.maispreco.soap.data.Product;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.soap.data.Store;
import com.cnc.maispreco.views.StoreViewControl;

public class Store1Asyn extends AsynTask {
	private Offers offers;
	private Product product;
	private List<Store> lStores = new ArrayList<Store>();
	private StoreViewControl storeViewControl;
	private boolean isRelogin = false;

	public Store1Asyn(MaisprecoScreen maisprecoScreen, Offers offers,
			Product product, StoreViewControl storeViewControl,
			boolean isRelogin) {
		super(maisprecoScreen);
		this.offers = offers;
		this.product = product;
		this.storeViewControl = storeViewControl;
		this.isRelogin = isRelogin;
	}

	private StoreResponse storeResponse;
	private Drawable bitmapProduct;
	private Drawable bitmapOffer;

	protected String doInBackground(String... params) {
		showDialog();
		TrackerGoogle.homeTracker(maisprecoScreen,
				"/detalhe_oferta/REPLACE_BY_THE_OFFER_NAME_THAT_BROUGHT_HERE");
		try {
			String url = product.get(Product.URLIMAGE);
			if("true".equals(product.get(Product.GROUP))){
				url = offers.get(Offers.OFFERIMAGE);
			}
			bitmapProduct = BitmapCommon.LoadImageFromWebOperations(url);
		} catch (Exception e) {
		}
		if (isRelogin) {
			Common.relogin();
		} else {
		}

		Search search = new Search();
		search.setToken(Common.tooken);
		search.setSearchType("10");
		search.setSearch(offers.get(Offers.SITEID));

		StoreRequest request = new StoreRequest();
		request.setToken(Common.tooken);
		request.setSearchType("10");
		request.setSearch(offers.get(Offers.SITEID));
		storeResponse = StoreResponse.getLStore(request);
		lStores = Store.getLStore(search, maisprecoScreen);
		bitmapOffer = BitmapCommon.LoadImageFromWebOperations(offers
				.get(Offers.URLLOGOSITE));
		return null;
	}

	protected void onPostExecute(String result) {
		dimiss();
		Store store = new Store();
		if (lStores.size() >= 1)
			store = lStores.get(0);
		storeViewControl.setProduct(product, store, offers);
		storeViewControl.addImage(bitmapProduct, bitmapOffer);
		if (!"-1".equals(storeResponse.getStatusCode()))
			storeViewControl.addLPhones(storeResponse);
		storeViewControl.reloadView();

		storeViewControl.getImgBtnWebsite().setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						String url = storeViewControl.getStore().get(
								Store.URLSITE);
						new WebAsyn(maisprecoScreen, url).execute("");
					}
				});

		if ("-1".equals(storeResponse.getStatusCode())) {
			Builder builder = new Builder(maisprecoScreen);
			builder.setMessage(result);
			builder.setTitle("Error");
			builder.setCancelable(false);
			builder.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							new Store1Asyn(maisprecoScreen, offers, product,
									storeViewControl, true).execute("");
						}
					});
			builder.show();
		} else if (storeResponse.getLPhones().size() == 0) {
			Resources resources = maisprecoScreen.getResources();
			resources.getString(R.string.Sorry_there_is_no_result_available);
			CommonView.makeText(maisprecoScreen, resources
					.getString(R.string.Sorry_there_is_no_result_available));
		}
	}
}
