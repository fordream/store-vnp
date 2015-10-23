package com.cnc.maispreco.asyn;

import java.util.ArrayList;
import java.util.List;

import org.com.cnc.common.maispreco.response.AddressResponse;
import org.com.cnc.maispreco.MaisprecoScreen;
import org.com.cnc.maispreco.R;
import org.com.cnc.maispreco.common.BitmapCommon;
import org.com.cnc.maispreco.common.Common;
import org.com.cnc.maispreco.common.TrackerGoogle;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.cnc.maispreco.common.CommonView;
import com.cnc.maispreco.soap.data.Address;
import com.cnc.maispreco.soap.data.Search;
import com.cnc.maispreco.soap.data.Store;
import com.cnc.maispreco.views.AddressViewControl;
import com.cnc.maispreco.views.WebViewController;

public class AddressAsyn extends AsyncTask<String, String, String> {
	private Handler handler = new Handler();
	private MaisprecoScreen maisprecoScreen;
	private AddressResponse addressResponse;
	private Store store;
	private List<Address> lAddresses = new ArrayList<Address>();
	private OnClickListener onClickListener;
	private ProgressDialog dialog;
	private Drawable drawable;
	private AddressViewControl addressViewControl;
	private boolean isRelogin = false;

	public AddressAsyn(MaisprecoScreen maisprecoScreen, Store store,
			AddressViewControl addressViewControl, boolean isRelogin,
			View.OnClickListener clickListener) {
		this.maisprecoScreen = maisprecoScreen;
		this.store = store;
		this.addressViewControl = addressViewControl;
		this.isRelogin = isRelogin;
		this.onClickListener = clickListener;
	}

	protected String doInBackground(String... params) {
		handler.post(new Runnable() {
			public void run() {
				Resources resources = maisprecoScreen.getResources();
				String message = resources.getString(R.string.loading);
				dialog = ProgressDialog.show(maisprecoScreen, "", message);
			}
		});

		TrackerGoogle.homeTracker(maisprecoScreen,
				"/loja/REPLACE_BY_THE_STORE_NAME_THAT_BROUGHT_HERE");
		if (isRelogin) {
			Common.relogin();
		}
		Search search = new Search();
		search.setToken(Common.tooken);
		search.setSearchType("11");
		search.setSearch(store.get(Store.IDSITE));
		addressResponse = Address.getLAddress(search);
		lAddresses = addressResponse.getlAddresses();
		drawable = BitmapCommon.LoadImageFromWebOperations(store
				.get(Store.URLSITELOGO));
		return null;
	}

	protected void onPostExecute(String result) {
		addressViewControl.addAddress(lAddresses);
		addressViewControl.setImageOffer(drawable);
		addressViewControl.reload();
		addressViewControl.notifyDataSetChanged(AddressViewControl.NONE, null,
				true);
		addressViewControl.addOnClickListener(onClickListener);
		addressViewControl.getImgBtnWebsite().setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						new AsyncTask<String, String, String>() {
							protected void onPostExecute(String result) {
								WebViewController viewWeb = new WebViewController(
										maisprecoScreen);
								viewWeb.loadUrl(addressViewControl.getStore()
										.get(Store.URLSITE));
								maisprecoScreen.addlView(viewWeb);
							}

							protected String doInBackground(String... arg0) {
								return null;
							}

						}.execute("");
					}
				});
		// maisprecoScreen.addlView(view);

		if ("-1".equals(addressResponse.getStatusCode())) {
			Builder builder = new Builder(maisprecoScreen);
			builder.setMessage(result);
			builder.setTitle("Error");
			builder.setCancelable(false);
			builder.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							new AddressAsyn(maisprecoScreen, store,
									addressViewControl, true, onClickListener)
									.execute("");
						}
					});
			builder.show();
		} else if (lAddresses.size() == 0) {
			Resources resources = maisprecoScreen.getResources();
			CommonView
					.makeText(maisprecoScreen, resources
							.getString(R.string.There_is_no_address_available));
		}

		handler.post(new Runnable() {
			public void run() {
				dialog.dismiss();
			}
		});
	}

}
